package tadp


import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers._

class CombinatorsSpec extends AnyFlatSpec with should.Matchers {
  "char ('a') <|> char('b')" should "work with \"bola\"" in {
    val resultado = (char ('a') <|> char('b')).apply("bola")
    resultado.get.getElementoParseado shouldEqual 'b'
    resultado.get.getCadenaRestante shouldEqual "ola"
  }

  "char ('a') <|> char('b')" should "work with \"ala\"" in {
    val resultado = (char ('a') <|> char('b')).apply("ala")
    resultado.get.getElementoParseado shouldEqual 'a'
    resultado.get.getCadenaRestante shouldEqual "la"
  }

  "char ('a') <|> char('b')" should "not work with \"casa\"" in {
    val resultado = (char ('a') <|> char('b')).apply("casa")
    resultado.isFailure shouldEqual true
  }

  "string(\"ala\") <|> string(\"delta\")" should "work with \"aladelta\"" in {
    val resultado = (string("ala") <|> string("delta")).apply("aladelta")
    resultado.get.getElementoParseado shouldEqual "ala"
    resultado.get.getCadenaRestante shouldEqual "delta"
  }

  "string(\"ala\") <|> string(\"delta\")" should "work with \"deltaala\"" in {
    val resultado = (string("ala") <|> string("delta")).apply("deltaala")
    resultado.get.getElementoParseado shouldEqual "delta"
    resultado.get.getCadenaRestante shouldEqual "ala"
  }

  "char ('a') <> char('b')" should "work with \"abcd\"" in {
    val resultado = (char ('a') <> char('b')).apply("abcd")
    resultado.get.getElementoParseado shouldEqual ('a','b')
    resultado.get.getCadenaRestante shouldEqual "cd"
  }

  "char ('a') <> char('b')" should "not work with \"acbd\"" in {
    val resultado = (char ('a') <> char('b')).apply("acbd")
    resultado.isFailure shouldEqual true
  }

  "char ('a') <> char('b')" should "not work with \"bcde\"" in {
    val resultado = (char ('a') <> char('b')).apply("bcde")
    resultado.isFailure shouldEqual true
  }

  "string(\"hola\") <> char('b')" should "work with \"holabb\"" in {
    val resultado = (string("hola") <> char('b')).apply("holabb")
    resultado.get.getElementoParseado shouldEqual Tuple2("hola",'b')
    resultado.get.getCadenaRestante shouldEqual "b"
  }

  "string(\"hola\") <> char('c')" should "work with \"holacomova\"" in {
    val resultado = (string("hola") <> char('c')).apply("holacomova")
    resultado.get.getElementoParseado shouldEqual Tuple2("hola",'c')
    resultado.get.getCadenaRestante shouldEqual "omova"
  }

  "char('a') ~> char('b')" should "work with \"abcd\"" in {
    val resultado = (char('a') ~> char('b')).apply("abcd")
    resultado.get.getElementoParseado shouldEqual 'b'
    resultado.get.getCadenaRestante shouldEqual "cd"
  }

  "char('a') ~> string(\"qwe\")" should "work with \"aqwer\"" in {
    val resultado = (char('a') ~> string("qwe")).apply("aqwer")
    resultado.get.getElementoParseado shouldEqual "qwe"
    resultado.get.getCadenaRestante shouldEqual "r"
  }

  "char('a') ~> char('b')" should "not work with \"bacd\"" in {
    val resultado = (char('a') ~> char('b')).apply("bacd")
    resultado.isFailure shouldEqual true
  }

  "char('a') ~> char('b')" should "not work with \"xbcd\"" in {
    val resultado = (char('a') ~> char('b')).apply("xbcd")
    resultado.isFailure shouldEqual true
  }

  "char('a') <~ char('b')" should "work with \"abcd\"" in {
    val resultado = (char('a') <~ char('b')).apply("abcd")
    resultado.get.getElementoParseado shouldEqual 'a'
    resultado.get.getCadenaRestante shouldEqual "cd"
  }

  "char('a') <~ string(\"qwe\")" should "work with \"aqwer\"" in {
    val resultado = (char('a') <~ string("qwe")).apply("aqwer")
    resultado.get.getElementoParseado shouldEqual 'a'
    resultado.get.getCadenaRestante shouldEqual "r"
  }

  "char('a') <~ char('b')" should "not work with \"bacd\"" in {
    val resultado = (char('a') <~ char('b')).apply("bacd")
    resultado.isFailure shouldEqual true
  }

  "char('a') <~ char('b')" should "not work with \"xbcd\"" in {
    val resultado = (char('a') <~ char('b')).apply("xbcd")
    resultado.isFailure shouldEqual true
  }

  "anyChar.sepBy(char('#'))" should "work with \"a#sd#qwe.\"" in {
    val resultado = anyChar.sepBy(char('#')).apply("a#sd#qwe")
    resultado.get.getElementoParseado shouldEqual List('a','s')
    resultado.get.getCadenaRestante shouldEqual "d#qwe"
  }

  "anyChar.sepBy(char('#'))" should "work with \"a#s#q\"" in {
    val resultado = anyChar.sepBy(char('#')).apply("a#s#q")
    resultado.get.getElementoParseado shouldEqual List('a','s','q')
    resultado.get.getCadenaRestante shouldEqual ""
  }

  "anyChar.sepBy(char('#'))" should "not work with \"as#as\"" in {
    val resultado = anyChar.sepBy(char('#')).apply("as#as")
    resultado.isFailure shouldEqual true
  }

  "string(\"hola\").sepBy(digit)" should "work with \"hola2hola4holasd\"" in {
    val resultado = string("hola").sepBy(digit).apply("hola2hola4holasd")
    resultado.get.getElementoParseado shouldEqual List("hola","hola","hola")
    resultado.get.getCadenaRestante shouldEqual "sd"
  }

  "string(\"hola\").sepBy(digit)" should "work with \"hola2holaholasd\"" in {
    val resultado = string("hola").sepBy(digit).apply("hola2holaholasd")
    resultado.get.getElementoParseado shouldEqual List("hola","hola")
    resultado.get.getCadenaRestante shouldEqual "holasd"
  }

  "string(\"hola\").sepBy(digit)" should "not work with \"holahola4holasd\"" in {
    val resultado = string("hola").sepBy(digit).apply("holahola4holasd")
    resultado.isFailure shouldEqual true
  }

  "integer.sepBy(char('-'))" should "work with \"4123\"" in {
    val resultado = integer.sepBy(char('-')).apply("4123")
    resultado.get.getElementoParseado shouldEqual List(4123)
    resultado.get.getCadenaRestante shouldEqual ""
  }

  "integer.sepBy(char('-'))" should "not work with \"4123-\"" in {
    val resultado = integer.sepBy(char('-')).apply("4123-")
    resultado.isFailure shouldEqual true
  }




}