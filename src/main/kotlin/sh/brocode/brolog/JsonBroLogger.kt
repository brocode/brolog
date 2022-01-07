package sh.brocode.brolog

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonBroLogger(
    loggerName: String,
    logLevel: LogLevel,
) : BroLogger(
    loggerName = loggerName,
    logLevel = logLevel,
) {
    companion object {
        @OptIn(ExperimentalSerializationApi::class)
        private val json = Json { explicitNulls = false }
    }

    override fun write(entry: LogEntry) {
        println(json.encodeToString(entry))
    }
}
