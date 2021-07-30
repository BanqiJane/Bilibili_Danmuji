package xyz.acproject.danmuji.serialize;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import xyz.acproject.danmuji.tools.ParseIndentityTools;

import java.lang.reflect.Type;

/**
 * @author Jane
 * @ClassName CoinTypeDeserializer
 * @Description TODO
 * @date 2021/7/25 23:54
 * @Copyright:2021
 */
public class CoinTypeDeserializer implements ObjectDeserializer {

    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        String value = parser.parseObject(String.class);
        Short coin_type = ParseIndentityTools.parseCoin_type(value);
        return (T)coin_type;

    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
