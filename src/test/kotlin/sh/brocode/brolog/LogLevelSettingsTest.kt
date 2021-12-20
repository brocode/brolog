package sh.brocode.brolog

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.shouldBe

class LogLevelSettingsTest : FunSpec() {

    private data class LogSettingExpectation(val settings: String?, val level: LogLevel)
    init {

        test("give back root level if nothing is configured") {
            val settings = LogLevelSettings(
                rootLevel = LogLevel.WARN,
                settings = emptyMap(),
            )
            settings.getLoggerLevel("fkbr") shouldBe LogLevel.WARN
        }

        context("load root level from env") {
            withData(
                LogSettingExpectation("WARN", LogLevel.WARN),
                LogSettingExpectation("INFO", LogLevel.INFO),
                LogSettingExpectation("DSKJFSDF", LogLevel.TRACE),
                LogSettingExpectation(null, LogLevel.TRACE),
            ) { (setting, expectedLevel) ->
                withEnvironment(mapOf("BROLOG_ROOT_LEVEL" to setting)) {
                    LogLevelSettings.loadFromEnv().rootLevel shouldBe expectedLevel
                }
            }
        }
    }
}
