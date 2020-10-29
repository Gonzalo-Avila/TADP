package tadp


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers._
import general._
import operaciones._
import combinators._
import scala.util.{Failure, Success, Try}

class ParsersSpec extends AnyFlatSpec with should.Matchers {
   "anyChar" should "work with \"hola\"" in {
    val resultado = anyChar.apply("Hola")
    resultado.get.getElementoParseado shouldEqual 'H'
    resultado.get.getCadenaRestante shouldEqual "ola"
  }
  it should "not work with \"\"" in {
    val resultado = anyChar.apply("")
    resultado.isFailure shouldEqual true
  }

  "char('a')" should "work with \"aloha\"" in {
    val resultado = char('a').apply("aloha")
    resultado.get.getElementoParseado shouldEqual 'a'
    resultado.get.getCadenaRestante shouldEqual "loha"
  }
  it should "not work with \"Aloha\"" in {
    val resultado = char('a').apply("Aloha")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"\" " in {
    val resultado = char('a').apply("")
    resultado.isFailure shouldEqual true
  }

  "digit" should "work with \"1asd\"" in {
    val resultado = digit.apply("1asd")
    resultado.get.getElementoParseado shouldEqual '1'
    resultado.get.getCadenaRestante shouldEqual "asd"
  }
  it should "not work with \"asd\"" in {
    val resultado = digit.apply("asd")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"\"" in {
    val resultado = digit.apply("")
    resultado.isFailure shouldEqual true
  }
  "string(\"Ala\")" should "work with \"Aladelta\"" in {
    val resultado = string("Ala").apply("Aladelta")
    resultado.get.getElementoParseado shouldEqual "Ala"
    resultado.get.getCadenaRestante shouldEqual "delta"
  }
  it should "not work with \"deltaAla\"" in {
    val resultado = string("Ala").apply("deltaAla")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"alamo\"" in {
    val resultado = string("Ala").apply("alamo")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"\"" in {
    val resultado = string("Ala").apply("")
    resultado.isFailure shouldEqual true
  }

  "integer" should "work with \"123\"" in {
    val resultado = integer.apply("123")
    resultado.get.getElementoParseado shouldEqual 123
    resultado.get.getCadenaRestante shouldEqual ""
  }
  it should "work with \"-123\"" in {
    val resultado = integer.apply("-123")
    resultado.get.getElementoParseado shouldEqual (-123)
    resultado.get.getCadenaRestante shouldEqual ""
  }
  it should "work with \"-123asd\"" in {
    val resultado = integer.apply("-123asd")
    resultado.get.getElementoParseado shouldEqual (-123)
    resultado.get.getCadenaRestante shouldEqual "asd"
  }
  it should "work with \"-123-123\"" in {
    val resultado = integer.apply("-123-123")
    resultado.get.getElementoParseado shouldEqual (-123)
    resultado.get.getCadenaRestante shouldEqual "-123"
  }

  it should "work with \"55.124\"" in {
    val resultado = integer.apply("55.124")
    resultado.get.getElementoParseado shouldEqual 55
    resultado.get.getCadenaRestante shouldEqual ".124"
  }
  it should "work with \"0123\"" in {
    val resultado = integer.apply("0123")
    resultado.get.getElementoParseado shouldEqual 123
    resultado.get.getCadenaRestante shouldEqual ""
  }
  it should "not work with \"a12345\"" in {
    val resultado = integer.apply("a012345")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"+5\"" in {
    val resultado = integer.apply("+5")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"\"" in {
    val resultado = integer.apply("")
    resultado.isFailure shouldEqual true
  }

  "double" should "work with \"123.45\"" in {
    val resultado = double.apply("123.45")
    resultado.get.getElementoParseado shouldEqual 123.45
    resultado.get.getCadenaRestante shouldEqual ""
  }
  it should "work with \"-123.45\"" in {
    val resultado = double.apply("-123.45")
    resultado.get.getElementoParseado shouldEqual (-123.45)
    resultado.get.getCadenaRestante shouldEqual ""
  }
  it should "work with \"123.45asd\"" in {
    val resultado = double.apply("123.45asd")
    resultado.get.getElementoParseado shouldEqual 123.45
    resultado.get.getCadenaRestante shouldEqual "asd"
  }
  it should "work with \"123.45.67.89\"" in {
    val resultado = double.apply("123.45.67.89")
    resultado.get.getElementoParseado shouldEqual 123.45
    resultado.get.getCadenaRestante shouldEqual ".67.89"
  }
  it should "not work with \"12345\"" in {
    val resultado = double.apply("12345")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"-12345\"" in {
    val resultado = double.apply("-12345")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"a1234.52\"" in {
    val resultado = double.apply("a1234.52")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \".45\"" in {
    val resultado = double.apply(".45")
    resultado.isFailure shouldEqual true
  }
  it should "not work with \"\"" in {
    val resultado = double.apply("")
    resultado.isFailure shouldEqual true
  }
}