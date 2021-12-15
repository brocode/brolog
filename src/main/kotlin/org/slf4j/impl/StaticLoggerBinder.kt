package org.slf4j.impl

import org.slf4j.ILoggerFactory
import org.slf4j.spi.LoggerFactoryBinder
import sh.brocode.brolog.BroLoggerFactory

class StaticLoggerBinder private constructor() : LoggerFactoryBinder {
    /** The ILoggerFactory instance returned by the [.getLoggerFactory] method
     * should always be the same object
     */
    private val loggerFactory: ILoggerFactory

    init {
        loggerFactory = BroLoggerFactory()
    }

    override fun getLoggerFactory(): ILoggerFactory {
        return loggerFactory
    }

    override fun getLoggerFactoryClassStr(): String {
        return Companion.loggerFactoryClassStr
    }

    companion object {
        private val singleton = StaticLoggerBinder()

        @JvmStatic
        fun getSingleton(): StaticLoggerBinder = singleton

        /**
         * Declare the version of the SLF4J API this implementation is compiled against.
         * The value of this field is modified with each major release.
         */
        // to avoid constant folding by the compiler, this field must *not* be final
        @JvmField
        var REQUESTED_API_VERSION = "1.6.99" // !final

        private val loggerFactoryClassStr = BroLoggerFactory::class.java.name
    }
}
