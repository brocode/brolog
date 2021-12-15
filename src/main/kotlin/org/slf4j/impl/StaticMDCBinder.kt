package org.slf4j.impl

import org.slf4j.spi.MDCAdapter
import sh.brocode.brolog.BroMDCAdapter

class StaticMDCBinder private constructor() {
    fun getMDCA(): MDCAdapter {
        return BroMDCAdapter()
    }

    fun getMDCAdapterClassStr(): String {
        return BroMDCAdapter::class.java.name
    }

    companion object {
        private val singleton = StaticMDCBinder()

        @JvmStatic
        fun getSingleton(): StaticMDCBinder = singleton
    }
}
