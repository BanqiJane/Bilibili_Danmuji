package xyz.acproject.danmuji.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;

/**
 * @author Jane
 * @ClassName JsonFileTools
 * @Description TODO
 * @date 2021/2/7 16:40
 * @Copyright:2021
 */
public class JsonFileTools {

    /**
     * 生成.json格式文件
     */
    public static boolean createJsonFile(String jsonString) {
        boolean flag = true;
        String path = System.getProperty("user.dir");
        FileTools fileTools = new FileTools();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            path = URLDecoder.decode(fileTools.getBaseJarPath().toString(), "utf-8");
        } catch (Exception e1) {
            // TODO 自动生成的 catch 块
            e1.printStackTrace();
        }

        // 生成json格式文件
        try {
            path = path+"/set/";
            // 保证创建一个新文件
            File file = new File(path+"set"+".json");
            file.setWritable(true, false);
            if (file.exists() == false)
                file.mkdirs();
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();

            // 格式化json字符串
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(content);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
}
