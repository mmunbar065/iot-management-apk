# Instrucciones para usar Ngrok

Este proyecto utiliza Ngrok para exponer un servidor local a una URL pública accesible desde tu dispositivo móvil o cualquier otro cliente fuera de la red local.

## Requisitos previos

1. Tener [Ngrok instalado](https://ngrok.com/download).
2. Tener el servidor en funcionamiento en tu máquina local (por ejemplo, Flask en el puerto 5000).

## Pasos para iniciar Ngrok

### Paso 1: Instalar Ngrok (si aún no lo tienes)
Si aún no tienes Ngrok instalado, puedes hacerlo desde su sitio web oficial:

- [Descargar Ngrok](https://ngrok.com/download)

### Paso 2: Ejecutar el servidor local

Asegúrate de que tu servidor (por ejemplo, Flask, Express, etc.) esté en funcionamiento en tu máquina local. En este ejemplo, el servidor está ejecutándose en el puerto `5000`.

Por ejemplo, para Flask:

```bash
Configuraciones del proyecto
ngrok config add-authtoken 2uS0j9RCGepbH2zkD1dR60DO0sS_xVxhCFU9sY1TkBSKjhxS
https://5df5-2a0c-5a84-7410-ed00-c535-490d-52d7-4e1e.ngrok-free.app

flask run
