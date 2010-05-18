/** 
 * This file is part of Example6 project.
 *
 * Example6 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example6 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example6 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

import java.util.Locale;

import javax.swing.JOptionPane;

import loader.messages.DMLoader;

/**
 * Main class for example 6
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class Main {

	/**
	 * Main method
	 * 
	 * @param args
	 *            : parameters
	 */
	public static void main(String[] args) {

		// we use the first arguments to know locale
		Locale.setDefault(new Locale(args[0], args[1]));

		// now we load our messages file and print it on screen. The right
		// folder should be automatically chosen
		DMLoader.addPropertyFile("example6_messages.prop");

		// we show both message (the second message has a parameter, we fill it
		// too)
		String helloMessage = DMLoader.getMessage("hello.message");
		String welcomeMessage = DMLoader.getMessage("welcome.message",
				"Example 6");
		JOptionPane.showMessageDialog(null, welcomeMessage, helloMessage,
				JOptionPane.INFORMATION_MESSAGE);

	}
}
