package operaciones

import general._

import scala.util.{Failure, Success, Try}

class ClausuraKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{

  def parsear(resultadoParcial: Resultado[List[T]]): Resultado[List[T]] =
    parserOriginal(resultadoParcial.getCadenaRestante)
      .map{r => parsear(Resultado(resultadoParcial.getElementoParseado ++ List(r.getElementoParseado), r.getCadenaRestante ))}
      .recoverWith {_ => Try(resultadoParcial)}.get

  def apply(cadena: String): Try[Resultado[List[T]]] = Try(parsear(Resultado(List(),cadena)))

  /*def apply(cadena:String): Try[Resultado[List[T]]] = {
    
    var listaParcial: List[T] = List()
    var cadenaParcial = cadena
    var seguir = true

    //TODO - Reemplazar while por recursividad - DONE
    while (seguir){
        val resultadoActual = parserOriginal.apply(cadenaParcial)
        resultadoActual match {
          case Failure(error) => seguir = false
          case Success(resultado) => {
            cadenaParcial = resultado.getCadenaRestante
            listaParcial = listaParcial ++ List(resultado.getElementoParseado)
          }
        }
    }
    Try(Resultado(listaParcial,cadenaParcial))
  }*/
}