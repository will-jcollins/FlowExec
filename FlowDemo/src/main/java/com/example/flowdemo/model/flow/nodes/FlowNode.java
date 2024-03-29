package com.example.flowdemo.model.flow.nodes;

import java.io.Serializable;
import java.util.Objects;

/**
 * Model representation of a flow-chart node with a unique identifier
 */
public abstract class FlowNode implements Serializable {
    private static int idCounter = 0; // Static ID counter, assigns unique ID to instantiated objects

    private final int id;

    public FlowNode() {
        this.id = idCounter++;
    }

    /**
     * Get node's unique identifier
     * @return unique id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns a flow-chart node's flow language equivalent
     * @return flow language string
     */
    public String toCode() {
        return "|" + id + "|";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlowNode flowNode = (FlowNode) o;
        return id == flowNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Retrieves the largest id value given to a node.
     * Necessary for loading models from a file as new nodes could otherwise be assigned ids that are already in use
     * @return maximum id value
     */
    public static int getIdCounter() {
        return idCounter;
    }

    /**
     * Sets the largest id value given to a node.
     * Necessary for loading models from a file as new nodes could otherwise be assigned ids that are already in use
     */
    public static void setIdCounter(int id) {
        idCounter = id;
    }
}
