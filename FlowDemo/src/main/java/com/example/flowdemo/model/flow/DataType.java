package com.example.flowdemo.model.flow;

/**
 * Enumeration of flow-chart supported data types
 */
public enum DataType {
    IntType("Int"),
    BoolType("Bool"),
    CharType("Char"),
    VoidType("Void"),
    IntArrayType("IntArray"),
    BoolArrayType("BoolArray"),
    CharArrayType("CharArray");

    private String code;

    DataType(String code) {
        this.code = code;
    }

    /**
     * Parses a string and returns the equivalent data type enumeration
     * @param in string value
     * @return equivalent data type enumeration
     */
    public static DataType fromString(String in) {
        for (DataType type : DataType.values()) {
            if (in.equals(type.toString())) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return code;
    }
}
