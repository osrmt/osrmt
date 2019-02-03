/*
    //usage

    Copyright (C) 2006  Aron Lancout Smith

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

*/
package com.osframework.modellibrary.reference.group;

public class CustomRef4Framework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int CustomRef4RefId = 0;
	public CustomRef4Framework(int CustomRef4RefId) {
		this.CustomRef4RefId = CustomRef4RefId;		
	}

	public int getCustomRef4RefId() {
		return CustomRef4RefId;
	}

	public static CustomRef4Framework get(int CustomRef4RefId) {
		return new CustomRef4Framework(CustomRef4RefId);
	}

}
