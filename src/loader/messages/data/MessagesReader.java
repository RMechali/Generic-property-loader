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

package loader.messages.data;

import loader.PropertyReader;
import container.Property;

/**
 * Reader for compound message
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class MessagesReader implements PropertyReader<CompoundMessage> {

	/**
	 * Singletong instance
	 */
	private static MessagesReader __instance;

	/**
	 * Constructor
	 */
	private MessagesReader() {
		// forbids external instance
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<CompoundMessage> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		// propapate class exceptions
		return new Property<CompoundMessage>(new CompoundMessage(
				propertyRepresentation), propertyRepresentation);
	}

	/**
	 * Getter -
	 * 
	 * @return the singleton instance
	 */
	public static MessagesReader getInstance() {
		if (__instance == null) {
			__instance = new MessagesReader();
		}
		return __instance;
	}

}
