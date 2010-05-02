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

import java.util.Date;
import java.util.Locale;

import loader.messages.DMLoader;
import loader.messages.error.DMLoaderErrorAdapter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for message loader methods.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class MessagesLoaderWorkingTest {

	/**
	 * Test adding a messages file for fr locale.
	 */
	@Test
	public void testAddMessagesFile() {
		Locale.setDefault(Locale.FRANCE);

		final boolean[] missingFile = { false };

		// listen for errors
		DMLoader.addErrorListener(new DMLoaderErrorAdapter() {

			@Override
			public void notifyCurrentLocaleNotFound(String localeFolder,
					String fileName, String resultingPath) {
				missingFile[0] = true;
			}
		});
		// add the message file
		DMLoader.addPropertyFile("found_messages.prop");

		Assert.assertFalse(missingFile[0]);
	}

	/**
	 * Test adding a messages file for fr locale. As this file is missing,
	 * replace it by the nearest possible file (here french / canadian)
	 */
	@Test
	public void testAddMessagesFile2() {
		Locale.setDefault(Locale.FRANCE);

		final boolean[] missingFile = { false };
		final boolean[] attemptReadingUS = { false };

		// listen for errors
		DMLoader.addErrorListener(new DMLoaderErrorAdapter() {

			@Override
			public void notifyCurrentLocaleNotFound(String localeFolder,
					String fileName, String resultingPath) {
				missingFile[0] = true;
			}

			@Override
			public void notifyUSLocaleNotFound(String fileName) {
				// this should not be called as canadian must be read before
				attemptReadingUS[0] = true;
			}
		});
		// add the message file
		DMLoader.addPropertyFile("found_messages2.prop");

		Assert.assertTrue(missingFile[0]);
		Assert.assertFalse(attemptReadingUS[0]);
	}

	/**
	 * Test to get a static message
	 */
	@Test
	public void testStaticMessage() {
		DMLoader.addPropertyFile("test_static_messages.prop");
		String message = DMLoader.getMessage("static.message");
		Assert.assertEquals("Bonjour", message);
	}

	/**
	 * Test to get a static message
	 */
	@Test
	public void testDynamicMessage() {
		DMLoader.addPropertyFile("test_dynamic_messages.prop");
		String message = DMLoader.getMessage("dynamic.message",
				"tout le monde", "des messages dynamiques");
		Assert
				.assertEquals(
						"Bonjour tout le monde, ceci est le test des messages dynamiques.",
						message);
	}

	/**
	 * A simple test performance
	 */
	@Test
	public void testPerformance() {
		long initialTime = new Date().getTime();

		DMLoader.addPropertyFile("test_performance.prop");
		// load a thousand properties from the same file
		for (int i = 0; i < 1000; i++) {
			DMLoader.getMessage("one.property." + i);
		}
		// assume the loader did it quicker than 2 seconds (a reasonable delay
		// for the user to wait, knowing most application wont have so much
		// messages)
		long finalTime = new Date().getTime();

		Assert.assertTrue(2000 > finalTime - initialTime);

	}
}
