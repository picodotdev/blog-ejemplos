package io.github.picodotdec.blogbitix.quartzspring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class QuartzJob implements Job {

    private static final Logger logger = LogManager.getLogger(QuartzJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("Job: QuartzJob");
    }
}