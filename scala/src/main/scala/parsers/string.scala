package parsers

import general._

import scala.util.Try

case class string (cadenaFiltro:String) extends Parser[String]{
  
  def apply(cadenaAParsear:String): Try[Resultado[String]] = {
    Try (
        cadenaAParsear match {
          case "" => throw new Exception();
          case c if c startsWith cadenaFiltro => Resultado(cadenaFiltro,cadenaAParsear.substring(cadenaFiltro.size));
          case _ => throw new Exception();
        }
    )   
   }
}