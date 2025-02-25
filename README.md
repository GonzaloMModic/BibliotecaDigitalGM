# BibliotecaDigitalGM

Descripción general del sistema:
Este sistema está diseñado para gestionar una biblioteca digital que funciona como una plataforma de préstamos de libros. Permite administrar los libros, los préstamos y la información de los clientes y empleados.
Principales funcionalidades del sistema:
•	Gestión de libros:
    Registrar, editar, eliminar y consultar libros disponibles en la biblioteca.
•	Gestión de préstamos:
    Registrar y controlar los libros prestados a los clientes.
•	Gestion de clientes:
    Administrar la información de los clientes, como nombre, email, libros prestados, entre otros.
•	Gestión de empleados:
    Registrar y organizar la información de los empleados que trabajan en la biblioteca.


|----Instrucciones de compilación y ejecución----|
Requisitos previos:
1.	Java Development Kit (JDK): Versión 8 o superior.
2.	XAMPP: Para ejecutar la base de datos MySQL.

Configuración inicial:
1.	Activar XAMPP:
    Abre XAMPP y asegúrate de iniciar los servicios Apache y MySQL.
2.	Base de datos:
    La base de datos requerida por el programa posee el nombre de “test” para facilitar su prueba.
    Si deseas cargar datos iniciales, ejecuta la clase de pruebas incluida en el proyecto llamada “TestBiblioteca”. Esta clase generará varios inserts en la base de datos para que puedas interactuar con información precargada.
    Ejecución del programa:
1.	Abrir el proyecto en un IDE (por ejemplo, Eclipse).
2.	Verificar que XAMPP esté activo y la base de datos esté configurada correctamente (Que exista la bd “test”).
3.	Ejecuta el programa tocando el botón "Run". La clase principal (que contiene el método main) se encuentra en la carpeta pantallas, llamada “IniciarBibliotecaDigital”.
