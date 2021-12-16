package sh.brocode.brolog

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonBroLogger(
    loggerName: String,
    logLevel: LogLevel,
) : BroLogger(
    loggerName = loggerName,
    logLevel = logLevel,
) {

    override fun write(entry: LogEntry) {
        println(Json.encodeToString(entry))
    }
}
