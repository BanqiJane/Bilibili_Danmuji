package xyz.acproject.danmuji.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Hashtable;

/**
 * @ClassName ProFileTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:42
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ProFileTools {
	public static Hashtable<String, String> read(String filename) {
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		File file = new File(path);
		file.setWritable(true, false);
		if (file.exists() == false)
			file.mkdirs();
		file = new File(path + "/" + filename);
		file.setWritable(true, false);
		if (file.exists() == false)
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		Hashtable<String, String> hashtables = new Hashtable<String, String>();
		BufferedReader bufferedReader = null;
		String dataString = null;
		String[] strings = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			while ((dataString = bufferedReader.readLine()) != null) {
				strings = dataString.split(":@:");
				if (strings != null) {
					if (strings.length == 2) {
						hashtables.put(strings[0], strings[1]);
					} else {
						return null;
					}
				}
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return hashtables;

	}

	public static void write(Hashtable<String, String> hashtables, String filename) {
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		File file = new File(path);
		file.setWritable(true, false);
		if (file.exists() == false)
			file.mkdirs();
		file = new File(path + "/" + filename);
		file.setWritable(true, false);
		final StringBuffer stringBuffer = new StringBuffer();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			hashtables.forEach((k, v) -> {

				stringBuffer.append(k);
				stringBuffer.append(":@:");
				stringBuffer.append(v);
				stringBuffer.append("\r\n");
			});
			bufferedWriter.write(stringBuffer.toString());
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
}
