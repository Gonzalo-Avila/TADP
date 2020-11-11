package parsers

import general._

import scala.util.Try

case class char (caracter: Char) extends Parser[Char] {

    def apply(cadena:String): Try[Resultado[Char]] =
        Try (
            cadena match {
            case "" => throw new RuntimeException("La cadena está vacia");
            case c if c.head == caracter => Resultado(c.head,c.tail);
            case _ => throw new RuntimeException("El caracter obtenido no coincide con el esperado")
          }
      )

}