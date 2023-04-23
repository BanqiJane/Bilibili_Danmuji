package xyz.acproject.danmuji.file;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName ProFileTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:42
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class ProFileTools {
	public static Map<String, String> read(String filename) {
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		File file = new File(path);
//		file.setWritable(true, false);
		if (file.exists() == false)
			file.mkdirs();
		file = new File(path + "/" + filename);
//		file.setWritable(true, false);
		if (file.exists() == false)
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		Map<String, String> profileMap = new ConcurrentHashMap<>();
		BufferedReader bufferedReader = null;
		String dataString = null;
		String[] strings = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
			while ((dataString = bufferedReader.readLine()) != null) {
				strings = dataString.split(":@:");
				if (strings != null) {
					if (strings.length == 2) {
						profileMap.put(strings[0], strings[1]);
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
		return profileMap;

	}

	public static void write(Map<String, String> profileMap, String filename) {
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		File file = new File(path);
//		file.setWritable(true, false);
		if (file.exists() == false)
			file.mkdirs();
		file = new File(path + "/" + filename);
//		file.setWritable(true, false);
		final StringBuffer stringBuffer = new StringBuffer();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		OutputStreamWriter os= null;
		BufferedWriter bufferedWriter = null;
		try {
			os = new OutputStreamWriter(new FileOutputStream(file),"utf-8");
			bufferedWriter = new BufferedWriter(os);
			profileMap.forEach((k, v) -> {

				stringBuffer.append(k);
				stringBuffer.append(":@:");
				stringBuffer.append(v);
				stringBuffer.append("\r\n");
			});
			bufferedWriter.write(stringBuffer.toString());
			os.flush();
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
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
