package main.basic;

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

import junit.framework.Assert;
import loader.BasicResourcesLoader;
import loader.error.BadPropertyFormatError;
import loader.error.LoaderErrorAdapter;
import loader.standard.readers.direct.conversion.DoubleReader;

import org.junit.Test;

/**
 * Tests for the basic loader errors.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BasicResourcesLoaderErrorsTest {

	/** Basic resource loader for the class **/
	private final BasicResourcesLoader loader = new BasicResourcesLoader();

	/**
	 * Test method for a missing file error.
	 */
	@Test
	public void testAddMissingFile() {
		final boolean[] missingFile = { false };

		loader.addErrorListener(new LoaderErrorAdapter() {
			@Override
			public void notifyMissingFile(String fileName) {
				missingFile[0] = true;
			}
		});
		loader.addPropertyFile("errors/missing_file.prop");
		Assert.assertTrue(missingFile[0]);
	}

	/**
	 * Test method for a bad formed property (finally read)
	 */
	@Test
	public void badFormedProperty() {
		final int[] readFailures = { 0 };
		final boolean[] finallyRead = { true };

		// add both files at files end, make sure the bad formatted has higher
		// priority
		loader.addPropertyFile("errors/bad_formated_property_1b.prop");
		loader.addPropertyFile("errors/bad_formated_property_1a.prop");

		// add a listener
		loader.addErrorListener(new LoaderErrorAdapter() {

			@Override
			public void notifyPropertyParseError(BadPropertyFormatError error) {
				readFailures[0]++;
			}

			@Override
			public void notifyCouldNotReadProperty(String key) {
				finallyRead[0] = false;
			}
		});
		// read the bad formated property
		Double value = loader.getProperty("test.wrong.formated.property",
				DoubleReader.getInstance());
		Assert.assertNotNull(value);
		Assert.assertTrue(finallyRead[0]);
		Assert.assertEquals(1, readFailures[0]);
	}

	/**
	 * Test method for a bad formed property (not read)
	 */
	@Test
	public void badFormedProperty2() {
		final int[] readFailures = { 0 };
		final boolean[] finallyRead = { true };

		loader.addErrorListener(new LoaderErrorAdapter() {

			@Override
			public void notifyPropertyParseError(BadPropertyFormatError error) {
				readFailures[0]++;
			}

			@Override
			public void notifyCouldNotReadProperty(String key) {
				finallyRead[0] = false;
			}
		});
		// attempt to read a property bad formated in two files
		loader.addPropertyFile("errors/bad_formated_property_2b.prop");
		loader.addPropertyFile("errors/bad_formated_property_2a.prop");

		// read the bad formated property
		Double value = loader.getProperty("test.wrong.formated.property",
				DoubleReader.getInstance());

		Assert.assertNull(value);
		Assert.assertEquals(2, readFailures[0]);
		Assert.assertFalse(finallyRead[0]);
	}
}
