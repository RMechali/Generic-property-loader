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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import loader.PropertyReader;
import loader.messages.DMLoader;
import loader.standard.SPLoaderMessages;
import container.Property;

/**
 * Default reader for font properties. The font file can be specified as
 * relative to classpath or to system file path.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class FontReader implements PropertyReader<Font> {

	/** Singleton instance **/
	private static FontReader __instance;

	/**
	 * Constructor
	 */
	private FontReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Font> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		String filePath = propertyRepresentation.trim();
		URL fontURL = ClassLoader.getSystemClassLoader().getResource(filePath);
		if (fontURL == null) {
			// the font file is not in class path, is it in absolute path?
			try {
				final File file = new File(filePath);
				if (!file.exists()) {
					// the file was not found
					throw new IllegalArgumentException(DMLoader.getMessage(
							SPLoaderMessages.FONT_READER_PATH_ERROR,
							propertyRepresentation));
				}
				fontURL = file.toURI().toURL();
			} catch (MalformedURLException ex) {
				throw new IllegalArgumentException(DMLoader.getMessage(
						SPLoaderMessages.FONT_READER_PATH_ERROR,
						propertyRepresentation));
			}
		}

		Font loadedFont = readFont(fontURL, Font.TRUETYPE_FONT);
		if (loadedFont == null) {
			// is it a type 1 font?
			readFont(fontURL, Font.TYPE1_FONT);
		}

		if (loadedFont == null) {
			// invalid font
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.FONT_READER_INVALID_FONT_FILE_ERROR,
					fontURL.toString()));
		}

		// build a property font that can be exported in property file later on
		return new Property<Font>(loadedFont, propertyRepresentation);
	}

	/**
	 * Reads a font from an URL
	 * 
	 * @param fontURL
	 *            : font URL
	 * @param type
	 *            : font type
	 * @return - the font read or null
	 */
	private Font readFont(URL fontURL, int type) {
		InputStream fontStream = null;
		try {
			fontStream = fontURL.openStream();
			return Font.createFont(type, fontStream);
		} catch (IOException e1) {
			try {
				if (fontStream != null) {
					fontStream.close();
				}
			} catch (Exception e3) {
				// ignore this exception
			}
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.FONT_READER_FILE_ACCES_ERROR, fontURL
							.toString()));
		} catch (FontFormatException e2) {
			try {
				if (fontStream != null) {
					fontStream.close();
				}
			} catch (Exception e3) {
				// ignore this exception
			}
			return null;
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static FontReader getInstance() {
		if (__instance == null) {
			__instance = new FontReader();
		}
		return __instance;
	}

}
