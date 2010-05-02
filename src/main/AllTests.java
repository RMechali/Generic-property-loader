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

package main;

import main.basic.BasicResourcesLoaderErrorsTest;
import main.basic.BasicResourcesLoaderWorkingTest;
import main.export.ExportTest;
import main.messages.MessagesLoaderErrorTest;
import main.messages.MessagesLoaderWorkingTest;
import main.standard.SPLoaderTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * All tests
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( { BasicResourcesLoaderWorkingTest.class,
		BasicResourcesLoaderErrorsTest.class, MessagesLoaderWorkingTest.class,
		MessagesLoaderErrorTest.class, SPLoaderTest.class, ExportTest.class })
public class AllTests {

	// all tests

}
