package com.osframework.appclient.ui.common;

/**
 * <pre>
 * Generic layout of controls on a panel.
 * Width = (nbr columns * 4)+1
 * Height = (nbr rows * 2)+1
 * 
 *    1    2   3      4       5    6   7      8       9
 * ----------------------------------------------------
 * |                                                     1   
 * |    [Label]  [field cntrl]  [Label]  [field cntrl]   2
 * |                                                     3
 * |    [Label]  [field cntrl]  [Label]  [field cntrl]   4
 * |                                                     5
 * ----------------------------------------------------
 * 
 *
 * </pre>
 */
public interface IControlDef {

	/**
	 * Grid height. Typically 1 for controls and labels.
	 * 
	 * @return 
	 */
	public int getHeight();
	
	/**
	 * Grid width. Typically 1 for controls and labels.
	 * Typically 5 to be the width for 2 column forms
	 * 
	 * @return 
	 */
	public int getWidth();
	
	/**
	 * Typically 1.  Represents weighted growth of control relative
	 * to the other controls growthheight within the available space
	 * on the form.
	 * 
	 * @return 
	 */
	public double getGrowthHeight();
	
	/**
	 * Typically 1.  Represents weighted growth of control relative
	 * to the other controls growthheight within the available space
	 * on the form.
	 * 
	 * @return 
	 */
	public double getGrowthWidth();
	
	/**
	 * Label for control
	 * @return
	 */
	public String getLabel();
	
}

