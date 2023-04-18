package xyz.acproject.danmuji.file;

import xyz.acproject.danmuji.conf.PublicDataConf;
import xyz.acproject.danmuji.utils.JodaTimeUtils;

import java.io.*;
import java.net.URLDecoder;

/**
 * @ClassName LogFileTools
 * @Description TODO
 * @author BanqiJane
 * @date 2020年8月10日 下午12:28:39
 *
 * @Copyright:2020 blogs.acproject.xyz Inc. All rights reserved.
 */
public class LogFileTools {
	private volatile static LogFileTools logFileTools;

	private LogFileTools() {
	}

	public static LogFileTools getlogFileTools() {
		if (logFileTools == null) {
			synchronized (LogFileTools.class) {
				if (logFileTools == null) {
				logFileTools = new LogFileTools();
				}
			}
		}
		return logFileTools;
	}

	public void logFile(String msg) {
//		FileWriter fw = null;
		OutputStreamWriter os= null;
		BufferedWriter bw = null;
		PrintWriter pw = null;
		String path = System.getProperty("user.dir");
		FileTools fileTools = new FileTools();
		StringBuilder stringBuilder = new StringBuilder();
		try {
			path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
			path = path + "/Danmuji_log/";
			File file = new File(path);
//			file.setWritable(true, false);
			if (file.exists() == false)
				file.mkdirs();
			stringBuilder.append(JodaTimeUtils.getCurrentDateString());
			stringBuilder.append("(");
			stringBuilder.append(PublicDataConf.ROOMID);
			stringBuilder.append(")");
			file = new File(path + stringBuilder.toString() + ".txt");
//			file.setWritable(true, false);
			stringBuilder.delete(0, stringBuilder.length());
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
