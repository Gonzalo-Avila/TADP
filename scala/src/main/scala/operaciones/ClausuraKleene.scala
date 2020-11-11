package operaciones

import general._

import scala.util.{Failure, Success, Try}

case class ClausuraKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{

  def parsear(resultadoParcial: Resultado[List[T]]): Resultado[List[T]] =
    parserOriginal(resultadoParcial.getCadenaRestante)
      .map{r => parsear(Resultado(resultadoParcial.getElementoParseado ++ List(r.getElementoParseado), r.getCadenaRestante ))}
      .recoverWith {_ => Try(resultadoParcial)}.get

  def apply(cadena: String): Try[Resultado[List[T]]] = Try(parsear(Resultado(List(),cadena)))

}