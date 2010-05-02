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

import loader.error.LoaderErrorAdapter;

/**
 * Adapter for {@link IDMLoaderErrorListener}
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class DMLoaderErrorAdapter extends LoaderErrorAdapter implements
		IDMLoaderErrorListener {

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyCurrentLocaleNotFound(String localeFolder,
			String fileName, String resultingPath) {
		// nothing to do
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyUSLocaleNotFound(String fileName) {
		// nothing to do
	}

	/**
	 * {@inherit}
	 */
	@Override
	public void notifyInvalidParametersNumber(String key,
			int awaitedparametersNumber, String messagePattern,
			String errorMessage) {
		// nothing to do
	}

}
