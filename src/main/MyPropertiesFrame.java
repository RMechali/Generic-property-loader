/** 
 * This file is part of Example2 project.
 *
 * Example2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example2 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import loader.standard.SPLoader;
import container.ResourcesContainer;

/**
 * Main project frame
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class MyPropertiesFrame extends JFrame {

	/**
	 * Default UID
	 */
	private static final long serialVersionUID = 1L;

	/** Label to show the current color property value **/
	private JLabel colorView;

	/** Label to show the current integer value **/
	private JLabel integerView;

	/** Label to show the current image value **/
	private JLabel imageView;

	/**
	 * Constructor
	 */
	public MyPropertiesFrame() {
		// set the frame title
		super("Demo frame");
		// set the default close operation to "quit application"
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// build the content of the frame
		createContent();
		// install binding system
		installBinding();
		// initialize view labels
		initializeViews();
	}

	/**
	 * Creates the frame content
	 */
	private void createContent() {
		// sets the layout to a grid layout 4 rows * 3 columns with 4 pixels gap
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(4, 3, 4, 4));

		// create the first line
		contentPane.add(new JLabel("My color"));
		// use a custom action to control property current value
		JButton colorSelector = new JButton(getColorSelectionAction());
		contentPane.add(colorSelector);
		colorView = new JLabel();
		contentPane.add(colorView);

		// create the second line
		contentPane.add(new JLabel("My integer"));
		JSlider integerSelector = new JSlider(SwingConstants.HORIZONTAL,
				Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		integerSelector.addChangeListener(getChangeListener());
		contentPane.add(integerSelector);
		integerView = new JLabel();
		contentPane.add(integerView);

		// create the third line
		contentPane.add(new JLabel("My image"));
		// use a custom action to control property current value
		JButton imageSelector = new JButton(getImageSelectionAction());
		contentPane.add(imageSelector);
		imageView = new JLabel();
		contentPane.add(imageView);

		// create the forth line : OK button
		// column 1 : filler
		contentPane.add(new JLabel());
		// column 2 : filler
		contentPane.add(new JLabel());
		// column 3 : OK button
		contentPane.add(new JButton(getExportAction()));

		// pack all the frame graphical elements
		pack();
	}

	/**
	 * Creates and returns the export action
	 * 
	 * @return -
	 */
	private Action getExportAction() {
		return new AbstractAction("OK") {

			/**
			 * Default UID
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				SPLoader.exportFileContent("editable_properties.prop");
			}
		};
	}

	/**
	 * Create and return a change listener to select an integer
	 * 
	 * @return -
	 */
	private ChangeListener getChangeListener() {
		return new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				Integer value = ((JSlider) e.getSource()).getValue();
				// the user selected a new value
				// we put the new value in the resource container, using
				// SPloader API
				SPLoader.setInteger("an.integer.property", value);
			}
		};
	}

	/**
	 * Create and returns an action to select a color
	 * 
	 * @return -
	 */
	private AbstractAction getColorSelectionAction() {
		return new AbstractAction("Select here...") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Show color selection dialog
				Color userSelection = JColorChooser.showDialog(
						MyPropertiesFrame.this, "Select a color", Color.white);
				if (userSelection != null) {
					// the user selected a color
					// we put the new value in the resource container, using
					// SPloader API
					SPLoader.setColor("a.color.property", userSelection);
				}
			}
		};
	}

	/**
	 * Create and returns an action to select an image
	 * 
	 * @return -
	 */
	private AbstractAction getImageSelectionAction() {
		return new AbstractAction("Select here...") {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// to select an image, we first use the swing file chooser with
				// filters images : "*.png", "*.gif", "*.jpeg", "*.jpg"
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(
						"Images", "png", "gif", "jpeg", "jpg"));

				// let the user select a file (or cancel it)
				int userAnswer = fileChooser
						.showOpenDialog(MyPropertiesFrame.this);
				if (userAnswer == JFileChooser.APPROVE_OPTION) {
					// the user selected a file, we put the new value in the
					// resource container (using SPloader API to parse it)
					File selectedFile = fileChooser.getSelectedFile();
					String path = selectedFile.getPath();
					SPLoader.setIcon("an.image.property", path);
				}

			}
		};
	}

	/**
	 * Install binding from properties values to property views
	 */
	private void installBinding() {
		// bind the central model
		ResourcesContainer.getInstance().addPropertyChangeListener(
		// anonymous property change listener instance
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						// handle event using the corresponding property name
						if (evt.getPropertyName().equals("a.color.property")) {
							// we cast the value into the type we have read
							// before
							Color newColor = (Color) evt.getNewValue();
							if (newColor != null) {
								// update the corresponding view if the new
								// value is not null. Otherwise do nothing
								colorView.setBackground(newColor);
							}

						} else if (evt.getPropertyName().equals(
								"an.integer.property")) {
							Integer newValue = (Integer) evt.getNewValue();
							if (newValue != null) {
								// update the corresponding view if the new
								// integer value is not null
								integerView.setText(newValue.toString());
							}
						} else if (evt.getPropertyName().equals(
								"an.image.property")) {
							ImageIcon newIcon = (ImageIcon) evt.getNewValue();
							// update the corresponding view (icons can be null
							// so we do not verify)
							imageView.setIcon(newIcon);
						}
					}
				});
	}

	/**
	 * Initialize views values
	 */
	private void initializeViews() {
		// 1 - we add the property file we will use
		SPLoader.addPropertyFile("editable_properties.prop");
		// 2 - we read our properties and project them on one of the label
		// attributes (we are optimistic and suppose the values are not
		// erroneous)
		colorView.setBackground(SPLoader.getColor("a.color.property"));
		integerView.setText(SPLoader.getInteger("an.integer.property")
				.toString());
		imageView.setIcon(SPLoader.getIcon("an.image.property"));

		// 3 - for the label to show its background color, we make it opaque
		colorView.setOpaque(true);
	}

}
