package parsers

import general._

import scala.util.Try


object digit extends Parser[Char]{
  
   def apply(cadena:String): Try[Resultado[Char]] =
       Try(
         cadena match {
           case cad if cad.head.isDigit => Resultado(cad.head,cad.tail);
           case "" => throw new RuntimeException("La cadena está vacia");
           case _ => throw new RuntimeException("El caracter obtenido no es un dígito");
         }
       )

}
