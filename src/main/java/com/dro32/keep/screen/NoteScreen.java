package com.dro32.keep.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import com.dro32.keep.data.NoteDataManager;

public class NoteScreen extends Screen {
	private String noteText = "";
	private int cursorPosition = 0;
	private long lastCursorBlink = 0;
	private boolean cursorVisible = true;
	private static final int MAX_CHARS = 500;

	public NoteScreen() {
		super(Text.literal("Keep Note"));
		this.noteText = NoteDataManager.getCurrentNote();
		this.cursorPosition = noteText.length();
	}

	@Override
	public void close() {
		NoteDataManager.setCurrentNote(noteText);
		NoteDataManager.saveNote(noteText);
		super.close();
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == 256) { // ESC
			this.close();
			return true;
		}

		if (keyCode == 259) { // Backspace
			if (cursorPosition > 0) {
				noteText = noteText.substring(0, cursorPosition - 1) + noteText.substring(cursorPosition);
				cursorPosition--;
			}
			return true;
		}

		if (keyCode == 262) { // Right arrow
			if (cursorPosition < noteText.length()) {
				cursorPosition++;
			}
			return true;
		}

		if (keyCode == 263) { // Left arrow
			if (cursorPosition > 0) {
				cursorPosition--;
			}
			return true;
		}

		if (keyCode == 268) { // Home
			cursorPosition = 0;
			return true;
		}

		if (keyCode == 269) { // End
			cursorPosition = noteText.length();
			return true;
		}

		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		if (noteText.length() < MAX_CHARS && chr >= 32) {
			noteText = noteText.substring(0, cursorPosition) + chr + noteText.substring(cursorPosition);
			cursorPosition++;
			return true;
		}
		return false;
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		// Background
		fill(matrices, 0, 0, this.width, this.height, 0xFF2B2B2B);
		
		// Note box
		fill(matrices, 50, 50, this.width - 50, this.height - 100, 0xFF1E1E1E);
		fill(matrices, 48, 48, this.width - 48, this.height - 98, 0xFF404040);

		// Title
		this.textRenderer.draw(matrices, "Keep Notes", 60, 60, 0xFFFFFFFF);

		// Character counter
		String counter = noteText.length() + "/" + MAX_CHARS;
		this.textRenderer.draw(matrices, counter, this.width - 120, 60, 0xFF888888);

		// Note text
		int yOffset = 90;
		int xOffset = 60;
		
		// Handle line wrapping
		String[] lines = noteText.split("\n", -1);
		for (String line : lines) {
			if (yOffset > this.height - 120) break;
			this.textRenderer.draw(matrices, line, xOffset, yOffset, 0xFFEEEEEE);
			yOffset += 12;
		}

		// Cursor
		updateCursorBlink();
		if (cursorVisible) {
			int cursorX = calculateCursorX();
			int cursorY = calculateCursorY();
			fill(matrices, cursorX, cursorY, cursorX + 2, cursorY + 12, 0xFFFFFFFF);
		}

		// Instructions
		this.textRenderer.draw(matrices, "Press ESC to save and close", 60, this.height - 60, 0xFF888888);

		super.render(matrices, mouseX, mouseY, delta);
	}

	private void updateCursorBlink() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastCursorBlink > 500) {
			cursorVisible = !cursorVisible;
			lastCursorBlink = currentTime;
		}
	}

	private int calculateCursorX() {
		int cursorCount = 0;
		int lineStart = 0;
		
		for (int i = 0; i < noteText.length() && i < cursorPosition; i++) {
			if (noteText.charAt(i) == '\n') {
				lineStart = i + 1;
			}
		}
		
		String upToCursor = noteText.substring(lineStart, Math.min(cursorPosition, noteText.length()));
		return 60 + this.textRenderer.getWidth(upToCursor);
	}

	private int calculateCursorY() {
		int line = 0;
		for (int i = 0; i < cursorPosition && i < noteText.length(); i++) {
			if (noteText.charAt(i) == '\n') {
				line++;
			}
		}
		return 90 + (line * 12);
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}
