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

package loader.standard;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.ImageIcon;

import loader.BasicResourcesLoader;
import loader.PropertyReader;
import loader.error.ILoaderErrorListener;
import loader.standard.readers.BigDecimalReader;
import loader.standard.readers.BooleanReader;
import loader.standard.readers.CharacterReader;
import loader.standard.readers.ColorReader;
import loader.standard.readers.DoubleReader;
import loader.standard.readers.FloatReader;
import loader.standard.readers.FontReader;
import loader.standard.readers.ImageIconReader;
import loader.standard.readers.IntegerReader;
import loader.standard.readers.LongReader;
import loader.standard.readers.StringReader;

/**
 * Standard Property Loader, that uses standard readers and singleton pattern to
 * offer a simplified resource loading API. This class has been designed to be
 * fully accessed through static methods (to shorten code)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class SPLoader {

	/** Loader singleton instance **/
	private static BasicResourcesLoader __loaderInstance;

	/**
	 * Constructor
	 */
	private SPLoader() {
		super();
	}

	/**
	 * Getter -
	 * 
	 * @return the loader singleton instance
	 */
	private static BasicResourcesLoader getLoaderInstance() {
		if (__loaderInstance == null) {
			__loaderInstance = new BasicResourcesLoader();
		}
		return __loaderInstance;
	}

	/**
	 * Adds an error listener to loader instance
	 * 
	 * @param listener
	 *            : listener
	 * @see BasicResourcesLoader#addErrorListener(ILoaderErrorListener)
	 */
	public static void addErrorListener(ILoaderErrorListener listener) {
		getLoaderInstance().addErrorListener(listener);
	}

	/**
	 * Removes an error listener from loader instance
	 * 
	 * @param listener
	 *            : listener
	 * @see BasicResourcesLoader#removeErrorListener(ILoaderErrorListener)
	 */
	public static void removeErrorListener(ILoaderErrorListener listener) {
		getLoaderInstance().removeErrorListener(listener);
	}

	/**
	 * Adds a property file to the loader instance
	 * 
	 * @param fileName
	 *            : name of the property file to add
	 * @see BasicResourcesLoader#addPropertyFile(String)
	 */
	public static void addPropertyFile(String fileName) {
		getLoaderInstance().addPropertyFile(fileName);
	}

	/**
	 * Adds a property file to the loaded instance
	 * 
	 * @param fileName
	 *            : file to add
	 * @param index
	 *            : index of the file
	 * @see BasicResourcesLoader#addPropertyFile(String, int)
	 */
	public static void addPropertyFile(String fileName, int index) {
		getLoaderInstance().addPropertyFile(fileName, index);
	}

	/**
	 * Exports file content
	 * 
	 * @param fileName
	 *            : name of the file to export
	 * @see loader.BasicResourcesLoader#exportFileContent(java.lang.String)
	 */
	public static void exportFileContent(String fileName) {
		getLoaderInstance().exportFileContent(fileName);
	}

	/**
	 * Is the file as parameter loaded
	 * 
	 * @param fileName
	 *            : name of the file
	 * @see loader.BasicResourcesLoader#isFileLoaded(java.lang.String)
	 */
	public static boolean isFileLoaded(String fileName) {
		return getLoaderInstance().isFileLoaded(fileName);
	}

	/**
	 * Setter for the class loader to be used by loader instance
	 * 
	 * @param classLoader
	 *            : class loader to use
	 * @see loader.BasicResourcesLoader#setClassLoader(java.lang.ClassLoader)
	 */
	public static void setClassLoader(ClassLoader classLoader) {
		getLoaderInstance().setClassLoader(classLoader);
	}

	/**
	 * Method to get a property through the loader instance
	 * 
	 * @param T
	 *            : type of value awaited
	 * @param key
	 *            : key of the property loaded
	 * @param reader
	 *            : reader for the property
	 * @return - the property loaded
	 * @see loader.BasicResourcesLoader#getProperty(java.lang.String,
	 *      loader.PropertyReader)
	 */
	public static <T> T getProperty(String key, PropertyReader<T> reader) {
		return getLoaderInstance().getProperty(key, reader);
	}

	public static BigDecimal getBigDecimal(String key) {
		return getProperty(key, BigDecimalReader.getInstance());
	}

	public static Boolean getBoolean(String key) {
		return getProperty(key, BooleanReader.getInstance());
	}

	public static Character getCharacter(String key) {
		return getProperty(key, CharacterReader.getInstance());
	}

	public static Color getColor(String key) {
		return getProperty(key, ColorReader.getInstance());
	}

	public static Double getDouble(String key) {
		return getProperty(key, DoubleReader.getInstance());
	}

	public static Float getFloat(String key) {
		return getProperty(key, FloatReader.getInstance());
	}

	public static Font getFont(String key) {
		return getProperty(key, FontReader.getInstance());
	}

	public static ImageIcon getIcon(String key) {
		return getProperty(key, ImageIconReader.getInstance());
	}

	public static Integer getInteger(String key) {
		return getProperty(key, IntegerReader.getInstance());
	}

	public static Long getLong(String key) {
		return getProperty(key, LongReader.getInstance());
	}

	public static String getString(String key) {
		return getProperty(key, StringReader.getInstance());
	}

}
