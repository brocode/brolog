package sh.brocode.brolog

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.MDC
import org.slf4j.helpers.MarkerIgnoringBase
import org.slf4j.helpers.MessageFormatter
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.time.Instant

enum class LogLevel {
    TRACE,
    DEBUG,
    INFO,
    WARN,
    ERROR,
}

@Serializable
data class LogEntry(
    val time: String,
    val logger: String,
    val message: String,
    val mdc: Map<String, String?>?,
    val level: LogLevel,
    val exception: String?,
)

class JsonBroLogger(
    loggerName: String,
    traceEnabled: Boolean,
    debugEnabled: Boolean,
    infoEnabled: Boolean,
    warnEnabled: Boolean,
    errorEnabled: Boolean,
) : BroLogger(
    loggerName = loggerName,
    traceEnabled = traceEnabled,
    debugEnabled = debugEnabled,
    infoEnabled = infoEnabled,
    warnEnabled = warnEnabled,
    errorEnabled = errorEnabled,
) {

    override fun write(entry: LogEntry) {
        println(Json.encodeToString(entry))
    }
}

class SimpleBroLogger(
    loggerName: String,
    traceEnabled: Boolean,
    debugEnabled: Boolean,
    infoEnabled: Boolean,
    warnEnabled: Boolean,
    errorEnabled: Boolean,
) : BroLogger(
    loggerName = loggerName,
    traceEnabled = traceEnabled,
    debugEnabled = debugEnabled,
    infoEnabled = infoEnabled,
    warnEnabled = warnEnabled,
    errorEnabled = errorEnabled,
) {

    override fun write(entry: LogEntry) {
        val output = """
            |# ${entry.level} ${entry.logger}
            |${entry.message}
        """.trimMargin()

        if (entry.exception != null) {
            println(output + "\n" + entry.exception)
        } else {
            println(output)
        }
    }
}

