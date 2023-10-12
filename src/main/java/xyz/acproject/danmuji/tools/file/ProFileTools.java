package xyz.acproject.danmuji.tools.file;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
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
@Slf4j
public class ProFileTools {
	private static final String STORE_DIR;
	static {
		FileTools fileTools = new FileTools();
		String tmp;
		try {
			tmp = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
		} catch (Exception e1) {
			log.warn(e1.getMessage(),e1);
			tmp = System.getProperty("user.dir");
		}
		STORE_DIR = tmp;
	}

	/**
	 * 读取profile文件内容 转为 Map对象
	 * @param filename 文件名称,非绝对地址
	 * @return is not null
	 * @throws IOException io流处理异常
	 * @throws FileNotFoundException 文件未找到
	 */
	public static Map<String, String> read(String filename) throws IOException{
		File file = new File(STORE_DIR);
		file.mkdirs();
		file = new File(STORE_DIR + "/" + filename);
		Map<String, String> profileMap = new ConcurrentHashMap<>();
		if (file.createNewFile()){
			//如果成功创建了空文件则直接返回空Map
			return profileMap;
		}

		String dataString;
		String[] strings;
		//try-catch-resource
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
			while ((dataString = bufferedReader.readLine()) != null) {
				strings = dataString.split(":@:");
				if (strings.length == 2) {
					profileMap.put(strings[0], strings[1]);
				}
			}
		} catch (FileNotFoundException e) {
			log.warn("文件{}不存在!",file.getAbsolutePath());
			throw e;
		} catch(IOException e) {
			log.error(e.getMessage(),e);
			throw e;
		}
		return profileMap;

	}

	public static void write(Map<String, String> profileMap, String filename) {
		File file = new File(STORE_DIR);
		file.mkdirs();
		file = new File(STORE_DIR + "/" + filename);
		final StringBuffer stringBuffer = new StringBuffer();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		BufferedWriter bufferedWriter = null;
		try (OutputStreamWriter os = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)){
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
		}
	}
}
