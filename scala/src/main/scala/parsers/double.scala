package parsers

import general._

import scala.util.Try

//Nico | Luego de desarrollar el *satisfies*, ver si se puede usar para evitar las validaciones

 object double extends Parser [Double]{
  def apply(cadena: String): Try[Resultado[Double]] = {
    val cadenaSeparada: Array[String] = cadena.split('.')

    Try(
      cadenaSeparada match {
        case cad if cad.head.isEmpty => throw new Exception()
        case _ =>
          val resultadoParteEntera = integer.apply(cadenaSeparada.head)
          val resultadoParteDecimal = integer.satisfies((entero => entero >= 0)).apply(cadenaSeparada.tail.head)
          println(cadenaSeparada.tail.head)

          val elementoParseado = (resultadoParteEntera.get.getElementoParseado + "." + resultadoParteDecimal.get.getElementoParseado).toDouble
          var cadenaRestante = resultadoParteEntera.get.getCadenaRestante + resultadoParteDecimal.get.getCadenaRestante
          //Agrego otros elementos de la lista a la cadenaRestante en caso de que haya más de un '.' en la cadena a parsear.
          if (cadenaSeparada.size > 2) {
            cadenaRestante = cadenaRestante + "." + cadenaSeparada.tail.tail.mkString(".")
          }

          new Resultado(elementoParseado, cadenaRestante)
      }
    )
  }
}