package tadp


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers._
import general._
import operaciones._
import combinators._
import scala.util.{Failure, Success, Try}

class ParsersSpec extends AnyFlatSpec with should.Matchers {
  it should "anyChar #1" in {
    val resultado = anyChar.apply("Hola")
    resultado.get.getElementoParseado shouldEqual 'H'
    resultado.get.getCadenaRestante shouldEqual "ola"
  }
}