package main.basic;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.Assert;
import loader.BasicResourcesLoader;
import loader.standard.readers.direct.conversion.StringReader;

import org.junit.Test;

import container.ResourcesContainer;

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

/**
 * Tests for the basic loader
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class BasicResourcesLoaderWorkingTest {

	/**
	 * Test for the get property method (basic test)
	 */
	@Test
	public void testGetProperty() {
		BasicResourcesLoader loader = new BasicResourcesLoader();
		loader.addPropertyFile("working/test_get_property.prop");
		String value = loader.getProperty("basic.property", StringReader
				.getInstance());
		Assert.assertNotNull(value);
		Assert.assertEquals("a value", value);
	}

	/**
	 * Test for the get property method : property not in the upper file
	 */
	@Test
	public void testGetProperty2() {
		BasicResourcesLoader loader = new BasicResourcesLoader();
		// add file at end
		loader.addPropertyFile("working/test_get_property_2a.prop");
		// add file at end
		loader.addPropertyFile("working/test_get_property_2b.prop");
		// get a property defined in 2.a only
		String value = loader.getProperty("single.property", StringReader
				.getInstance());
		Assert.assertNotNull(value);
		Assert.assertEquals("2a", value);

		// get a property defined in 2.a and 2b, verify that the 2.b is the one
		// returned
		value = loader.getProperty("overriden.property", StringReader
				.getInstance());
		Assert.assertNotNull(value);
		Assert.assertEquals("2b", value);
	}

	/**
	 * Test for the add file method through resource container property
	 * notification system
	 */
	@Test
	public void testAddFile() {
		final int[] notifications = { 0 };

		ResourcesContainer.getInstance().addPropertyChangeListener(
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						if (evt.getPropertyName().equals("test.add.property")) {
							notifications[0] = notifications[0] + 1;
						}
					}
				});

		BasicResourcesLoader loader = new BasicResourcesLoader();
		// add the initial file
		loader.addPropertyFile("working/test_add_file_1.prop");
		// get its property value
		String value = loader.getProperty("test.add.property", StringReader
				.getInstance());
		Assert.assertEquals("file1", value);
		// assert that a notification has been performed
		Assert.assertEquals(1, notifications[0]);
		// add the new file
		loader.addPropertyFile("working/test_add_file_2.prop");
		// assert that a notification has been performed
		Assert.assertEquals(2, notifications[0]);
		// get its property value
		value = loader.getProperty("test.add.property", StringReader
				.getInstance());
		Assert.assertEquals("file2", value);
		// assert that a notification has been performed
		Assert.assertEquals(3, notifications[0]);
	}

}
