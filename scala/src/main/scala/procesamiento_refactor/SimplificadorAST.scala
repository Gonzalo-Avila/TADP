package procesamiento_refactor

import scalafx.scene.paint.Color

object SimplificadorAST {

  /*def simplificar(nodo: Nodo): Nodo = {
    nodo match {

        //case Colores(c1,Colores(c2,hijo)) if c1.equals(c2) => Colores(c2,hijo)
        case Traslacion(x1,y1,Traslacion(x2,y2,hijo)) => Traslacion(x1+x2,y1+y2,hijo)
        case Rotacion(d1,Rotacion(d2,hijo)) => Rotacion(d1+d2,hijo)
        case Escala(x1,y1,Escala(x2,y2,hijo)) => Escala(x1*x2,y1*y2,hijo)
        case Traslacion(0,0,hijo) => hijo
        case Rotacion(0,hijo) => hijo
        case Escala(1,1,hijo) => hijo
        case Grupo(listaRotaciones @ List(Rotacion(d,_))) =>
          Rotacion(d,Grupo(listaRotaciones.map {rotacion => rotacion.asInstanceOf[Rotacion].hijo}))
        case Grupo(listaTraslaciones @ List(Traslacion(x,y,_))) =>
          Traslacion(x,y,Grupo(listaTraslaciones.map {traslacion => traslacion.asInstanceOf[Traslacion].hijo}))
        case Grupo(listaEscalas @ List(Escala(x,y,_))) =>
          Escala(x,y,Grupo(listaEscalas.map {escala => escala.asInstanceOf[Traslacion].hijo}))
        //case Grupo(listaColores @ List(Colores(color,_))) =>
         // Colores(color,Grupo(listaColores.map {listaColores => listaColores.asInstanceOf[Traslacion].hijo}))
        case otro => otro

      }
  }*/

}
