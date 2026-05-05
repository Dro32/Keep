package com.dro32.keep.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.fabricmc.loader.api.FabricLoader;

public class NoteDataManager {
	private static final String NOTES_DIR = "keep_notes";
	private static final String NOTE_FILE = "note.txt";
	private static Path notesPath;

	static {
		Path configPath = FabricLoader.getInstance().getConfigDir();
		notesPath = Paths.get(configPath.toString(), NOTES_DIR);
	}

	/**
	 * Obtiene el contenido de la nota guardada
	 */
	public static String loadNote() {
		try {
			// Crear directorio si no existe
			Files.createDirectories(notesPath);

			Path notePath = notesPath.resolve(NOTE_FILE);

			// Si el archivo no existe, retornar string vacío
			if (!Files.exists(notePath)) {
				return "";
			}

			// Leer el contenido del archivo
			return new String(Files.readAllBytes(notePath));
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Guarda el contenido de la nota
	 */
	public static void saveNote(String content) {
		try {
			// Crear directorio si no existe
			Files.createDirectories(notesPath);

			Path notePath = notesPath.resolve(NOTE_FILE);

			// Limitar a 500 caracteres
			String limitedContent = content.length() > 500 ? content.substring(0, 500) : content;

			// Escribir el archivo
			Files.write(notePath, limitedContent.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la ruta del archivo de notas
	 */
	public static Path getNotePath() {
		try {
			Files.createDirectories(notesPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return notesPath.resolve(NOTE_FILE);
	}
}
