package sh.brocode.brolog

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.json.shouldContainJsonKey
import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.kotest.assertions.json.shouldNotContainJsonKey
import io.kotest.core.spec.style.FunSpec
import org.slf4j.LoggerFactory
import org.slf4j.MDC

class LoggerTest : FunSpec() {

    companion object {
        val logger = LoggerFactory.getLogger(LoggerTest::class.java)
    }

    init {

        test("simple log") {

            val output = tapSystemOut {
                logger.warn("msg")
            }

            assertSoftly {
                output.shouldContainJsonKey("$.time")
                output.shouldContainJsonKeyValue("$.message", "msg")
                output.shouldContainJsonKeyValue("$.level", "WARN")
                output.shouldNotContainJsonKey("$.mdc")
            }
        }

        test("info log with mdc") {
            MDC.put("test", "fkbr")
            MDC.put("sxoe", "kuci")

            val output = tapSystemOut {
                logger.info("log 1")
            }

            assertSoftly {
                output.shouldContainJsonKey("$.time")
                output.shouldContainJsonKeyValue("$.message", "log 1")
                output.shouldContainJsonKeyValue("$.level", "INFO")
                output.shouldContainJsonKeyValue("$.mdc.test", "fkbr")
                output.shouldContainJsonKeyValue("$.mdc.sxoe", "kuci")
            }
        }
    }
}
