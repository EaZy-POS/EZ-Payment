/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.data;

import co.id.ez.deposit.enums.Fields;
import co.id.ez.deposit.resource.DepositHandler;
import co.id.ez.database.DB;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;
import co.id.ez.database.enums.OrderType;
import co.id.ez.database.query.QueryBuilder;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryOrder;
import co.id.ez.database.query.QueryType;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONArray;
import com.json.JSONObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 *
 * @author LUTFI
 */
public class Repository {

    private static final Repository loogingHook = new Repository();

    public static String getDepAccount(final JSONObject pRequest) {
        String tDash = "";
        try {
            tDash = pRequest.getString(Fields.das_id.name());
            QueryBuilder builder = new QueryBuilder("bal_account", QueryType.SELECT);
            builder.addEntry("id as account");
            builder.addWhereValue(new WhereField("das", tDash, Operator.EQUALS));
            builder.addWhereValue(new WhereField("status", "1", Operator.EQUALS, QueryConditional.AND));

            LinkedList<JSONObject> tResult = DB.executeQuery(DepositHandler.dbName, builder);

            if (tResult != null) {
                if (tResult.size() > 0) {
                    return tResult.getFirst().getString("account");
                }
            }

        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to Get Deposit Account", ex);
        }

        throw new ServiceException(RC.ERROR_UNREGISTERED_ACCOUNT, "Invalid Account with das ID[" + tDash + "]");
    }

    public static void insertDepAccount(final JSONObject pRequest, String pAccount) {
        String tDash;
        try {
            tDash = pRequest.getString(Fields.das_id.name());
            QueryBuilder builder = new QueryBuilder("bal_account", QueryType.INSERT);
            builder.addEntryValue("id", pAccount);
            builder.addEntryValue("das", tDash);
            builder.addEntryValue("created_at", "NOW()", true);
            builder.addEntryValue("status", "1", true);

            DB.executeUpdate(DepositHandler.dbName, builder);

            insertNewBalanceAccount(pAccount);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to Insert Deposit Account", ex);
        }

    }

    public static BigDecimal getBalanceAccount(String pAccount) {
        try {
            QueryBuilder builder = new QueryBuilder("bal_deposit", QueryType.SELECT);
            builder.addEntry("balance");
            builder.addWhereValue(new WhereField("account", pAccount, Operator.EQUALS));

            LinkedList<JSONObject> tResult = DB.executeQuery(DepositHandler.dbName, builder);

            if (tResult != null) {
                if (tResult.size() > 0) {
                    return new BigDecimal(tResult.getFirst().getDouble("balance"));
                }
            }

        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to getBalanceAccount", ex);
        }

        throw new ServiceException(RC.ERROR_UNREGISTERED_ACCOUNT,
                "Invalid Balance Account with account ID[" + pAccount + "]");
    }

    public static void insertNewBalanceAccount(String pAccount) {
        try {
            QueryBuilder builder = new QueryBuilder("bal_deposit", QueryType.INSERT);
            builder.addEntryValue("balance", "0");
            builder.addEntryValue("account", pAccount);

            DB.executeUpdate(DepositHandler.dbName, builder);
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to getBalanceAccount", ex);
        }
    }

    public static void insertJournal(String pAccount, String pJounal, String pRefnum, String pRemarks, BigDecimal pAmount, BigDecimal pBalance) {
        try {
            QueryBuilder builder = new QueryBuilder("trx_balance_history", QueryType.INSERT);
            builder.addEntryValue("id", "UUID()", true);
            builder.addEntryValue("transaction_date", "NOW()", true);
            builder.addEntryValue("account", pAccount);
            builder.addEntryValue("journal", pJounal);
            builder.addEntryValue("refnum", pRefnum);
            builder.addEntryValue("remarks", pRemarks);
            builder.addEntryValue("amount", pAmount.toPlainString());
            builder.addEntryValue("balance", pBalance.toPlainString());
            builder.addEntryValue("ending_balance",
                    (pJounal.contains("C")
                            ? pBalance.add(pAmount)
                            : pBalance.subtract(pAmount))
                            .toPlainString()
            );

            LogService.getInstance(loogingHook).db().log("Before insert journal");
            DB.executeUpdate(DepositHandler.dbName, builder);
            LogService.getInstance(loogingHook).db().log("After insert journal (Success)");
            updateBalance(pAccount, (pJounal.equals("C") ? "+" : "-") + pAmount.toPlainString());
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to insert journal", e);
        }
    }

    public static void updateBalance(String pAccount, String pAmount) {
        try {
            QueryBuilder builder = new QueryBuilder("bal_deposit", QueryType.UPDATE);
            builder.addEntryValue("updated_at", "NOW()", true);
            builder.addEntryValue("balance", "balance " + pAmount, true);
            builder.addWhereValue(new WhereField("account", pAccount, Operator.EQUALS));

            LogService.getInstance(loogingHook).db().log("Before update balance");
            DB.executeUpdate(DepositHandler.dbName, builder);
            LogService.getInstance(loogingHook).db().log("After update balance (success)");
        } catch (SQLException e) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to update balance", e);
        }
    }

    public static JSONArray getStatement(String pAccount, String pFrom, String pUntil) {
        JSONArray tData = new JSONArray();
        try {
            QueryBuilder builder = new QueryBuilder("trx_balance_history", QueryType.SELECT);
            builder.addEntry("DATE_FORMAT(transaction_date, '%Y-%m-%d %h:%i:%s') as date");
            builder.addEntry("journal");
            builder.addEntry("balance");
            builder.addEntry("amount");
            builder.addEntry("ending_balance");
            builder.addEntry("refnum as trxid");
            builder.addEntry("remarks");
            builder.addWhereValue(new WhereField("account", pAccount, Operator.EQUALS));
            builder.addWhereValue(new WhereField("transaction_date",
                    "'" + pFrom + " 00:00:00' AND '" + pUntil + " 23:59:59'",
                    Operator.BETWEEN, QueryConditional.AND, true));
            builder.setOrder(new QueryOrder(OrderType.DESC, "transaction_date"));

            LinkedList<JSONObject> tResult = DB.executeQuery(DepositHandler.dbName, builder);

            if (tResult != null) {
                if (tResult.size() > 0) {
                    tResult.forEach(jSONObject -> {
                        tData.put(jSONObject);
                    });

                    return tData;
                }
            }

        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "[SQLException] Failed to getStatement", ex);
        }

        return tData;
    }
}
