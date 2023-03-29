package com.example.flowdemo.model.flow.expression;

/**
 * Enumerates an operator that is applied to two arguments
 */
public enum Operator {
    // Arithmetic Operations
    Add         ("+"),
    Sub         ("-"),
    Mult        ("*"),
    Div         ("/"),
    Mod         ("%"),
    // Boolean Operations
    Greater     (">"),
    GreaterEq   (">="),
    Less        ("<"),
    LessEq      ("<="),
    And         ("AND"),
    Or          ("OR"),
    Xor         ("XOR"),
    Eq          ("EQUALS");

    private String code;

    Operator(String code) {
        this.code = code;
    }

    /**
     * Parses a string and returns the equivalent operator enumeration
     * @param in string value
     * @return equivalent modifier enumeration
     */
    public static Operator fromString(String in) {
        for (Operator operator : Operator.values()) {
            if (in.equals(operator.toString())) {
                return operator;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }
}
