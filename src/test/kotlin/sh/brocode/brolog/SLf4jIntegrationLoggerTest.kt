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
import org.slf4j.MarkerFactory
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

        test("info log with marker") {

            val testMarker = MarkerFactory.getMarker("test")
            val output = tapSystemOut {
                logger.info(testMarker, "log 1")
            }

            assertSoftly {
                output.shouldContainJsonKey("$.time")
                output.shouldContainJsonKeyValue("$.message", "log 1")
                output.shouldContainJsonKeyValue("$.level", "INFO")
                output.shouldContainJsonKeyValue("$.marker[0]", "test")
            }
        }

        test("test fluent") {

            val testMarker = MarkerFactory.getMarker("test")
            val test2Marker = MarkerFactory.getMarker("test2")
            val output = tapSystemOut {
                logger
                    .atInfo()
                    .addMarker(testMarker)
                    .addMarker(test2Marker)
                    .addKeyValue("test_key", "test_value")
                    .addKeyValue("number", 1)
                    .log("log 1")
            }

            assertSoftly {
                output.shouldContainJsonKey("$.time")
                output.shouldContainJsonKeyValue("$.message", "log 1")
                output.shouldContainJsonKeyValue("$.level", "INFO")
                output.shouldContainJsonKeyValue("$.marker[0]", "test")
                output.shouldContainJsonKeyValue("$.marker[1]", "test2")
                output.shouldContainJsonKeyValue("$.keyValues.test_key", "test_value")
                output.shouldContainJsonKeyValue("$.keyValues.number", "1")
            }
        }

        test("log formatted") {
            val output = tapSystemOut {
                logger.info("log {} {}", 1, 2)
            }

            output.shouldContainJsonKeyValue("$.message", "log 1 2")
        }

        test("log exception") {
            val exception = RuntimeException("Test")
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
