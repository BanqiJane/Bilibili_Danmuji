package xyz.acproject.danmuji.utils;

import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * 依赖于fastjson包
 * @author zjian
 * @version fastjsonTools v1.0
 */
public class FastJsonUtils {
	private static final SerializeConfig CONFIG = new SerializeConfig();

	private static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";

	@SuppressWarnings("unused")
	private static final String FORMAT_DATE = "yyyy-MM-dd";

	private static final SerializerFeature[] FEATURES = {

				SerializerFeature.WriteMapNullValue,

				SerializerFeature.WriteDateUseDateFormat,

				SerializerFeature.WriteNullListAsEmpty

	}; 

	

//	WriteMapNullValue

//	WriteDateUseDateFormat

	static {

		CONFIG.put(Date.class, new SimpleDateFormatSerializer(FORMAT_TIME));

	}

	

	public static <T> T parseObject(String json, Class<T> clazz) {

		try {

			T t = JSON.parseObject(json, clazz);

			return t;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static <T> List<T> parseList(String json, Class<T> clazz) {

		try {

			List<T> list = JSON.parseArray(json, clazz);

			return list;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	/**

	 * 某人转为yyyy-MM-dd HH:mm:ss格式

	 * @param object

	 * @return

	 */

	public static String toJson(Object object) {

		try {

			String json = JSON.toJSONString(object, CONFIG, FEATURES);

			return json;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static JSONObject getAsJSONObjectFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getJSONObject(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static JSONArray getAsJSONArrayFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getJSONArray(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static String getAsStringFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getString(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static Integer getAsIntegerFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getInteger(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static Long getAsLongFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getLong(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static Double getAsDoubleFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getDouble(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static Boolean getAsBooleanFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getBoolean(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static Short getAsShortFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getShort(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static Byte getAsByteFromObject(String json, String key) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getByte(key);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static <T> T getAsObjectFromObject(String json, String key, Class<T> clazz) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			return jsonObject.getObject(key, clazz);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static <T> List<T> getAsListFromObject(String json, String key, Class<T> clazz) {

		try {

			JSONObject jsonObject = JSON.parseObject(json);

			JSONArray jsonArray = jsonObject.getJSONArray(key);

			return jsonArray.toJavaList(clazz);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static <T> T  toJavaObject(JSONObject jsonObject, Class<T> clazz) {

		try {

			T t = jsonObject.toJavaObject(clazz);

			return t;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static <T> List<T> toJavaList(JSONArray jsonArray, Class<T> clazz) {

		try {

			List<T> list = jsonArray.toJavaList(clazz);

			return list;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static JSONObject parseJSONObject(Object object) {

		try {

			return JSON.parseObject(toJson(object));

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	public static JSONArray parseJSONArray(Object object) {

		try {

			return JSON.parseArray(toJson(object));

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	//解决泛型问题 不能强制转换的方法

	public static <T> T generateAssignObject(Object source, Class<T> clazz) {

		try {

			T t = toJavaObject(parseJSONObject(source), clazz);

			return t;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

	

	//解决泛型问题 不能强制转换的方法

	public static <T> List<T> generateAssignList(Object source, Class<T> clazz) {

		try {

			List<T> list = toJavaList(parseJSONArray(source), clazz);

			return list;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

}

