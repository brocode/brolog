package sh.brocode.brolog

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class LogLevelSettingsTest : FunSpec() {

    init {

        test("give back root level if nothing is configured") {
            val settings = LogLevelSettings(
                rootLevel = LogLevel.WARN,
                settings = emptyMap(),
            )
            settings.getLoggerLevel("fkbr") shouldBe LogLevel.WARN
        }
    }
}
