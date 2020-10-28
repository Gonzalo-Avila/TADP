package operaciones

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._

class ClausuraKleene[T](parserOriginal:Parser[T]) extends Parser[List[T]]{
  def apply(cadena:String): Try[Resultado[List[T]]] = {
    
    var listaParcial: List[T] = List()
    var cadenaParcial = cadena
    var seguir = true
    
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
    Try(new Resultado(listaParcial,cadenaParcial))
  }
}