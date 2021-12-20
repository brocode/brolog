package sh.brocode.brolog

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import io.kotest.assertions.assertSoftly
import io.kotest.assertions.json.shouldContainJsonKey
import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.kotest.assertions.json.shouldNotContainJsonKey
import io.kotest.core.spec.style.FunSpec
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import java.lang.RuntimeException

class SLf4jIntegrationLoggerTest : FunSpec() {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(SLf4jIntegrationLoggerTest::class.java)
    }

    init {

        beforeTest {
            MDC.clear()
        }

        test("simple message") {

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

        test("log formatted") {
            val output = tapSystemOut {
                logger.info("log {} {}", 1, 2)
            }

            output.shouldContainJsonKeyValue("$.message", "log 1 2")
        }

        test("log exception") {
            val exception: RuntimeException = RuntimeException("Test")
            exception.fillInStackTrace()

            val output = tapSystemOut {
                logger.info("log {}", 1, exception)
            }

            assertSoftly {
                output.shouldContainJsonKeyValue("$.message", "log 1")
                output.shouldContainJsonKey("$.exception")
            }
        }
    }
}
