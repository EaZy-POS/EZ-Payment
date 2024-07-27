/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.database.DBService;
import co.id.ez.ezpay.abstracts.AbstractModule;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;

/**
 *
 * @author RCS
 */
public class MigrationManager {

    public void migrate() {
        try {
            HikariDataSource dataSource = DBService.getDataSource(AbstractModule.dbName);
            Flyway flyway = Flyway
                    .configure()
                    .dataSource(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword())
                    .baselineOnMigrate(true)
                    .load();
            flyway.migrate();
        } catch (FlywayException e) {
            Common.errorLog("[FlywayException] Failed migrate database", e);
        }
    }

    private static class Holder {
        private static final MigrationManager instance = new MigrationManager();
    }

    public static MigrationManager instance() {
        return Holder.instance;
    }
}
