package general

case class Resultado[T](elementoParseado:T, cadenaRestante:String){
  def getElementoParseado = elementoParseado
  def getCadenaRestante = cadenaRestante
}

