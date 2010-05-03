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
 * If not, see <http://www.gnu.org/licenses/.
 **/

package main.export;

import loader.BasicResourcesLoader;
import loader.standard.readers.BooleanReader;
import loader.standard.readers.LongReader;

import org.junit.Assert;
import org.junit.Test;

import container.Property;
import container.ResourcesContainer;

/**
 * Tests to export a property file
 * 
 * 
 * Copyright 2010, Raphael Mechali <br
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class ExportTest {

	/**
	 * Tests a property file export
	 */
	@Test
	public void testExport() {
		// test procedure :
		// A - load a property file with a long property and a boolean property
		// B - change it into the resource container
		// C - export it in the original file
		// D - reload it in a new property reader and verify the values it
		// contains

		// A-
		BasicResourcesLoader basicLoader1 = new BasicResourcesLoader();
		basicLoader1.addPropertyFile("imported_exported.prop");

		Long initialLongProp = basicLoader1.getProperty("long.property",
				LongReader.getInstance());
		Boolean initialBooleanProp = basicLoader1.getProperty(
				"boolean.property", BooleanReader.getInstance());
		// if those properties are null a NullPointerException will be
		// propagated

		// B - (modulating long value so that it never goes out of bounds)
		Long newLongValue = initialLongProp + 1 % 100000;
		Boolean newBoolValue = !initialBooleanProp;
		ResourcesContainer.addPropertyI("long.property", new Property<Long>(
				newLongValue, newLongValue.toString()));
		ResourcesContainer.addPropertyI("boolean.property",
				new Property<Boolean>(newBoolValue, newBoolValue.toString()));

		// C -
		basicLoader1.exportFileContent("imported_exported.prop");

		// D -
		BasicResourcesLoader basicLoader2 = new BasicResourcesLoader();
		basicLoader2.addPropertyFile("imported_exported.prop");

		Long newLongProp = basicLoader1.getProperty("long.property", LongReader
				.getInstance());
		Boolean newBooleanProp = basicLoader1.getProperty("boolean.property",
				BooleanReader.getInstance());

		Assert.assertEquals(newLongValue, newLongProp);
		Assert.assertEquals(newBoolValue, newBooleanProp);
	}
}
