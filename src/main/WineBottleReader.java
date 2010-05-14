/** 
 * This file is part of Example5 project.
 *
 * Example5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example5 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

import java.util.StringTokenizer;

import loader.PropertyReader;

/**
 * Wine bottle property reader
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class WineBottleReader implements PropertyReader<WineBottle> {

	/**
	 * {@inherit}
	 */
	@Override
	public WineBottle readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		// parse the value as [name,year,domain]
		StringTokenizer tokenizer = new StringTokenizer(propertyRepresentation,
				",");
		if (tokenizer.countTokens() != 3) {
			throw new IllegalArgumentException(
					"Invalid attributes count for a wine bottle");
		}

		// retrieve bottle name
		String name = tokenizer.nextToken();

		// retrieve bottle year
		int year = 0;
		String yearToken = tokenizer.nextToken();

		try {
			year = new Integer(yearToken);
		} catch (NumberFormatException e) {
			// invalid year
			throw new IllegalArgumentException("The year is bad formatted : "
					+ yearToken + " does not stand for a valid integer value.");
		}

		// retrieve bottle domain
		String domain = tokenizer.nextToken();

		// return the corresponding object
		return new WineBottle(name, domain, year);
	}

}
