package com.example.flowdemo.model.flow.nodes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlowNodeTest {

    @Test
    void getId() {
        // Test a FlowNode instance is created with the correct id
        FlowNode flowNode1 = new DeclareAssignNode();
        assertEquals(FlowNode.getIdCounter() - 1, flowNode1.getId());
        FlowNode flowNode2 = new DeclareAssignNode();
        assertEquals(FlowNode.getIdCounter() - 1, flowNode2.getId());
    }
}