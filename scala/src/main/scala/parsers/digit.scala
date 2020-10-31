package parsers

import general._

import scala.util.Try


object digit extends Parser[Char]{
  
   def apply(cadena:String): Try[Resultado[Char]] = {
       Try(
         cadena match {
           case cad if cad.head.isDigit => Resultado(cad.head,cad.tail);
           case _ => throw new Exception();
         }
       )
   }
}
