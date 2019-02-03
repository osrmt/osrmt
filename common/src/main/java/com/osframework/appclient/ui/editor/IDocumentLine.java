package com.osframework.appclient.ui.editor;

public interface IDocumentLine {

	/**
	 * Returns the text for this line
	 * 
	 */
	public Object getValue();
	
	/**
	 * Update the text
	 * 
	 * @param value
	 */
	public void setValue(Object value);
	
	/**
	 * Get the output for the ireport document xml
	 * @return
	 */
	public String getIReportValue();
	
	/**
	 * Get the renderer component
	 * 
	 * @return
	 */
	public java.awt.Component getRendererComponent();
	
	/**
	 * Get pixel height required for this line
	 * 
	 * @return
	 */
	public int getRowHeight();
}
