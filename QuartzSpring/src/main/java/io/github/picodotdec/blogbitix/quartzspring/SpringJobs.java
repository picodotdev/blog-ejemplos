package io.github.picodotdec.blogbitix.quartzspring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringJobs {

    private static final Logger logger = LogManager.getLogger(SpringJobs.class);

    @Scheduled(fixedRate = 2000)
    public void scheduleJobWithFixedRate() {
        logger.info("SpringJob: scheduleJobWithFixedRate");
    }


    @Scheduled(fixedDelay = 2000)
    public void scheduleJobWithDelay() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("SpringJob: scheduleJobWithDelay");
    }

    @Scheduled(cron = "0 * * * * ?")
    public void scheduleJobWithCron() {
        logger.info("SpringJob: scheduleJobWithCron");
    }
}