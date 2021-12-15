package sh.brocode.brolog

import org.slf4j.ILoggerFactory
import org.slf4j.Logger

class BroLoggerFactory : ILoggerFactory {
    override fun getLogger(name: String): Logger {
        return BroLogger()
    }
}
