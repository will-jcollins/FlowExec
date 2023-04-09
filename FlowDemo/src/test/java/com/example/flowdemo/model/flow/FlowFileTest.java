package com.example.flowdemo.model.flow;

import com.example.flowdemo.model.flow.nodes.Flow;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class FlowFileTest {

    @Test
    void getFlowMap() {
        // Test that the flow map is returned correctly
        HashMap<String, Flow> flowMap = new HashMap<>();
        FlowFile flowFile = new FlowFile(flowMap, 0, 0);
        assertEquals(flowMap, flowFile.getFlowMap());
    }

    @Test
    void setFlowMap() {
        // Test that the flow map is set correctly
        FlowFile flowFile = new FlowFile(null, 0, 0);
        HashMap<String, Flow> flowMap = new HashMap<>();
        flowFile.setFlowMap(flowMap);
        assertEquals(flowMap, flowFile.getFlowMap());
    }

    @Test
    void getMaxNodeId() {
        // Test that the max node id is returned correctly
        FlowFile flowFile = new FlowFile(null, 200, 0);
        assertEquals(200, flowFile.getMaxNodeId());
    }

    @Test
    void setMaxNodeId() {
        // Test that the max node id is set correctly
        FlowFile flowFile = new FlowFile(null, 0, 0);
        flowFile.setMaxNodeId(200);
        assertEquals(200, flowFile.getMaxNodeId());
    }

    @Test
    void getMaxExprId() {
        // Test that the max expr id is returned correctly
        FlowFile flowFile = new FlowFile(null, 0, 200);
        assertEquals(200, flowFile.getMaxExprId());
    }

    @Test
    void setMaxExprId() {
        // Test that the max expr id is set correctly
        FlowFile flowFile = new FlowFile(null, 0, 0);
        flowFile.setMaxExprId(200);
        assertEquals(200, flowFile.getMaxExprId());
    }
}