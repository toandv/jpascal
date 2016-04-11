package io.github.toandv.wci.frontend;

import io.github.toandv.wci.frontend.pascal.PascalParserTD;
import io.github.toandv.wci.frontend.pascal.PascalScanner;

public class FrontendFactory {
    public static Parser createParser(String language, String type, Source source) throws Exception {
        if (language.equalsIgnoreCase("Pascal") && type.equalsIgnoreCase("top-down")) {
            Scanner scanner = new PascalScanner(source);
            return new PascalParserTD(scanner);
        } else if (!language.equalsIgnoreCase("Pascal")) {
            throw new Exception("Parser factory: Invalid language '" + language + "'");
        } else {
            throw new Exception("Parser factory: Invalid type '" + type + "'");
        }
    }
}
