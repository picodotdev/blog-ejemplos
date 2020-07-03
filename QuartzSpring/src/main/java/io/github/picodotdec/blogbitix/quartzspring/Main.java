package io.github.picodotdec.blogbitix.quartzspring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;

import org.quartz.JobBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    @Bean(name = "QuartzJob")
    public JobDetail quartzJob() {
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity("QuartzJob", "QuartzJobs")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger quartzTrigger(@Qualifier("QuartzJob") JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity("QuartzTrigger", "QuartzJobs")
            .withDescription("Quartz trigger")
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInSeconds(10))
            .build();
    }

    @Bean
    public Trigger cronQuartzTrigger(@Qualifier("QuartzJob") JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("CronQuartzTrigger", "QuartzJobs")
                .withDescription("Cron Quartz trigger")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?"))
                .build();
    }

    @Bean(name = "QuartzJobListener")
    public JobListener quartzListener() {
        return new QuartzJobListener();
    }

    @Bean
    public SchedulerFactoryBeanCustomizer schedulerConfiguration(@Qualifier("QuartzJobListener") JobListener listener) {
        return bean -> {
            bean.setGlobalJobListeners(listener);
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
