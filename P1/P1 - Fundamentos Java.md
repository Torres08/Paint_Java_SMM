## 1. Ejemplo Aula 

- [x] prueba los elementos fundamentales de todo lenguaje PDO:
	- Clases y objetos
	- Herencia
	- Polimorfismo
	- Sobrecarga

- [x] Define una nueva clase delegado (hereda de alumno)
- [x] Crea un vector (array) de diez delegados
- [x] Probar las protecciones
- [x] Probar el modificador static

Pasos
0. Correr el programa y ver como interactúan las clases
1. Crear la clase delegado que hereda de alumno "un delegado es un alumno"
2. creo un vector de 10 delegados
3. delegado tiene una variable $static$ contadorDelegados
4. Cambio en Persona que nombre y edad sean protected, ahora sin un getNOmbre o getEdad no puedo acceder a dichas variables 

# 2. Ejercicio usando el paquete java.lang

Crea un nuevo proyecto "CuestionesBasicas" y usando las clases de paquete java.lang resuelve las siguientes cuestiones:

- [x] Valor absoluto de 3.7
	- Math.abs(3.7)

- [x] Raíz cuadrada de 37
	- Math.sqrt(37)

- [x] Si ‘a’ es minúscula, imprimir el mensaje “Es minúscula”, sino “Es mayúscula”
	- Character.isLowerCase(a)

- [x] Convertir 5 a String
	- String valor = String.valueOf(5);

- [x] Convertir “5” a int
	- valor2 = Integer.parseInt(valor)

- [x] Convertir 5.5 a String
	- String valor3 = String.valueOf(5.5)

- [x] ¿Cuál es el máximo flotante?
	- float maxFloat = Float.MAX_VALUE;

- [x] Imprimir el segundo carácter de “Hola”
	-  char segundoCaracter = saludo.charAt(1);

- [x] Comparar “hola” con “Hola”
	-  boolean sonIguales = str1.equals(str2);

- [x] Crear “Hola” y añadirle “ y adiós” (sin crear un nuevo objeto
	- StringBuilder mensaje = new StringBuilder("Hola");
    - mensaje.append(" y adiós");


