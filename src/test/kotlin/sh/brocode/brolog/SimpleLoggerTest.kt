package sh.brocode.brolog

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SimpleLoggerTest : FunSpec() {

    companion object {
        val logger = SimpleBroLogger(
            loggerName = "test",
            logLevel = LogLevel.TRACE
        )
    }

    init {

        test("simple log") {

            val output = tapSystemOut {
                logger.warn("msg")
            }

            output shouldBe """
            |# WARN test
            |msg
            |
            """.trimMargin()
        }
    }
}
