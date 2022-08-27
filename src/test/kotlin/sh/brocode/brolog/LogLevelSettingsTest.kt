package sh.brocode.brolog

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.extensions.system.withEnvironment
import io.kotest.matchers.maps.shouldContainExactly
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
                LogSettingExpectation("DSKJFSDF", LogLevel.INFO),
                LogSettingExpectation(null, LogLevel.INFO),
            ) { (setting, expectedLevel) ->
                withEnvironment(mapOf("BROLOG_ROOT_LEVEL" to setting)) {
                    LogLevelSettings.loadFromEnv().rootLevel shouldBe expectedLevel
                }
            }
        }

        test("level by settings") {
            val settings = LogLevelSettings(
                rootLevel = LogLevel.ERROR,
                settings = mapOf(
                    "report" to LogLevel.TRACE,
                    "sh.brocode" to LogLevel.WARN,
                    "sh.brocode.test" to LogLevel.INFO,
                ),
            )
            settings.getLoggerLevel("sh.brocode.Service") shouldBe LogLevel.WARN
            settings.getLoggerLevel("sh.brocode.service.Service") shouldBe LogLevel.WARN
            settings.getLoggerLevel("sh.brocode.test.Repo") shouldBe LogLevel.INFO
            settings.getLoggerLevel("sh.brocode.test.Repo2") shouldBe LogLevel.INFO
            settings.getLoggerLevel("sh.brocode.test.entity.Entity1") shouldBe LogLevel.INFO
            settings.getLoggerLevel("report") shouldBe LogLevel.TRACE
            settings.getLoggerLevel("test") shouldBe LogLevel.ERROR
            settings.getLoggerLevel("test.test") shouldBe LogLevel.ERROR
        }

        test("settings from env") {
            val settings = withEnvironment(
                mapOf(
                    "BROLOG_LEVEL_sh.brocode" to "warn",
                    "BROLOG_LEVEL_sh.brocode.test" to "INFO",
                    "BROLOG_ROOT_LEVEL" to "TRACE",
                ),
            ) {
                LogLevelSettings.loadFromEnv()
            }

            settings.rootLevel shouldBe LogLevel.TRACE

            settings.settings shouldContainExactly mapOf(
                "sh.brocode" to LogLevel.WARN,
                "sh.brocode.test" to LogLevel.INFO,
            )
        }
    }
}
