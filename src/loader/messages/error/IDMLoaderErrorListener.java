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

package loader.messages.error;

import loader.error.ILoaderErrorListener;

/**
 * A loader dedicated to messages loader errors. <br>
 * Note that notifyMissingFile(...) means that the loader attempted to load one
 * same language file (or current locale / US files). It can be used as
 * indication but the dedicated interface methods should be more significant
 * 
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public interface IDMLoaderErrorListener extends ILoaderErrorListener {

	/**
	 * Notifies that a file has not been found for the current locale
	 * 
	 * @param localeFolder
	 *            : locale folder
	 * @param fileName
	 *            : file name
	 * @param resultingPath
	 *            : resulting path ([locale folder]/[file name])
	 */
	void notifyCurrentLocaleNotFound(String localeFolder, String fileName,
			String resultingPath);

	/**
	 * Notifies that a file has not been found for US locale (as it is the last
	 * locale tried, the property file is missing)
	 * 
	 * @param fileName
	 *            : file name
	 * @note that the resulting path is necessary (US/[file name])
	 */
	void notifyUSLocaleNotFound(String fileName);

	/**
	 * Notifies that the number of parameters is not valid for a property
	 * message
	 * 
	 * @param key
	 *            : property key
	 * @param awaitedparametersNumber
	 *            : number of parameters awaited
	 * @param messagePattern
	 *            : message pattern of the property (the value read from file)
	 * @param errorMessage
	 *            : internationalized error message
	 */
	void notifyInvalidParametersNumber(String key, int awaitedparametersNumber,
			String messagePattern, String errorMessage);

}
