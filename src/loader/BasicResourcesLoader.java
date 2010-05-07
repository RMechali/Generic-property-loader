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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import loader.error.BadPropertyFormatError;
import loader.error.ILoaderErrorListener;
import container.Property;
import container.ResourcesContainer;

/**
 * Loader for externalized values and resources. Once a value is loaded in one
 * data type (like Integer or Double), the get property method should never be
 * called with another type reader (because the initial type is dynamically
 * casted). Instead, recover the value with the same reader and then perform
 * conversion operation.<br>
 * When using the loader keep in mind the following facts : <br>
 * - The property files have priority so that you can override a property
 * defined in another file with lesser priority (a good practice for Jars that
 * would be included in applications is to add their file at priority 0). When a
 * property is not valid, the loader searches automatically for the next file
 * containing valid property. This allows embedding in applications a read-only
 * property file that would be used as default values if the user can modify the
 * effective property file <br>
 * - The property files are loaded from the classpath. This means that the first
 * file with the file name provided will be parsed an not others. In most of the
 * cases (if you have not configured your project differently) the first
 * classpath elements that will get tested are the application ones so you could
 * replace any property files defined by included Jars. - When adding a new
 * property file, every known files are cleared. Therefore you should try to add
 * all property files as soon as possible (otherwise the loader will probably
 * search many times the same properties).<br>
 * - You can decide to export one of the property files you added at any time.
 * This functionality meets the particular need to leave the user or the
 * application editing properties and then save them. When exporting the file,
 * the toString() method will be called for any known property - this means the
 * property will have the first {@link ResourcesContainer} valid value OR the
 * invalid value of the property file. This also means that a property reader
 * should always be able to read the toString() value.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BasicResourcesLoader {

	/** Class loader for this resource loader **/
	private ClassLoader classLoader;

	/** List of property aggregation loaded, sorted by file name **/
	private final Map<String, Properties> knownPropertyFiles;

	/** List of listeners for the errors that occur while loading properties **/
	private final Collection<ILoaderErrorListener> _errorListeners;

	/**
	 * List of property files available for the loader. Those files will be
	 * loaded using the class loader. Therefore it might be relative paths or
	 * file name only. Greater is its index in the list, greater is the file
	 * priority. It involves, for instance, that if the key
	 * "myapplication.title" is defined in files(0) and in files(3), the
	 * property value will be the one defined in file(3)).
	 */
	private final List<String> propertyFiles;

	/**
	 * Constructor
	 */
	public BasicResourcesLoader() {
		propertyFiles = new ArrayList<String>();
		knownPropertyFiles = new HashMap<String, Properties>();
		classLoader = ClassLoader.getSystemClassLoader();
		_errorListeners = new ArrayList<ILoaderErrorListener>();
	}

	/**
	 * Adds an available property file to the loader. This file will later be
	 * retrieved using the classpath
	 * 
	 * @param fileName
	 *            : file name
	 * @param index
	 *            : index in the property files list. Provide -1 to add it at
	 *            list end
	 * @note : this method should be called at application start for more
	 *       efficiency (otherwise all key may need to be verified)
	 * @throws IllegalArgumentException
	 *             if the file name is null or the file index is out of bounds
	 */
	public void addPropertyFile(String fileName, int index) {

		// verify parameters
		if (fileName == null) {
			throw new IllegalArgumentException("The file name cannot be null");
		}

		if (index < -1 || index > propertyFiles.size()) {
			throw new IllegalArgumentException(
					"The index provided is out of the files list indices");
		}

		// do not add a file already known
		if (isFileLoaded(fileName)) {
			return;
		}

		// verify file existence
		InputStream resourceAsStream = classLoader
				.getResourceAsStream(fileName);
		if (resourceAsStream == null) {
			fireFileNotFound(fileName);
			return;
		}
		// build the properties
		try {
			Properties fileProperties = new Properties();
			fileProperties.load(resourceAsStream);
			// add the file
			if (index == -1) {
				propertyFiles.add(fileName);
			} else {
				propertyFiles.add(index, fileName);
			}
			// store the new file properties
			knownPropertyFiles.put(fileName, fileProperties);

			// revalidate property data
			mapsDataInvalidated();
		} catch (IllegalArgumentException excepion) {
			fireCouldNotRead(fileName);
		} catch (IOException exception) {
			fireCouldNotRead(fileName);
		}
	}

	/**
	 * The maps data has been invalidated, update it
	 * 
	 * @param fileProperties
	 *            : the property file added in properties form
	 * 
	 */
	private void mapsDataInvalidated() {

		// clear the map to force next call to getProperty(...) reloading the
		// whole list
		ResourcesContainer.clearPropertiesI();
	}

	/**
	 * Returns the property in the given type
	 * 
	 * @param <T>
	 *            : type of property to read
	 * @param key
	 *            : key
	 * @param propertyClass
	 *            : property class type
	 * @return - the property value
	 * @throws IllegalArgumentException
	 *             - if the listener is reader is null <br>
	 *             - if the key is null <br>
	 *             - if the property was already loaded but do not match reader
	 *             type
	 * @warning : no class cast exception will be propagating at return (at
	 *          least in the JVM I am currently using, that seems not to check
	 *          dynamic types). If the progammer attempts to get a property as
	 *          {ClassA} and then as {ClassB} the exception will happen at
	 *          assignment time.
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProperty(String key, PropertyReader<T> reader) {

		// Test parameters
		if (reader == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ "- getProperty(): Property reader can not be null");
		}
		if (key == null) {
			throw new IllegalArgumentException(getClass().getName()
					+ "- getProperty(): Property key can not be null");
		}

		Property<?> loadedKeyValue = ResourcesContainer.getPropertyI(key);

		if (ResourcesContainer.isUnfoundProperty(loadedKeyValue)) {
			// A - the property has already been loaded but was not found
			return null;
		}
		// B - the property has correctly been loaded previously
		if (loadedKeyValue != null) {
			// send the property value at the required type
			return (T) loadedKeyValue.getValue();
		}

		// C - The property has never been loaded. Find and parse the property
		// value
		Property<T> basicPropertyValue = getBasicProperty(key, reader);
		if (basicPropertyValue != null) {
			// the property has been found, store it
			ResourcesContainer.addPropertyI(key, basicPropertyValue);
			// return the property found
			return basicPropertyValue.getValue();
		}

		// D - the property has been parsed but not found, store an item not
		// found
		ResourcesContainer.addPropertyI(key);
		return null;
	}

	/**
	 * Returns the basic property for the key as parameter. Notifies any
	 * listener when an error occurs.
	 * 
	 * @param key
	 *            : key
	 * @return - the value found or null if none
	 */
	private <T> Property<T> getBasicProperty(String key,
			PropertyReader<T> reader) {

		// counter for errors (allows to determinate a missing property)
		int errorsCount = 0;

		// search in files for the property
		if (propertyFiles.isEmpty()) {
			// no property files defines
			throw new IllegalStateException(getClass().getName()
					+ " : no property file defined");
		}

		// iterator from more important file to less important one
		for (ListIterator<String> fileNameIterator = propertyFiles
				.listIterator(propertyFiles.size()); fileNameIterator
				.hasPrevious();) {

			// retrieve the property set corresponding to that file
			String fileName = fileNameIterator.previous();
			Properties properties = knownPropertyFiles.get(fileName);
			String litteralValue = properties.getProperty(key);

			if (litteralValue != null) {
				// The property has been found. Read its content through user
				// provided reader
				try {

					// read serialized data
					Property<T> newValue = reader.readProperty(litteralValue);

					// store the property value
					ResourcesContainer.addPropertyI(key, newValue);

					// return it
					return newValue;
				} catch (IllegalArgumentException e) {
					// notifies that the parser found an error
					fireParseError(new BadPropertyFormatError(fileName, key,
							litteralValue, e.getMessage()));
					errorsCount++;
				}
			}
		}

		// error case : no valid property has been found
		if (errorsCount == 0) {
			// the property has not been found
			fireNotFoundError();
		}

		// error case : all properties was bad formatted, the property has
		// finally not been loaded (the notified class may decide here to push a
		// default value)
		fireNotLoaded(key);
		return null;
	}

	/**
	 * Adds an available property file to the loader as most important one. This
	 * file will later be retrieved using the classpath
	 * 
	 * @param fileName
	 *            : file name
	 * @note : this method should be called at application start for more
	 *       efficiency (otherwise all key may need to be verified)
	 */
	public void addPropertyFile(String fileName) {
		addPropertyFile(fileName, -1);
	}

	/**
	 * Has the file as parameter been loaded
	 * 
	 * @param fileName
	 *            : file name
	 * @return - true if it has been loaded
	 */
	public boolean isFileLoaded(String fileName) {
		return propertyFiles.contains(fileName);
	}

	/**
	 * Adds a loader error listener
	 * 
	 * @param listener
	 *            : listener
	 * @throws IllegalArgumentException
	 *             : if the listener is null
	 */
	public void addErrorListener(ILoaderErrorListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("A listener can not be null");
		}
		_errorListeners.add(listener);
	}

	/**
	 * Removes a loader error listener
	 * 
	 * @param listener
	 *            : listener
	 * @throws IllegalArgumentException
	 *             : if the listener is null
	 */
	public void removeErrorListener(ILoaderErrorListener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("A listener can not be null");
		}
		_errorListeners.remove(listener);
	}

	/**
	 * Getter -
	 * 
	 * @return the classLoader
	 */
	public ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * Setter -
	 * 
	 * @param classLoader
	 *            the classLoader to set
	 */
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * Exports the file named [fileName] and overwrite its current properties
	 * definition with the one known in {@link ResourcesContainer} instances for
	 * the same keys. This method is especially designed for the programmer to
	 * provide a user property files. Actually, the user could edit the
	 * properties through the application - or the application could force some
	 * property values - and then save it over the initial file. Remember that
	 * such a mutable file should not be placed within a Jar to adapt your
	 * deployment strategy.<br>
	 * The properties stored are the one currently defined in the application.
	 * So, if some of them are unknown, the method store those properties
	 * unchanged.
	 * 
	 * @param fileName
	 *            : file name
	 * @throws IllegalArgumentException
	 *             if the file name is null or unknown <br>
	 *             if the file can no longer be found<br>
	 *             if the file can not be written (no rights or placed in a Jar) <br>
	 *             if the file can not be opened (already opened by another
	 *             application for instance)
	 * @throws RuntimeException
	 *             : if an IO exception occurs (not considered as illegal
	 *             argument exception)
	 * @note : any comment will be lost in the file
	 * 
	 */
	public void exportFileContent(String fileName) {

		// test parameters
		if (fileName == null) {
			throw new IllegalArgumentException("The file name can not be null");
		}
		Properties fileProperties = knownPropertyFiles.get(fileName);
		if (fileProperties == null) {
			throw new IllegalArgumentException(
					"Unknown file name. Has it been loaded correctly?");
		}

		// get the file resource
		URL resourceURL = classLoader.getResource(fileName);
		if (resourceURL == null) {
			// should not happen as the file was loaded before
			throw new IllegalArgumentException(
					"Unexpeced error : the file can no longer be found in class path - maybe did the user suppressed it?");
		}
		File resourceFile = null;
		try {
			resourceFile = new File(resourceURL.toURI());
		} catch (URISyntaxException e1) {
			// cannot happen
		}
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(resourceFile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(
					"The file can not be opened : Is it already opened by another application?");
		} catch (SecurityException e) {
			throw new IllegalArgumentException("The file can not be written");
		}

		// update the corresponding properties
		Set<Entry<Object, Object>> entrySet = fileProperties.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			Property<?> currentPropertyValue = ResourcesContainer.getInstance()
					.getProperty((String) entry.getKey());
			if (currentPropertyValue != null
					&& !ResourcesContainer
							.isUnfoundProperty(currentPropertyValue)) {
				// that property is known, update it in case where the
				// application changed it
				fileProperties.setProperty((String) entry.getKey(),
						currentPropertyValue.getRepresentation());
			}
		}

		// write the new file (lose any comment of the previous one)
		try {
			fileProperties.store(fileOutputStream, null);
		} catch (IOException e) {
			throw new RuntimeException("Could not save the property file "
					+ fileName + ". The following error occured : "
					+ e.getMessage());
		}

	}

	/**
	 * Notifies listener that a file was not found
	 * 
	 * @param fileName
	 *            : file name
	 * 
	 */
	private void fireFileNotFound(String fileName) {
		// avoid concurrent access by duplicating the list
		for (ILoaderErrorListener listener : new ArrayList<ILoaderErrorListener>(
				_errorListeners)) {
			listener.notifyMissingFile(fileName);
		}
	}

	/**
	 * Notifies listener that a file could not be read
	 * 
	 * @param fileName
	 *            : file name
	 * 
	 */
	private void fireCouldNotRead(String fileName) {
		// avoid concurrent access by duplicating the list
		for (ILoaderErrorListener listener : new ArrayList<ILoaderErrorListener>(
				_errorListeners)) {
			listener.notifyCouldNotReadFile(fileName);
		}
	}

	/**
	 * Notifies listeners that a property was not loaded
	 * 
	 * @param key
	 *            : key not loaded
	 */
	private void fireNotLoaded(String key) {
		// avoid concurrent access by duplicating the list
		for (ILoaderErrorListener listener : new ArrayList<ILoaderErrorListener>(
				_errorListeners)) {
			listener.notifyCouldNotReadProperty(key);
		}
	}

	/**
	 * Notify listeners that a property was not found in current files
	 */
	private void fireNotFoundError() {
		// avoid concurrent access by duplicating the list
		for (ILoaderErrorListener listener : new ArrayList<ILoaderErrorListener>(
				_errorListeners)) {
			listener
					.notifyPropertyNotFound(new ArrayList<String>(propertyFiles));
		}
	}

	/**
	 * Notify the listeners that reading a property failed
	 * 
	 * @param error
	 *            : error that happened
	 */
	private void fireParseError(BadPropertyFormatError error) {
		// avoid concurrent access by duplicating the list
		for (ILoaderErrorListener listener : new ArrayList<ILoaderErrorListener>(
				_errorListeners)) {
			listener.notifyPropertyParseError(error);
		}
	}

}
