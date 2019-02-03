package generator;

import utilities.*;

public class GenerateFileSystem {
	
	private MainGenerator maingen = null;
	
	public GenerateFileSystem(MainGenerator m) {
		this.maingen = m;
	}
	
	public void CreateDirectories() {
		// Create root directory e.g. C:\temp
		String rootDir = maingen.props.getProperty(MainGenerator.propROOTDIR);
		FileSystemUtil.CreateDirectory(rootDir);
		
		// Create directory for generated source code e.g. C:\temp\gen_src
		String genSourceDir = maingen.props.getProperty(MainGenerator.propGENSRCDIR);
		FileSystemUtil.CreateDirectory(genSourceDir);
		
	}
	
}
