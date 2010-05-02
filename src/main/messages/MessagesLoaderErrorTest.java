/** 
 * This file is part of TestsGenericPropertyLoader project.
 *
 * TestsGenericPropertyLoader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * TestsGenericPropertyLoader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with TestsGenericPropertyLoader project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main.messages;

import junit.framework.Assert;
import loader.error.BadPropertyFormatError;
import loader.messages.DMLoader;
import loader.messages.error.DMLoaderErrorAdapter;

import org.junit.Test;

/**
 * Tests for message loader errors.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class MessagesLoaderErrorTest {

	/**
	 * Test adding a file that can not be found in current locale folder and in
	 * default folder
	 */
	@Test
	public void testMissingMessageFile() {
		final boolean[] missingFiles = { false, false };
		DMLoader.addErrorListener(new DMLoaderErrorAdapter() {

			@Override
			public void notifyCurrentLocaleNotFound(String localeFolder,
					String fileName, String resultingPath) {
				missingFiles[0] = true;
			}

			@Override
			public void notifyUSLocaleNotFound(String fileName) {
				missingFiles[1] = true;
			}
		});

		DMLoader.addPropertyFile("one_missing_file.prop");

		Assert.assertTrue(missingFiles[0]);
		Assert.assertTrue(missingFiles[1]);
	}

	/**
	 * Test getting a message with an invalid parameters number
	 */
	@Test
	public void testInvalidParametersNumber() {
		final boolean[] invalidParameters = { false, false };

		DMLoader.addErrorListener(new DMLoaderErrorAdapter() {

			@Override
			public void notifyInvalidParametersNumber(String key,
					int awaitedparametersNumber, String messagePattern,
					String errorMessage) {
				if (key.equals("static.message")) {
					invalidParameters[0] = true;
				} else if (key.equals("dynamic.message")) {
					invalidParameters[1] = true;
				}
			}
		});

		DMLoader.addPropertyFile("test_invalid_parameters.prop");
		DMLoader.getMessage("static.message", "one parameter");
		DMLoader.getMessage("dynamic.message");

		Assert.assertTrue(invalidParameters[0]);
		Assert.assertTrue(invalidParameters[1]);
	}

	/**
	 * Test getting bad formatted messages
	 */
	@Test
	public void testBadFormattedMessages() {
		final boolean[] parseErrors = { false, false, false };

		DMLoader.addErrorListener(new DMLoaderErrorAdapter() {

			/**
			 * {@inherit}
			 */
			@Override
			public void notifyPropertyParseError(BadPropertyFormatError error) {
				String key = error.getKey();
				if (key.equals("bad.formatted.1")) {
					parseErrors[0] = true;
				} else if (key.equals("bad.formatted.2")) {
					parseErrors[1] = true;
				} else if (key.equals("bad.formatted.3")) {
					parseErrors[2] = true;
				}
			}

		});

		DMLoader.addPropertyFile("bad_formatted_messages.prop");
		DMLoader.getMessage("bad.formatted.1");
		DMLoader.getMessage("bad.formatted.2");
		DMLoader.getMessage("bad.formatted.3");

		Assert.assertTrue(parseErrors[0]);
		Assert.assertTrue(parseErrors[1]);
		Assert.assertTrue(parseErrors[2]);
	}

}
