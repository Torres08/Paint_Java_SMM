# P6 Graficos 2D Parte 2

## Descripción
Seguir viendo las principales caracteristicas de la programacion de graficos usando la tecnologia Java2D (atributos de dibujo). Luego ampliamos la practica 5

## FormasAvanzadas
Crea una nueva aplicacion, que incorpore un JFrame donde sobrecargamos el metodo paint e incorporemos el codigo para ir modificando los diferentes atributos de dibujo.
override al paint. Vamos a ver algunos ejemplos. 

tutorial 2D Graphics java (entre parentesis estan los tutoriales de java sobre cada atributo)

- Trazo ("Stroking and Filling Graphics Primitives”)
- Relleno ("Stroking and Filling Graphics Primitives”)
- Composicion (“Compositing Graphics”)
- Transformaciones (“Transforming Shapes, Text, and Images”)
  - escala a 0.25 y rota 45 grados el rectangulo
  - tras las transformaciones anteriores, dejar la identidad como transformacion activa
- Fuente ("Working with Text APIs”, “Using Text Attributes to Style Text”)
- Renderizacion (“Controlling Rendering Quality”)
- Recorte (“Clipping the Drawing Region”)

tambien se le han introudciodo algunas mejoras 
1. Incorporar a nuestra aplicación la lista de fuentes disponible, de forma que el texto aparezca con la fuente seleccionada por el usuario
2. Efecto “ventana”: hacer que el área de recorte se mueva siguiendo al ratón

## Avanzando la P5
Vamos a incorporar algunos atributos de dibujo que ofrece Java2D 

- nuevo aspecto visual
- boton grosor de trazo
  - usando un spinner
- transparencia
  - usando checkbox
- alisado de las formas 
  - usando checkbox

vamos a ampliar la clase lienzo, tendremos que definir variables miembro de la clase Lienzo que representa nueva propiedades de grosor, transparencia y alisado

```bash
// para grosor del trazo
Stroke trazo = new BasicStroke();
```

implementa tambien los metodos set/get para modificar las propiedades 

```bash
public void setGrosor(int grosor) {
  this.stroke = new BasicStroke(grosor);
}
```

en el paint del lienzo, habra que incluir el código que active los atributos correspondientes
