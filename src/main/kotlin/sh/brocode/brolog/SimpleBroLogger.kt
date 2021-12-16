package sh.brocode.brolog

class SimpleBroLogger(
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
        val output = """
            |# ${entry.level} ${entry.logger}
            |${entry.message}
        """.trimMargin()

        if (entry.exception != null) {
            println(output + "\n" + entry.exception)
        } else {
            println(output)
        }
    }
}
