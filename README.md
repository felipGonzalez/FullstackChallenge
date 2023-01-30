
Prueba de conocimientos de aplicación a oferta laboral como desarrollador back en java

## Pila de tecnologías

* [Sprig boot](https://spring.io/guides/gs/spring-boot/) para el backen usando java
* [Junit](https://junit.org/junit5/) Para escribir pruebas unitarias
* [VUE 3](https://github.com/vuejs/) para el front


## Configuración de desarrollo
Requerimientos : Java Jdk 8, Maven, acceso a internet
* Opcional: Puede agregar el proyecto a su Ide de preferencia y ejecutarlo desde ahí

### Instalación Jdk java
Seguir instrucciones de instalación para java, seguir pasos en [Pagina oficial Oracle](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)

### Instalación Maven

Seguir instrucciones de instalación para Maven, seguir pasos en [Pagina Oficial Maven](http://maven.apache.org/download.cgi#Installation)

### Ejecutando la aplicación localmente

Abrir terminal de cmd o powershell y navegar al directorio raíz de preferencia

Clonar este repositorio (Solicitar permisos de acceso al repositorio)
```
git clone https://github.com/felipGonzalez/FullstackChallenge
```
Navegar al nuevo repositorio clonado
```
cd TestSaludTools
```

Encontrara tres carpetas, Back Hexagonal Architecture y Back mvc. 

### Ejecutando el servidor aplicación localmente
Dependiendo de la version del back que desee ejecutar ya sea mvc o Hexagonal Architecture, navegue al contenido
de estas carpetas para su ejecución.

Hay varias formas de ejecutar una aplicación Spring Boot en su máquina local. Una forma es ejecutar el  método main desde su IDE.


Alternativamente, puede usar el complemento [Spring Boot Maven](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins.html#build-tool-plugins-maven-plugin) así:
`mvn spring-boot:run`

Cuando se despliegue el servidor  navegar a http://localhost:8080/  para comprobar correcto funcionamiento

### Ejecutando el Front  localmente

# Challenge App

Este proyecto fue generado con [VUE CLI](https://github.com/vuejs/vue-cli) version 5.0.8.

## Requisitos
debe tener instalado node
ejecute `npm install` para descargar todas las librerías necesarias para ejecutar la aplicación 

## Development server

Ejecute `npm run dev` para un servidor de desarrollo. Navegue a `http://localhost:5173/`. La aplicación se recargará automáticamente si cambia alguno de los archivos de origen.



