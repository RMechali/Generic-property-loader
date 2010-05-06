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

package loader.standard.readers.direct.conversion;

import loader.messages.DMLoader;
import loader.standard.SPLoaderMessages;
import container.Property;

/**
 * Default reader for char properties
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class CharacterReader implements IDirectValueConverter<Character> {

	/** Singleton instance **/
	private static CharacterReader __instance;

	/**
	 * Constructor
	 */
	private CharacterReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Character> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		String stringValue = propertyRepresentation.trim();
		if (stringValue.isEmpty() || stringValue.length() > 1) {
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.CHARACTER_READER_ERROR,
					propertyRepresentation));
		}
		return new Property<Character>(new Character(stringValue.charAt(0)),
				propertyRepresentation);
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Character> convertToProperty(Character value) {
		if (value == null) {
			return null;
		} else {
			return new Property<Character>(value, value.toString());
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static CharacterReader getInstance() {
		if (__instance == null) {
			__instance = new CharacterReader();
		}
		return __instance;
	}

}
