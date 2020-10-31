package procesamiento

import scalafx.scene.paint.Color

object SimplificadorAST {

  def simplificarArbol(raiz: Nodo): Unit ={
    raiz.hijos.foreach(hijo => simplificarArbol(hijo))
    simplificarNodo(raiz)
  }

  def simplificarNodo(nodo: Nodo): Unit ={
    nodo.elemento match {
      case Colores(color) => {
      }
    }
  }

}
