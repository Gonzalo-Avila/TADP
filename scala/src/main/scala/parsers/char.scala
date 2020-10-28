package parsers

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._


class char (caracter: Char) extends Parser[Char] {
    def apply(cadena:String): Try[Resultado[Char]] = {
        Try (
          cadena match {
          case "" => throw new Exception();
          case c if c.head == caracter => new Resultado(c.head,c.tail);
          case _ => throw new Exception();
          }
      )
    }
}
object char {
    def apply(caracter: Char): char = {
        new char(caracter)
    }
}