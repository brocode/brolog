package sh.brocode.brolog

import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class BroLoggerFactory : ILoggerFactory {
    private val simpleMode: Boolean =
        System.getenv("BROLOG_SIMPLE_MODE")?.toBoolean()
            ?: System.getProperty("brolog.simple.mode")?.toBoolean()
            ?: false

    override fun getLogger(name: String): Logger {
        if (simpleMode) {
            return SimpleBroLogger(
                loggerName = name,
                traceEnabled = true,
                debugEnabled = true,
                infoEnabled = true,
                warnEnabled = true,
                errorEnabled = true,
            )
        } else {
            return JsonBroLogger(
                loggerName = name,
                traceEnabled = true,
                debugEnabled = true,
                infoEnabled = true,
                warnEnabled = true,
                errorEnabled = true,
            )
        }
    }
}
