/** 
 * This file is part of GenericPropertyLoader project.
 *
 * GenericPropertyLoader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * GenericPropertyLoader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with GenericPropertyLoader project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package loader.standard.readers;

import java.awt.Color;
import java.util.StringTokenizer;

import loader.PropertyReader;
import loader.messages.DMLoader;
import loader.standard.SPLoaderMessages;
import container.Property;

/**
 * Default reader for color properties. The supported color properties are : <br>
 * - hexadecimal format color #[RR][GG][BB]{[AA]}<br>
 * - Coma separated color [RR],[GG],[BB]{,[AA]}
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class ColorReader implements PropertyReader<Color> {

	/** Singleton instance **/
	private static ColorReader __instance;

	/**
	 * Comma separator
	 */
	private static final String __COMMA_SEPARATOR = ",";
	/**
	 * Hexadecimal character
	 */
	private static final char __HEXA_CHAR = '#';

	/**
	 * Constructor
	 */
	private ColorReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Color> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		String stringValue = propertyRepresentation.trim();
		if (!stringValue.isEmpty()) {
			// attempt parsing as hexadecimal
			if (stringValue.charAt(0) == __HEXA_CHAR) {
				return new Property<Color>(readHexaColor(stringValue),
						propertyRepresentation);
			}
			// comma separated values
			return new Property<Color>(readCommaSeparatedColor(stringValue),
					propertyRepresentation);
		}
		throw new IllegalArgumentException(DMLoader
				.getMessage(SPLoaderMessages.COLOR_READER_ERROR_EMPTY));

	}

	/**
	 * Reads a color at hexadeciaml format (#A0A0B0,#FFFFFFB0)
	 * 
	 * @param stringValue
	 *            : color as string
	 * @return - the color read
	 * @throws - Illegal argument exception if the string representation is not
	 *         valid
	 */
	private Color readHexaColor(String stringValue) {
		// a - is the string length invalid?
		if (stringValue.length() > 9) {
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.COLOR_READER_ERROR_HEXA_TOO_LONG,
					stringValue));
		}

		// b - parse string
		int r = getHexaValueAt(1, stringValue, false);
		int g = getHexaValueAt(3, stringValue, false);
		int b = getHexaValueAt(5, stringValue, false);
		int a = getHexaValueAt(7, stringValue, true);
		return new Color(r, g, b, a);
	}

	/**
	 * Get an hexadecimal value from a string as a couple of values
	 * 
	 * @param index
	 *            : hexadecimal couple first location
	 * @param stringValue
	 *            : string in which the hexadecimal should be found
	 * @param isOptionnal
	 *            : is the value optional (if not, exceptions could be thrown)
	 * @return - the hexadecimal value found, 255 if the value was not found and
	 *         optional
	 * @throws - Illegal argument exception if the string is to short
	 */
	private int getHexaValueAt(int index, String stringValue,
			boolean isOptionnal) {
		if (stringValue.length() < index + 2) {
			// the hexadecimal couple does not stand in the string
			if (isOptionnal) {
				return 255;
			}

			throw new IllegalArgumentException(
					DMLoader
							.getMessage(
									SPLoaderMessages.COLOR_READER_ERROR_HEXA_MISSING_ATTRIBUTES,
									stringValue));
		}
		int parsedInt;
		String hexadecimalString = stringValue.substring(index, index + 2);
		try {
			parsedInt = Integer.parseInt(hexadecimalString, 16);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.COLOR_READER_ERROR_HEXA_INVALID_ATTRIBUTE,
					stringValue, hexadecimalString));
		}

		return parsedInt;
	}

	/**
	 * Reads a color at comma separated format (255,200,20 or
	 * 200,200,100,235...)
	 * 
	 * @param stringValue
	 *            : color as string
	 * @return - The corresponding color
	 * @throws - Illegal argument exception if the string representation is not
	 *         valid
	 */
	private Color readCommaSeparatedColor(String stringValue) {
		StringTokenizer stringTokenizer = new StringTokenizer(stringValue,
				__COMMA_SEPARATOR);
		if (stringTokenizer.countTokens() < 3) {
			throw new IllegalArgumentException(
					DMLoader
							.getMessage(
									SPLoaderMessages.COLOR_READER_ERROR_RGBA_MISSING_ATTRIBUTES,
									stringValue));
		}
		if (stringTokenizer.countTokens() > 4) {
			throw new IllegalArgumentException(
					DMLoader
							.getMessage(
									SPLoaderMessages.COLOR_READER_ERROR_RGBA_TOO_MANY_ATTRIBUTES,
									stringValue));
		}
		int count = 0;
		int r = 255;
		int g = 255;
		int b = 255;
		int a = 255;
		while (stringTokenizer.hasMoreTokens()) {
			int temp;
			String token = stringTokenizer.nextToken();
			try {
				temp = new Integer(token.trim());
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException(
						DMLoader
								.getMessage(
										SPLoaderMessages.COLOR_READER_ERROR_RGBA_INVALID_ATTRIBUTE,
										stringValue, token));
			}
			if (temp < 0 || temp > 255) {
				throw new IllegalArgumentException(
						DMLoader
								.getMessage(
										SPLoaderMessages.COLOR_READER_ERROR_RGBA_INVALID_INTEGER_VALUE,
										stringValue, token));
			}

			switch (count) {
			case 0:
				r = temp;
				break;
			case 1:
				g = temp;
				break;
			case 2:
				b = temp;
				break;
			default:
				a = temp;
			}
			count++;
		}

		return new Color(r, g, b, a);
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static ColorReader getInstance() {
		if (__instance == null) {
			__instance = new ColorReader();
		}
		return __instance;
	}
}
