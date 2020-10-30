package tadp


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers._
import general._
import operaciones._
import combinators._
import scala.util.{Failure, Success, Try}

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
  "string(\"hola\") <> char('c')" should "work with \"holacomova\"" in {
    val resultado = (string("hola") <> char('c')).apply("holacomova")
    resultado.get.getElementoParseado shouldEqual Tuple2("hola",'c')
    resultado.get.getCadenaRestante shouldEqual "omova"
  }
}