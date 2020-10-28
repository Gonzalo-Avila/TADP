package parsers

import general._

import scala.util.Try

object anyChar extends Parser[Char] {
  
   def apply (cadena:String): Try[Resultado[Char]] = {
      Try(
          cadena match {
          case "" => throw new Exception();
          case cad => new Resultado(cad.head,cad.tail);
          }
      )
  }
}