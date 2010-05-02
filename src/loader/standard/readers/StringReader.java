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
import container.Property;

/**
 * Default reader for string properties.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class StringReader implements PropertyReader<String> {

	/** Singleton instance **/
	private static StringReader __instance;

	/**
	 * Constructor
	 */
	private StringReader() {
		// forbids external instance
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<String> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		// identity
		return new Property<String>(propertyRepresentation,
				propertyRepresentation);
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static StringReader getInstance() {
		if (__instance == null) {
			__instance = new StringReader();
		}
		return __instance;
	}

}
