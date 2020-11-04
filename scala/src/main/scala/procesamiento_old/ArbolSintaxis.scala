package procesamiento_old

import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter


trait Nodo {


  var hijos: List[Nodo]= List()
  //var padre: Nodo = ???

  def agregarHijo(hijo: Nodo): Unit ={
    hijos = hijos ++ List(hijo)
  }

  def agregarHijos(nuevosHijos: List[Nodo]): Unit ={
    nuevosHijos.foreach(h => this.agregarHijo(h))
  }

  def preOrden(adapter: TADPDrawingAdapter): TADPDrawingAdapter =
    this.aplicarPost(hijos.foldLeft (this.aplicarPre(adapter)) {(adap,hijo) => hijo.preOrden(adap)})

  def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter
  def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter

  def imprimirPre = print("")
  def imprimirPost = print("")

  def imprimir(): Unit = {
    this.imprimirPre
    hijos.foreach(hijo => hijo.imprimir)
    this.imprimirPost
  }

  def esColor() = {
        this match{
          case Colores(_) => true
          case _ => false
        }
  }
  def esTraslacion() = {
    this match{
      case Traslacion(_,_) => true
      case _ => false
    }
  }
  def esRotacion() = {
    this match{
      case Rotacion(_) => true
      case _ => false
    }
  }

  def esEscala() = {
    this match{
      case Escala(_,_) => true
      case _ => false
    }
  }

  //def hayDosHijosIguales():Boolean = {
  //  hijos.exists(h => hijos.)
  //}

  def sonIdenticos(nodo1: Nodo, nodo2: Nodo)= {
    (nodo1,nodo2) match {
      case (Colores(color1),Colores(color2)) => (color1.blue,color1.green,color1.red) ==  (color2.blue,color2.green,color2.red)
      case (Traslacion(x1,y1),Traslacion(x2,y2)) => (x1,y1) ==  (x2,y2)
      case (Escala(x1,y1),Escala(x2,y2)) => (x1,y1) ==  (x2,y2)
      case (Rotacion(degrees1),Rotacion(degrees2)) => degrees1 ==  degrees2
    }
  }
}

class Raiz extends Nodo{
  override def imprimirPre = print("adapter")
}


case class Rectangulo(topLeft: (Double,Double), bottomRight: (Double,Double)) extends Nodo {
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.rectangle(topLeft,bottomRight)
  override def imprimirPre = print(".rectangle(" + topLeft + "," + bottomRight +")")
}

case class Triangulo(p1: (Double,Double), p2: (Double,Double), p3: (Double,Double)) extends Nodo {
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.triangle(p1,p2,p3)
  override def imprimirPre = print(".triangle(" + p1 + "," + p2 + "," + p3 + ")")
}

case class Circulo(center: (Double,Double), radius: Double) extends Nodo {
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.circle(center,radius)
  override def imprimirPre = print(".circle(" + center + "," + radius + ")")
}

case class Colores(color: Color) extends Nodo{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginColor(color)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
  override def imprimirPre = print(".beginColor(" + color.red + "," + color.green + "," + color.blue + ")")
  override def imprimirPost = print(".end")
}

case class Escala(x: Double, y:Double) extends Nodo{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginScale(x,y)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
  override def imprimirPre = print(".beginScale(" + x + "," + y +")")
  override def imprimirPost = print(".end")
}

case class Rotacion(degrees: Double) extends Nodo{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginRotate(degrees)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
  override def imprimirPre = print(".beginRotate(" + degrees + ")")
  override def imprimirPost = print(".end")
}

case class Traslacion(x: Double, y:Double) extends Nodo{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginTranslate(x,y)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
  override def imprimirPre = print(".beginTraslate(" + x + "," + y + ")")
  override def imprimirPost = print(".end")
}
