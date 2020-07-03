package io.github.picodotdec.blogbitix.quartzspring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class QuartzJobListener implements JobListener {

    private static final Logger logger = LogManager.getLogger(QuartzJobListener.class);

    @Override
    public String getName() {
        return "QuartzJobListener";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        logger.info("QuartzJobListener: jobToBeExecuted. Job: {}, Trigger: {}", context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        logger.info("QuartzJobListener: jobExecutionVetoed. Job: {}, Trigger: {}", context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        logger.info("QuartzJobListener: jobWasExecuted. Job: {}, Trigger: {}", context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName());
    }
}