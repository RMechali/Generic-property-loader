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
 * Default reader for boolean properties
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BooleanReader implements IDirectValueConverter<Boolean> {

	/** Singleton instance **/
	private static BooleanReader __instance;

	/**
	 * Constructor
	 */
	private BooleanReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Boolean> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		String stringValue = propertyRepresentation.trim();
		// create the parsing method as the Boolean one do not propagate errors
		if (stringValue.equalsIgnoreCase("true")) {
			return new Property<Boolean>(true, propertyRepresentation);
		}
		if (stringValue.equalsIgnoreCase("false")) {
			return new Property<Boolean>(false, propertyRepresentation);
		}
		throw new IllegalArgumentException(DMLoader.getMessage(
				SPLoaderMessages.BOOLEAN_READER_ERROR, propertyRepresentation));
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Boolean> convertToProperty(Boolean value) {
		if (value == null) {
			return null;
		} else {
			return new Property<Boolean>(value, value.toString());
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static BooleanReader getInstance() {
		if (__instance == null) {
			__instance = new BooleanReader();
		}
		return __instance;
	}

}
