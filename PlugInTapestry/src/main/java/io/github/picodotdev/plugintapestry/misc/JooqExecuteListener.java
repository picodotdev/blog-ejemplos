package io.github.picodotdev.plugintapestry.misc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;

public class JooqExecuteListener extends DefaultExecuteListener {

    private static final Logger logger = LogManager.getLogger(JooqExecuteListener.class);

    private long start;
    private long end;

    @Override
    public void executeStart(ExecuteContext ctx) {
        start = System.nanoTime();
    }

    @Override
    public void executeEnd(ExecuteContext ctx) {
        end = System.nanoTime();
        logger.info("{} ({}ms)", ctx.sql(), getTime(start, end));
    }

    private long getTime(long start, long end) {
        return (end - start) / 1000;
    }
}
