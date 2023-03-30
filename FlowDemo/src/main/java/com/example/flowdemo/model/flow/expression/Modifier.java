package com.example.flowdemo.model.flow.expression;

/**
 * Enumerates an operator that is applied to one argument
 */
public enum Modifier {
    Not("NOT"),
    Size("SIZE");

    private String text;

    Modifier(String text) {
        this.text = text;
    }

    /**
     * Parses a string and returns the equivalent modifier enumeration
     * @param in string value
     * @return equivalent modifier enumeration
     */
    public static Modifier fromString(String in) {
        for (Modifier modifier : Modifier.values()) {
            if (in.equals(modifier.toString())) {
                return modifier;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
