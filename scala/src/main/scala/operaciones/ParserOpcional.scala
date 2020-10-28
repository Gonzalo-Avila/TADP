package operaciones

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._

class ParserOpcional[T](parserOriginal:Parser[T]) extends Parser[Option[T]]{
  
  def apply(cadena:String): Try[Resultado[Option[T]]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
    Try(
          resultadoOriginal match {
            case Success(resultado) => new Resultado (Some(resultado.getElementoParseado), resultado.getCadenaRestante)
            case Failure(error) => new Resultado(None, cadena)
          } 
    )
  }
}