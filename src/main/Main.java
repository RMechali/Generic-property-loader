/** 
 * This file is part of Example3 project.
 *
 * Example3 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example3 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example3 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

import javax.swing.JOptionPane;

import loader.standard.SPLoader;

/**
 * Main class for example 3
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class Main {

	/**
	 * Main method
	 * 
	 * @param args
	 *            : program parameters (useless)
	 */
	public static void main(String[] args) {
		// add defaults properties file with a lower priority
		SPLoader.addPropertyFile("defaults_example3.prop", 0);
		// add editable properties file with a higher priority
		SPLoader.addPropertyFile("example3.prop", 1);
		// show the property value
		JOptionPane.showMessageDialog(null, "My value worthes : "
				+ SPLoader.getInteger("an.integer.property"));
	}
}
