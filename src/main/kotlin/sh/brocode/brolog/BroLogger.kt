package sh.brocode.brolog

import org.slf4j.Logger
import org.slf4j.MDC
import org.slf4j.Marker
import org.slf4j.helpers.MessageFormatter
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets
import java.time.Instant

abstract class BroLogger(
    private val name: String,
    private val logLevel: LogLevel
) : Logger {

    override fun getName(): String = this.name

    override fun isTraceEnabled(): Boolean {
        return logLevel.traceEnabled
    }

    override fun trace(msg: String) {
        if (logLevel.traceEnabled) {
            write(createEntry(msg, LogLevel.TRACE))
        }
    }

    override fun trace(format: String, arg: Any) {
        if (isTraceEnabled) {
            write(createEntry(format, LogLevel.TRACE, arrayOf(arg)))
        }
    }

    override fun trace(format: String, arg1: Any, arg2: Any) {
        if (isTraceEnabled) {
            write(createEntry(format, LogLevel.TRACE, arrayOf(arg1, arg2)))
        }
    }

    override fun trace(format: String, vararg argArray: Any) {
        if (isTraceEnabled) {
            write(createEntry(format, LogLevel.TRACE, argArray))
        }
    }

    override fun trace(msg: String, t: Throwable) {
        if (isTraceEnabled) {
            write(createEntry(msg, LogLevel.TRACE, t))
        }
    }

    override fun isDebugEnabled(): Boolean {
        return logLevel.debugEnabled
    }

    override fun debug(msg: String) {
        if (isDebugEnabled) {
            write(createEntry(msg, LogLevel.DEBUG))
        }
    }

    override fun debug(format: String, arg: Any) {
        if (isDebugEnabled) {
            write(createEntry(format, LogLevel.DEBUG, arrayOf(arg)))
        }
    }

    override fun debug(format: String, arg1: Any, arg2: Any) {
        if (isDebugEnabled) {
            write(createEntry(format, LogLevel.DEBUG, arrayOf(arg1, arg2)))
        }
    }

    override fun debug(format: String, vararg argArray: Any) {
        if (isDebugEnabled) {
            write(createEntry(format, LogLevel.DEBUG, argArray))
        }
    }

    override fun debug(msg: String, t: Throwable) {
        if (isDebugEnabled) {
            write(createEntry(msg, LogLevel.DEBUG, t))
        }
    }

    override fun isInfoEnabled(): Boolean {
        return logLevel.infoEnabled
    }

    override fun info(msg: String) {
        if (isInfoEnabled) {
            write(createEntry(msg, LogLevel.INFO))
        }
    }

    override fun info(format: String, arg1: Any) {
        if (isInfoEnabled) {
            write(createEntry(format, LogLevel.INFO, arrayOf(arg1)))
        }
    }

    override fun info(format: String, arg1: Any, arg2: Any) {
        if (isInfoEnabled) {
            write(createEntry(format, LogLevel.INFO, arrayOf(arg1, arg2)))
        }
    }

    override fun info(format: String, vararg argArray: Any) {
        if (isInfoEnabled) {
            write(createEntry(format, LogLevel.INFO, argArray))
        }
    }

    override fun info(msg: String, t: Throwable) {
        if (isInfoEnabled) {
            write(createEntry(msg, LogLevel.INFO, t))
        }
    }

    override fun isWarnEnabled(): Boolean {
        return logLevel.warnEnabled
    }

    override fun warn(msg: String) {
        if (isWarnEnabled) {
            write(createEntry(msg, LogLevel.WARN))
        }
    }

    override fun warn(format: String, arg1: Any) {
        if (isWarnEnabled) {
            write(createEntry(format, LogLevel.WARN, arrayOf(arg1)))
        }
    }

    override fun warn(format: String, arg1: Any, arg2: Any) {
        if (isWarnEnabled) {
            write(createEntry(format, LogLevel.WARN, arrayOf(arg1, arg2)))
        }
    }

    override fun warn(format: String, vararg argArray: Any) {
        if (isWarnEnabled) {
            write(createEntry(format, LogLevel.WARN, argArray))
        }
    }

    override fun warn(msg: String, t: Throwable) {
        if (isWarnEnabled) {
            write(createEntry(msg, LogLevel.WARN, t))
        }
    }

    override fun isErrorEnabled(): Boolean {
        return logLevel.errorEnabled
    }

    override fun error(msg: String) {
        if (isErrorEnabled) {
            write(createEntry(msg, LogLevel.ERROR))
        }
    }

    override fun error(format: String, arg1: Any) {
        if (isErrorEnabled) {
            write(createEntry(format, LogLevel.ERROR, arrayOf(arg1)))
        }
    }

    override fun error(format: String, arg1: Any, arg2: Any) {
        if (isErrorEnabled) {
            write(createEntry(format, LogLevel.ERROR, arrayOf(arg1, arg2)))
        }
    }

    override fun error(format: String, vararg argArray: Any) {
        if (isErrorEnabled) {
            write(createEntry(format, LogLevel.ERROR, argArray))
        }
    }

    override fun error(msg: String, t: Throwable) {
        if (isErrorEnabled) {
            write(createEntry(msg, LogLevel.ERROR, t))
        }
    }

    protected abstract fun write(entry: LogEntry)

    override fun isTraceEnabled(marker: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun trace(marker: Marker?, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun trace(marker: Marker?, format: String?, arg: Any?) {
        TODO("Not yet implemented")
    }

    override fun trace(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        TODO("Not yet implemented")
    }

    override fun trace(marker: Marker?, format: String?, vararg argArray: Any?) {
        TODO("Not yet implemented")
    }

    override fun trace(marker: Marker?, msg: String?, t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun isDebugEnabled(marker: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun debug(marker: Marker?, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun debug(marker: Marker?, format: String?, arg: Any?) {
        TODO("Not yet implemented")
    }

    override fun debug(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        TODO("Not yet implemented")
    }

    override fun debug(marker: Marker?, format: String?, vararg arguments: Any?) {
        TODO("Not yet implemented")
    }

    override fun debug(marker: Marker?, msg: String?, t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun isInfoEnabled(marker: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun info(marker: Marker?, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun info(marker: Marker?, format: String?, arg: Any?) {
        TODO("Not yet implemented")
    }

    override fun info(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        TODO("Not yet implemented")
    }

    override fun info(marker: Marker?, format: String?, vararg arguments: Any?) {
        TODO("Not yet implemented")
    }

    override fun info(marker: Marker?, msg: String?, t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun isWarnEnabled(marker: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun warn(marker: Marker?, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun warn(marker: Marker?, format: String?, arg: Any?) {
        TODO("Not yet implemented")
    }

    override fun warn(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        TODO("Not yet implemented")
    }

    override fun warn(marker: Marker?, format: String?, vararg arguments: Any?) {
        TODO("Not yet implemented")
    }

    override fun warn(marker: Marker?, msg: String?, t: Throwable?) {
        TODO("Not yet implemented")
    }

    override fun isErrorEnabled(marker: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun error(marker: Marker?, msg: String?) {
        TODO("Not yet implemented")
    }

    override fun error(marker: Marker?, format: String?, arg: Any?) {
        TODO("Not yet implemented")
    }

    override fun error(marker: Marker?, format: String?, arg1: Any?, arg2: Any?) {
        TODO("Not yet implemented")
    }

    override fun error(marker: Marker?, format: String?, vararg arguments: Any?) {
        TODO("Not yet implemented")
    }

    override fun error(marker: Marker?, msg: String?, t: Throwable?) {
        TODO("Not yet implemented")
    }

    private fun createEntry(msg: String, level: LogLevel): LogEntry {
        val mdc: MutableMap<String, String?>? = MDC.getCopyOfContextMap()

        return LogEntry(
            logger = name,
            time = Instant.now().toString(),
            message = msg,
            mdc = mdc,
            level = level,
            exception = null
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
            exception = formattedException
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
            exception = formattedException
        )
    }

    private fun formatException(t: Throwable): String {
        val out = ByteArrayOutputStream()
        t.printStackTrace(PrintStream(out))
        return String(out.toByteArray(), StandardCharsets.UTF_8)
    }
}
