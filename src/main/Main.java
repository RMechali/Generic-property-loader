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

import loader.standard.SPLoader;

/**
 * Example 5 main class.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class Main {

	/**
	 * Main method
	 * 
	 * @param args
	 *            : application parameters
	 */
	public static void main(String[] args) {
		// add our property file
		SPLoader.addPropertyFile("properties.prop");
		// display the bottle instance on standard output stream
		System.out.println(SPLoader.getProperty("my.preferred.bottle",
				new WineBottleReader()));
	}
}
