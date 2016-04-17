package io.github.toandv.wci.message;

public enum MessageType {

    // lineNum - source line number,
    // line - text of source line
    SOURCE_LINE,

    SYNTAX_ERROR,

    // token.getLineNumber() - number of source lines read
    // getErrorCount() - number of syntax errors
    // elapsedTime - elapsed parsing time
    PARSER_SUMMARY,

    // executionCount - number of statements executed
    // runtimeErrors - number of runtime errors
    // elapsedTime - elapsed execution time
    INTERPRETER_SUMMARY,

    // instructionCount - number of instructions generated
    // elapsedTime - elapsed code generation time
    COMPILER_SUMMARY,

    MISCELLANEOUS,

    // token.getLineNumber() source line number
    // token.getPosition() beginning source position
    // token.getType() token type
    // token.getText() token text
    // token.getValue() token value
    TOKEN,

    ASSIGN,

    FETCH,

    BREAKPOINT,

    RUNTIME_ERROR,

    CALL,

    RETURN
}
