package sh.brocode.brolog

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.slf4j.LoggerFactory

class LibraryTest : FunSpec() {

    companion object {
        val logger = LoggerFactory.getLogger(LibraryTest::class.java)
    }
    init {

        test("fkbr") {
            logger.info("fkbr")
            true shouldBe true
        }
    }
}
