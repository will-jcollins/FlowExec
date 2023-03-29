package com.example.flowdemo.model.transpiler;

import com.example.flowdemo.model.flow.DataType;

public class FlowType {

    private DataType type;
    private FlowType containedType;

    public FlowType(DataType type, FlowType containedType) {
        this.type = type;
        this.containedType = containedType;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public FlowType getContainedType() {
        return containedType;
    }

    public void setContainedType(FlowType containedType) {
        this.containedType = containedType;
    }
}
