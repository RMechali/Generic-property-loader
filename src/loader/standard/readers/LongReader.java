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

import loader.PropertyReader;
import loader.messages.DMLoader;
import loader.standard.SPLoaderMessages;
import container.Property;

/**
 * Default reader for long properties
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class LongReader implements PropertyReader<Long> {

	/** Singleton instance **/
	private static LongReader __instance;

	/**
	 * Constructor
	 */
	private LongReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<Long> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		try {
			return new Property<Long>(new Long(propertyRepresentation),
					propertyRepresentation);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.LONG_READER_ERROR, propertyRepresentation));
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static LongReader getInstance() {
		if (__instance == null) {
			__instance = new LongReader();
		}
		return __instance;
	}

}
