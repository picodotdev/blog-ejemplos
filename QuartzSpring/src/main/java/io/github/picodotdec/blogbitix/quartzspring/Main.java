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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import org.quartz.JobBuilder;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableScheduling
public class Main implements ApplicationListener {

    private static final Logger logger = LogManager.getLogger(Main.class);

    @Autowired
    private JavaJob javaJob;

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

    @Bean
    public JavaJob javaJob() {
        return new JavaJob();
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartedEvent) {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
            scheduler.scheduleAtFixedRate(javaJob::jobWithFixedRate, 0, 2, TimeUnit.SECONDS);
            scheduler.scheduleWithFixedDelay(javaJob::jobWithDelay, 0, 2, TimeUnit.SECONDS);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
