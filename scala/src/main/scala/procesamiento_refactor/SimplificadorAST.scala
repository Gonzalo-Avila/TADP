package procesamiento_refactor

import scalafx.scene.paint.Color

object SimplificarAST {

  def apply(nodo: Nodo): Nodo = {
    nodo match {

        case Colores(_, _, _ , Colores(r,g,b,hijo)) => Colores(r,g,b,SimplificarAST(hijo))
        case Traslacion(x1,y1,Traslacion(x2,y2,hijo)) => Traslacion(x1+x2,y1+y2,SimplificarAST(hijo))
        case Rotacion(d1,Rotacion(d2,hijo)) => Rotacion(d1+d2,SimplificarAST(hijo))
        case Escala(x1,y1,Escala(x2,y2,hijo)) => Escala(x1*x2,y1*y2,SimplificarAST(hijo))
        case Traslacion(0,0,hijo) => SimplificarAST(hijo)
        case Rotacion(0,hijo) => SimplificarAST(hijo)
        case Escala(1,1,hijo) => SimplificarAST(hijo)
        case Grupo(listaRotaciones @ Rotacion(d,_) :: List(Rotacion(_,_))) if listaRotaciones.forall(r => r.asInstanceOf[Rotacion].degrees == d)
        => Rotacion(d,Grupo(listaRotaciones.map {rotacion => SimplificarAST(rotacion.asInstanceOf[Rotacion].hijo)}))
        case Grupo(listaTraslaciones @ Traslacion(x,y,_) :: List(Traslacion(_,_,_))) if listaTraslaciones.forall(t => t.asInstanceOf[Traslacion].x == x
          && t.asInstanceOf[Traslacion].y == y)
        => Traslacion(x,y,Grupo(listaTraslaciones.map {traslacion => SimplificarAST(traslacion.asInstanceOf[Traslacion].hijo)}))
        case Grupo(listaEscalas @ Escala(x,y,_) :: List(Escala(_,_,_))) if listaEscalas.forall(e => e.asInstanceOf[Escala].x == x
          && e.asInstanceOf[Escala].y == y)
        => Escala(x,y,Grupo(listaEscalas.map {escala => SimplificarAST(escala.asInstanceOf[Escala].hijo)}))
        case Grupo(listaColores @ Colores(r,g,b,_)::List(Colores(_,_,_,_)))
          if listaColores.forall(c => c.asInstanceOf[Colores].red == r
            && c.asInstanceOf[Colores].blue == b
            &&  c.asInstanceOf[Colores].green == g)
        => Colores(r,g,b,Grupo(listaColores.map {listaColores => SimplificarAST(listaColores.asInstanceOf[Colores].hijo)}))
        case otro => otro
      }
  }

}
