package sh.brocode.brolog

class LogLevelSettings(val rootLevel: LogLevel, val settings: Map<String, LogLevel>) {
    companion object {
        fun loadFromEnv(): LogLevelSettings {
            val rootLevelSetting = System.getenv()["BROLOG_ROOT_LEVEL"] ?: "TRACE"
            val rootLevel = LogLevel.values().find { it.name == rootLevelSetting } ?: LogLevel.TRACE
            // TODO actually load something
            return LogLevelSettings(
                rootLevel = rootLevel,
                settings = emptyMap(),
            )
        }
    }

    fun getLoggerLevel(name: String): LogLevel {
        // TODO actually determine log level
        return rootLevel
    }
}
