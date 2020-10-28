package parsers

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._

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