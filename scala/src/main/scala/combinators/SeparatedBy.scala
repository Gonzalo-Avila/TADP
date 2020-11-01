package combinators

import general._

import scala.util.{Failure, Success, Try}


class SeparatedBy[T,X](parserContenido: Parser[T], parserSeparador: Parser[X]) extends Parser[List[T]]{

  //TODO - Hacer algo del estilo: ((contentParser <~ sepParser) <|> contentParser <~ ).+ - DONE
  //Primero intenta matchear con un patron CADENA-SEPARADOR-CADENA, si no puede intenta machear con CADENA-FIN
  def apply (cadena:String): Try[Resultado[List[T]]] =
    ((parserContenido.map(r => List(r)) <> (parserSeparador ~> parserContenido).+).map{r => r._1 ++ r._2}
      <|> parserContenido.map{r => List(r)}.isFinal).apply(cadena)

     /* def apply (cadena:String): Try[Resultado[List[T]]] = {

        var listaParcial: List[T] = List()
        var cadenaParcial = cadena
        var seguir = true

        val primerResultado = (parserContenido <~ parserSeparador).apply(cadena)

        Try(
          primerResultado match {
            case Failure(error) => throw new Exception()
            case Success(resultado) => {
              listaParcial = listaParcial ++ List(resultado.getElementoParseado)
              cadenaParcial = resultado.getCadenaRestante
              while(seguir){
                (parserContenido <~ parserSeparador).apply(cadenaParcial) match {
                  case Failure(error) => {
                      parserContenido.apply(cadenaParcial) match{
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
              Resultado (listaParcial,cadenaParcial)
            }
          }
        )

     }*/
  }