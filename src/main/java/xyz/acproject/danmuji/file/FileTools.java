package xyz.acproject.danmuji.file;

import java.io.File;

import org.springframework.boot.system.ApplicationHome;

/**
 * @ClassName FileTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:27
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class FileTools {
	public File getBaseJarPath() {
		ApplicationHome home = new ApplicationHome(getClass());
		File jarFile = home.getSource();
		return jarFile.getParentFile();
	}
}
