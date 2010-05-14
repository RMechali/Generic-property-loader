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
import loader.standard.readers.FontReader;
import loader.standard.readers.ImageIconReader;
import loader.standard.readers.direct.conversion.BigDecimalReader;
import loader.standard.readers.direct.conversion.BooleanReader;
import loader.standard.readers.direct.conversion.CharacterReader;
import loader.standard.readers.direct.conversion.ColorReader;
import loader.standard.readers.direct.conversion.DoubleReader;
import loader.standard.readers.direct.conversion.FloatReader;
import loader.standard.readers.direct.conversion.IDirectValueConverter;
import loader.standard.readers.direct.conversion.IntegerReader;
import loader.standard.readers.direct.conversion.LongReader;
import loader.standard.readers.direct.conversion.StringReader;
import container.Property;
import container.ResourcesContainer;

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

	/**
	 * API extension : set a property from its new value representation and the
	 * corresponding reader. Think about catching parse exceptions if you can
	 * not grant it is valid.
	 * 
	 * @param <T>
	 *            : type of value for that property
	 * @param key
	 *            : property key
	 * @param newValueRepresentation
	 *            : property new value representation (as it were in the
	 *            property file)
	 * @param newValueReader
	 *            : the new value reader
	 * @throws IllegalArgumentException
	 *             - if the reader failed parsing the new property value (you
	 *             may use the internationalized message to warn the user)<br>
	 *             - if the key is null<br>
	 *             - if the reader is null
	 */
	public static <T> void setProperty(String key,
			String newValueRepresentation, PropertyReader<T> newValueReader) {
		if (key == null) {
			throw new IllegalArgumentException(
					"The property key can not be null");
		}
		if (newValueReader == null) {
			throw new IllegalArgumentException(
					"The property reader can not be null");
		}

		Property<T> newPropertyValue = null;
		if (newValueRepresentation != null) {
			// read the property only if it is not null
			newPropertyValue = new Property<T>(newValueReader
					.readProperty(newValueRepresentation),
					newValueRepresentation);
		}

		ResourcesContainer.addPropertyI(key, newPropertyValue);
	}

	/**
	 * API extension : set a property from its new value and the corresponding
	 * converter.
	 * 
	 * @param <T>
	 *            : type of value for that property
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : property new value
	 * @param newValueConverter
	 *            : the new value converter, that will provide a property from
	 *            the value
	 * @throws IllegalArgumentException
	 *             - if the key is null<br>
	 *             - if the converter is null
	 */
	public static <T> void setProperty(String key, T newValue,
			IDirectValueConverter<T> newValueConverter) {
		if (key == null) {
			throw new IllegalArgumentException(
					"The property key can not be null");
		}
		if (newValueConverter == null) {
			throw new IllegalArgumentException(
					"The property reader can not be null");
		}
		ResourcesContainer.addPropertyI(key, new Property<T>(newValue,
				newValueConverter.convertToProperty(newValue)));
	}

	/**
	 * Big decimal property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static BigDecimal getBigDecimal(String key) {
		return getProperty(key, BigDecimalReader.getInstance());
	}

	/**
	 * Boolean property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Boolean getBoolean(String key) {
		return getProperty(key, BooleanReader.getInstance());
	}

	/**
	 * Character property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Character getCharacter(String key) {
		return getProperty(key, CharacterReader.getInstance());
	}

	/**
	 * Color property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Color getColor(String key) {
		return getProperty(key, ColorReader.getInstance());
	}

	/**
	 * Double property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Double getDouble(String key) {
		return getProperty(key, DoubleReader.getInstance());
	}

	/**
	 * Float property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Float getFloat(String key) {
		return getProperty(key, FloatReader.getInstance());
	}

	/**
	 * Font property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Font getFont(String key) {
		return getProperty(key, FontReader.getInstance());
	}

	/**
	 * Image icon property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static ImageIcon getIcon(String key) {
		return getProperty(key, ImageIconReader.getInstance());
	}

	/**
	 * Integer property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Integer getInteger(String key) {
		return getProperty(key, IntegerReader.getInstance());
	}

	/**
	 * Long property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static Long getLong(String key) {
		return getProperty(key, LongReader.getInstance());
	}

	/**
	 * String property getter (property getter closure)
	 * 
	 * @param key
	 *            : property key
	 * @return - property value
	 */
	public static String getString(String key) {
		return getProperty(key, StringReader.getInstance());
	}

	/**
	 * Big decimal property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setBigDecimal(String key, BigDecimal newValue) {
		setProperty(key, newValue, BigDecimalReader.getInstance());
	}

	/**
	 * Boolean property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setBoolean(String key, Boolean newValue) {
		setProperty(key, newValue, BooleanReader.getInstance());
	}

	/**
	 * Character property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setCharacter(String key, Character newValue) {
		setProperty(key, newValue, CharacterReader.getInstance());
	}

	/**
	 * Color property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setColor(String key, Color newValue) {
		setProperty(key, newValue, ColorReader.getInstance());
	}

	/**
	 * Color property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValueRepresentation
	 *            : new value representation for the property (at comma
	 *            separated or hexadecimal format)
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setColor(String key, String newValueRepresentation) {
		setProperty(key, newValueRepresentation, ColorReader.getInstance());
	}

	/**
	 * Double property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setDouble(String key, Double newValue) {
		setProperty(key, newValue, DoubleReader.getInstance());
	}

	/**
	 * Float property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setFloat(String key, Float newValue) {
		setProperty(key, newValue, FloatReader.getInstance());
	}

	/**
	 * Font property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param filePath
	 *            : path to the font file
	 * @throws IllegalArgumentException
	 *             - if the file path does not contain any valid font<br>
	 *             - if the key is null
	 */
	public static void setFont(String key, String filePath) {
		setProperty(key, filePath, FontReader.getInstance());
	}

	/**
	 * Icon property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param filePath
	 *            : path to the image file
	 * @throws IllegalArgumentException
	 *             - if the file path does not contain any valid image<br>
	 *             - if the key is null
	 */
	public static void setIcon(String key, String filePath) {
		setProperty(key, filePath, ImageIconReader.getInstance());
	}

	/**
	 * Integer property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setInteger(String key, Integer newValue) {
		setProperty(key, newValue, IntegerReader.getInstance());
	}

	/**
	 * Long property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setLong(String key, Long newValue) {
		setProperty(key, newValue, LongReader.getInstance());
	}

	/**
	 * String property value setter (property setter closure)
	 * 
	 * @param key
	 *            : property key
	 * @param newValue
	 *            : new value for the property
	 * @throws IllegalArgumentException
	 *             : if the key is null
	 */
	public static void setString(String key, String newValue) {
		if (key == null) {
			throw new IllegalArgumentException(
					"The property key can not be null");
		}
		// the closure can not work for identity, the method access directly to
		// resource container
		Property<String> newProperty = new Property<String>(newValue, newValue);
		ResourcesContainer.addPropertyI(key, newProperty);
	}

}
