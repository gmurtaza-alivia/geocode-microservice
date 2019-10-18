package com.absoluteinsight.geocode.utils;

/** 
 * Data types for data source columns
 * 
 * @author Ghulam Murtaza
 * 24/09/2019
 * 
 */
public enum DataType {
	
	 
UnKnown(-1), 
  
 EmptyString(0), 
  
 Boolean(1), 
  
 Integer(2), 
  
 Double(3), 
  
 String(4), 
  
 Date(5),
 Geographic(6);
	 
	private int precedence;

	/**
	 * Instantiates a new data type.
	 *
	 * @param precedence (int)
	 */
	private DataType(int precedence) {
		this.precedence = precedence;
	}

	/**
	 * Gets the precedence.
	 *
	 * @return the precedence
	 */
	public int getPrecedence() {
		return precedence;
	}

	/**
	 * Gets the preceding type.
	 *
	 * @param previousType (DataType) the previous type
	 * @param currentType (DataType) the current type
	 * @return the preceding type
	 */
	public static DataType getPrecedingType(DataType previousType, DataType currentType) {
		if (previousType.getPrecedence() > currentType.getPrecedence()) {
			return previousType;
		}
		return currentType;
	}

}
