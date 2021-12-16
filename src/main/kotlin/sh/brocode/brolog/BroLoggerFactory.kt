package sh.brocode.brolog

import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class BroLoggerFactory : ILoggerFactory {
    private val simpleMode: Boolean =
        System.getenv("BROLOG_SIMPLE_MODE")?.toBoolean()
            ?: System.getProperty("brolog.simple.mode")?.toBoolean()
            ?: false

    override fun getLogger(name: String): Logger {
        return if (simpleMode) {
            SimpleBroLogger(
                loggerName = name,
                logLevel = LogLevel.TRACE,
            )
        } else {
            JsonBroLogger(
                loggerName = name,
                logLevel = LogLevel.TRACE,
            )
        }
    }
}
