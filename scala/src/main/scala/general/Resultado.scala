package general

class Resultado[T](elementoParseado:T, cadenaRestante:String){
  def getElementoParseado = elementoParseado
  def getCadenaRestante = cadenaRestante
}

object Resultado {
  def apply[T](elementoParseado:T, cadenaRestante:String): Resultado[T] = {
    new Resultado(elementoParseado, cadenaRestante)
  }
}