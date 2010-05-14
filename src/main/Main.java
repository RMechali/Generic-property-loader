/** 
 * This file is part of Example4 project.
 *
 * Example4 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example4 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example4 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

import java.util.List;

import loader.error.BadPropertyFormatError;
import loader.error.ILoaderErrorListener;
import loader.standard.SPLoader;

/**
 * Example 4 main class
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

		// 1 - Declare our listener
		ILoaderErrorListener listener = new ILoaderErrorListener() {

			@Override
			public void notifyPropertyParseError(BadPropertyFormatError error) {
				// this event is propagated every time a property is bad formed
				// here we display simply the internationalized error that
				// happened when attempting to read the property
				System.err.println("Parsing error for poperty : "
						+ error.getKey() + ".Message : "
						+ error.getReaderErrorMessage());
			}

			@Override
			public void notifyPropertyNotFound(String key,
					List<String> searchedFiles) {
				// this event is propagated every time a property has not been
				// found after searching in every property files
				System.err.println("Property " + key + " not found in files "
						+ searchedFiles);
			}

			@Override
			public void notifyMissingFile(String fileName) {
				// this event is propagated every time a property file is not
				// found in classpath
				System.err.println("File not found " + fileName);
			}

			@Override
			public void notifyCouldNotReadProperty(String key) {
				// this event is propagated if the loader failed reading a
				// property (whatever the reason may be)
				System.err.println("Could not read property " + key);
			}

			@Override
			public void notifyCouldNotReadFile(String fileName) {
				// this event is propagated every time a property file could not
				// be read (mainly character formats problem)
				// we will not illustrate it here
			}
		};

		// 2 - Add the error listener
		SPLoader.addErrorListener(listener);

		// 3 - Add our property file
		SPLoader.addPropertyFile("properties.prop");

		// Now make those errors intentionally

		// 4 - error : file not found
		SPLoader.addPropertyFile("unexisting_file.prop");

		// 5 - error : property parse error and, finally, could not read
		// property (as the parsing attempt failed)
		Integer integer = SPLoader.getInteger("invalid.integer.property");
		System.err.println("Integer value : " + integer);

		// 6 - error : property not found and, finally, could not read
		// property (as it was found in no file)
		SPLoader.getColor("unexisting.property.color");
	}
}
