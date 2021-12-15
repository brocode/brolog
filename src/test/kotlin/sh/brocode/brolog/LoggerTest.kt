package sh.brocode.brolog

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory
import org.slf4j.MDC

class LoggerTest : FunSpec() {

    companion object {
        val logger = LoggerFactory.getLogger(LoggerTest::class.java)
    }
    init {

        test("fkbr") {
            MDC.put("test", "fkbr")
            logger.info("log 1")
            MDC.put("sxoe", "kuci")
            logger.info("log 2")
            true shouldBe true
        }
    }
}
