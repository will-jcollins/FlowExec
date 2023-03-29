package com.example.flowdemo.model.flow.nodes;

/**
 * Model representation of a flow-chart comment
 */
public class CommentNode extends FlowNode {

    private String comment;

    public CommentNode(String comment) {
        this.comment = comment;
    }

    /**
     * Returns a flow-language comment
     * @return flow language comment string
     */
    @Override
    public String toCode() {
        return super.toCode() + " " + "//" + comment;
    }
}
