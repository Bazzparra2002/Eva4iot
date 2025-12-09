Eva4iot: Aplicación de Monitoreo de Sensores y Ubicación (Android)

Aplicación móvil desarrollada en Android Nativo (Kotlin) como prueba de concepto para Adquisición de Datos IoT desde un dispositivo móvil.
Permite visualizar en tiempo real la información de sensores, módulos de conectividad y coordenadas, demostrando el uso correcto de permisos, servicios del sistema y Google Maps.

Tabla de Contenidos:

*Descripción General

*Características Principales

*Tecnologías Utilizadas

*Instalación y Ejecución

*Pruebas de Sensores en el Emulador

*Estructura del Proyecto

Eva4iot tiene como objetivo demostrar la implementación correcta de:

Monitoreo activo de sensores y conectividad

Manejo de permisos en tiempo de ejecución

Uso del SDK de Google Maps para mostrar coordenadas

Comunicación en tiempo real con los módulos del sistema (ubicación, Wi-Fi, Bluetooth, acelerómetro)

La app obtiene, actualiza y muestra información constantemente para fines educativos y de desarrollo IoT.

Características Principales: 

Característica	Detalle Técnico	Estado
Ubicación GPS	Obtención continua de Latitud/Longitud mediante FusedLocationProviderClient.
Mapa Visual	Mapa interactivo de Google Maps centrado en la ubicación actual.
Acelerómetro	Lectura en tiempo real de aceleración en ejes X, Y, Z.
Wi-Fi	Estado de red (Conectado/Desconectado) y SSID.
Bluetooth	Estado del adaptador vía BroadcastReceiver.	
Interfaz	SplashScreen nativo y layout simple en XML.	

Tecnologías Utilizadas:

Lenguaje: Kotlin

Plataforma: Android Nativo

IDE: Android Studio

SDK mínimo: API 24 (Android 7.0 Nougat)

Librerías clave
implementation("com.google.android.gms:play-services-location:21.0.1")
implementation("com.google.android.gms:play-services-maps:18.2.0")
implementation("androidx.core:core-splashscreen:1.0.1")

Instalación y Ejecución
1. Requisitos Previos

Android Studio actualizado

Máquina virtual (AVD) o teléfono físico

API Key de Google Maps habilitada para Maps SDK for Android

2. Configurar el Proyecto
A. Agregar clave API en AndroidManifest.xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="TU_CLAVE_API_DE_MAPS_AQUI" />


Colócala dentro de la etiqueta <application>.

B. Verificar dependencias en build.gradle.kts
implementation("com.google.android.gms:play-services-location:21.0.1")
implementation("com.google.android.gms:play-services-maps:18.2.0")
implementation("androidx.core:core-splashscreen:1.0.1")

3. Ejecutar el Proyecto

Abre el proyecto en Android Studio

Conecta un dispositivo o inicia un AVD

Compila y presiona Run 

Pruebas de Sensores en el Emulador
A. Probar Ubicación (GPS)

Ejecutar la app

Abrir Extended Controls del emulador (tres puntos …)

<img width="597" height="1277" alt="image" src="https://github.com/user-attachments/assets/9801c4b1-8459-406a-a267-060a7111ff64" />


Ir a Location

Insertar coordenadas y presionar SET LOCATION

La ubicación se actualizará gracias a requestLocationUpdates().

<img width="1255" height="975" alt="Captura de pantalla 2025-12-08 192505" src="https://github.com/user-attachments/assets/439d9472-58a4-4fe6-9442-f182ec4ca8e7" />


B. Probar el Acelerómetro

Abrir Extended Controls

Ir a Virtual Sensors

En la sección Device Pose, mover el modelo 3D

Los valores X, Y y Z cambiarán en tiempo real.

<img width="1246" height="1015" alt="Captura de pantalla 2025-12-08 194022" src="https://github.com/user-attachments/assets/dec701c8-5af9-4a85-adf3-dcf09dba87d1" />


 C. Probar Wi-Fi

El emulador permite simular:

Modo avión

Conexión activa/desconectada

Cambios de red

D. Probar Bluetooth

Nota: en el emulador NO existe hardware Bluetooth real, por lo que el estado puede aparecer fijado como Activo aunque tu PC no lo tenga.

En dispositivo físico funcionará correctamente mediante:

BluetoothAdapter.ACTION_STATE_CHANGED

BroadcastReceiver

Estructura del Proyecto
app/
 ├── manifests/
 ├── java/
 │    └── com.example.eva4iot/
 │          ├── MainActivity.kt
 │          ├── SplashActivity.kt
 │          └── helpers...
 └── res/
      ├── layout/activity_main.xml
      ├── layout/activity_splash.xml
      └── values/

Autor: Romina Baeza Parra – 2025
