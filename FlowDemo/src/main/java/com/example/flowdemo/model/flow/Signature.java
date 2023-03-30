package com.example.flowdemo.model.flow;

import java.io.Serializable;

/**
 * Model representation of a typed identifier
 */
public class Signature implements Serializable {

    private String identifier;
    private DataType type;

    public Signature() {
        identifier = "";
        type = DataType.IntType;
    }

    public Signature(String identifier, DataType type) {
        this.identifier = identifier;
        this.type = type;
    }

    /**
     * Returns the identifier
     * @return string identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets the identifier
     * @return string identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the data type related to the identifier
     * @return string identifier
     */
    public DataType getType() {
        return type;
    }

    /**
     * Sets the data type related to the identifier
     * @param type new value of the data type related to the identifier
     */
    public void setType(DataType type) {
        this.type = type;
    }

    /**
     * Returns a flow-language typed identifier
     * @return flow language typed identifier string
     */
    public String toCode() {
        return type.toString() + " " + identifier;
    }
}
