package io.github.devwillee.koreametrograph;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.devwillee.koreametrograph.cities.seoul.Model;
import io.github.devwillee.koreametrograph.section.Section;
import io.github.devwillee.koreametrograph.section.SectionParser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class SectionParserTest {
    public static void main(String[] args) throws IOException {

        String lineNum = "S";

        ObjectMapper mapper = new ObjectMapper();

        List<String> stationNames = Model.build().get(lineNum);
        for (int i=0 ; i < stationNames.size() - 1 ; i++) {
            String first = stationNames.get(i);
            String second = stationNames.get(i + 1);

            try {
                FileWriter writer = new FileWriter("src/test/resources/out/seoul-section.json", true);

                Section section = SectionParser.getInstance().parse(first).stream()
                        .filter(x ->
                                x.getEndLineNum() == null &&
                                x.getEndName().equals(second))
                        .findFirst().get();

                writer.append(",\n");
                mapper.writeValue(writer, section);

                System.out.println(section);
            } catch (NoSuchElementException e) {
                System.out.println("Not found. " + first + " / " + second);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        // FIXME 모델과 비교해서 개수 맞는 지 파악 필요
        // SectionParser.getInstance().parse("미금").forEach(System.out::println);
    }
}
