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

package loader.messages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import loader.BasicResourcesLoader;
import loader.PropertyReader;
import loader.error.LoaderErrorAdapter;
import loader.messages.data.CompoundMessage;
import loader.messages.data.MessagesReader;
import loader.messages.error.IDMLoaderErrorListener;

/**
 * Default Messages Loader, acting in a way as a resource bundle. Every file you
 * add to it will also have the locale folder prefix (for instance, adding
 * "messages.prop" will result in adding "en_EN/messages.prop" if your current
 * locale is England one). <br>
 * The DMLoader properties are put in the ResourcesContainer. So you may use
 * that singleton to listen property values and edit them.<br>
 * See {@link DMLoader#addPropertyFile(String,int)} for more informations about
 * file loading, default locales and folder system.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public final class DMLoader extends LoaderErrorAdapter {

	/** Separator used for locale folder **/
	private static final String FOLDER_SEPARATOR = "_";

	/** Inner instance **/
	private static DMLoader __instance;

	/** Message properties loader **/
	private final BasicResourcesLoader loader;

	/** List of available locale folders (by preference order) **/
	private final List<String> availableLocaleFolders;

	/** Loader delegate **/
	private final FileLoaderDelegate loaderDelegate;

	/** List of error listeners **/
	private final Collection<IDMLoaderErrorListener> loaderErrorsListeners;

	/**
	 * Constructor
	 */
	private DMLoader() {

		// build the loader
		loader = new BasicResourcesLoader();

		// install file loader delegate
		loaderDelegate = new FileLoaderDelegate();
		loader.addErrorListener(loaderDelegate);

		// initialize dedicated listeners list
		loaderErrorsListeners = new ArrayList<IDMLoaderErrorListener>();

		// Retrieve available locale folders
		availableLocaleFolders = new ArrayList<String>();

		// default locale folder
		Locale defaultLocale = Locale.getDefault();
		availableLocaleFolders.add(getFolderFor(defaultLocale));

		// if the locale is not US...
		if (!defaultLocale.equals(Locale.US)) {
			// if the langage is English, add only US locale (to ensure US files
			// will be used every time a file is missing, for a english locale)
			if (!defaultLocale.getLanguage().equals(Locale.US.getLanguage())) {
				// same languages folders
				String defaultLangage = defaultLocale.getLanguage();
				for (Locale locale : Locale.getAvailableLocales()) {
					if (!locale.equals(defaultLocale)
							&& locale.getLanguage().equals(defaultLangage)) {
						// locale with the same language available
						availableLocaleFolders.add(getFolderFor(locale));
					}
				}
			}
			// US locale folder
			availableLocaleFolders.add(getFolderFor(Locale.US));
		}
	}

	/**
	 * Returns the folder for the locale
	 * 
	 * @param locale
	 *            locale
	 * @return - the locale folder, unic for the same language and country
	 */
	private String getFolderFor(Locale locale) {
		return locale.getLanguage() + FOLDER_SEPARATOR + locale.getCountry();
	}

	/**
	 * Singleton getter (for inner use)
	 * 
	 * @return - the singleton instance
	 */
	private static DMLoader getInstance() {
		if (__instance == null) {
			__instance = new DMLoader();
			// add internationalization files for this component
			addPropertyFile(IDMLoaderMessages.__INTERNATIONALIZED_FILE);
		}
		return __instance;
	}

	/**
	 * Retrieves a message in property files. If the message has parameters, it
	 * applies the function parameters to it by calling the toString() method.
	 * 
	 * @param key
	 *            : message key
	 * @param messageParameters
	 *            : message parameters
	 * @return - the formatted message with function parameters applied (or null
	 *         if the key has not been found)
	 * @example message : Hello {0}, function parameter : buddy, return : Hello
	 *          buddy
	 * @throws IllegalArgumentException
	 *             if function parameters are null
	 */
	public static String getMessage(String key, Object... messageParameters) {

		// verify parameters
		if (key == null) {
			throw new IllegalArgumentException(
					"The property key can not be null");
		}
		for (Object messageParameter : messageParameters) {
			if (messageParameter == null) {
				throw new IllegalArgumentException(
						"No message parameter can be null (you may use \"\" string instead)");
			}
		}

		DMLoader instance = getInstance();
		CompoundMessage messageFormatter = instance.loader.getProperty(key,
				MessagesReader.getInstance());

		if (messageFormatter != null) {
			// the property has been found, try to complete the message with
			// dynamic parameters
			try {
				return messageFormatter.formatMessage(messageParameters);
			} catch (IllegalArgumentException e) {
				instance.fireInvalidParametersNumber(key, messageFormatter
						.getAwaitedParametersCount(), messageFormatter
						.getMessagePattern(), e.getMessage());
			}
		}
		// return empty message
		return "";
	}

	/**
	 * Getter for a property stored in locale property file <br>
	 * Note : that method allows internationalization of locale depending
	 * properties like voice files, locale depending constants values...
	 * 
	 * @param <T>
	 *            : Type of property awaited
	 * @param key
	 *            : property key
	 * @param localeProperty
	 * @return
	 */
	public static <T> T getLocaleProperty(String key,
			PropertyReader<T> propertyReader) {
		DMLoader instance = getInstance();
		return instance.loader.getProperty(key, propertyReader);
	}

	/**
	 * Adds an error listener for this loader errors
	 * 
	 * @param listener
	 *            listener to add
	 */
	public static void addErrorListener(IDMLoaderErrorListener listener) {
		getInstance().loader.addErrorListener(listener);
		getInstance().loaderErrorsListeners.add(listener);
	}

	/**
	 * Adds an error listener
	 * 
	 * @param listener
	 *            listener to remove
	 */
	public static void removeErrorListener(IDMLoaderErrorListener listener) {
		getInstance().loader.removeErrorListener(listener);
		getInstance().loaderErrorsListeners.remove(listener);
	}

	/**
	 * Adds a property file in messages files as following :
	 * [locale_country_folder]/fileName (example : fr_FR/messages.prop,
	 * en_US/messages.prop). To know which folder to use, the class concatenates
	 * {@link Locale#getLanguage()},"_",{@link Locale#getCountry()} (see its
	 * constants to get folder names).
	 * 
	 * @param fileName
	 *            : file name (ex : "messages.prop" or, better,
	 *            "my_project_1.prop" - see the comments below)
	 * @param index
	 *            : index in the property files list, @see
	 *            {@link loader.BasicResourcesLoader#addPropertyFile(String, int)}
	 * @warning : if the file can not be found in the locale country folder, it
	 *          will be switched to the first country folder with the similar
	 *          language; for instance fr_CA (for French / Canada) is not
	 *          available, then fr_FR will be tried (or any other French
	 *          speaking land). Finally, if still not found, it will be added as
	 *          en_US/[file_name] as USA is considered as default land for
	 *          language. Make sure that it is <em> always available</em> in
	 *          your Jar files. That rule has an exception for English speaking
	 *          lands where only US folder will be tried when failing to find
	 *          the current locale folder (as US is considered as default within
	 *          English languages).
	 * @note that the applications that use your Jar can override your messages
	 *       files by defining in class path a file with the same name. That way
	 *       they can provide it for the missing languages or override its
	 *       content. This also implies potential name conflicts (if two
	 *       applications intend to use a /en_US/messages.prop file for
	 *       instance). To solve it you should use a file name like
	 *       [project_name]_messages.prop that should be a nice singleton
	 *       identifier for your message files.
	 * 
	 */
	public static void addPropertyFile(String fileName, int index) {
		getInstance().loaderDelegate.addMessageFile(fileName, index);
	}

	/**
	 * Adds a property file in messages file at messages files end. (see
	 * {@link DMLoader#addPropertyFile(String, int)})
	 */
	public static void addPropertyFile(String fileName) {
		addPropertyFile(fileName, -1);
	}

	/**
	 * Delegate method.
	 * 
	 * @see loader.BasicResourcesLoader#getClassLoader()
	 */
	public static ClassLoader getClassLoader() {
		return getInstance().loader.getClassLoader();
	}

	/**
	 * Delegate method.
	 * 
	 * @see loader.BasicResourcesLoader#setClassLoader(java.lang.ClassLoader)
	 */
	public static void setClassLoader(ClassLoader classLoader) {
		getInstance().loader.setClassLoader(classLoader);
	}

	/**
	 * Fires a current locale not found event
	 * 
	 * @param localeFolder
	 *            : locale folder
	 * @param fileName
	 *            : file name
	 * @param resultingPath
	 *            : resulting path ([locale folder]/[file name])
	 */
	private void fireCurrentLocaleNotFound(String localeFolder,
			String fileName, String resultingPath) {
		// avoid concurrent access by duplicating the list
		for (IDMLoaderErrorListener listener : new ArrayList<IDMLoaderErrorListener>(
				loaderErrorsListeners)) {
			listener.notifyCurrentLocaleNotFound(localeFolder, fileName,
					resultingPath);
		}
	}

	/**
	 * Fires a US locale not found event (property file not added)
	 * 
	 * @param fileName
	 *            : file name
	 */
	private void fireUSLocaleNotFound(String fileName) {
		// avoid concurrent access by duplicating the list
		for (IDMLoaderErrorListener listener : new ArrayList<IDMLoaderErrorListener>(
				loaderErrorsListeners)) {
			listener.notifyUSLocaleNotFound(fileName);
		}
	}

	/**
	 * Fires an invalid number of parameters event
	 * 
	 * @param key
	 *            : property key
	 * @param awaitedparametersNumber
	 *            : number of parameters awaited
	 * @param messagePattern
	 *            : message pattenr
	 * @param errorMessage
	 *            : internationalized error message
	 */
	private void fireInvalidParametersNumber(String key,
			int awaitedparametersNumber, String messagePattern,
			String errorMessage) {
		// avoid concurrent access by duplicating the list
		for (IDMLoaderErrorListener listener : new ArrayList<IDMLoaderErrorListener>(
				loaderErrorsListeners)) {
			listener.notifyInvalidParametersNumber(key,
					awaitedparametersNumber, messagePattern, errorMessage);
		}
	}

	/**
	 * Class dedicate to messages file loading
	 */
	private class FileLoaderDelegate extends LoaderErrorAdapter {

		private boolean fileLoaded;

		/**
		 * Loads the messages file as parameter
		 * 
		 * @param fileName
		 *            : file name
		 * @param index
		 *            : index of the file
		 */
		public void addMessageFile(String fileName, int index) {
			// verify that the loader does not already contain it
			if (loader.isFileLoaded(fileName)) {
				return;
			}
			// initialize loaded contextual data
			fileLoaded = false;
			// build the resulting string
			for (ListIterator<String> availableFoldersIt = availableLocaleFolders
					.listIterator(); availableFoldersIt.hasNext()
					&& !fileLoaded;) {
				fileLoaded = true;

				// build folder name
				String localeFolder = availableFoldersIt.next();
				StringBuffer buffer = new StringBuffer(localeFolder);
				buffer.append("/");
				buffer.append(fileName);

				// attempt to load the file
				loader.addPropertyFile(buffer.toString(), index);

				if (fileLoaded) {
					return;
				}

				if (!fileLoaded) {
					int currentIndex = availableFoldersIt.previousIndex();
					if (currentIndex == 0) {
						// the default locale has not been found
						fireCurrentLocaleNotFound(localeFolder, fileName,
								buffer.toString());
					}
				}
			}
			fireUSLocaleNotFound(fileName);
		}

		/**
		 * {@inherit}
		 */
		@Override
		public void notifyMissingFile(String fileName) {
			fileLoaded = false;
		}

	}

}
