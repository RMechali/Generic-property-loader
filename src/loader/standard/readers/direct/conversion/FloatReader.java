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
 * Default reader for float properties
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class FloatReader implements IDirectValueConverter<Float> {

	/** Singleton instance **/
	private static FloatReader __instance;

	/**
	 * Constructor
	 */
	private FloatReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Float> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		try {
			return new Property<Float>(new Float(propertyRepresentation),
					propertyRepresentation);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(DMLoader
					.getMessage(SPLoaderMessages.FLOAT_READER_ERROR,
							propertyRepresentation));
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Float> convertToProperty(Float value) {
		if (value == null) {
			return null;
		} else {
			return new Property<Float>(value, value.toString());
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static FloatReader getInstance() {
		if (__instance == null) {
			__instance = new FloatReader();
		}
		return __instance;
	}

}
