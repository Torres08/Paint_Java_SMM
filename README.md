# Practicas_SMM
Paint en Java con Netbeans

### P1 - Fundamentos Java
### P2 - Interfaces de usuario en JAVA (Swing)
### P3 - Gestión de eventos en JAVA
### P4 - Paint Basico
### P5 - Graficos 2D: Formas Básicas
### P6 - Graficos 2D: Atributos
### P7 - Paint 2D
### P8 - E/S Imagenes
### P9 - Procesamiento de Imagenes RescaleOp COnvolveOp
### P10 - Procesamiento de Imagenes AffineTransformOp LookupOp
### P11 - Procesamiento de Imagenes BandCOmbineOp y ColorConvertOp

### Por hacer
- Mostrar en la barra de estado el valor (RGB) del pixel sobre el que está situado el ratón y la posicion en cada lienzo
- contador a la hora de reproducir video 
- Cuando se seleccione una figura en el lienzo, hacer que los atributos de la ventana principal se activen conforme a los atributos de la figura seleccionada (no se) 
- arreglar la funcion lookup reto, hacerla como pide el maestro

### Solucionar 
1. Solucionado la funcion calcular kernel cometa + preguntar por bandas + deshacer el cambio (tengo que aplicar mas, corregir la funcion) (distribuir mas los valores) X

2. Preguntar al maestro si esta bien aplicado: Transformación lineal con un punto de control reto 10 P10 (float) (bien hecho)
X

3. Volcar imagene lo hace bien pero salta una excepcion (cambiarlo para que el iterator sea solo el actual) (solucionado) X

4. Copia hereda las cosas, por que?, // focus gaines, creas copia, arreglar esto , hago una copia, replantear de nuevo
(hecho, implementar coloen en atributos imagen y moforma)
X aplicar clone a atributo Miforma y getImage

5. revisar el ultimo reto, revisar slider de rojoOp // umbral pequeño porque es asi se detecta rapido 


manejador de eventos para rgb y lienzo, manejador en la ventana principal
manejador de mouse en la VP
new para crearla
asociarla al lienzo
cuando haces un nuevo o abris hago el lienzo
addlistener cada vez que creas ventana interna, new, abrir y nuevo 

### Retos Implementados para repasar
- P6 "Formas Avanzadas"
- P8 "marcar figura seleccionada (multiseleccion)" editar atributos figura seleccionada, Volcar Imagen
- P8 clip de la imagen + rectangulo + botones seacutalizan segun el lienzo seleccionado
- P8 filtros para guardar imagen tipo archivo jpg bmp png (tamaño de la imagen nueva no implementado)
- P9 EventoListener (no terminado, a medias si da tiempo terminarlo)
- P9 emborronamiento iluminado
- P9 Emborronamiento cometa (por temrinar preguntar al maestro )
- P9 crear imagen nueva que sea TYPE_INTARGB
- P9 seleccion figura del lienzo actualice la barra de herramientas (no hecho) (he hecho multiseleccion, por lo que lo puedo complicar)
- P10 Oscurecer zonas claras
- P10 transformacion lineal cn un punto de control (Por revisar)
- P10 operacion negativo
- P10 operacion duplicar .2x
- P11 mostrar en la barra de herramientas el valor RGB pixel (lo mismo que la posicion del raton, hecho para el escritorio pero no para el lienzo, por terminar)
- P12 Reto CAmbio color c1 por c2
- P12 añadido slider para operacionRojo y tintado


# examen
poca gente no va al examen
no muy largo, te puedes quedar
no es dificil si lo entiendes, importante estudiar lo de imagenes, entender lo que entiendes
proyecto nuevo
trasnparencia del curso
lienzo es esencial, el como hacerlo, pintar linea, etc No drawline

abrir imagen y subir el brillo (jeemplo)

usa este operador y haz convolucion

ver lo que has hecho, 


2 ejercicio dibujar
3 imagenes
- lookupOP convolucion
- operacion propia 
- ejemplo de funciones de clase