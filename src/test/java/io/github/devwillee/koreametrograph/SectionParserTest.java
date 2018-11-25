package io.github.devwillee.koreametrograph;

import io.github.devwillee.koreametrograph.section.SectionParser;

import java.io.IOException;

public class SectionParserTest {
    public static void main(String[] args) throws IOException {
        SectionParser parser = SectionParser.newInstance();
        parser.parse("서울").forEach(System.out::println);
    }
}
