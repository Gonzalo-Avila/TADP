package parsers

import general._

import scala.util.Try

class string (cadenaFiltro:String) extends Parser[String]{
  
  def apply(cadenaAParsear:String): Try[Resultado[String]] = {
    Try (
        cadenaAParsear match {
        case "" => throw new Exception();
        case c if c startsWith cadenaFiltro => new Resultado(cadenaFiltro,cadenaAParsear.substring(cadenaFiltro.size));
        case _ => throw new Exception();
        }
    )   
   }
}

object string {
    def apply(cadenaFiltro: String): string = {
        new string(cadenaFiltro)
    }
}