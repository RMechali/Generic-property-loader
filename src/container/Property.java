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

package container;

/**
 * A simple property, that holds both the current value and the string value
 * from which it was built. That string value is useful to rebuild the property
 * file later one (it stores user / programmer exact entry)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class Property<T> {

	/** Property value **/
	private final T value;

	/** Property representation **/
	private final String representation;

	/**
	 * Constructor
	 */
	public Property(T value, String representation) {
		this.value = value;
		this.representation = representation;
	}

	/**
	 * Getter -
	 * 
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Getter -
	 * 
	 * @return the representation
	 */
	public String getRepresentation() {
		return representation;
	}

}
