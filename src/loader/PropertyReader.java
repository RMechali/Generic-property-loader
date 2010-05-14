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

package loader;

/**
 * Interface for property deserializer from String values. It allows the
 * {@link BasicResourcesLoader} user to control the format he wants for
 * properties (the loader uses this interface to instantiate a new property
 * value)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 * 
 * @param T
 *            : type of property value for the property instantiated
 */
public interface PropertyReader<T> {

	/**
	 * Reads and returns property value from its string representation in the
	 * property file
	 * 
	 * @param propertyRepresentation
	 *            : property representation in the property text file
	 * @return - the property value (not null)
	 * @throws IllegalArgumentException
	 *             : if the representation can not be parsed, such exceptions
	 *             should be thrown (the exception message will be propagated)
	 */
	T readProperty(String propertyRepresentation)
			throws IllegalArgumentException;

}
