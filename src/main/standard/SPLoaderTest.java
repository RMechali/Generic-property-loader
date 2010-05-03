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

package main.standard;

import java.awt.Color;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.ImageIcon;

import loader.error.BadPropertyFormatError;
import loader.error.ILoaderErrorListener;
import loader.error.LoaderErrorAdapter;
import loader.standard.SPLoader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the standard propery loader.
 * 
 * Copyright 2010, Raphael Mechali <br
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class SPLoaderTest {

	// test all readers nominal cases and error cases
	// does not test readers absolute path because the test would not work on
	// other configurations

	/**
	 * Is the value ok for that property? - used by the error listener to
	 * persist property parsing state
	 **/
	private static boolean isValueOk;

	/**
	 * Delegate for all tests that switches attribute isValueOk
	 */
	private static final ILoaderErrorListener listener = new LoaderErrorAdapter() {

		@Override
		public void notifyPropertyParseError(BadPropertyFormatError error) {
			isValueOk = false;
			// Trace the error for graphical verification (verifying error
			// message would be very complex for too few interest)
			System.err.println("Property : " + error.getKey()
					+ "\nReader error " + error.getReaderErrorMessage());
		}

	};

	static {
		// initialization (can not use constructor because it is instanciated as
		// many times as there are tests)
		SPLoader.addPropertyFile("standard_reader_test.prop");
		SPLoader.addErrorListener(listener);
	}

	/**
	 * Test big decimal read nominal case
	 */
	@Test
	public void testBigDecimal1() {
		isValueOk = true;
		BigDecimal bigDecimalProp = SPLoader
				.getBigDecimal("test.big.decimal.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// it is equal to the expected value
		Assert.assertEquals(new BigDecimal("1500.588888888888888"),
				bigDecimalProp);
	}

	/**
	 * Test big decimal read error
	 */
	@Test
	public void testBigDecimal2() {
		isValueOk = true;
		SPLoader.getBigDecimal("test.big.decimal.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test boolean reader nominal case
	 */
	@Test
	public void testBoolean1() {
		isValueOk = true;
		Boolean boolean1 = SPLoader.getBoolean("test.boolean.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertTrue(boolean1);
	}

	/**
	 * Test boolean reader nominal case
	 */
	@Test
	public void testBoolean2() {
		isValueOk = true;
		Boolean boolean2 = SPLoader.getBoolean("test.boolean.2");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertFalse(boolean2);
	}

	/**
	 * Test boolean reader error case
	 */
	@Test
	public void testBoolean3() {
		isValueOk = true;
		SPLoader.getBoolean("test.boolean.3");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test character reader nominal case
	 */
	@Test
	public void testCharacter1() {
		isValueOk = true;
		Character characterProp = SPLoader.getCharacter("test.character.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Character('z'), characterProp);
	}

	/**
	 * Test character reader error case
	 */
	@Test
	public void testCharacter2() {
		isValueOk = true;
		SPLoader.getCharacter("test.character.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test character reader error case
	 */
	@Test
	public void testCharacter3() {
		isValueOk = true;
		SPLoader.getCharacter("test.character.3");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader nominal case
	 */
	@Test
	public void testColor1() {
		isValueOk = true;
		Color colorProp = SPLoader.getColor("test.color.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Color(255, 255, 255, 255), colorProp);
	}

	/**
	 * Test color reader nominal case
	 */
	@Test
	public void testColor2() {
		isValueOk = true;
		Color colorProp = SPLoader.getColor("test.color.2");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Color(0, 0, 0, 0), colorProp);
	}

	/**
	 * Test color reader nominal case
	 */
	@Test
	public void testColor3() {
		isValueOk = true;
		Color colorProp = SPLoader.getColor("test.color.3");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Color(255, 255, 255, 255), colorProp);
	}

	/**
	 * Test color reader nominal case
	 */
	@Test
	public void testColor4() {
		isValueOk = true;
		Color colorProp = SPLoader.getColor("test.color.4");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Color(0, 0, 0, 0), colorProp);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor5() {
		isValueOk = true;
		SPLoader.getColor("test.color.5");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor6() {
		isValueOk = true;
		SPLoader.getColor("test.color.6");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor7() {
		isValueOk = true;
		SPLoader.getColor("test.color.7");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor8() {
		isValueOk = true;
		SPLoader.getColor("test.color.8");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor9() {
		isValueOk = true;
		SPLoader.getColor("test.color.9");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor10() {
		isValueOk = true;
		SPLoader.getColor("test.color.10");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor11() {
		isValueOk = true;
		SPLoader.getColor("test.color.11");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test color reader error case
	 */
	@Test
	public void testColor12() {
		isValueOk = true;
		SPLoader.getColor("test.color.12");
		// the property has been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test double reader nominal case
	 */
	@Test
	public void testDouble() {
		isValueOk = true;
		Double doubleProp = SPLoader.getDouble("test.double.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Double(566.66), doubleProp);
	}

	/**
	 * Test double reader error case
	 */
	@Test
	public void testDouble2() {
		isValueOk = true;
		SPLoader.getDouble("test.double.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test float reader nominal case
	 */
	@Test
	public void testFloat() {
		isValueOk = true;
		Float floatProp = SPLoader.getFloat("test.float.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Float(566.66), floatProp);
	}

	/**
	 * Test float reader error case
	 */
	@Test
	public void testFloat2() {
		isValueOk = true;
		SPLoader.getFloat("test.float.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test font reader nominal case
	 */
	@Test
	public void testFont() {
		isValueOk = true;
		@SuppressWarnings("unused")
		Font fontProp = SPLoader.getFont("test.font.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Cannot compare with the expected value
	}

	/**
	 * Test font reader error case
	 */
	@Test
	public void testFont2() {
		isValueOk = true;
		SPLoader.getFont("test.font.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test font reader error case
	 */
	@Test
	public void testFont3() {
		isValueOk = true;
		SPLoader.getFont("test.font.3");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test image icon reader nominal case
	 */
	@Test
	public void testImageIcon() {
		isValueOk = true;
		@SuppressWarnings("unused")
		ImageIcon imageIconProp = SPLoader.getIcon("test.image.icon.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Cannot compare with the expected value
	}

	/**
	 * Test image icon reader error case
	 */
	@Test
	public void testImageIcon2() {
		isValueOk = true;
		SPLoader.getIcon("test.image.icon.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test image icon reader error case
	 */
	@Test
	public void testImageIcon3() {
		isValueOk = true;
		SPLoader.getIcon("test.image.icon.3");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test integer reader nominal case
	 */
	@Test
	public void testInteger() {
		isValueOk = true;
		Integer integerProp = SPLoader.getInteger("test.integer.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Integer(22), integerProp);
	}

	/**
	 * Test integer reader error case
	 */
	@Test
	public void testInteger2() {
		isValueOk = true;
		SPLoader.getLong("test.integer.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test long reader nominal case
	 */
	@Test
	public void testLong() {
		isValueOk = true;
		Long longProp = SPLoader.getLong("test.long.1");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals(new Long(16666666645L), longProp);
	}

	/**
	 * Test long reader error case
	 */
	@Test
	public void testLong2() {
		isValueOk = true;
		SPLoader.getLong("test.long.2");
		// the property has not been parsed
		Assert.assertFalse(isValueOk);
	}

	/**
	 * Test string reader nominal case (no error case handled)
	 */
	@Test
	public void testString() {
		isValueOk = true;
		String stringProp = SPLoader.getString("test.string");
		// the property has been parsed
		Assert.assertTrue(isValueOk);
		// Compare with the expected value
		Assert.assertEquals("a string", stringProp);
	}
}
