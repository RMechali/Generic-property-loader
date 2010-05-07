/** 
 * This file is part of Example2 project.
 *
 * Example2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example2 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

import javax.swing.SwingUtilities;

/**
 * Main class for the example 2 project
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class Main {

	/**
	 * Main
	 * 
	 * @param args
	 *            : parameters (useless)
	 */
	public static void main(String[] args) {
		// invoke GUI in AWT event queue (general good practice when using
		// swing)
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				// create a frame and set it visible
				new MyPropertiesFrame().setVisible(true);
			}
		});
	}

}
