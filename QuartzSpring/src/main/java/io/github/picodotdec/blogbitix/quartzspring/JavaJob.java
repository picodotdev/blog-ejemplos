package io.github.picodotdec.blogbitix.quartzspring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaJob {

    private static final Logger logger = LogManager.getLogger(JavaJob.class);

    public void jobWithFixedRate() {
        logger.info("JavaJob: jobWithFixedRate");
    }

    public void jobWithDelay() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.info("JavaJob: jobWithDelay");
    }
}
