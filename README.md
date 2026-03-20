# Simulador de Combate RPG - Patrón Decorator

## Descripción
Este proyecto es una implementación práctica del patrón de diseño estructural Decorator, desarrollado íntegramente en Java. Consiste en un simulador de combate con una interfaz web servida a través de la librería nativa `com.sun.net.httpserver`. 

El objetivo principal es demostrar cómo añadir responsabilidades y alterar atributos (Ataque, Defensa y Puntos de Salud) a objetos base de forma dinámica. Esto se logra envolviendo a los personajes con equipamiento (armas, armaduras y accesorios) utilizando la composición en lugar de la herencia clásica, evitando así la explosión de subclases.

## Características Principales
* Implementación pura del Patrón Decorator.
* Servidor HTTP embebido (sin dependencias externas).
* Interfaz gráfica interactiva y representaciones visuales construidas con CSS puro.
* Motor matemático de resolución de daño en el backend.

## Instrucciones de Ejecución
1. Abrir una terminal en el directorio del proyecto.
2. Compilar el código fuente: `javac DarkRPG.java`
3. Ejecutar el servidor: `java DarkRPG`
4. Acceder a la interfaz desde el navegador web en: `http://localhost:8080`

Desarrollado como proyecto académico para la aplicación y análisis de Patrones de Software.
