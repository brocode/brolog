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
