package io.github.toandv.wci.frontend.pascal;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;

import io.github.toandv.wci.frontend.TokenType;

public enum PascalTokenType implements TokenType {
    // Reserved words.
    AND("and"), ARRAY("array"), BEGIN("begin"), CASE("case"), CONST("const"),

    DIV("div"), DO("do"), DOWNTO("downto"), ELSE("else"), END("end"),

    FILE("file"), FOR("for"), FUNCTION("function"), GOTO("goto"), IF("if"),

    IN("in"), LABEL("label"), MOD("mod"), NIL("nil"), NOT("not"),

    OF("of"), OR("or"), PACKED("packed"), PROCEDURE("procedure"), PROGRAM("program"),

    RECORD("record"), REPEAT("repeat"), SET("set"), THEN("then"), TO("to"),

    TYPE("type"), UNTIL("until"), VAR("var"), WHILE("while"), WITH("with"),

    // Special symbols.
    PLUS("+"), MINUS("-"), STAR("*"), SLASH("/"), COLON_EQUALS(":="),

    DOT("."), COMMA(","), SEMICOLON(";"), COLON(":"), QUOTE("'"),

    EQUALS("="), NOT_EQUALS("<>"), LESS_THAN("<"), LESS_EQUALS("<="),

    GREATER_EQUALS(">="), GREATER_THAN(">"), LEFT_PAREN("("), RIGHT_PAREN(")"),

    LEFT_BRACKET("["), RIGHT_BRACKET("]"), LEFT_BRACE("{"), RIGHT_BRACE("}"),

    UP_ARROW("^"), DOT_DOT(".."),

    IDENTIFIER("identifier"), INTEGER("integer"), REAL("real"), STRING("string"), ERROR("error"), END_OF_FILE(
            "end_of_file");

    private static final int FIRST_RESERVED_INDEX = AND.ordinal();
    private static final int LAST_RESERVED_INDEX = WITH.ordinal();

    private static final int FIRST_SPECIAL_INDEX = PLUS.ordinal();
    private static final int LAST_SPECIAL_INDEX = DOT_DOT.ordinal();

    private String text; // token text

    /**
     * Constructor.
     */
    PascalTokenType() {
    }

    /**
     * Constructor.
     * 
     * @param text
     *            the token text.
     */
    PascalTokenType(String text) {
        this.text = text;
    }

    /**
     * Getter.
     * 
     * @return the token text.
     */
    public String getText() {
        return text;
    }

    // Set of lower-cased Pascal reserved word text strings.
    // why not map ?
    public static Map<String, PascalTokenType> RESERVED_WORDS;
    static {
        RESERVED_WORDS = new HashMap<>();
        PascalTokenType values[] = PascalTokenType.values();
        for (int i = FIRST_RESERVED_INDEX; i <= LAST_RESERVED_INDEX; ++i) {
            RESERVED_WORDS.put(values[i].getText(), values[i]);
        }

        RESERVED_WORDS = ImmutableMap.copyOf(RESERVED_WORDS);
    }

    // Hash table of Pascal special symbols. Each special symbol's text
    // is the key to its Pascal token type.
    public static Map<String, PascalTokenType> SPECIAL_SYMBOLS;
    static {
        SPECIAL_SYMBOLS = new HashMap<>();
        PascalTokenType values[] = PascalTokenType.values();
        for (int i = FIRST_SPECIAL_INDEX; i <= LAST_SPECIAL_INDEX; ++i) {
            SPECIAL_SYMBOLS.put(values[i].getText(), values[i]);
        }
        SPECIAL_SYMBOLS = ImmutableMap.copyOf(SPECIAL_SYMBOLS);
    }

    public static PascalTokenType getReservedWord(String tokenText) {
        PascalTokenType pascalTokenType = RESERVED_WORDS.get(tokenText.toLowerCase());
        if (pascalTokenType == null) {
            return PascalTokenType.IDENTIFIER;
        }
        return pascalTokenType;
    }

    public static PascalTokenType getSpecialSymbol(String key) {
        return SPECIAL_SYMBOLS.get(key);
    }

    public static boolean isSpecialSymbol(char currentChar) {
        return SPECIAL_SYMBOLS.containsKey(Character.toString(currentChar));
    }

}
