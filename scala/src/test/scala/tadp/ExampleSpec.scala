package tadp

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

class ExampleSpec extends AnyFlatSpec with should.Matchers {
  it should "sumar 1 + 1" in {
    1 + 1 shouldEqual 2
  }
}
