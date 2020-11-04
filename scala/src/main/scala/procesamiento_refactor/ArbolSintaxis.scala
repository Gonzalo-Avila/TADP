package procesamiento_refactor

import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter


trait Nodo {

  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter
  def simplificar:Nodo = this
  def imprimir

}



case class Grupo(hijos: List[Nodo]) extends Nodo{
  def imprimir = {
    hijos.foreach(h => h.imprimir)
  }

  def extraerRepetidos: Nodo = this match {

    case Grupo(listaRotaciones @ Rotacion(d,_) :: List(Rotacion(_,_))) if listaRotaciones.forall(r => r.asInstanceOf[Rotacion].degrees == d)
     => Rotacion(d,Grupo(listaRotaciones.map {rotacion => rotacion.asInstanceOf[Rotacion].hijo}))

    case Grupo(listaTraslaciones @ Traslacion(x,y,_) :: List(Traslacion(_,_,_))) if listaTraslaciones.forall(t => t.asInstanceOf[Traslacion].x == x
      && t.asInstanceOf[Traslacion].y == y)
     => Traslacion(x,y,Grupo(listaTraslaciones.map {traslacion => traslacion.asInstanceOf[Traslacion].hijo}))

    case Grupo(listaEscalas @ Escala(x,y,_) :: List(Escala(_,_,_))) if listaEscalas.forall(e => e.asInstanceOf[Escala].x == x
      && e.asInstanceOf[Escala].y == y)
    => Escala(x,y,Grupo(listaEscalas.map {escala => escala.asInstanceOf[Escala].hijo}))

    case Grupo(listaColores @ Colores(r,g,b,_)::List(Colores(_,_,_,_)))
      if listaColores.forall(c => c.asInstanceOf[Colores].red == r
      && c.asInstanceOf[Colores].blue == b
      &&  c.asInstanceOf[Colores].green == g)
      => Colores(r,g,b,Grupo(listaColores.map {listaColores => listaColores.asInstanceOf[Colores].hijo}))

    case grupoNormal => grupoNormal
  }

  override def simplificar: Nodo = Grupo(hijos.map{m => m.simplificar}).extraerRepetidos

  def aplicar(adapter:TADPDrawingAdapter): TADPDrawingAdapter = hijos.foldLeft (adapter) {(adap,hijo) => hijo.aplicar(adap)}
}

case class Rectangulo(topLeft: (Double,Double), bottomRight: (Double,Double)) extends Nodo {
  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.rectangle(topLeft,bottomRight)
  def imprimir = print(".rectangle(" + topLeft + "," + bottomRight +")")
}

case class Triangulo(p1: (Double,Double), p2: (Double,Double), p3: (Double,Double)) extends Nodo {
  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.triangle(p1,p2,p3)
  def imprimir = print(".triangle(" + p1 + "," + p2 + "," + p3 + ")")
}

case class Circulo(center: (Double,Double), radius: Double) extends Nodo {
 def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.circle(center,radius)
  def imprimir = print(".circle(" + center + "," + radius + ")")
}

case class Colores(red: Int, green: Int, blue: Int, hijo: Nodo) extends Nodo{
  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = hijo.aplicar(adapter.beginColor(Color.rgb(red,green,blue))).end()

  def imprimir = {
    print(".beginColor(" + red + "," + green + "," + blue + ")")
    hijo.imprimir
    print(".end")
  }
  def reemplazarPorOtro: Nodo = this match {
    case Colores(_, _, _ , otroColor @ Colores(_,_,_,_)) => otroColor
    case colorNormal => colorNormal
  }

  override def simplificar: Nodo = Colores(red,green,blue,hijo.simplificar).reemplazarPorOtro


}

case class Escala(x: Double, y:Double, hijo: Nodo) extends Nodo{
  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = hijo.aplicar(adapter.beginScale(x,y)).end()

  def imprimir = {
    print(".beginScale(" + x + "," + y +")")
    hijo.imprimir
    print(".end")
  }

  def simplificarEscala: Nodo = this match {
    case Escala(x1,y1,Escala(x2,y2,hijo)) => Escala(x1*x2,y1*y2,hijo)
    case Escala(1,1,hijo) => hijo
    case escalaNormal => escalaNormal
  }

  override def simplificar: Nodo = Escala(x,y,hijo.simplificar).simplificarEscala
}

case class Rotacion(degrees: Double, hijo: Nodo) extends Nodo{
  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = hijo.aplicar(adapter.beginRotate(degrees)).end()

  def imprimir = {
    print(".beginRotate(" + degrees + ")")
    hijo.imprimir
    print(".end")
  }

  def simplificarRotacion: Nodo = this match {
    case Rotacion(d1,Rotacion(d2,hijo)) => Rotacion(d1+d2,hijo)
    case Rotacion(0,hijo) => hijo
    case rotacionNormal => rotacionNormal
  }

  override def simplificar: Nodo = Rotacion(degrees,hijo.simplificar).simplificarRotacion
}

case class Traslacion(x: Double, y:Double, hijo: Nodo) extends Nodo{
  def aplicar(adapter: TADPDrawingAdapter): TADPDrawingAdapter = hijo.aplicar(adapter.beginTranslate(x,y)).end()

  def imprimir = {
    print(".beginTraslate(" + x + "," + y + ")")
    hijo.imprimir
    print(".end")
  }

  def simplificarTraslacion: Nodo = this match {
    case Traslacion(x1,y1,Traslacion(x2,y2,hijo)) => Traslacion(x1+x2,y1+y2,hijo)
    case Traslacion(0,0,hijo) => hijo
    case traslacionNormal => traslacionNormal
  }

  override def simplificar: Nodo = Traslacion(x,y,hijo.simplificar).simplificarTraslacion
}
