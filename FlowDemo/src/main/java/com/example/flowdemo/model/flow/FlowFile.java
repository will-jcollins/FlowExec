package com.example.flowdemo.model.flow;

import com.example.flowdemo.model.flow.nodes.Flow;

import java.io.Serializable;
import java.util.Map;

/**
 * Container class for information stored in the .flow file extension
 */
public class FlowFile implements Serializable {
    private Map<String, Flow> flowMap;
    private int maxNodeId;
    private int maxExprId;

    public FlowFile(Map<String, Flow> flowMap, int maxNodeId, int maxExprId) {
        this.flowMap = flowMap;
        this.maxNodeId = maxNodeId;
        this.maxExprId = maxExprId;
    }

    public Map<String, Flow> getFlowMap() {
        return flowMap;
    }

    public void setFlowMap(Map<String, Flow> flowMap) {
        this.flowMap = flowMap;
    }

    public int getMaxNodeId() {
        return maxNodeId;
    }

    public void setMaxNodeId(int maxNodeId) {
        this.maxNodeId = maxNodeId;
    }

    public int getMaxExprId() {
        return maxExprId;
    }

    public void setMaxExprId(int maxExprId) {
        this.maxExprId = maxExprId;
    }
}
