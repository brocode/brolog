package sh.brocode.brolog

import org.slf4j.spi.MDCAdapter
import java.util.Deque

class BroMDCAdapter : MDCAdapter {

    private val content = ThreadLocal<MutableMap<String, String?>>()

    override fun put(key: String?, `val`: String?) {
        key ?: return
        val mdc: MutableMap<String, String?>? = content.get()
        if (mdc != null) {
            mdc[key] = `val`
        } else {
            content.set(mutableMapOf(key to `val`))
        }
    }

    override fun get(key: String?): String? {
        key ?: return null
        val mdc: MutableMap<String, String?>? = content.get()
        return mdc?.get(key)
    }

    override fun remove(key: String?) {
        key ?: return
        val mdc: MutableMap<String, String?>? = content.get()
        if (mdc != null) {
            mdc.remove(key)
            if (mdc.isEmpty()) {
                content.remove()
            }
        }
    }

    override fun clear() {
        content.remove()
    }

    override fun getCopyOfContextMap(): MutableMap<String, String?>? {
        val mdc: MutableMap<String, String?>? = content.get()

        return mdc?.let { LinkedHashMap(it) }
    }

    override fun setContextMap(contextMap: MutableMap<String, String?>?) {
        if (contextMap == null) {
            content.remove()
        } else {
            content.set(LinkedHashMap(contextMap))
        }
    }

    override fun pushByKey(key: String?, value: String?) {
        TODO("Not yet implemented")
    }

    override fun popByKey(key: String?): String {
        TODO("Not yet implemented")
    }

    override fun getCopyOfDequeByKey(key: String?): Deque<String> {
        TODO("Not yet implemented")
    }

    override fun clearDequeByKey(key: String?) {
        TODO("Not yet implemented")
    }
}
