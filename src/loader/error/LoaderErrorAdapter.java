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
 * Adapter for {@link ILoaderErrorListener}.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class LoaderErrorAdapter implements ILoaderErrorListener {

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyCouldNotReadFile(String fileName) {
		// nothing to do
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyCouldNotReadProperty(String key) {
		// nothing to do
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyMissingFile(String fileName) {
		// nothing to do
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyPropertyNotFound(List<String> searchedFiles) {
		// nothing to do
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyPropertyParseError(BadPropertyFormatError error) {
		// nothing to do
	}

}
