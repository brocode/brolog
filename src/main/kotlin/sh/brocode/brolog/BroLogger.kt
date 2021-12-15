package sh.brocode.brolog

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.slf4j.MDC
import org.slf4j.helpers.MarkerIgnoringBase
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
)

data class BroLogger(
    private val name: String,
    private val traceEnabled: Boolean,
    private val debugEnabled: Boolean,
    private val infoEnabled: Boolean,
    private val warnEnabled: Boolean,
    private val errorEnabled: Boolean,
) : MarkerIgnoringBase() {

    override fun getName(): String = name

    override fun isTraceEnabled(): Boolean {
        return traceEnabled
    }

    override fun trace(msg: String) {
        if (traceEnabled) {
            write(createEntry(msg, LogLevel.TRACE))
        }
    }

    override fun trace(format: String, arg: Any) {
    }

    override fun trace(format: String, arg1: Any, arg2: Any) {
    }

    override fun trace(format: String, vararg argArray: Any) {
    }

    override fun trace(msg: String, t: Throwable) {
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
    }

    override fun debug(format: String, arg1: Any, arg2: Any) {
    }

    override fun debug(format: String, vararg argArray: Any) {
    }

    override fun debug(msg: String, t: Throwable) {
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
    }

    override fun info(format: String, arg1: Any, arg2: Any) {
    }

    override fun info(format: String, vararg argArray: Any) {
    }

    override fun info(msg: String, t: Throwable) {
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
    }

    override fun warn(format: String, arg1: Any, arg2: Any) {
    }

    override fun warn(format: String, vararg argArray: Any) {
    }

    override fun warn(msg: String, t: Throwable) {
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
    }

    override fun error(format: String, arg1: Any, arg2: Any) {
    }

    override fun error(format: String, vararg argArray: Any) {
    }

    override fun error(msg: String, t: Throwable) {
    }

    private fun write(entry: LogEntry) {
        println(Json.encodeToString(entry))
    }

    private fun createEntry(msg: String, level: LogLevel): LogEntry {
        val mdc: MutableMap<String, String?>? = MDC.getCopyOfContextMap()

        return LogEntry(
            logger = name,
            time = Instant.now().toString(),
            message = msg,
            mdc = mdc,
            level = level,
        )
    }
}
