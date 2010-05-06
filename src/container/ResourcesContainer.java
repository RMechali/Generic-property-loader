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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * Global container for resources. Any user can put properties here so that they
 * are centralized. <br>
 * It uses property change support to propagate change events (the element fired
 * are {@link Property}). Such mechanism allows the programmer to bind directly
 * the resource container into GUI properties, like colors, fonts, ...
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class ResourcesContainer {

	/** Marker for for a value that was not found **/
	private static final Property<Object> ITEM_NOT_FOUND = new Property<Object>(
			null, null);

	/** Singleton instance **/
	private static ResourcesContainer __instance;

	/** List of properties already loaded **/
	private final Map<String, Property<?>> loadedProperties;

	/** Property change support **/
	private final PropertyChangeSupport support;

	/**
	 * Constructor
	 */
	private ResourcesContainer() {
		loadedProperties = new HashMap<String, Property<?>>();
		support = new PropertyChangeSupport(this);
	}

	/**
	 * Adds a loaded property value in this container
	 * 
	 * @param key
	 *            : key loaded
	 * @param value
	 *            : value for the key
	 */
	public void addProperty(String key, Property<?> value) {
		Property<?> oldProp = loadedProperties.get(key);
		Object oldValue = null;
		if (oldProp != null && !isUnfoundProperty(oldProp)) {
			oldValue = oldProp.getValue();
		}

		loadedProperties.put(key, value);
		Object newValue = null;
		if (value != null && !isUnfoundProperty(value)) {
			newValue = value.getValue();
		}

		// fire a property change of values only
		support.firePropertyChange(key, oldValue, newValue);
	}

	/**
	 * Adds a property that was not found in this container
	 * 
	 * @param key
	 *            : key loaded
	 * @param value
	 *            : value for the key
	 */
	public void addProperty(String key) {
		addProperty(key, ITEM_NOT_FOUND);
	}

	/**
	 * Returns a property value
	 * 
	 * @param key
	 *            : key of the property
	 * @return - the value found or null
	 * @warning - this method should be called by loaders only as it does not
	 *          grant that the property has been already extracted from files or
	 *          that it is not an item not found. When you call it, verify both
	 */
	public Property<?> getProperty(String key) {
		return loadedProperties.get(key);
	}

	/**
	 * Clear properties - this method will force every properties to be reloded
	 * after on
	 * 
	 */
	public void clearProperties() {
		for (String key : loadedProperties.keySet()) {
			// notify clearing
			addProperty(key, null);
		}
		// clear the map
		loadedProperties.clear();
	}

	/**
	 * Adds a loaded property value in the instance
	 * 
	 * @param key
	 *            : key loaded
	 * @param value
	 *            : value for the key
	 */
	public static void addPropertyI(String key, Property<?> value) {
		getInstance().addProperty(key, value);
	}

	/**
	 * Adds a property that was not found in the instance
	 * 
	 * @param key
	 *            : key loaded
	 * @param value
	 *            : value for the key
	 */
	public static void addPropertyI(String key) {
		getInstance().addProperty(key);
	}

	/**
	 * Returns a property value from the instance
	 * 
	 * @param key
	 *            : key of the property
	 * @return - the value found or null
	 * @warning - this method should be called by loaders only as it does not
	 *          grant that the property has been already extracted from files
	 */
	public static Property<?> getPropertyI(String key) {
		return getInstance().getProperty(key);
	}

	/**
	 * Is the property value as parameter a property that was not found?
	 * 
	 * @param value
	 *            : the value
	 * @return - true if the property has already been read but has not been
	 *         found
	 */
	public static boolean isUnfoundProperty(Object value) {
		return value == ITEM_NOT_FOUND;
	}

	/**
	 * Clear instance properties
	 */
	public static void clearPropertiesI() {
		getInstance().clearProperties();
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#addPropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners()
	 */
	public PropertyChangeListener[] getPropertyChangeListeners() {
		return support.getPropertyChangeListeners();
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#getPropertyChangeListeners(java.lang.String)
	 */
	public PropertyChangeListener[] getPropertyChangeListeners(
			String propertyName) {
		return support.getPropertyChangeListeners(propertyName);
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#hasListeners(java.lang.String)
	 */
	public boolean hasListeners(String propertyName) {
		return support.hasListeners(propertyName);
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	/**
	 * Delegate method.
	 * 
	 * @see java.beans.PropertyChangeSupport#removePropertyChangeListener(java.lang.String,
	 *      java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static ResourcesContainer getInstance() {
		if (__instance == null) {
			__instance = new ResourcesContainer();
		}
		return __instance;
	}

}
