-- deployment: Creating v_summary_sales...
CREATE VIEW v_summary_sales AS select 'Voucher' AS `module`,date_format(ifnull(`v_all_summary_daily`.`transaction_date`,curdate()),'%Y-%m-%d') AS `transaction_date`,ifnull(sum(`v_all_summary_daily`.`total_transaction`),0) AS `total_transaction`,ifnull(sum(`v_all_summary_daily`.`total_amount`),0) AS `total_amount` from `v_all_summary_daily` where (`v_all_summary_daily`.`module` = 'VOUCHER') group by `v_all_summary_daily`.`transaction_date` union select 'PLN Postpaid' AS `module`,date_format(ifnull(`v_all_summary_daily`.`transaction_date`,curdate()),'%Y-%m-%d') AS `transaction_date`,ifnull(sum(`v_all_summary_daily`.`total_transaction`),0) AS `total_transaction`,ifnull(sum(`v_all_summary_daily`.`total_amount`),0) AS `total_amount` from `v_all_summary_daily` where (`v_all_summary_daily`.`module` = 'POSPAID') group by `v_all_summary_daily`.`transaction_date` union select 'PLN Prepaid' AS `module`,date_format(ifnull(`v_all_summary_daily`.`transaction_date`,curdate()),'%Y-%m-%d') AS `transaction_date`,ifnull(sum(`v_all_summary_daily`.`total_transaction`),0) AS `total_transaction`,ifnull(sum(`v_all_summary_daily`.`total_amount`),0) AS `total_amount` from `v_all_summary_daily` where (`v_all_summary_daily`.`module` = 'PREPAID') group by `v_all_summary_daily`.`transaction_date` union select 'PDAM' AS `module`,date_format(ifnull(`v_all_summary_daily`.`transaction_date`,curdate()),'%Y-%m-%d') AS `transaction_date`,ifnull(sum(`v_all_summary_daily`.`total_transaction`),0) AS `total_transaction`,ifnull(sum(`v_all_summary_daily`.`total_amount`),0) AS `total_amount` from `v_all_summary_daily` where (`v_all_summary_daily`.`module` = 'PDAM') group by `v_all_summary_daily`.`transaction_date` union select 'Lainnya' AS `module`,date_format(ifnull(`v_all_summary_daily`.`transaction_date`,curdate()),'%Y-%m-%d') AS `transaction_date`,ifnull(sum(`v_all_summary_daily`.`total_transaction`),0) AS `total_transaction`,ifnull(sum(`v_all_summary_daily`.`total_amount`),0) AS `total_amount` from `v_all_summary_daily` where (`v_all_summary_daily`.`module` not in ('PDAM','POSPAID','PREPAID','VOUCHER')) group by `v_all_summary_daily`.`transaction_date`;


-- deployment: Creating ap_integration_config...
CREATE TABLE ap_integration_config (
    id char(50) NOT NULL,
    config text NOT NULL
);
ALTER TABLE ap_integration_config ADD PRIMARY KEY (id);

