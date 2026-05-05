package com.dro32.keep;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import com.dro32.keep.data.NoteDataManager;
import com.dro32.keep.screen.NoteScreen;

public class KeepClient implements ClientModInitializer {
	private static KeyBinding openNoteKey;

	@Override
	public void onInitializeClient() {
		// Registrar la tecla N para abrir el bloc de notas
		openNoteKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.keep.open_note",
			InputUtil.Type.KEYSYM,
			GLFW.GLFW_KEY_N,
			"category.keep"
		));

		// Escuchar eventos del tick del cliente
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openNoteKey.wasPressed()) {
				client.setScreen(new NoteScreen());
			}
		});
	}
}
