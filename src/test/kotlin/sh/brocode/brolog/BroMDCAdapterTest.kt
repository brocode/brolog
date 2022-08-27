package sh.brocode.brolog

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class BroMDCAdapterTest : FunSpec() {
    init {
        test("store variable") {
            val adapter = BroMDCAdapter()
            adapter.get("fkbr") shouldBe null
            adapter.put("fkbr", "sxoe")
            adapter.get("fkbr") shouldBe "sxoe"
        }

        test("clear") {
            val adapter = BroMDCAdapter()
            adapter.get("fkbr") shouldBe null
            adapter.put("fkbr", "sxoe")
            adapter.get("fkbr") shouldBe "sxoe"
            adapter.clear()
            adapter.get("fkbr") shouldBe null
        }

        test("remove") {
            val adapter = BroMDCAdapter()
            adapter.get("fkbr") shouldBe null
            adapter.put("fkbr", "sxoe")
            adapter.get("fkbr") shouldBe "sxoe"
            adapter.remove("fkbr")
            adapter.get("fkbr") shouldBe null
        }

        test("get copy") {
            val adapter = BroMDCAdapter()
            adapter.put("fkbr", "sxoe")
            adapter.put("kuci", "sxoe")

            adapter.copyOfContextMap shouldBe mapOf(
                "fkbr" to "sxoe",
                "kuci" to "sxoe"
            )
        }
    }
}
