package parsersImagenes

import general._
import parsers._
import scalafx.scene.paint.Color

import scala.util.Try

object parserTexto {

  def armarNodo (resultado: Try[Resultado[((List[String], List[String]), (List[String], List[String]))]]): Nodo = {
    resultado.get.getElementoParseado._1._1(0) match {
      case "escala" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toDouble
        val param2 = resultado.get.getElementoParseado._2._1(1).toDouble
        Nodo(Escala(param1,param2))
      }
      case "color" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toInt
        val param2 = resultado.get.getElementoParseado._2._1(1).toInt
        val param3 = resultado.get.getElementoParseado._2._1(2).toInt
        Nodo(Colores(Color.rgb(param1,param2,param3)))
      }
      case "rotacion" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toDouble
        Nodo(Rotacion(param1))
      }
      case "traslacion" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toDouble
        val param2 = resultado.get.getElementoParseado._2._1(1).toDouble
        Nodo(Traslacion(param1,param2))
      }
      case "rectangulo" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toDouble
        val param2 = resultado.get.getElementoParseado._2._1(1).toDouble
        val param3 = resultado.get.getElementoParseado._2._1(2).toDouble
        val param4 = resultado.get.getElementoParseado._2._1(3).toDouble
        Nodo(Rectangulo((param1,param2),(param3,param4)))
      }
      case "triangulo" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toDouble
        val param2 = resultado.get.getElementoParseado._2._1(1).toDouble
        val param3 = resultado.get.getElementoParseado._2._1(2).toDouble
        val param4 = resultado.get.getElementoParseado._2._1(3).toDouble
        val param5 = resultado.get.getElementoParseado._2._1(4).toDouble
        val param6 = resultado.get.getElementoParseado._2._1(5).toDouble
        Nodo(Triangulo((param1,param2),(param3,param4),(param5,param6)))
      }
      case "circulo" => {
        val param1 = resultado.get.getElementoParseado._2._1(0).toDouble
        val param2 = resultado.get.getElementoParseado._2._1(1).toDouble
        val param3 = resultado.get.getElementoParseado._2._1(2).toDouble
        Nodo(Circulo((param1,param2),param3))
      }
    }
  }

 /* def armarNodo2(resultado: Try[Resultado[((List[String], List[String]), (List[String], List[String]))]]): Nodo ={
    val nombre = (resultado.get.getElementoParseado._1._1(0).head.toUpper + resultado.get.getElementoParseado._1._1(0).tail.toUpperCase())

    val clase  = Class.forName(nombre).newInstance.asInstanceOf[{ def armarNodo(resultado: Try[Resultado[((List[String], List[String]), (List[String], List[String]))]]): Nodo }]
    clase.armarNodo(resultado.get.getElementoParseado._2._1)
    //esta implementacion requiere que cada clase de estas tenga el metodo armarNodo.
  }
*/

  def armarNodo3(resultado: Try[Resultado[((List[String], List[String]), (List[String], List[String]))]]): Nodo ={
    resultado.get.getElementoParseado._1._1(0) match {
      case "escala" => {
        val parametros = resultado.get.getElementoParseado._2._1.map { p => p.toDouble }
        Nodo(Escala(parametros(0),parametros(1)))
      }
      case "color" => {
        val listInts = resultado.get.getElementoParseado._2._1.map { p => p.toInt }
        Nodo(Colores(Color.rgb(listInts(0),listInts(1),listInts(2))))
      }
      case "rotacion" => {
        Nodo(Rotacion(resultado.get.getElementoParseado._2._1(0).toDouble))
      }
      case "traslacion" => {
        val parametros = resultado.get.getElementoParseado._2._1.map{p => p.toDouble}
        Nodo(Traslacion(parametros(0),parametros(1)))
      }
      case "rectangulo" => {
        val parametros = resultado.get.getElementoParseado._2._1.map{p => p.toDouble}
        Nodo(Rectangulo((parametros(0),parametros(1)),(parametros(2),(parametros(3)))))
      }
      case "triangulo" => {
        val parametros = resultado.get.getElementoParseado._2._1.map{p => p.toDouble}
        Nodo(Triangulo((parametros(0),parametros(1)),(parametros(2),parametros(3)),(parametros(4),parametros(5))))
      }
      case "circulo" => {
        val parametros = resultado.get.getElementoParseado._2._1.map{p => p.toDouble}
        Nodo(Circulo((parametros(0),parametros(1)),parametros(2)))
      }
    }
  }

  def parsearGrupo(cadena: String, nuevoNodo: Nodo): String = {
    var seguir = true
    var cadenaRestante = cadena
    //cadenaRestante = char('(').apply(cadenaRestante).get.getCadenaRestante
    while (seguir) {
      cadenaRestante = parsear(cadenaRestante, nuevoNodo)
      seguir = char(',').apply(cadenaRestante).isSuccess
      if(seguir){
        cadenaRestante = char(',').apply(cadenaRestante).get.getCadenaRestante
      }

    }
    cadenaRestante = char(')').apply(cadenaRestante).get.getCadenaRestante
    cadenaRestante
  }

  def parsear(cadena: String, nodoActual: Nodo): String = {

    var cadenaRestante = cadena

    val resultado = anyTransformation.apply(cadenaRestante)

    if (resultado.isSuccess) {
      val nuevoNodo = armarNodo(resultado)
      nodoActual.agregarHijo(nuevoNodo)
      cadenaRestante = resultado.get.getCadenaRestante
      cadenaRestante = char('(').apply(cadenaRestante).get.getCadenaRestante
      cadenaRestante = parsear(cadenaRestante,nuevoNodo)
      cadenaRestante = char(')').apply(cadenaRestante).get.getCadenaRestante
    }
    else {
      val resultado = parserGrupo.apply(cadena)
      if (resultado.isSuccess) {
        cadenaRestante = parsearGrupo(resultado.get.getCadenaRestante,nodoActual)
      }
      else {
        val resultado = anyImage.apply(cadena)
        if (resultado.isSuccess) {
          val nuevoNodo = armarNodo(resultado)
          nodoActual.agregarHijo(nuevoNodo)
          cadenaRestante = resultado.get.getCadenaRestante
        }
        else {
          throw new Exception("El texto es invalido")
        }
      }
    }
    cadenaRestante
  }

  def apply(cadena: String): Nodo = {

    val raiz = Nodo(Raiz)

    var cadenaParcial = cadena
    while(!cadenaParcial.isEmpty){
      cadenaParcial = parsear(cadenaParcial,raiz)
    }

    raiz

  }
}
