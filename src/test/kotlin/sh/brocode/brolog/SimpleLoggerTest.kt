package sh.brocode.brolog

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.slf4j.MDC

class SimpleLoggerTest : FunSpec() {

    companion object {
        val logger = SimpleBroLogger(
            loggerName = "test",
            logLevel = LogLevel.TRACE,
        )
    }

    init {

        test("simple log") {

            val output = tapSystemOut {
                MDC.put("mdckey", "fkbr")
                logger.atWarn()
                    .addKeyValue("kvpkey", "sxoe")
                    .log("msg")
            }

            output shouldBe """
            |# WARN test
            |msg
            |	kvp.kvpkey: sxoe
            |	mdc.mdckey: fkbr
            |
            """.trimMargin()
        }
    }
}
