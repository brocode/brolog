package sh.brocode.brolog
import org.slf4j.ILoggerFactory
import org.slf4j.IMarkerFactory
import org.slf4j.helpers.BasicMDCAdapter
import org.slf4j.helpers.BasicMarkerFactory
import org.slf4j.spi.MDCAdapter
import org.slf4j.spi.SLF4JServiceProvider

class BrologProvider : SLF4JServiceProvider {
    // to avoid constant folding by the compiler, this field must *not* be final
    companion object {
        @JvmStatic
        public val REQUESTED_API_VERSION = "2.0.0" // !final
    }

    private val markerFactory: IMarkerFactory = BasicMarkerFactory()
    private val mdcAdapter: MDCAdapter = BasicMDCAdapter()
    private val broLoggerFactory: ILoggerFactory = BroLoggerFactory()

    override fun getLoggerFactory(): ILoggerFactory = broLoggerFactory

    override fun getMarkerFactory(): IMarkerFactory = markerFactory

    override fun getMDCAdapter(): MDCAdapter = mdcAdapter

    override fun getRequestedApiVersion(): String = REQUESTED_API_VERSION

    override fun initialize() {
    }
}
