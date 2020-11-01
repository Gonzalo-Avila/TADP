package tadp;

import org.scalatest.flatspec.AnyFlatSpec;
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers._
import general._
import operaciones._
import combinators._
import scala.util.{Failure, Success, Try}

class OperacionesSpec extends AnyFlatSpec with should.Matchers {

        "anyChar.satisfies(a => a.isUpper)" should "work with \"Hola\"" in {
                val resultado = anyChar.satisfies(a => a.isUpper).apply("Hola")
                resultado.get.getElementoParseado shouldEqual 'H'
                resultado.get.getCadenaRestante shouldEqual "ola"
        }

        "anyChar.satisfies(a => a.isUpper)" should "not work with \"hola\"" in {
                val resultado = anyChar.satisfies(a => a.isUpper).apply("hola")
                resultado.isFailure shouldEqual true
        }

        "char('a').opt" should "work with \"amigo\"" in {
                val resultado = char('a').opt.apply("amigo")
                resultado.get.getElementoParseado shouldEqual Some('a')
                resultado.get.getCadenaRestante shouldEqual "migo"
        }

        "char('a').opt" should "work with \"xmigo\"" in {
                val resultado = char('a').opt.apply("xmigo")
                resultado.get.getElementoParseado shouldEqual None
                resultado.get.getCadenaRestante shouldEqual "xmigo"
        }

        "string(\"ab\").*" should "work with \"ababcabab\"" in {
                val resultado = string("ab").*.apply("ababcabab")
                resultado.get.getElementoParseado shouldEqual List("ab","ab")
                resultado.get.getCadenaRestante shouldEqual "cabab"
        }
        "string(\"ab\").*" should "work with \"babcabab\"" in {
                val resultado = string("ab").*.apply("babcabab")
                resultado.get.getElementoParseado shouldEqual List()
                resultado.get.getCadenaRestante shouldEqual "babcabab"
        }
        "string(\"ab\").+" should "work with \"ababcabab\"" in {
                val resultado = string("ab").+.apply("ababcabab")
                resultado.get.getElementoParseado shouldEqual List("ab","ab")
                resultado.get.getCadenaRestante shouldEqual "cabab"
        }

        "string(\"ab\").+" should "not work with \"babcabab\"" in {
                val resultado = string("ab").+.apply("babcabab")
                resultado.isFailure shouldEqual true
        }

        "string(\"ab\").map(cad => cad.toUpperCase())" should "work with \"ababcabab\"" in {
                val resultado = string("ab").map(cad => cad.toUpperCase()).apply("ababcabab")
                resultado.get.getElementoParseado shouldEqual "AB"
                resultado.get.getCadenaRestante shouldEqual "abcabab"
        }

        "string(\"ab\").map(cad => cad.toUpperCase())" should "not work with \"babcabab\"" in {
                val resultado = string("ab").map(cad => cad.toUpperCase()).apply("babcabab")
                resultado.isFailure shouldEqual true
        }

}