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

import loader.PropertyReader;
import container.Property;

/**
 * This interface marks a reader that can export a property value as a property
 * (convenience method)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param T
 *            : type of value to read / convert
 */
public interface IDirectValueConverter<T> extends PropertyReader<T> {

	/**
	 * Converts a value into the corresponding property
	 * 
	 * @param value
	 * @return - the corresponding property or null if null as parameter
	 * @note : For a property reader to implement this method, the following
	 *       rule must be verified : the property representation can be
	 *       converted in a unique value, the property value can be converted in
	 *       a unique representation
	 * 
	 */
	Property<T> convertToProperty(T value);

}
