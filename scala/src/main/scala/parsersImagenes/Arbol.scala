package parsersImagenes

import java.awt.geom.Point2D

import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

class Nodo (elemento: Aplicable) {
  var hijos: List[Nodo]= List()

  def agregarHijo(hijo: Nodo): Unit ={
    hijos = hijos ++ List(hijo)
  }

  def preOrden(adapter: TADPDrawingAdapter): TADPDrawingAdapter ={
    var adap = adapter
    adap = elemento.aplicarPre(adap)
    hijos.foreach(hijo => adap = hijo.preOrden(adap))
    elemento.aplicarPost(adap)
  }
}

object Nodo {
  def apply(elemento: Aplicable): Nodo = new Nodo(elemento)
}

trait Aplicable{
  def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter
  def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter
}

object Raiz extends Aplicable{}

class Rectangulo(topLeft: (Double,Double), bottomRight: (Double,Double)) extends Aplicable {
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.rectangle(topLeft,bottomRight)
}
object Rectangulo {
  def apply(topLeft: (Double,Double), bottomRight: (Double,Double)): Rectangulo = new Rectangulo(topLeft, bottomRight)
}
class Triangulo(p1: (Double,Double), p2: (Double,Double), p3: (Double,Double)) extends Aplicable {
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.triangle(p1,p2,p3)
}
object Triangulo {
  def apply(p1: (Double,Double), p2: (Double,Double), p3: (Double,Double)): Triangulo = new Triangulo(p1, p2, p3)
}

class Circulo(center: (Double,Double), radius: Double) extends Aplicable {
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.circle(center,radius)
}
object Circulo {
  def apply(center: (Double,Double), radius: Double): Circulo = new Circulo(center,radius)
}

class Colores(color: Color) extends Aplicable{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginColor(color)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
}

object Colores {
  def apply(color: Color): Colores = new Colores(color)
}

class Escala(x: Double, y:Double) extends Aplicable{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginScale(x,y)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
}

object Escala {
  def apply(x: Double, y:Double): Escala = new Escala(x,y)
}

class Rotacion(degrees: Double) extends Aplicable{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginRotate(degrees)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
}
object Rotacion {
  def apply(degrees: Double): Rotacion = new Rotacion(degrees)
}

class Traslacion(x: Double, y:Double) extends Aplicable{
  override def aplicarPre(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.beginTranslate(x,y)
  override def aplicarPost(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.end()
}
object Traslacion {
  def apply(x: Double, y:Double): Traslacion = new Traslacion(x,y)
}
