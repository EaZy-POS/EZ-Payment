/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler.scheduller;

import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.service.FutureService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author Lutfi
 */
public class VoucherPriceListScheduller extends FutureService{

    @Override
    public void run() {
        try {
            LogService.getInstance(this).temp("scheduller").log("Start initialize scheduller");
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
            JobDetail job = JobBuilder.newJob(GetVoucherListJob.class)
                    .withIdentity("GetVoucherList", "Voucher").build();
            
            String tCronName = ConfigService.getInstance()
                    .getString("crontab.config.cronname", "GetVoucherList");
            String tCronExpression = ConfigService.getInstance()
                    .getString("crontab.config.cronexpretion", "0 0/30 * * * ? *");

            Trigger triger = TriggerBuilder.newTrigger()
                    .withIdentity(tCronName, "Voucher")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule(tCronExpression)
                    ).build();
            scheduler.scheduleJob(job, triger);
            LogService.getInstance(this).temp("scheduller").log("Finish initialize scheduller");
        } catch (SchedulerException ex) {
            LogService.getInstance(this).temp("schedulle-error").withCause(ex).log("Failed Start Scheduller", true);
        } catch (Exception ex) {
            LogService.getInstance(this).temp("scheduller-error").withCause(ex).log("Failed Start Scheduller", true);
        }    
    }

    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
