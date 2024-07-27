package co.id.ez.system.core.rc;

public enum RC {
    /**
     * Success. ResponseCode = 0000
     */
    SUCCESS("0000"),
    /**
     * Success. ResponseCode = 0000
     */
    SUCCESS_NOTCONFIRMED("1000"),
    /**
     * Error message for unregistered biller condition. ResponseCode = 0004
     */
    ERROR_UNREGISTERED_BILLER("0004"),
    /**
     * Error message for unhandled exception. ResponseCode = 0005
     */
    ERROR_OTHER("0005"),
    /**
     * Error message for blocked partner central condition. ResponseCode = 0006
     */
    ERROR_BLOCKED_CENTRAL("0006"),
    /**
     * Error message for blocked terminal. ResponseCode = 0007
     */
    ERROR_BLOCKED_TERMINAL("0007"),
    /**
     * Error message for invalid access time. ResponseCode = 0008
     */
    ERROR_INVALID_ACCESS_TIME("0008"),
    /**
     * Error message for inactive account condition. ResponseCode = 0009
     */
    ERROR_INACTIVE_ACCOUNT("0009"),
    /**
     * Error message for deleted account condition. ResponseCode = 0010
     */
    ERROR_ACCOUNT_ALREADY_DELETED("0010"),
    /**
     * Error mesage = User need to sign on first. ResponseCode = 0011
     */
    ERROR_NEED_TO_SIGN_ON("0011"),
    /**
     * Error mesage = User need to sign on first. ResponseCode = 0011
     */
    ERROR_ACCOUNT_ALREADY_SIGNED_ON("0021"),
    /**
     * Error message = Cannot reverse becase exceed time limit. ResponseCode =
     * 0012
     */
    ERROR_CANNOT_REVERSE("0012"),
    /**
     * Error messafe = Invalid transaction amount. Maybe transactiom amount on
     * the token unsold purchase doesn't match the given unsold token.
     * ResponseCode = 0013
     */
    ERROR_INVALID_TRANSACTION_AMOUNT("0013"),
    /**
     * Error message for unknown subscriber condition. ResponseCode = 0014
     */
    ERROR_UNKNOWN_SUBSCRIBER("0014"),
    /**
     * Error message = Unknown Meter serial number. ResponseCode = 0015
     */
    ERROR_UNREGISTERED_METER("0015"),
    /**
     * Error message = Subscriber who has bad debts / PRR(Piutang Ragu-Ragu).
     * ResponseCode = 0016
     */
    ERROR_PRR_SUBSCRIBER("0016"),
    /**
     * Error message = Subscriber has debt. ResponseCode = 0017
     */
    ERROR_SUBSCRIBER_HAS_DEBT("0017"),
    /**
     * Error message = now request is being processed. ResponseCode = 0018
     */
    ERROR_REQUEST_IS_BEING_PROCESSED("0018"),
    /**
     * Error message = Unknown Application. ResponseCode = 0019
     */
    ERROR_UNKNOWN_APPLICATION("0019"),
    /**
     * Error message = Invalid Key. ResponseCode = 0020
     */
    ERROR_INVALID_KEY("0020"),
    /**
     * Error message = Insufficient privilege. ResponseCode = 0025
     */
    ERROR_INSUFFICIENT_PRIVILEGE("0025"),
    /**
     * Error message = Insufficient quota. ResponseCode = 0026
     */
    ERROR_INSUFFICIENT_QUOTA("0026"),
    /**
     * Error message = invalid hashcode. ResponseCode = 0029
     */
    ERROR_INVALID_HASHCODE("0029"),
    /**
     * Error message = Invalid message caused by unmatch type. ResponseCode =
     * 0030
     */
    ERROR_INVALID_MESSAGE("0030"),
    /**
     * Error message = Unregistered bank code. ResponseCode = 0031
     */
    ERROR_UNREGISTERED_BANK_CODE("0031"),
    /**
     * Error message = Unregistered partner central. ResponseCode = 0032
     */
    ERROR_UNREGISTERED_PARTNER_CENTRAL("0032"),
    /**
     * Error message = Unregistered product. ResponseCode = 0033
     */
    ERROR_UNREGISTERED_PRODUCT("0033"),
    /**
     * Error message = Unregistered terminal. ResponseCode = 0034
     */
    ERROR_UNREGISTERED_TERMINAL("0034"),
    /**
     * Error message = Unregistered account. ResponseCode = 0035
     */
    ERROR_UNREGISTERED_ACCOUNT("0035"),
    /**
     * Error message = Payment cannot be done online, only direct payment
     * allowed
     */
    ERROR_DIRECT_PAYMENT_ONLY("0040"),
    /**
     * Error message = transaction amount is below minimum. ResponseCode = 0041
     */
    ERROR_TRANSACTION_BELOW_MINIMUM("0041"),
    /**
     * Error message = transaction amount is exceed maximum. ResponseCode = 0042
     */
    ERROR_TRANSACTION_ABOVE_MAXIMUM("0042"),
    /**
     * Error message = new power purchased is less than current power.
     * ResponseCode = 0043
     */
    ERROR_NEW_POWER_LESS_THAN_CURRENT_POWER("0043"),
    /**
     * Error message = invalid power value. ResponseCode = 0044
     */
    ERROR_INVALID_POWER_VALUE("0044"),
    /**
     * Error message = invalid administration charges amount. ResponseCode =
     * 0045
     */
    ERROR_INVALID_ADMIN_CHARGES("0045"),
    /**
     * Error message = deposit amount is not enough. ResponseCode = 0046
     */
    ERROR_INSUFFICIENT_DEPOSIT("0046"),
    /**
     * Error message = over limit kilo Watt per hour usage. ResponseCode = 0047
     */
    ERROR_OVER_KWH_LIMIT("0047"),
    /**
     * Error message = request has already expired. ResponseCode = 0048
     */
    ERROR_REQUEST_EXPIRED("0048"),
    /**
     * Error message = transaction failed from vending. ResponseCode = 0051
     */
    ERROR_TRANSACTION_FAILED_FROM_VENDING("0051"),
    /**
     * Error message = transaction pending from vending. ResponseCode = 0052
     */
    ERROR_TRANSACTION_PENDING_FROM_VENDING("0052"),
    /**
     * Error message = product is not found. ResponseCode = 0053
     */
    ERROR_PRODUCT_NOT_FOUND("0053"),
    /**
     * Error message = Biller response is not found. ResponseCode = 0054
     */
    ERROR_BILLER_RESPONSE_NOT_FOUND("0054"),
    /**
     * Error message = Unkown error from vending. ResponseCode = 0055
     */
    ERROR_UNKNOWN_FROM_VENDING("0055"),
    /**
     * Error message = unknown mobile station number. ResponseCode = 0056
     */
    ERROR_UNKNOWN_MOBILE_STATION_NUMBER("0056"),
    /**
     * Error message = no payment happen. ResponseCode = 0063
     */
    ERROR_NO_PAYMENT("0063"),
    /**
     * Error message = account is already registered. ResponseCode = 0063
     */
    ERROR_ACCOUNT_ALREADY_REGISTERED("0066"),
    /**
     * Error message = cannot connect. ResponseCode = 0067
     */
    ERROR_CANNOT_CONNECT("0067"),
    /**
     * Error message = Request time out. It can be caused by timeout connection
     * to database ResponseCode = 0068
     */
    ERROR_TIMEOUT("0068"),
    /**
     * Error message = unknown certificate. ResponseCode = 0069
     */
    ERROR_UNKNOWN_CERTIFICATE("0069"),
    /**
     * Error message = timeout no refund. ResponseCode = 0070
     */
    ERROR_TIMEOUT_NOREFUND("0070"), // Internal use only
    /**
     * Error message = request not feasible. ResponseCode = 0072
     */
    ERROR_REQUEST_NOT_FEASIBLE("0072"),
    /**
     * Error message = request pending from biller. ResponseCode = 0073
     */
    ERROR_REQUEST_PENDING_FROM_BILLER("0073"),
    /**
     * Error message = subscriber suspended one of the cause is no voucher
     * purchase for over 3 months or has praqtis. ResponseCode = 0077
     */
    ERROR_SUBSCRIBER_SUSPENDED("0077"),
    /**
     * Error message = bill already been paid, including current month.
     * ResponseCode = 0088
     */
    ERROR_BILL_ALREADY_PAID("0088"),
    /**
     * Error message = current month bill is not available yet. ResponseCode =
     * 0089
     */
    ERROR_BILL_UNAVAILABLE("0089"),
    /**
     * Error message = Cut-off is in progress, so system cannot handle the
     * incoming request message yet. ResponseCode = 0090
     */
    ERROR_CUT_OFF("0090"),
    /**
     * Error message = Error database. ResponseCode = 0091
     */
    ERROR_DATABASE("0091"),
    /**
     * Error message = Switcher reference number is empty or is not available.
     * ResponseCode = 0092
     */
    ERROR_SREFNUM_NOT_AVAIL("0092"),
    /**
     * Error message = Invalid switcher reference number. ResponseCode = 0093
     */
    ERROR_INVALID_SWITCHER_REF_NUMBER("0093"),
    /**
     * Error message = Reverse has already been done. ResponseCode = 0094
     */
    ERROR_REVERSAL_HAD_BEEN_DONE("0094"),
    /**
     * Error message = Merchant type is not registered. ResponseCode = 0095
     */
    ERROR_UNREGISTERED_MERCHANT_TYPE("0095"),
    /**
     * Error message = Transaction requested is not found. ResponseCode = 0096
     */
    ERROR_TRANSACTION_NOT_FOUND("0096"),
    /**
     * Error message = invalid date time or STAN or Bank Code or MTI from
     * original data element message. ResponseCode = 0097
     */
    ERROR_NOT_IDENTICAL_SW_BANK("0097"),
    /**
     * Error message can be caused by Invalid Gateway reference number or no
     * record found in PLN reference number/invalid PLN reference number.
     * ResponseCode = 0098
     */
    ERROR_INVALID_SWITCHING_REFNUM("0098"),
    /**
     * Error message for inactive token/otp condition. ResponseCode = 0109
     */
    ERROR_INACTIVE_OTP("0109"),
    /**
     * Error relation already exist Response Code = 0111
     */
    ERROR_RELATION_IS_NOT_VALID("0111"),
    /**
     * Error in service deposit. Response Code = 0146
     */
    ERROR_IN_SERVICE_DEPOSIT("0146"),
    /**
     * Error product not available Response Code = 1053
     */
    ERROR_PRODUCT_NOT_AVAILABLE("1053"),
    /**
     * Error Invalid Parameter Response Code = 1053
     */
    ERROR_INVALID_PARAMETER("1030");

    /**
     * revision number
     */
    public static final String cRevisionNumber = "$Revision: 8386 $";

    /**
     * Response code in string
     */
    private String mResponseCode;

    /**
     * Construct response code
     *
     * @param pResponseCode
     */
    private RC(String pResponseCode) {
        mResponseCode = pResponseCode;
    }

    /**
     * Get RC
     *
     * @return RC in string
     */
    public String getResponseCodeString() {
        return mResponseCode;
    }

    /**
     * Parse input string to appropriate <code>RC</code>
     *
     * @param pTypeString String to be parsed to RC
     * @return appropriate RC
     */
    public static RC parseResponseCodeString(String pTypeString) {
        RC tRetResponseCode = null;
        for (RC tResponseCode : values()) {
            if (tResponseCode.getResponseCodeString().equals(pTypeString)) {
                tRetResponseCode = tResponseCode;
                break;
            }
        }
        return tRetResponseCode;
    }
}
