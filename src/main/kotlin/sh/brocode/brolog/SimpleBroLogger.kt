package sh.brocode.brolog

class SimpleBroLogger(
    loggerName: String,
    logLevel: LogLevel,
) : BroLogger(
    name = loggerName,
    logLevel = logLevel,
) {

    override fun write(entry: LogEntry) {
        val builder = StringBuilder()
        builder.append("# ${entry.level} ${entry.logger}\n")
        builder.append(entry.message)

        if (entry.exception != null) {
            builder.append("\n" + entry.exception)
        }
        if (!entry.keyValues.isNullOrEmpty()) {
            entry.keyValues.forEach { (key, value) ->
                builder.append("\n\tkvp.$key: $value")
            }
        }
        if (!entry.mdc.isNullOrEmpty()) {
            entry.mdc.forEach { (key, value) ->
                builder.append("\n\tmdc.$key: $value")
            }
        }

        println(builder.toString())
    }
}
