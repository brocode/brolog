package sh.brocode.brolog

import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class BroLoggerFactory : ILoggerFactory {
    companion object {
        private val simpleMode: Boolean =
            System.getenv("BROLOG_SIMPLE_MODE")?.toBoolean()
                ?: System.getProperty("brolog.simple.mode")?.toBoolean()
                ?: false

        private val settings: LogLevelSettings = LogLevelSettings.loadFromEnv()
    }

    override fun getLogger(name: String): Logger {
        val level = settings.getLoggerLevel(name)
        return if (simpleMode) {
            SimpleBroLogger(
                loggerName = name,
                logLevel = level,
            )
        } else {
            JsonBroLogger(
                loggerName = name,
                logLevel = level,
            )
        }
    }
}
