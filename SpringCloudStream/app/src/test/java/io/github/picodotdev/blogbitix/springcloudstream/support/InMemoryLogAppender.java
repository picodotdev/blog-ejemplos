package io.github.picodotdev.blogbitix.springcloudstream.support;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;

/**
 * In-memory Log4j 2 appender that collects {@link LogEvent}s so tests can assert
 * on what was logged. Attach it to the root logger in a {@code @BeforeEach} and
 * detach it in an {@code @AfterEach}.
 */
public final class InMemoryLogAppender extends AbstractAppender {

    private static final String APPENDER_NAME = "InMemoryTestAppender";

    private final List<LogEvent> events = new CopyOnWriteArrayList<>();

    private InMemoryLogAppender() {
        super(APPENDER_NAME, null, null, true, Property.EMPTY_ARRAY);
    }

    @Override
    public void append(LogEvent event) {
        // LogEvent instances may be mutated/reused by the framework, so snapshot.
        events.add(event.toImmutable());
    }

    public List<LogEvent> events() {
        return events;
    }

    /** Creates, starts and attaches an appender to the root logger. */
    public static InMemoryLogAppender attachToRootLogger() {
        InMemoryLogAppender appender = new InMemoryLogAppender();
        appender.start();

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration configuration = context.getConfiguration();
        configuration.getRootLogger().addAppender(appender, Level.ALL, null);
        context.updateLoggers();
        return appender;
    }

    /** Detaches and stops this appender. */
    public void detachFromRootLogger() {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration configuration = context.getConfiguration();
        configuration.getRootLogger().removeAppender(APPENDER_NAME);
        context.updateLoggers();
        stop();
    }
}
