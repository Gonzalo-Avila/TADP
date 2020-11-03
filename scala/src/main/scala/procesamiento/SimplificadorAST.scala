package procesamiento

import scalafx.scene.paint.Color

object SimplificadorAST {

  def sumarTraslaciones(t1: Traslacion, t2: Traslacion): Traslacion = {
    val traslacionResultante = Traslacion(t1.x+t2.x, t1.y+t2.y)
    traslacionResultante.agregarHijos(t2.hijos)
    traslacionResultante
  }

  def sumarRotaciones(r1: Rotacion, r2: Rotacion): Rotacion = {
    val rotacionResultante = Rotacion(r1.degrees + r2.degrees)
    rotacionResultante.agregarHijos(r2.hijos)
    rotacionResultante
  }

  def multiplicarEscalas(e1: Escala, e2: Escala): Escala = {
    val escalaResultante = Escala(e1.x*e2.x, e1.y*e2.y)
    escalaResultante.agregarHijos(e2.hijos)
    escalaResultante
  }

  def simplificarNodo(nodo: Nodo): Nodo = {
    nodo match {

      //case nodo if nodo.hijos.isEmpty => nodo

      case Colores(_) if nodo.hijos.size == 1 && nodo.hijos.head.esColor() => nodo.hijos.head

      case Traslacion(_,_) if nodo.hijos.size == 1 && nodo.hijos.head.esTraslacion() =>
        sumarTraslaciones(nodo.asInstanceOf[Traslacion],nodo.hijos.head.asInstanceOf[Traslacion])

      case Rotacion(_) if nodo.hijos.size == 1 && nodo.hijos.head.esRotacion() =>
        sumarRotaciones(nodo.asInstanceOf[Rotacion],nodo.hijos.head.asInstanceOf[Rotacion])

      case Escala(_,_) if nodo.hijos.size == 1 && nodo.hijos.head.esEscala()  =>
        multiplicarEscalas(nodo.asInstanceOf[Escala],nodo.hijos.head.asInstanceOf[Escala])

      case Traslacion(0,0) if  nodo.hijos.size == 1 => nodo.hijos.head

      case Rotacion(0) if  nodo.hijos.size == 1 => nodo.hijos.head

      case Escala(1,1) if  nodo.hijos.size == 1 => nodo.hijos.head

      case _ => nodo

    }
  }

  def simplificarArbol(nodoOriginal: Nodo): Nodo = {
    val nodoSimplificado = simplificarNodo(nodoOriginal)
    nodoSimplificado.hijos = nodoSimplificado.hijos.map(hijo => simplificarArbol(hijo))
    nodoSimplificado
  }


}
