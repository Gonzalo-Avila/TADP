package parsers

import general._

import scala.util.Try


class char (caracter: Char) extends Parser[Char] {
    def apply(cadena:String): Try[Resultado[Char]] = {
        Try (
            cadena match {
            case "" => throw new Exception();
            case c if c.head == caracter => Resultado(c.head,c.tail);
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