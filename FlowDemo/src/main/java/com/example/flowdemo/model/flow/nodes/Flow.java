package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.Expr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents an ordered list of flow-chart nodes
 */
public class Flow implements Serializable {
    private Signature signature; // Typed identifier for Flow objects that represent functions
    private List<Signature> parameters; // Typed identifiers of parameters for Flow objects that represent functions
    private LinkedList<FlowNode> nodes = new LinkedList<>();

    public Flow() {
        signature = new Signature("main", DataType.VoidType);
        parameters = new ArrayList<>();
    }

    public Flow(Signature signature, List<Signature> parameters) {
        this.signature = signature;
        this.parameters = parameters;
    }

    /**
     * Returns the typed identifier for the Flow (if it's a function)
     * @return typed identifier
     */
    public Signature getSignature() {
        return signature;
    }

    /**
     * Sets the typed identifier for the Flow (if it's a function)
     * @param signature new value of the typed identifier
     */
    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    /**
     * Returns the list of expected parameter types for the FLow (if it's a function)
     * @return list of typed identifiers
     */
    public List<Signature> getParameters() {
        return parameters;
    }

    /**
     * Sets the expected parameter types for the Flow (if it's a function)
     * @param parameters new value of the parameter list
     */
    public void setParameters(List<Signature> parameters) {
        this.parameters = parameters;
    }

    /**
     * Returns expression with id that exists within any node of the Flow
     * @param id id of target expression
     * @return expr object that matches the id passed
     */
    public Expr getExprById(int id) {
        Expr result = null;

        for (FlowNode node : nodes) {
            // If node contains a flow, search within node's flow(s)
            if (node instanceof FlowContainer container) {
                for (Flow flow : container.getFlows()) {
                    result = (result == null) ? flow.getExprById(id) : result;
                }
            }

            // If node contains an expr, search all expressions for expr matching parameter id
            if (node instanceof ExprContainer container) {
                for (Expr expr : container.getExprs()) {
                    result = (result == null) ? ExprContainer.searchExpr(expr, id) : result;
                }
            }

            // If a result has been found break loop and return result
            if (result != null) {
                break;
            }
        }

        return result;
    }

    /**
     * Returns expression which contains an expression with id that exists within nested expression structure
     * @param id id of target expression
     * @return expr object that contains an expression which matches the id passed
     */
    public Expr getExprParentById(int id) {
        Expr result = null;

        for (FlowNode node : nodes) {
            // If node contains a flow, search within node's flow(s)
            if (node instanceof FlowContainer container) {
                for (Flow flow : container.getFlows()) {
                    result = (result == null) ? flow.getExprById(id) : result;
                }
            }

            // If node contains an expr, search all expressions for expr matching parameter id
            if (node instanceof ExprContainer container) {
                for (Expr expr : container.getExprs()) {
                    result = (result == null) ? ExprContainer.searchExpr(expr, id) : result;
                }
            }

            // If a result has been found break loop and return result
            if (result != null) {
                break;
            }
        }

        return result;
    }

    /**
     * Returns node which contains the expression with the id passed
     * @param id id of target expression
     * @return FlowNode object that contains the expression with the id passed
     */
    public ExprContainer getNodeByExprId(int id) {
        ExprContainer result = null;

        for (FlowNode node : nodes) {
            // If node contains a flow, search within node's flow(s)
            if (node instanceof FlowContainer container) {
                for (Flow flow : container.getFlows()) {
                    result = (result == null) ? flow.getNodeByExprId(id) : result;
                }
            }

            // If node contains an expr, search all expressions for expr matching parameter id
            if (node instanceof ExprContainer container) {
                for (Expr expr : container.getExprs()) {
                    result = (result == null) ? (ExprContainer.searchExpr(expr, id) != null ? container : result) : result;
                }
            }

            // If a result has been found break loop and return result
            if (result != null) {
                break;
            }
        }

        return result;
    }

