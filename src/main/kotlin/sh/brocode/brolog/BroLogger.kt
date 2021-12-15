package sh.brocode.brolog

import org.slf4j.MDC
import org.slf4j.helpers.MarkerIgnoringBase

class BroLogger : MarkerIgnoringBase() {

    override fun getName(): String {
        return "NOP"
    }

    override fun isTraceEnabled(): Boolean {
        return false
    }

    override fun trace(msg: String) {
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
        return false
    }

    override fun debug(msg: String) {
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
        return false
    }

    override fun info(msg: String) {
        val mdc: MutableMap<String, String>? = MDC.getCopyOfContextMap()
        println("Bro message:")
        println(msg)
        println(mdc)
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
        return false
    }

    override fun warn(msg: String) {
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
        return false
    }

    override fun error(msg: String) {
    }

    override fun error(format: String, arg1: Any) {
    }

    override fun error(format: String, arg1: Any, arg2: Any) {
    }

    override fun error(format: String, vararg argArray: Any) {
    }

    override fun error(msg: String, t: Throwable) {
    }
}