abstract class BroLogger(
    loggerName: String,
    private val traceEnabled: Boolean,
    private val debugEnabled: Boolean,
    private val infoEnabled: Boolean,
    private val warnEnabled: Boolean,
    private val errorEnabled: Boolean,
) : MarkerIgnoringBase() {

    init {
        super.name = loggerName
    }

    override fun isTraceEnabled(): Boolean {
        return traceEnabled
    }

    override fun trace(msg: String) {
        if (traceEnabled) {
            write(createEntry(msg, LogLevel.TRACE))
        }
    }

    override fun trace(format: String, arg: Any) {
        if (traceEnabled) {
            write(createEntry(format, LogLevel.TRACE, arrayOf(arg)))
        }
    }

    override fun trace(format: String, arg1: Any, arg2: Any) {
        if (traceEnabled) {
            write(createEntry(format, LogLevel.TRACE, arrayOf(arg1, arg2)))
        }
    }

    override fun trace(format: String, vararg argArray: Any) {
        if (traceEnabled) {
            write(createEntry(format, LogLevel.TRACE, argArray))
        }
    }

    override fun trace(msg: String, t: Throwable) {
        if (traceEnabled) {
            write(createEntry(msg, LogLevel.TRACE, t))
        }
    }

    override fun isDebugEnabled(): Boolean {
        return debugEnabled
    }

    override fun debug(msg: String) {
        if (debugEnabled) {
            write(createEntry(msg, LogLevel.DEBUG))
        }
    }

    override fun debug(format: String, arg: Any) {
        if (debugEnabled) {
            write(createEntry(format, LogLevel.DEBUG, arrayOf(arg)))
        }
    }

    override fun debug(format: String, arg1: Any, arg2: Any) {
        if (debugEnabled) {
            write(createEntry(format, LogLevel.DEBUG, arrayOf(arg1, arg2)))
        }
    }

    override fun debug(format: String, vararg argArray: Any) {
        if (debugEnabled) {
            write(createEntry(format, LogLevel.DEBUG, argArray))
        }
    }

    override fun debug(msg: String, t: Throwable) {
        if (debugEnabled) {
            write(createEntry(msg, LogLevel.DEBUG, t))
        }
    }

    override fun isInfoEnabled(): Boolean {
        return infoEnabled
    }

    override fun info(msg: String) {
        if (infoEnabled) {
            write(createEntry(msg, LogLevel.INFO))
        }
    }

    override fun info(format: String, arg1: Any) {
        if (infoEnabled) {
            write(createEntry(format, LogLevel.INFO, arrayOf(arg1)))
        }
    }

    override fun info(format: String, arg1: Any, arg2: Any) {
        if (infoEnabled) {
            write(createEntry(format, LogLevel.INFO, arrayOf(arg1, arg2)))
        }
    }

    override fun info(format: String, vararg argArray: Any) {
        if (infoEnabled) {
            write(createEntry(format, LogLevel.INFO, argArray))
        }
    }

    override fun info(msg: String, t: Throwable) {
        if (infoEnabled) {
            write(createEntry(msg, LogLevel.INFO, t))
        }
    }

    override fun isWarnEnabled(): Boolean {
        return warnEnabled
    }

    override fun warn(msg: String) {
        if (warnEnabled) {
            write(createEntry(msg, LogLevel.WARN))
        }
    }

    override fun warn(format: String, arg1: Any) {
        if (warnEnabled) {
            write(createEntry(format, LogLevel.WARN, arrayOf(arg1)))
        }
    }

    override fun warn(format: String, arg1: Any, arg2: Any) {
        if (warnEnabled) {
            write(createEntry(format, LogLevel.WARN, arrayOf(arg1, arg2)))
        }
    }

    override fun warn(format: String, vararg argArray: Any) {
        if (warnEnabled) {
            write(createEntry(format, LogLevel.WARN, argArray))
        }
    }

    override fun warn(msg: String, t: Throwable) {
        if (warnEnabled) {
            write(createEntry(msg, LogLevel.WARN, t))
        }
    }

    override fun isErrorEnabled(): Boolean {
        return errorEnabled
    }

    override fun error(msg: String) {
        if (errorEnabled) {
            write(createEntry(msg, LogLevel.ERROR))
        }
    }

    override fun error(format: String, arg1: Any) {
        if (errorEnabled) {
            write(createEntry(format, LogLevel.ERROR, arrayOf(arg1)))
        }
    }

    override fun error(format: String, arg1: Any, arg2: Any) {
        if (errorEnabled) {
            write(createEntry(format, LogLevel.ERROR, arrayOf(arg1, arg2)))
        }
    }

    override fun error(format: String, vararg argArray: Any) {
        if (errorEnabled) {
            write(createEntry(format, LogLevel.ERROR, argArray))
        }
    }

    override fun error(msg: String, t: Throwable) {
        if (errorEnabled) {
            write(createEntry(msg, LogLevel.ERROR, t))
        }
    }

    protected abstract fun write(entry: LogEntry)

    private fun createEntry(msg: String, level: LogLevel): LogEntry {
        val mdc: MutableMap<String, String?>? = MDC.getCopyOfContextMap()

        return LogEntry(
            logger = name,
            time = Instant.now().toString(),
            message = msg,
            mdc = mdc,
            level = level,
            exception = null,
        )
    }

    private fun createEntry(format: String, level: LogLevel, argArray: Array<out Any>): LogEntry {
        val mdc: MutableMap<String, String?>? = MDC.getCopyOfContextMap()

        val formattedMessage = MessageFormatter.arrayFormat(format, argArray)
        val throwable: Throwable? = formattedMessage.throwable
        val formattedException = throwable?.let(::formatException)

        return LogEntry(
            logger = name,
            time = Instant.now().toString(),
            message = formattedMessage.message,
            mdc = mdc,
            level = level,
            exception = formattedException,
        )
    }

    private fun createEntry(msg: String, level: LogLevel, throwable: Throwable): LogEntry {
        val mdc: MutableMap<String, String?>? = MDC.getCopyOfContextMap()

        val formattedException = formatException(throwable)

        return LogEntry(
            logger = name,
            time = Instant.now().toString(),
            message = msg,
            mdc = mdc,
            level = level,
            exception = formattedException,
        )
    }

    private fun formatException(t: Throwable): String {
        val out = ByteArrayOutputStream()
        t.printStackTrace(PrintStream(out))
        return String(out.toByteArray(), StandardCharsets.UTF_8)
    }
}
