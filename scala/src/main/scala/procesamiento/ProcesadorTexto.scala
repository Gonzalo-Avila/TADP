package procesamiento

import general.Resultado
import parsers.char
import parsersImagenes.{AnyImage, AnyTransformation, ParserGrupo}
import scalafx.scene.paint.Color

import scala.util.Try

object ProcesadorTexto {

  //TODO - llevar esta logica al parser, que los parsers devuelvan los objetos en el resultado
  def armarNodo(resultado: Try[Resultado[((List[String], List[String]), (List[String], List[String]))]]): Nodo = {
    val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
    resultado.get.getElementoParseado._1._1(0) match {
      case "escala" => Nodo(Escala(parametros(0), parametros(1)))
      case "color" => Nodo(Colores(Color.rgb(parametros(0).toInt, parametros(1).toInt, parametros(2).toInt)))
      case "rotacion" => Nodo(Rotacion(parametros(0)))
      case "traslacion" => Nodo(Traslacion(parametros(0), parametros(1)))
      case "rectangulo" => Nodo(Rectangulo((parametros(0), parametros(1)), (parametros(2), (parametros(3)))))
      case "triangulo" => Nodo(Triangulo((parametros(0), parametros(1)), (parametros(2), parametros(3)), (parametros(4), parametros(5))))
      case "circulo" => Nodo(Circulo((parametros(0), parametros(1)), parametros(2)))
    }
  }

  def limpiarEspacios(cadena: String): String = (char(' ') <|> char('\n') <|> char('\t')).*.apply(cadena).get.getCadenaRestante

  def parsearGrupo(cadena: String, nuevoNodo: Nodo): String = {
    var seguir = true
    var cadenaRestante = cadena
    while (seguir) {
      cadenaRestante = parsear(cadenaRestante, nuevoNodo)
      seguir = char(',').apply(cadenaRestante).isSuccess
      if (seguir) {
        cadenaRestante = char(',').apply(cadenaRestante).get.getCadenaRestante
      }
    }
    char(')').apply(cadenaRestante).get.getCadenaRestante

  }

  def parsear(cadena: String, nodoActual: Nodo): String = {

    var cadenaRestante = cadena.filterNot(caracter => caracter.isWhitespace || caracter == '\n' || caracter == '\t')
    val resultado = AnyTransformation.apply(cadenaRestante)
    if (resultado.isSuccess) {
      val nuevoNodo = armarNodo(resultado)
      nodoActual.agregarHijo(nuevoNodo)
      cadenaRestante = resultado.get.getCadenaRestante
      cadenaRestante = char('(').apply(cadenaRestante).get.getCadenaRestante
      cadenaRestante = parsear(cadenaRestante, nuevoNodo)
      cadenaRestante = char(')').apply(cadenaRestante).get.getCadenaRestante
    }
    else {
      val resultado = ParserGrupo.apply(cadena)
      if (resultado.isSuccess) {
        cadenaRestante = parsearGrupo(resultado.get.getCadenaRestante, nodoActual)
      }
      else {
        val resultado = AnyImage.apply(cadena)
        if (resultado.isSuccess) {
          val nuevoNodo = armarNodo(resultado)
          nodoActual.agregarHijo(nuevoNodo)
          cadenaRestante = resultado.get.getCadenaRestante
        }
        else {
          throw new Exception("Descripción de imágen inválida.")
        }
      }
    }
    cadenaRestante
  }

  def apply(cadena: String): Nodo = {

    val raiz = Nodo(Raiz)

    var cadenaParcial = cadena
    while (!cadenaParcial.isEmpty) {
      cadenaParcial = parsear(cadenaParcial, raiz)
    }

    raiz

  }
}
