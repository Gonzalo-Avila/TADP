package combinators

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._


class SeparatedBy[T,X](parser1: Parser[T], parser2: Parser[X]){
   
      def apply (cadena:String): Try[Resultado[List[T]]] = {
        
        var listaParcial: List[T] = List()
        var cadenaParcial = cadena
        var seguir = true
        
        val primerResultado = (parser1 <~ parser2).apply(cadena)
        
        Try(
          primerResultado match {
            case Failure(error) => throw new Exception()
            case Success(resultado) => {
              listaParcial = listaParcial ++ List(resultado.getElementoParseado)
              cadenaParcial = resultado.getCadenaRestante
              while(seguir){
                (parser1 <~ parser2).apply(cadenaParcial) match {
                  case Failure(error) => {
                      parser1.apply(cadenaParcial) match{
                         case Failure(error) => seguir = false
                         case Success(resultado) => {
                           listaParcial = listaParcial ++ List(resultado.getElementoParseado)
                           cadenaParcial = resultado.getCadenaRestante
                           seguir = false
                         }
                      }
                  }
                  case Success(resultado) => {
                     listaParcial = listaParcial ++ List(resultado.getElementoParseado)
                     cadenaParcial = resultado.getCadenaRestante
                  }
                }
              }
              new Resultado (listaParcial,cadenaParcial)
            }
          }
        )
        
     }
  }