package io.github.toandv.wci.backend.intepreter;

/**
 * Created by toan on 5/22/16.
 */
public enum RuntimeErrorCode {

    UNINITIALIZED_VALUE("Uninitialized value"),

    VALUE_RANGE("Value out of range"),

    INVALID_CASE_EXPRESSION_VALUE("Invalid CASE expression value"),

    DIVISION_BY_ZERO("Division by zero"),

    INVALID_STANDARD_FUNCTION_ARGUMENT("Invalid standard function argument. "),

    INVALID_INPUT("Invalid input"),

    STACK_OVERFLOW("Runtime stack overflow"),

    UNIMPLEMENTED_FEATURE("Unimplemented feature");

    private final String message;

    RuntimeErrorCode(String msg) {
        this.message = msg;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
