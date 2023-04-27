package com.example.flowdemo.view.editor.cell;

import java.util.List;

/**
 * Interface for UI classes that can accept drag gestures from FlowNode objects
 */
public interface UIFlowContainer {

    List<UIFlow> getFlows();
}
