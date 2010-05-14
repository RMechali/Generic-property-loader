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

package loader.error;

import java.util.List;

/**
 * A loader error handler that can be notified when errors occur while
 * attempting to load a property.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public interface ILoaderErrorListener {

	/**
	 * Invoked on an attempt to add a missing file
	 * 
	 * @param fileName
	 *            : missing file name
	 */
	void notifyMissingFile(String fileName);

	/**
	 * Invoked on an attempt to add a file that can not be read
	 * 
	 * @param fileName
	 *            : missing file name
	 */
	void notifyCouldNotReadFile(String fileName);

	/**
	 * Invoked when a property could not be read after on or many parse errors
	 * 
	 * @param key
	 *            : property key
	 */
	void notifyCouldNotReadProperty(String key);

	/**
	 * Invoked when a property could not be read because it was not defined in
	 * any file
	 * 
	 * @param key
	 *            : property key
	 * @param searchedFiles
	 *            : list of files searched (and current list of the loader
	 *            files)
	 */
	void notifyPropertyNotFound(String key, List<String> searchedFiles);

	/**
	 * Invoked when a property was loaded but could not be parsed by the reader.
	 * 
	 * @param error
	 *            : error that happened
	 */
	void notifyPropertyParseError(BadPropertyFormatError error);

}
