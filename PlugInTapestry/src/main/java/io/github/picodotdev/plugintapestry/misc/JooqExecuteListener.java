package io.github.picodotdev.plugintapestry.misc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;

import java.math.BigDecimal;
import java.math.MathContext;

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

    private String getTime(long start, long end) {
        return new BigDecimal(end - start, new MathContext(4)).divide(new BigDecimal("1000000")).toString();
    }
}
