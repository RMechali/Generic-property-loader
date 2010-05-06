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

import java.math.BigDecimal;

import loader.messages.DMLoader;
import loader.standard.SPLoaderMessages;
import container.Property;

/**
 * Default reader for big decimal properties
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BigDecimalReader implements IDirectValueConverter<BigDecimal> {

	/** Singleton instance **/
	private static BigDecimalReader __instance;

	/**
	 * Constructor
	 */
	private BigDecimalReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<BigDecimal> readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		try {
			return new Property<BigDecimal>(new BigDecimal(
					propertyRepresentation), propertyRepresentation);
		} catch (NumberFormatException e) {
			// bad formatted number
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.BIG_DECIMAL_READER_ERROR,
					propertyRepresentation));
		}
	}

	/**
	 * {@inherit}
	 */
	@Override
	public Property<BigDecimal> convertToProperty(BigDecimal value) {
		if (value == null) {
			return null;
		} else {
			return new Property<BigDecimal>(value, value.toString());
		}
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static BigDecimalReader getInstance() {
		if (__instance == null) {
			__instance = new BigDecimalReader();
		}
		return __instance;
	}

}
