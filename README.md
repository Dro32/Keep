# Keep - Bloc de Notas para Minecraft

Un mod de Fabric para Minecraft 1.21.1 que proporciona un bloc de notas similar a Google Keep.

## Características

- 📝 Bloc de notas con límite de 500 caracteres
- 💾 Las notas se guardan automáticamente y persisten entre sesiones
- ⌨️ Abre el bloc de notas presionando la tecla **N**
- 🎮 No pausable, funciona durante el juego

## Instalación

1. Descarga [Fabric Loader](https://fabricmc.net/use/installer/) para Minecraft 1.21.1
2. Descarga el mod compilado o compila desde el código fuente
3. Coloca el archivo JAR en la carpeta `mods` de tu instalación de Minecraft

## Compilación

```bash
./gradlew build
```

El JAR compilado se encontrará en `build/libs/`.

## Uso

1. Abre Minecraft con el mod instalado
2. Presiona **N** en cualquier momento para abrir el bloc de notas
3. Escribe hasta 500 caracteres
4. Los cambios se guardan automáticamente al cerrar

## Estructura de Directorios

Las notas se guardan en: `.minecraft/config/keep_notes/note.txt`

## Requisitos

- Minecraft 1.21.1
- Fabric Loader 0.16.9+
- Java 21+

## Licencia

CC0 1.0 Universal
