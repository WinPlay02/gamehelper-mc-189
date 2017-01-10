package net.wfoas.gh.dropsapi.pdr;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Maps;

public enum ChatColor {
	BLACK('0', 0x00), DARK_BLUE('1', 0x1), DARK_GREEN('2', 0x2), DARK_AQUA('3', 0x3), DARK_RED('4',
			0x4), DARK_PURPLE('5', 0x5), GOLD('6', 0x6), GRAY('7', 0x7), DARK_GRAY('8', 0x8), BLUE('9', 0x9), GREEN('a',
					0xA), AQUA('b', 0xB), RED('c', 0xC), LIGHT_PURPLE('d', 0xD), YELLOW('e', 0xE), WHITE('f',
							0xF), MAGIC('k', 0x10, true), BOLD('l', 0x11, true), STRIKETHROUGH('m', 0x12,
									true), UNDERLINE('n', 0x13, true), ITALIC('o', 0x14, true), RESET('r', 0x15);
	public static final char COLOR_CHAR = '\u00A7';
	private static final Pattern STRIP_COLOR_PATTERN = Pattern
			.compile("(?i)" + String.valueOf(COLOR_CHAR) + "[0-9A-FK-OR]");

	private final int intCode;
	private final char code;
	private final boolean isFormat;
	private final String toString;
	private final static Map<Integer, ChatColor> BY_ID = Maps.newHashMap();
	private final static Map<Character, ChatColor> BY_CHAR = Maps.newHashMap();

	private ChatColor(char code, int intCode) {
		this(code, intCode, false);
	}

	private ChatColor(char code, int intCode, boolean isFormat) {
		this.code = code;
		this.intCode = intCode;
		this.isFormat = isFormat;
		this.toString = new String(new char[] { COLOR_CHAR, code });
	}

	public char getChar() {
		return code;
	}

	@Override
	public String toString() {
		return toString;
	}

	public boolean isFormat() {
		return isFormat;
	}

	public boolean isColor() {
		return !isFormat && this != RESET;
	}

	public static ChatColor getByChar(char code) {
		return BY_CHAR.get(code);
	}

	public static ChatColor getByChar(String code) {
		Validate.notNull(code, "Code cannot be null");
		Validate.isTrue(code.length() > 0, "Code must have at least one char");
		return BY_CHAR.get(code.charAt(0));
	}

	public static String stripColor(final String input) {
		if (input == null) {
			return null;
		}
		return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
	}

	public static String translateAlternateColorCodes(char altColorChar, String textToTranslate) {
		char[] b = textToTranslate.toCharArray();
		for (int i = 0; i < b.length - 1; i++) {
			if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
				b[i] = ChatColor.COLOR_CHAR;
				b[i + 1] = Character.toLowerCase(b[i + 1]);
			}
		}
		return new String(b);
	}

	public static String getLastColors(String input) {
		String result = "";
		int length = input.length();
		for (int index = length - 1; index > -1; index--) {
			char section = input.charAt(index);
			if (section == COLOR_CHAR && index < length - 1) {
				char c = input.charAt(index + 1);
				ChatColor color = getByChar(c);
				if (color != null) {
					result = color.toString() + result;
					if (color.isColor() || color.equals(RESET)) {
						break;
					}
				}
			}
		}
		return result;
	}

	static {
		for (ChatColor color : values()) {
			BY_ID.put(color.intCode, color);
			BY_CHAR.put(color.code, color);
		}
	}
}
