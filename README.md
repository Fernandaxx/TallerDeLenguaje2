# Billetera Virtual de Criptomonedas

## Descripción del Proyecto

Este es un sistema de gestión de billetera virtual que permite a los usuarios manejar diferentes tipos de monedas (criptomonedas y monedas fiat), 
realizar operaciones como compra, swap, y seguimiento de activos.

## Estructura del Proyecto

- `BilleteraVirtualManager.java`: Clase principal que maneja la interacción con el usuario
- `CLI.java`: Punto de entrada de la aplicación
- `DataBaseManager.java`: Gestiona la creación y configuración de la base de datos
- `MonedaDAO.java`: Manejo de operaciones de base de datos para monedas
- `ActivoCriptoDAO.java`: Manejo de operaciones de base de datos para activos de criptomonedas
- `ActivoFiatDAO.java`: Manejo de operaciones de base de datos para activos de moneda FIAT


### Gestores de Transacciones
- `GestorCompra.java`: Administra las transacciones de compra entre monedas fiat y criptomonedas
- `GestorSwap.java`: Administra las operaciones de intercambio entre criptomonedas


### Acceso a Datos (DAOs)
Los nombres de las interfaces comienzan con 'I':
- `IMonedaDAO.java` y `MonedaDAO.java`: Manejo de operaciones de base de datos para monedas
- `IActivoCriptoDAO.java` y `ActivoCriptoDAO.java`: Manejo de operaciones de base de datos para activos de criptomonedas
- `IActivoFiatDAO.java` y `ActivoFiatDAO.java`: Manejo de operaciones de base de datos para activos de moneda FIAT
- `ITransaccionDAO.java` y `TransaccionDAO.java`: Gestiona los registros de transacciones

## Características Principales

- Gestión de monedas (criptomonedas y fiat)
- Generación y listado de stocks
- Compra y swap de criptomonedas
- Almacenamiento de datos en base de datos SQLite

## Consideraciones Importantes

### Creación de Monedas
- Las criptomonedas se cargan utilizando únicamente la nomenclatura para simplificar el proceso
- El conjunto de nomenclaturas permitidas es extensible en una constante para futuro crecimiento de la billetera

### Nomenclaturas Permitidas
Actualmente incluye:
- Criptomonedas: BTC, ETH, USDC, DOGE, USDT
- Monedas Fiat: ARS, USD, BS, EUR, UYU


### Observaciones
-  El valor en dólar se implementa como una variable/atributo en lugar del método definido originalmente en el diseño UML.
- El stock se mantiene en la tabla de Moneda aunque nos hubiera gustado separar el atributo (no le encontramos sentido en la moneda, pero no llegamos con el tiempo).
- Las marcas de tiempo se almacenan en milisegundos, no encontramos una solucion a este problema.
- Modificamos el retorno de errores en algunas clases DAO para no imprimir directamente en consola. El uso de `throws Exepcion` nos parecio incorrecto ya que detiene la ejecucion del programa. 


## Guía de Uso - Operaciones Disponibles

### 1. Crear Monedas
Esta opción permite registrar nuevas monedas en el sistema.

**Pasos:**
1. Seleccionar tipo de moneda:
   - 1: Criptomoneda
   - 2: Fiat
2. Ingresar datos solicitados:
   - Nombre de la moneda
   - Nomenclatura (debe ser una de las permitidas: BTC, ETH, USDC, DOGE, USDT, ARS, USD, BS, EUR, UYU)
   - Valor en dólar
3. Si es criptomoneda, además se solicita:
   - Volatilidad
   - Stock inicial
4. Confirmar la operación (y/n)

### 2. Listar Monedas
Muestra todas las monedas registradas en el sistema.

**Opciones:**
- Ordenar por nomenclatura (y/n)
  - "y": ordena alfabéticamente por nomenclatura
  - "n": ordena por valor en dólar

### 3. Generar Stock
Genera cantidades aleatorias de monedas para la billetera.

**Pasos:**
1. Confirmar la generación (y/n)
2. El sistema generará automáticamente el stock

### 4. Listar Stock
Muestra el inventario disponible de todas las monedas.

**Opciones:**
- Ordenar por nomenclatura (y/n)
  - "y": ordena alfabéticamente por nomenclatura
  - "n": ordena por cantidad en stock

### 5. Generar Mis Activos
Permite registrar nuevas cantidades de monedas en los activos personales.

**Pasos:**
1. Seleccionar tipo de activo:
   - 1: Criptomoneda
   - 2: Fiat
2. Ingresar datos:
   - Cantidad del activo
   - Nomenclatura 
3. Revisar el resumen mostrado
4. Confirmar la operación (y/n)

### 6. Listar Mis Activos
Muestra los activos disponibles en la cartera personal.

**Pasos:**
1. Seleccionar tipo de activo a listar:
   - 1: Criptomoneda
   - 2: Fiat
2. Elegir criterio de ordenamiento:
   - Ordenar por nomenclatura (y/n)
     - "y": ordena alfabéticamente
     - "n": ordena por cantidad

### 7. Simular Compra de Criptomoneda
Permite realizar una compra de criptomonedas usando moneda fiat.

**Pasos:**
1. Ingresar nomenclatura de la criptomoneda a comprar
2. Ingresar nomenclatura de la moneda fiat a utilizar
3. Ingresar cantidad de fiat para la compra
4. Revisar el resumen de la operación
5. Confirmar la compra (y/n)

### 8. Simular SWAP entre Criptomonedas
Permite intercambiar entre diferentes criptomonedas.

**Pasos:**
1. Ingresar nomenclatura de la criptomoneda que desea intercambiar
2. Ingresar nomenclatura de la criptomoneda que desea recibir
3. Ingresar cantidad a intercambiar
4. Revisar el resumen de la operación
5. Confirmar la operación (y/n)

### 9. Mostrar/Ocultar Menú Detallado
Alterna entre la vista simple y detallada del menú.
- Vista simple: solo muestra las opciones
- Vista detallada: incluye descripción de cada operación

### 0. Salir
Finaliza la ejecución del programa.



## Código Fuente y Colaboradores

### Repositorio
El código fuente completo del proyecto está disponible en GitHub:
- Repositorio: [TallerDeLenguaje2](https://github.com/Fernandaxx/TallerDeLenguaje2/tree/main)

### Equipo de Desarrollo
Este proyecto fue desarrollado por:
- Lucia Acuña
- Fernanda Avila

## Bibliografia
Para la implementacion de las clases Dao usamos como guia los siguientes fuentes:
- https://www.tutorialspoint.com/sqlite/sqlite_java.htm
- https://www.geeksforgeeks.org/how-to-handle-sqlexception-in-jdbc/




