package xyz.acproject.danmuji.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Hashtable;

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
	public static Hashtable<Long, String> read() {
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		Hashtable<Long, String> hashtable = new Hashtable<Long, String>();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		path = path +"/guardFile/";
		File file = new File(path);
		file.setWritable(true, false);
		if (file.exists() == false)
			file.mkdirs();
		file = new File(path + "/guards("+PublicDataConf.ROOMID +")"+ ".txt");
		file.setWritable(true, false);
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
				hashtable.put(Long.valueOf(str[0]), str[1]);
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
		return hashtable;
	}

	public static void write(String msg) {
		FileWriter fw = null;
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
			file.setWritable(true, false);
			if (file.exists() == false)
				file.mkdirs();
			file = new File(path + "guards("+PublicDataConf.ROOMID +")"+ ".txt");
			file.setWritable(true, false);
			if (file.exists() == false)
				try {
					file.createNewFile();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			fw = new FileWriter(file, true);
			pw = new PrintWriter(fw);
			pw.println(msg);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
}
