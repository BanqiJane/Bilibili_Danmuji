package xyz.acproject.danmuji.file;

import java.io.*;
import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xyz.acproject.danmuji.conf.PublicDataConf;

/**
 * @ClassName GuardFileTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:31
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class GuardFileTools {
	public static Map<Long, String> read() {
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		Map<Long, String> guardMap = new ConcurrentHashMap<>();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		path = path +"/guardFile/";
		File file = new File(path);
//		file.setWritable(true, false);
		if (file.exists() == false)
			file.mkdirs();
		file = new File(path + "/guards("+PublicDataConf.ROOMID +")"+ ".txt");
//		file.setWritable(true, false);
		if (file.exists() == false)
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		BufferedReader bufReader = null;
		try {
			bufReader = new BufferedReader(new FileReader(file));
			String s = "";
			while ((s = bufReader.readLine()) != null) {
				String[] str = s.split(",");
				guardMap.put(Long.valueOf(str[0]), str[1]);
			}
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} finally {
			try {
				if (bufReader != null)
					bufReader.close();
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return guardMap;
	}

	public static void write(String msg) {
//		FileWriter fw = null;
		OutputStreamWriter os= null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			path = path+"/guardFile/";
			File file = new File(path);
//			file.setWritable(true, false);
			if (file.exists() == false)
				file.mkdirs();
			file = new File(path + "guards("+PublicDataConf.ROOMID +")"+ ".txt");
//			file.setWritable(true, false);
			if (file.exists() == false)
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			os = new OutputStreamWriter(new FileOutputStream(file,true),"utf-8");
			bw = new BufferedWriter(os);
//			fw = new FileWriter(file, true);
			pw = new PrintWriter(bw);
			pw.println(msg);
			os.flush();
			bw.flush();
			pw.flush();
		} catch (Exception e) {
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
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
//			if (fw != null) {
//				try {
//					fw.close();
//				} catch (IOException e) {
//					// TODO 自动生成的 catch 块
//					e.printStackTrace();
//				}
//			}
		}
	}
}
