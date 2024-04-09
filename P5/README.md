
# P5 Graficos 2D Parte 1

## Descripción
Objetivo: Usar los distintos Shapes que ofrece y la ventaja de tener objetos asociados a las formas. Ampliamos la practica 4 para añadirle nuevas funcionalidades.


## Formas 

Crear una aplicacion llamada formas con java with ant

probamos las formas u las representamos en un JFrame
- Line2D
- Rectangle2D
- RoundRectangle2D
- Ellipse2D
- Arc2D
- QuadCurve2D
- CubicCurve2D

usa el trazo libre (general path): smiley face 

trabaja con la forma area, que permite definir nuevas figuras mediante la composicion de otras
otro smiley face 

## Ampliando P4
En formas hemos usado coordenadas fijas para probar las distintas formas. Vamos a mejorar la funcionalidad de las formas

### Mantener las formas : vector de Shape

usa un vector de formas para tener variaas figuras en el lienzo "cuando dibujas varias formas, luegos cuando cambia el color cambia a todas tambien"


### Moviendo las figuras 
Mover las figuras ya dibujadas

caso rectangulo

añade en la ventana principal una JChekBoxa la que llamaremos "mover"
, cuando este seleccionada indica que la interaccion del usuario será para mover las figuras  (secuencia pressed->dragged->released)

shape tiene el metodo contains(Point2D), comprueba si unn punto esta dentro de la correspondiente forma

```java
private Shape figuraSeleccionada(Point2D p){
    for(Shape s:vShape)
    if(s.contains(p)) return s;
        return null;
    }
```

para rectangulo usar setRect
                ((Rectangle2D)forma).setRect(evt.getPoint().getX(), evt.getPoint().getY(), ((Rectangle2D)forma).getWidth(), ((Rectangle2D)forma).getHeight());






