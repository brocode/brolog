package sh.brocode.brolog

private const val LEVEL_PREFIX = "BROLOG_LEVEL_"

class LogLevelSettings(val rootLevel: LogLevel, val settings: Map<String, LogLevel>) {
    companion object {
        fun loadFromEnv(): LogLevelSettings {
            val rootLevelSetting = (System.getenv()["BROLOG_ROOT_LEVEL"] ?: "TRACE").uppercase()
            val rootLevel = LogLevel.values().find { it.name == rootLevelSetting } ?: LogLevel.TRACE

            val loggerSettings = System.getenv()
                .filterKeys { it.startsWith(LEVEL_PREFIX) }
                .mapKeys { (key, _) -> key.substring(LEVEL_PREFIX.length) }
                .mapValues { (_, value) -> LogLevel.values().find { it.name == value.uppercase() } ?: LogLevel.TRACE }

            return LogLevelSettings(
                rootLevel = rootLevel,
                settings = loggerSettings,
            )
        }
    }

    private tailrec fun getLoggerLevelFromSettings(name: String): LogLevel? {
        val settingForName: LogLevel? = settings[name]
        return if (settingForName != null) {
            settingForName
        } else {
            val dotIndex = name.lastIndexOf(".")
            if (dotIndex < 1) {
                null
            } else {
                getLoggerLevelFromSettings(name.substring(0, dotIndex))
            }
        }
    }

    fun getLoggerLevel(name: String): LogLevel =
        getLoggerLevelFromSettings(name) ?: rootLevel
}
