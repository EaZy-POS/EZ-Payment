/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.deposit.resource;

import co.id.ez.database.DBService;
import co.id.ez.system.service.FutureService;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

/**
 *
 * @author RCS
 */
public class MigrationRunner extends FutureService {

    @Override
    public void execute() {
        HikariDataSource dataSource = DBService.getDataSource(DepositHandler.dbName);
        Flyway flyway = Flyway
                .configure()
                .dataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword())
                .baselineOnMigrate(true)
                .load();
        flyway.setLocations("db/migration");
        flyway.migrate();
    }

}