    /**
     * Sets expression with matching id to null
     * @param id id of expression to be removed
     */
    public void removeExprById(int id) {
        // Retrieve node containing expression
        ExprContainer parentNode = getNodeByExprId(id);
        List<Expr> expressions = parentNode.getExprs();

        for (int i = 0; i < expressions.size(); i++) {
            if (expressions.get(i) != null) {
                if (expressions.get(i).getId() == id) {
                    // Set expression to null if top-level expression is null
                    parentNode.setExpr(null, i);
                } else {
                    // Otherwise search children for matching id
                    ExprContainer.setChildExpr(expressions.get(i), null, id);
                }
            }
        }
    }

    /**
     * Returns node in Flow with matching id
     * @param id id of target FlowNode
     * @return target FlowNode object
     */
    public FlowNode getByID(int id) {
        FlowNode result = null;

        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getId() == id) {
                // Set result to current node id matches
                result = nodes.get(i);
            } else if (nodes.get(i) instanceof FlowContainer container) {
                // If node contains Flow objects, search flows for matching node
                for (Flow flow : container.getFlows()) {
                    result = (result == null) ? flow.getByID(id) : result;
                }
            }

            // Break loop if a result has been found
            if (result != null) {
                break;
            }
        }

        return result;
    }

    /**
     * Inserts node after node with id
     * @param node FlowNode to be inserted
     * @param id id of node new FlowNode should be inserted before
     */
    public void insertByID(FlowNode node, int id) {
        for (int i = 0; i < nodes.size(); i++) {
            // If node matches
            if (nodes.get(i).getId() == id) {
                nodes.add(i + 1, node);
                break;
            } else if (nodes.get(i) instanceof FlowContainer container) {
                for (Flow flow : container.getFlows()) {
                    flow.insertByID(node, id);
                }
            }
        }
    }

    /**
     * Removes node with id from node list
     * @param id id of FlowNode to be removed
     */
    public void removeByID(int id) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getId() == id) {
                // If node is a match remove it from the list
                nodes.remove(i);
            } else if (nodes.get(i) instanceof FlowContainer container) {
                // If node is a Flow container, recursively call removeById on Flow
                for (Flow flow : container.getFlows()) {
                    flow.removeByID(id);
                }
            }
        }
    }

    /**
     * Returns node at flow-chart index
     * @param index index of FlowNode
     * @return FlowNode at index
     */
    public FlowNode getByIndex(int index) {
        return nodes.get(index);
    }

    /**
     * Inserts node into flow-chart at index
     * @param node node to be inserted
     * @param index index to insert new FlowNode
     */
    public void insertByIndex(FlowNode node, int index) {
        nodes.add(index, node);
    }

    /**
     * Removes node at flow-chart index
     * @param index index of FlowNode to be removed
     */
    public void removeByIndex(int index) {
        nodes.remove(index);
    }

    /**
     * Returns the number of FlowNodes in the flow
     * @return number of FlowNodes in the flow
     */
    public int size() {
        return nodes.size();
    }

    /**
     * @return unmodifiable view of flow's nodes
     */
    public List<FlowNode> getFlowNodes() {
        return Collections.unmodifiableList(nodes);
    }

    /**
     * Returns a flow-language function definition representation of the flow-chart
     * @return flow language function definition string
     */
    public String toFunctionCode() {
        StringBuilder sb = new StringBuilder();

        // Build function signature
        sb.append(signature.toCode() + "(");

        // Builds list of typed identifiers
        String prefix = "";
        for (Signature param : parameters) {
            sb.append(prefix);
            prefix = ",";
            sb.append(param.toCode());
        }

        // Builds list of statements
        sb.append(") {\n" + toBodyCode().indent(5));
        sb.append("}");
        return sb.toString();
    }

    /**
     * Returns a flow-language list of statements equivalent to the Flow model
     * @return flow language statement list string
     */
    public String toBodyCode() {
        StringBuilder sb = new StringBuilder();

        // Build list of statements from flow-chart structure
        for (FlowNode node : nodes) {
            sb.append(node.toCode() + "\n");
        }

        return sb.toString();
    }
}
