package parsers

import general._

import scala.util.Try

object anyChar extends Parser[Char] {
  
   def apply (cadena:String): Try[Resultado[Char]] =
      Try(
          cadena match {
          case cad if cad.isEmpty => throw new RuntimeException("La cadena está vacia")
          case cad => Resultado(cad.head,cad.tail)
          }
      )

}