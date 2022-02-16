package xyz.acproject.danmuji.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * @author Jane
 * @ClassName ObjectUtils
 * @Description TODO
 * @date 2022/2/11 11:08
 * @Copyright:2022
 */
public class ObjectUtils {

    public static boolean isNotNullAndFieldIsTrue(Object object, String fieldName) {
        if (object == null || StringUtils.isBlank(fieldName)) return false;
        Class clazz = (Class) object.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = null;
            fieldValue = field.get(object);
//                Type fieldType = field.getGenericType();
            String fieldChildName = field.getName();
//                System.err.println("属性类型：" + fieldType + ",属性名：" + fieldChildName
//                        + ",属性值：" + fieldValue);
            if (fieldChildName.equals(fieldName) && fieldValue!=null && fieldValue instanceof Boolean) {
                return (boolean) fieldValue;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean isAllNull(Object object) {
        Class clazz = (Class) object.getClass();
        Field fields[] = clazz.getDeclaredFields();
        boolean flag = true;
        for (Field field : fields) {
            field.setAccessible(true);
            Object fieldValue = null;
            try {
                fieldValue = field.get(object);
//                Type fieldType = field.getGenericType();
//                String fieldName = field.getName();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fieldValue != null) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
