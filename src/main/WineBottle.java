/** 
 * This file is part of Example5 project.
 *
 * Example5 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * Example5 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with Example5 project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package main;

/**
 * Wine bottle class
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class WineBottle {

	/** Bottle name **/
	private String name;

	/** Bottle production domain **/
	private String productionDomain;

	/** Bottle production year **/
	private Integer year;

	/**
	 * Complete constructor
	 * 
	 * @param name
	 *            : bottle name
	 * @param productionDomain
	 *            : bottle production domain
	 * @param year
	 *            : bottle year
	 */
	public WineBottle(String name, String productionDomain, Integer year) {
		super();
		this.name = name;
		this.productionDomain = productionDomain;
		this.year = year;
	}

	/**
	 * Getter -
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter -
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter -
	 * 
	 * @return the productionDomain
	 */
	public String getProductionDomain() {
		return productionDomain;
	}

	/**
	 * Setter -
	 * 
	 * @param productionDomain
	 *            the productionDomain to set
	 */
	public void setProductionDomain(String productionDomain) {
		this.productionDomain = productionDomain;
	}

	/**
	 * Getter -
	 * 
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * Setter -
	 * 
	 * @param year
	 *            the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * {@inherit}
	 */
	@Override
	public String toString() {
		return "Bottle of " + this.name + " from " + this.productionDomain
				+ " (" + this.year + ")";
	}

}
