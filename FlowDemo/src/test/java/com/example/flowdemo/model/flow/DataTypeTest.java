package com.example.flowdemo.model.flow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTypeTest {

    @Test
    void fromString() {
        // Test that the correct data type is returned
        assertEquals(DataType.IntType, DataType.fromString(DataType.IntType.toString()));
    }
}