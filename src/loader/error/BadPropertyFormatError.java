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

/**
 * Class standing for a property bad format (it represents a property format
 * error in one property file)
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BadPropertyFormatError {

	/** Key that was asked for when attempting to get the property **/
	private final String key;

	/** Value that was found (and invalid) **/
	private final String errorValue;

	/** Internationalized message sent by the reader **/
	private final String readerErrorMessage;

	/** File containing the erroneous property value **/
	private final String fileName;

	/**
	 * Constructor
	 * 
	 * @param key
	 *            : see attribute
	 * @param errorValue
	 *            : see attribute
	 * @param readerErrorMessage
	 *            : see attribute
	 */
	public BadPropertyFormatError(String fileName, String key,
			String errorValue, String readerErrorMessage) {
		this.fileName = fileName;
		this.key = key;
		this.errorValue = errorValue;
		this.readerErrorMessage = readerErrorMessage;
	}

	/**
	 * Getter -
	 * 
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Getter -
	 * 
	 * @return the errorValue
	 */
	public String getErrorValue() {
		return errorValue;
	}

	/**
	 * Getter -
	 * 
	 * @return the readerErrorMessage
	 */
	public String getReaderErrorMessage() {
		return readerErrorMessage;
	}

	/**
	 * Getter -
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * {@inherit}
	 */
	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer("The file ");
		stringBuffer.append(getFileName());
		stringBuffer.append(" contains the invalid value ");
		stringBuffer.append(getErrorValue());
		stringBuffer.append(" for key ");
		stringBuffer.append(getKey());
		stringBuffer.append(" :\n\t");
		stringBuffer.append(getReaderErrorMessage());
		return stringBuffer.toString();
	}
}
