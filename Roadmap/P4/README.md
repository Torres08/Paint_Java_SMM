
# P4 - Paint Básico

## Descripción

Objetivo: Hacer una aplicación sencilla para dibujar formas básicas
- reusar la interfaz de la practica 2
- recuerda en el form de Ventana principal poner One Inner Class (para entender el codigo que genera)

Forma de la aplicacion
![Texto Alternativo](Imagenes/2024-03-03_17-05.png)

- [x] usuario puede elegir entre tres formas de dibujo: lineas rectángulo y eclipse, que se seleccionara desde la barra de herramientas
	- siempre esta seleccionado aquel correspondiente a la forma activa , la barra de estado también indica la forma activa

- [x]  usuario puede elegir entre un conjunto de colores predeterminados, y si esta relleno o no
	- el lienzo central solo muestra la ultima figura formada, no hace falta un vector de formas

- [x]  Tendrá una barra menu, donde se incluche la opcion archivo, que tendra a su vez 3 opciones: Nuevo, abrir y guardar 
	- Nuevo: debe borrar la forma que esté dibujada
	- Abrir y guardar deberán lanzar el dialogo correspondiente (si bien no se abrirán ni guardarán archivos)

- [x] Para el panel de dibujo central, se recomienda crear una clase JPanel llamada Lienzo, esta clase deberá incluir 
	- Info sobre la forma activa y los atributos (color y relleno) con los que pintar
		- define los metodos set/get para modificar estas propiedades
	- Metodo paint sobrecargado , incluye el codigo donde se pintara las distintas formas con sus atributos
	- gestion de eventos de raton vinculados al proceso de dibujo

- [x] Para lanzar los dialogos de Abrir y guardar usar la clase JFileChoooser
	el siguiente codigo lanzaria el dialogo abrr (para cerrar seria lo mismo pero usando showSaveDialog)

```java
JFileChooser dlg = new JFileChooser();
int resp = dlg.showOpenDialog(this);
if( resp == JFileChooser.APPROVE_OPTION) {
    File f = dlg.getSelectedFile();
    //Código
}
```

## Apendice: Incluir la clase panel propia "Lienzo"

- usar en el proyecto border layout
- para crear la clase lienzo, usa la plantilla de NetBenas: dale a crear, JPanel Form  y la llamamos lienzo 
- tras haberlo creado arrastralo a donde quieras situarlo 

**Dibujando en el lienzo**
creada la clase lienzo, podemos incorporarle nuevas variables y metodos 
- podemos replicar lo realizado en la practica 3 para dibujar puntos y lineas sobre un lienzo

pasos 
1. sobrecargar el método paint(Graphics), incluye en el el codigo a dibujar
2. manejar los eventos de raton asociados al clicl, pressed, releaased y dragged
3. Vamos a trabajar con Graphics2D y los objetos Shape

ejemplo sobrecarga del paint(Graphics)
```java
Shape forma = new Line2D.Float(50,50,200,200);
public void paint(Graphics g){
	super.paint(g);
	Graphics2D g2d = (Graphics2D)g;
	g2d.draw(forma);
}
```

si ejecutamos aparecería nuestro lienzo en el centro de la ventana con una línea uniendo los puntos (50,50) y (200,200) S combinamos lo anterior con la gestion de eventos

```java
public void mousePressed(MouseEvent evt){
	forma = new Line2D.Float(evt.getPoint(), evt.getPoint());
}
public void mouseDraggued(MouseEvent evt){
	((Line2D)forma).setLine(((Line2D)forma).getP1(), evt.getPoint());
	this.repaint();
}
```

tendremos que, al hacer un arrastrar y soltar, se irá pintando una línea 

**Cambiando el color**
para cambiar el color con graphics2D usamos setPaint(Color)

```java
public void paint(Graphics g){
	super.paint(g);
	Graphics2D g2d = (Graphics2D)g;
	g2d.setPaint(Color.red);
	g2d.draw(forma);
}
```

para viariar el color, tenemos que considerarlo una propiedad del lienzo y, por tanto, incluir un dato miembro asociado con sus correspondientes métoos set/get
- dato miembro se aplica cuando queramso añadir propiedades al lienzo (en este caso seria forma, color y relleno), por lo que añadiriamos sus get/set corresponidentes

luego, para cada accion boton añadimos -> actionPerformed, y variariamos esa propiedad de esta manera:

```java
private void botonRojoActionPerformed(java.awt.event.ActionEvent evt){
	this.lienzo.setColor(Color.red);
}
private void botonAzulActionPerformed(java.awt.event.ActionEvent evt){
	this.lienzo.setColor(Color.blue);
}
private void botonVerdeActionPerformed(java.awt.event.ActionEvent evt){
	this.lienzo.setColor(Color.green);
}
private void bAmarilloActionPerformed(java.awt.event.ActionEvent evt){
	this.lienzo.setColor(Color.yellow);
}
```

lienzo es la variable creada por NetBeans al incorporar un nuevo objeto Lienzo en el centro de nuestra ventana.

Importante, es la ventana principal la que se comunica con el lienzo para cambiarle propiedades y no al reves (es muy importante diseñar la clase Lienz  de forma que sea totalmente independiente del resto de clases, esto permitira que podamos usar esa clase en otros programas).

**Pintar distintas formas**
Lo que tenemos que hacer es crear el objeto shape correspondiente a la forma que quiero dibujar:
- linea: Linea2D.Float
- rectangulo: Rectangle2d.FLOAT
- elipse: Ellipse2D.Float
- punto: 

Este objeto se crea en el método mousePressed que gestiona el evento de pulsar el boton del raton, usamos condicionales (if/else o swithcj) para que, en guncion de la herramienta seleccionada se cree uno u otro objeto)
Mousedragged el objet creado tendrá que modificarse mediante el metodo adecuado dependiendo de la clase 
- caso para dibujar linea es setLine

Para el relleno habra que usar el método fill, implica que, dentro del método paint, tnedremos que incluir el if para, en funcion de que esté o no activa la propiedad de relleno llamar al metodo draw o a fill


## Notas

- propiedades que tienen relleno -> figuraRelleno

- plantear, ver diapositivas y hacer lo de vector de figuras 

- getbackground para coger el color del fondo del boton, generalizar el boton y hacerlo a mano

- usar un enum para el tipo de formas