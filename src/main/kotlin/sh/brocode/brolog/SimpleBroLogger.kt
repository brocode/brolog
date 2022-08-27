package sh.brocode.brolog

class SimpleBroLogger(
    loggerName: String,
    logLevel: LogLevel,
) : BroLogger(
    name = loggerName,
    logLevel = logLevel,
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
