package com.example.flowdemo.model.flow.nodes;

import java.util.List;

/**
 * Interface for Flow model objects which store other Flow models (e.g. list of statements in a while loop)
 */
public interface FlowContainer {

    /**
     * Returns a list of the Flow models contained within a FlowContainer
     * @return list of Flows
     */
    List<Flow> getFlows();
}
