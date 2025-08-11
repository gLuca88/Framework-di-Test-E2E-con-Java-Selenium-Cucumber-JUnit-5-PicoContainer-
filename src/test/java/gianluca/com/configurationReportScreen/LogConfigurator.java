package gianluca.com.configurationReportScreen;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.TimeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class LogConfigurator {

	public static void addRunFileAppender(Path logsDir) {
		LoggerContext ctx = (LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();

		String appenderName = "RunFile";
		String fileName = logsDir.resolve("execution.log").toString();
		String filePattern = logsDir.resolve("execution-%d{yyyy-MM-dd}-%i.log.gz").toString();

		PatternLayout layout = PatternLayout.newBuilder()
				.withPattern("[%d{yyyy-MM-dd HH:mm:ss.SSS}] %-5p [%t] %c - %m%n").withCharset(StandardCharsets.UTF_8)
				.withConfiguration(config).build();

		SizeBasedTriggeringPolicy sizePolicy = SizeBasedTriggeringPolicy.createPolicy("10 MB");
		TimeBasedTriggeringPolicy timePolicy = TimeBasedTriggeringPolicy.newBuilder().build();

		DefaultRolloverStrategy strategy = DefaultRolloverStrategy.newBuilder().withMax("10").withConfig(config)
				.build();

		RollingFileAppender appender = RollingFileAppender.newBuilder().withName(appenderName).withFileName(fileName)
				.withFilePattern(filePattern).withPolicy(CompositeTriggeringPolicy.createPolicy(timePolicy, sizePolicy))
				.withStrategy(strategy).withLayout(layout).withAppend(true).setConfiguration(config).build();

		appender.start();
		config.addAppender(appender);

		// Attacca al root logger
		LoggerConfig root = config.getRootLogger();
		root.addAppender(appender, null, null);

		ctx.updateLoggers();
	}
}
