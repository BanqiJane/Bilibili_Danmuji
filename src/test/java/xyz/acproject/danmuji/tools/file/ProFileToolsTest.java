package xyz.acproject.danmuji.tools.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tatooi
 * @since 2.6.41
 */
class ProFileToolsTest {


    @BeforeAll
    static void write() {
        HashMap<String, String> profileMap = new HashMap<>();
        profileMap.put("text","mono");
        profileMap.put("title","city");
        ProFileTools.write(profileMap,"test.file");
    }

    @Test
    void read() {
        Map<String, String> map;
        try {
            map = ProFileTools.read("test.file");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertThat(map)
                .containsEntry("text","mono")
                .containsEntry("title","city")
                ;
    }


}