package xyz.acproject.danmuji.tools;

import java.io.File;

import org.springframework.boot.system.ApplicationHome;

public class FileTools {
	public File getBaseJarPath() {
		ApplicationHome home = new ApplicationHome(getClass());
		File jarFile = home.getSource();
		return jarFile.getParentFile();
	}
}
