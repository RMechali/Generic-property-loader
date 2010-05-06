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
 * Default reader for integer properties
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class IntegerReader implements IDirectValueConverter<Integer> {

	/** Singleton instance **/
	private static IntegerReader __instance;

	/**
	 * Constructor
	 */
	private IntegerReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Integer> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		try {
			return new Property<Integer>(new Integer(propertyRepresentation),
					propertyRepresentation);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.INTEGER_READER_ERROR,
					propertyRepresentation));
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Integer> convertToProperty(Integer value) {
		if (value == null) {
			return null;
		} else {
			return new Property<Integer>(value, value.toString());
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static IntegerReader getInstance() {
		if (__instance == null) {
			__instance = new IntegerReader();
		}
		return __instance;
	}

}
