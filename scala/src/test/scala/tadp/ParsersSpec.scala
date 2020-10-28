package tadp


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers._
import general._
import operaciones._
import combinators._
import scala.util.{Failure, Success, Try}

class ParsersSpec extends AnyFlatSpec with should.Matchers {
   "anyChar" should "work with 'hola'" in {
    val resultado = anyChar.apply("Hola")
    resultado.get.getElementoParseado shouldEqual 'H'
    resultado.get.getCadenaRestante shouldEqual "ola"
  }

  "char('h')" should "not work with 'Hola'" in {
    val resultado = char('h').apply("Hola")
    resultado.isFailure shouldEqual true
  }
}