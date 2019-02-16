//************ AUTO GENERATED DO NOT EDIT *********//
package com.osrmt.modellibrary.reference.group;

public class ModuleGroup implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int ModuleRefId = 0;
	public ModuleGroup(int ModuleRefId) {
		this.ModuleRefId = ModuleRefId;		
	}

	public int getModuleRefId() {
		return ModuleRefId;
	}

	public static ModuleGroup get(int ModuleRefId) {
		return new ModuleGroup(ModuleRefId);
	}

}
