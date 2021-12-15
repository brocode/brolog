package sh.brocode.brolog

import org.slf4j.ILoggerFactory
import org.slf4j.Logger
import org.slf4j.helpers.NOPLogger

class BroLoggerFactory : ILoggerFactory {
    override fun getLogger(name: String): Logger {
        return NOPLogger.NOP_LOGGER
    }
}
