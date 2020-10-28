package parsers

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._


object digit extends Parser[Char]{
  
   def apply(cadena:String): Try[Resultado[Char]] = {
       Try(
         cadena match {
           case cad if cad.head.isDigit => new Resultado(cad.head,cad.tail);
           case _ => throw new Exception();
         }
       )
   }
}
