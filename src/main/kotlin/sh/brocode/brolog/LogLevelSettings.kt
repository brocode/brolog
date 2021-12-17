package sh.brocode.brolog

class LogLevelSettings(private val rootLevel: LogLevel, private val settings: Map<String, LogLevel>) {
    companion object {
        fun loadFromEnv(): LogLevelSettings {
            // TODO actually load something
            return LogLevelSettings(
                rootLevel = LogLevel.TRACE,
                settings = emptyMap(),
            )
        }
    }

    fun getLoggerLevel(name: String): LogLevel {
        // TODO actually determine log level
        return rootLevel
    }
}
