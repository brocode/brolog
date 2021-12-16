package sh.brocode.brolog

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class JsonBroLogger(
    loggerName: String,
    traceEnabled: Boolean,
    debugEnabled: Boolean,
    infoEnabled: Boolean,
    warnEnabled: Boolean,
    errorEnabled: Boolean,
) : BroLogger(
    loggerName = loggerName,
    traceEnabled = traceEnabled,
    debugEnabled = debugEnabled,
    infoEnabled = infoEnabled,
    warnEnabled = warnEnabled,
    errorEnabled = errorEnabled,
) {

    override fun write(entry: LogEntry) {
        println(Json.encodeToString(entry))
    }
}
