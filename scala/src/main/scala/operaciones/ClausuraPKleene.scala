package operaciones

import general._

import scala.util.{Failure, Success, Try}

class ClausuraPKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{
  def apply(cadena:String): Try[Resultado[List[T]]] = {
    
    var listaParcial: List[T] = List()
    var cadenaParcial = cadena
    var seguir = true

    //TODO - Concatenar apicacion de parser + clausura de Kleene
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
    Try(
        listaParcial match {
          case listaVacia if listaVacia.isEmpty => throw new Exception
          case listaNoVacia => new Resultado(listaParcial,cadenaParcial)
        }
     )
  }
}