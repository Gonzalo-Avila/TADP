package operaciones

import general._

import scala.util.{Failure, Success, Try}

class ParserOpcional[T](parserOriginal:Parser[T]) extends Parser[Option[T]]{

  def apply(cadena:String): Try[Resultado[Option[T]]] = parserOriginal.apply(cadena)
      .map{r => Resultado(Option(r.getElementoParseado),r.getCadenaRestante)}
      .recoverWith {_ => Try(Resultado(None,cadena))}



  /*def apply(cadena:String): Try[Resultado[Option[T]]] = {
    val resultadoOriginal = parserOriginal.apply(cadena)
    //TODO - usar map{}.recover{} - DONE
    Try(
          resultadoOriginal match {
            case Success(resultado) => Resultado (Some(resultado.getElementoParseado), resultado.getCadenaRestante)
            case Failure(error) => Resultado(None, cadena)
          } 
    )
  }*/
}