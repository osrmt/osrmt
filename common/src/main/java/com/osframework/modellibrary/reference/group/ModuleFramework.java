//************ AUTO GENERATED DO NOT EDIT *********//
package com.osframework.modellibrary.reference.group;

public class ModuleFramework implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	private int ModuleRefId = 0;
	public ModuleFramework(int ModuleRefId) {
		this.ModuleRefId = ModuleRefId;		
	}

	public int getModuleRefId() {
		return ModuleRefId;
	}

	public static ModuleFramework get(int ModuleRefId) {
		return new ModuleFramework(ModuleRefId);
	}

}
