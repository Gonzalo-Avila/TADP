package parsers

import scala.io.Source
import scala.util.{Try,Success,Failure}
import general._

//Nico | Luego de desarrollar el *satisfies*, ver si se puede usar para evitar las validaciones

 object double extends Parser [Double]{
  def apply(cadena: String): Try[Resultado[Double]] = {
    val cadenaSeparada: Array[String] = cadena.split('.')

    validarQueLaParteEnteraNoSeaVacio(cadenaSeparada.head)

    val resultadoParteEntera = integer.apply(cadenaSeparada.head)
    val resultadoParteDecimal = integer.satisfies((entero => entero >= 0)).apply(cadenaSeparada.tail.mkString)

    val elementoParseado = (resultadoParteEntera.get.getElementoParseado + "." + resultadoParteDecimal.get.getElementoParseado).toDouble
    val cadenaRestante = resultadoParteEntera.get.getCadenaRestante + resultadoParteDecimal.get.getCadenaRestante

    Try(new Resultado(elementoParseado, cadenaRestante))
  }

  private def validarQueLaParteEnteraNoSeaVacio(parteEntera: String) = {
    if (parteEntera.isEmpty) throw new Exception()
  }

}