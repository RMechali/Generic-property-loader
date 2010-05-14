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

package loader.standard.readers;

import java.awt.MediaTracker;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import loader.PropertyReader;
import loader.messages.DMLoader;
import loader.standard.SPLoaderMessages;

/**
 * Default reader for image icon properties. The image icon file can be
 * specified as relative to classpath or to system file path.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class ImageIconReader implements PropertyReader<ImageIcon> {

	/** Singleton instance **/
	private static ImageIconReader __instance;

	/**
	 * Constructor
	 */
	private ImageIconReader() {
		// forbids external instance
		// ensure reader messages are loaded
		SPLoaderMessages.addDefaultMessages();
	}

	/**
	 * {@inherit}
	 */
	@Override
	public ImageIcon readProperty(String propertyRepresentation)
			throws IllegalArgumentException {
		String filePath = propertyRepresentation.trim();
		URL iconURL = ClassLoader.getSystemClassLoader().getResource(filePath);
		if (iconURL == null) {
			// the file is not in class path
			try {
				final File file = new File(filePath);
				if (!file.exists()) {
					throw new IllegalArgumentException(DMLoader.getMessage(
							SPLoaderMessages.IMAGE_READER_PATH_ERROR,
							propertyRepresentation));
				}
				iconURL = file.toURI().toURL();
			} catch (MalformedURLException ex) {
				throw new IllegalArgumentException(DMLoader.getMessage(
						SPLoaderMessages.IMAGE_READER_PATH_ERROR,
						propertyRepresentation));
			}
		}

		// build the icon from the URL found
		ImageIcon imageIcon;
		try {
			// instantiate an extended image icon serialize it by its property
			// representation later (if the user property file must be rebuilt)
			imageIcon = new ImageIcon(iconURL, propertyRepresentation);
			if (imageIcon.getImageLoadStatus() == MediaTracker.ERRORED) {
				// invalid file
				throw new IllegalArgumentException(DMLoader.getMessage(
						SPLoaderMessages.IMAGE_READER_INVALID_IMAGE_FILE,
						iconURL.toString()));
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(DMLoader.getMessage(
					SPLoaderMessages.IMAGE_READER_INVALID_IMAGE_FILE, iconURL
							.toString()));
		}
		return imageIcon;
	}

	/**
	 * Singleton getter
	 * 
	 * @return - the singleton instance
	 */
	public static ImageIconReader getInstance() {
		if (__instance == null) {
			__instance = new ImageIconReader();
		}
		return __instance;
	}

}
