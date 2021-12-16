package sh.brocode.brolog

import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith

class BroLoggerTest : FunSpec() {
    class TestLogger : BroLogger(
        loggerName = "test",
        logLevel = LogLevel.TRACE,
    ) {
        var lastEntry: LogEntry? = null

        override fun write(entry: LogEntry) {
            lastEntry = entry
        }
    }

    companion object {
        val logger = TestLogger()
    }

    init {

        test("Write message") {
            val tests = mapOf(
                { msg: String -> logger.trace(msg) } to LogLevel.TRACE,
                { msg: String -> logger.debug(msg) } to LogLevel.DEBUG,
                { msg: String -> logger.info(msg) } to LogLevel.INFO,
                { msg: String -> logger.warn(msg) } to LogLevel.WARN,
                { msg: String -> logger.error(msg) } to LogLevel.ERROR,
            )

            tests.forEach { (method, logLevel) ->
                method("Test")
                with(logger.lastEntry.shouldNotBeNull()) {
                    assertSoftly {
                        message shouldBe "Test"
                        exception.shouldBeNull()
                        level shouldBe logLevel
                    }
                }
            }
        }

        test("Write message with arg") {
            val tests = mapOf(
                { msg: String, arg: String -> logger.trace(msg, arg) } to LogLevel.TRACE,
                { msg: String, arg: String -> logger.debug(msg, arg) } to LogLevel.DEBUG,
                { msg: String, arg: String -> logger.info(msg, arg) } to LogLevel.INFO,
                { msg: String, arg: String -> logger.warn(msg, arg) } to LogLevel.WARN,
                { msg: String, arg: String -> logger.error(msg, arg) } to LogLevel.ERROR,
            )

            tests.forEach { (method, logLevel) ->
                method("Test {}", "fkbr")
                with(logger.lastEntry.shouldNotBeNull()) {
                    assertSoftly {
                        message shouldBe "Test fkbr"
                        exception.shouldBeNull()
                        level shouldBe logLevel
                    }
                }
            }
        }

        test("Write message with 2 args") {
            val tests = mapOf(
                { msg: String, arg1: String, arg2: String -> logger.trace(msg, arg1, arg2) } to LogLevel.TRACE,
                { msg: String, arg1: String, arg2: String -> logger.debug(msg, arg1, arg2) } to LogLevel.DEBUG,
                { msg: String, arg1: String, arg2: String -> logger.info(msg, arg1, arg2) } to LogLevel.INFO,
                { msg: String, arg1: String, arg2: String -> logger.warn(msg, arg1, arg2) } to LogLevel.WARN,
                { msg: String, arg1: String, arg2: String -> logger.error(msg, arg1, arg2) } to LogLevel.ERROR,
            )

            tests.forEach { (method, logLevel) ->
                method("Test {} {}", "fkbr", "sxoe")
                with(logger.lastEntry.shouldNotBeNull()) {
                    assertSoftly {
                        message shouldBe "Test fkbr sxoe"
                        exception.shouldBeNull()
                        level shouldBe logLevel
                    }
                }
            }
        }

        test("Write message with varargs") {
            val tests = mapOf(
                { msg: String, arg1: String, arg2: String, arg3: String ->
                    logger.trace(
                        msg,
                        arg1,
                        arg2,
                        arg3
                    )
                } to LogLevel.TRACE,
                { msg: String, arg1: String, arg2: String, arg3: String ->
                    logger.debug(
                        msg,
                        arg1,
                        arg2,
                        arg3
                    )
                } to LogLevel.DEBUG,
                { msg: String, arg1: String, arg2: String, arg3: String ->
                    logger.info(
                        msg,
                        arg1,
                        arg2,
                        arg3
                    )
                } to LogLevel.INFO,
                { msg: String, arg1: String, arg2: String, arg3: String ->
                    logger.warn(
                        msg,
                        arg1,
                        arg2,
                        arg3
                    )
                } to LogLevel.WARN,
                { msg: String, arg1: String, arg2: String, arg3: String ->
                    logger.error(
                        msg,
                        arg1,
                        arg2,
                        arg3
                    )
                } to LogLevel.ERROR,
            )

            tests.forEach { (method, logLevel) ->
                method("Test {} {} {}", "fkbr", "sxoe", "kuci")
                with(logger.lastEntry.shouldNotBeNull()) {
                    assertSoftly {
                        message shouldBe "Test fkbr sxoe kuci"
                        exception.shouldBeNull()
                        level shouldBe logLevel
                    }
                }
            }
        }

        test("Write message with throwable") {
            val tests = mapOf(
                { msg: String, t: Throwable -> logger.trace(msg, t) } to LogLevel.TRACE,
                { msg: String, t: Throwable -> logger.debug(msg, t) } to LogLevel.DEBUG,
                { msg: String, t: Throwable -> logger.info(msg, t) } to LogLevel.INFO,
                { msg: String, t: Throwable -> logger.warn(msg, t) } to LogLevel.WARN,
                { msg: String, t: Throwable -> logger.error(msg, t) } to LogLevel.ERROR,
            )

            tests.forEach { (method, logLevel) ->
                method("Test", RuntimeException("Test"))
                with(logger.lastEntry.shouldNotBeNull()) {
                    assertSoftly {
                        message shouldBe "Test"
                        exception shouldStartWith "java.lang.RuntimeException: Test"
                        level shouldBe logLevel
                    }
                }
            }
        }

        test("Write message with arg and exception") {
            val tests = mapOf(
                { msg: String, arg1: String, t: Throwable -> logger.trace(msg, arg1, t) } to LogLevel.TRACE,
                { msg: String, arg1: String, t: Throwable -> logger.debug(msg, arg1, t) } to LogLevel.DEBUG,
                { msg: String, arg1: String, t: Throwable -> logger.info(msg, arg1, t) } to LogLevel.INFO,
                { msg: String, arg1: String, t: Throwable -> logger.warn(msg, arg1, t) } to LogLevel.WARN,
                { msg: String, arg1: String, t: Throwable -> logger.error(msg, arg1, t) } to LogLevel.ERROR,
            )

            tests.forEach { (method, logLevel) ->
                method("Test {}", "fkbr", RuntimeException("Test"))
                with(logger.lastEntry.shouldNotBeNull()) {
                    assertSoftly {
                        message shouldBe "Test fkbr"
                        exception shouldStartWith "java.lang.RuntimeException: Test"
                        level shouldBe logLevel
                    }
                }
            }
        }
    }
}
