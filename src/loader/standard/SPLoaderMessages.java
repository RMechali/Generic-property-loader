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

import loader.messages.DMLoader;

/**
 * Keys for internationalized messages thrown by the package components.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public final class SPLoaderMessages {

	/** Big decimal reader error **/
	public static final String BIG_DECIMAL_READER_ERROR = "standard.reader.big.decimal.error";

	/** Boolean reader error **/
	public static final String BOOLEAN_READER_ERROR = "standard.reader.boolean.error";

	/** Character reader error **/
	public static final String CHARACTER_READER_ERROR = "standard.reader.character.error";

	/** Double reader error **/
	public static final String DOUBLE_READER_ERROR = "standard.reader.double.error";

	/** Float reader error **/
	public static final String FLOAT_READER_ERROR = "standard.reader.float.error";

	/** Integer reader error **/
	public static final String INTEGER_READER_ERROR = "standard.reader.integer.error";

	/** Long reader error **/
	public static final String LONG_READER_ERROR = "standard.reader.long.error";

	/** Color reader error : empty representation **/
	public static final String COLOR_READER_ERROR_EMPTY = "standard.reader.color.error.empty";

	/** Color reader error : missing hexadecimal color attributes **/
	public static final String COLOR_READER_ERROR_HEXA_MISSING_ATTRIBUTES = "standard.reader.color.error.hexa.missing.attribute";

	/** Color reader error : hexadecimal color invalid attribute **/
	public static final String COLOR_READER_ERROR_HEXA_INVALID_ATTRIBUTE = "standard.reader.color.error.hexa.invalid.attribute";

	/** Color reader error : hexadecimal color representation too long **/
	public static final String COLOR_READER_ERROR_HEXA_TOO_LONG = "standard.reader.color.error.hexa.too.long";

	/** Color reader error : missing RGBA color attributes **/
	public static final String COLOR_READER_ERROR_RGBA_MISSING_ATTRIBUTES = "standard.reader.color.error.rgba.missing.attributes";

	/** Color reader error : to many RGBA color attributes **/
	public static final String COLOR_READER_ERROR_RGBA_TOO_MANY_ATTRIBUTES = "standard.reader.color.error.rgba.too.many.attributes";

	/** Color reader error : invalid attribute (cannot be parsed as integer) **/
	public static final String COLOR_READER_ERROR_RGBA_INVALID_ATTRIBUTE = "standard.reader.color.error.rgba.invalid.attribute";

	/**
	 * Color reader error : invalid attribute (the value v is lower than 0 or
	 * greater than 256)
	 **/
	public static final String COLOR_READER_ERROR_RGBA_INVALID_INTEGER_VALUE = "standard.reader.color.error.rgba.invalid.integer.value";

	/** Font reader error : invalid path **/
	public static final String FONT_READER_PATH_ERROR = "standard.reader.font.invalid.path";

	/** Font reader error : file access error **/
	public static final String FONT_READER_FILE_ACCES_ERROR = "standard.reader.font.file.access.error";

	/** Font reader error : invalid font file **/
	public static final String FONT_READER_INVALID_FONT_FILE_ERROR = "standard.reader.font.invalid.font.file";

	/** Image reader error : invalid path **/
	public static final String IMAGE_READER_PATH_ERROR = "standard.reader.image.invalid.path";

	/** Image reader error : invalid icon file **/
	public static final String IMAGE_READER_INVALID_IMAGE_FILE = "standard.reader.image.invalid.image.file";

	/**
	 * Adds the default SPLoader messages
	 */
	public static void addDefaultMessages() {
		DMLoader.addPropertyFile("sploader_messages.prop", 0);
	}
}
