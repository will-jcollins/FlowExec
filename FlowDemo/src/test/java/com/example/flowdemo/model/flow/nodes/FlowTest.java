package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.IntLit;
import com.example.flowdemo.model.flow.expression.OpExpr;
import com.example.flowdemo.model.flow.expression.Operator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlowTest {

    @Test
    void getSignature() {
        // Test that the signature is returned correctly
        Signature signature = new Signature("test", DataType.IntType);
        Flow flow = new Flow(signature, null);
        assertEquals(signature, flow.getSignature());
    }

    @Test
    void setSignature() {
        // Test that the signature is set correctly
        Signature signature = new Signature("test", DataType.IntType);
        Flow flow = new Flow(null, null);
        flow.setSignature(signature);
        assertEquals(signature, flow.getSignature());
    }

    @Test
    void getParameters() {
        // Test that the parameters are returned correctly
        List<Signature> parameters = List.of(new Signature("test", DataType.IntType), new Signature("test2", DataType.BoolType));
        Flow flow = new Flow(null, parameters);
        assertEquals(parameters, flow.getParameters());
    }

    @Test
    void setParameters() {
        // Test that the parameters are set correctly
        List<Signature> parameters = List.of(new Signature("test", DataType.IntType), new Signature("test2", DataType.BoolType));
        Flow flow = new Flow(null, null);
        flow.setParameters(parameters);
        assertEquals(parameters, flow.getParameters());
    }

    @Test
    void getExprById() {
        // Build a simple flow which contains multiple expression that test different parts of the search function
        Flow flow = new Flow(null, null);

        AssignNode assignNode1 = new AssignNode();
        IntLit intLit1 = new IntLit(0);
        assignNode1.setExpr(intLit1, 0);
        flow.insertByIndex(assignNode1, 0);

        IntLit intLit2 = new IntLit(1);
        IntLit intLit3 = new IntLit(2);
        OpExpr opExpr = new OpExpr(intLit2, intLit3, Operator.Add);
        AssignNode assignNode2 = new AssignNode();
        assignNode2.setExpr(opExpr, 0);
        flow.insertByIndex(assignNode2, 1);

        WhileNode whileNode = new WhileNode();
        AssignNode assignNode3 = new AssignNode();
        IntLit intLit4 = new IntLit(3);
        assignNode3.setExpr(intLit4, 0);
        whileNode.getFlows().get(0).insertByIndex(assignNode3, 0);
        flow.insertByIndex(whileNode, 2);


        // Test that an expression from the top of an expression tree is retrieved correctly
        assertEquals(intLit1, flow.getExprById(intLit1.getId()));

        // Test that an expression from the within an expression tree is retrieved correctly
        assertEquals(intLit3, flow.getExprById(intLit3.getId()));

        // Test than an expression from within a nested flow is retrieved correctly
        assertEquals(intLit4, flow.getExprById(intLit4.getId()));
    }

    @Test
    void getNodeByExprId() {
        // Build a simple flow which contains multiple expression that test different parts of the search function
        Flow flow = new Flow(null, null);

        AssignNode assignNode1 = new AssignNode();
        IntLit intLit1 = new IntLit(0);
        assignNode1.setExpr(intLit1, 0);
        flow.insertByIndex(assignNode1, 0);

        AssignNode assignNode2 = new AssignNode();
        IntLit intLit2 = new IntLit(1);
        assignNode2.setExpr(intLit2, 0);
        WhileNode whileNode = new WhileNode();
        whileNode.getFlows().get(0).insertByIndex(assignNode2, 0);
        flow.insertByIndex(whileNode, 1);

        // Test that a node which contains an expression is retrieved correctly
        assertEquals(assignNode1, flow.getNodeByExprId(intLit1.getId()));

        // Test that a node which contains an expression within a nested flow is retrieved correctly
        assertEquals(assignNode2, flow.getNodeByExprId(intLit2.getId()));
    }

    @Test
    void removeExprById() {
        // Build a simple flow which contains multiple expression that test different parts of the removal function
        Flow flow = new Flow(null, null);

        AssignNode assignNode1 = new AssignNode();
        IntLit intLit1 = new IntLit(0);
        assignNode1.setExpr(intLit1, 0);
        flow.insertByIndex(assignNode1, 0);

        IntLit intLit2 = new IntLit(1);
        IntLit intLit3 = new IntLit(2);
        OpExpr opExpr = new OpExpr(intLit2, intLit3, Operator.Add);
        AssignNode assignNode2 = new AssignNode();
        assignNode2.setExpr(opExpr, 0);
        flow.insertByIndex(assignNode2, 1);

        WhileNode whileNode = new WhileNode();
        AssignNode assignNode3 = new AssignNode();
        IntLit intLit4 = new IntLit(3);
        assignNode3.setExpr(intLit4, 0);
        whileNode.getFlows().get(0).insertByIndex(assignNode3, 0);
        flow.insertByIndex(whileNode, 2);

        // Test that an expression from the top of an expression tree is removed correctly
        flow.removeExprById(intLit1.getId());
        assertNull(assignNode1.getExprs().get(0));

        // Test that an expression from the within an expression tree is removed correctly
        flow.removeExprById(intLit3.getId());
        assertNull(opExpr.getExprs().get(1));

        // Test that an expression from within a nested flow is removed correctly
        flow.removeExprById(intLit4.getId());
        assertNull(assignNode3.getExprs().get(0));
    }

    @Test
    void getNodeByID() {
        // Build a simple flow which contains multiple nodes that test different parts of the search function
        Flow flow = new Flow(null, null);

        WhileNode whileNode = new WhileNode();
        AssignNode assignNode = new AssignNode();
        whileNode.getFlows().get(0).insertByIndex(assignNode, 0);
        flow.insertByIndex(whileNode, 0);

        // Test that a node is retrieved correctly
        assertEquals(whileNode, flow.getNodeByID(whileNode.getId()));

        // Test that a node within a nested flow is retrieved correctly
        assertEquals(assignNode, flow.getNodeByID(assignNode.getId()));
    }

    @Test
    void insertNodeByID() {
        // Build a simple flow which contains multiple nodes that test different parts of the search function
        Flow flow = new Flow(null, null);

        WhileNode whileNode = new WhileNode();
        AssignNode assignNode1 = new AssignNode();
        whileNode.getFlows().get(0).insertByIndex(assignNode1, 0);
        flow.insertByIndex(whileNode, 0);

        // Test that a node is inserted correctly
        AssignNode assignNode2 = new AssignNode();
        flow.insertNodeByID(assignNode2, whileNode.getId());
        assertEquals(assignNode2, flow.getByIndex(1));

        // Test that a node is inserted correctly within a nested flow
        AssignNode assignNode3 = new AssignNode();
        flow.insertNodeByID(assignNode3, assignNode1.getId());
        assertEquals(assignNode3, whileNode.getFlows().get(0).getByIndex(1));
    }

    @Test
    void removeNodeByID() {
        // Build a simple flow which contains multiple expression that test different parts of the removal function
        Flow flow = new Flow(null, null);

        WhileNode whileNode = new WhileNode();
        AssignNode assignNode1 = new AssignNode();
        whileNode.getFlows().get(0).insertByIndex(assignNode1, 0);
        flow.insertByIndex(whileNode, 0);

        // Test that a node that is within a nested flow is removed correctly
        assertEquals(1, whileNode.getFlows().get(0).size());
        flow.removeNodeByID(assignNode1.getId());
        assertEquals(0, whileNode.getFlows().get(0).size());

        // Test that a node is removed correctly
        assertEquals(1, flow.size());
        flow.removeNodeByID(whileNode.getId());
        assertEquals(0, flow.size());
    }

    @Test
    void getByIndex() {
        // Test that a node is retrieved correctly
        Flow flow = new Flow(null, null);
        WhileNode whileNode = new WhileNode();
        flow.insertByIndex(whileNode, 0);

        assertEquals(whileNode, flow.getByIndex(0));
    }

    @Test
    void insertByIndex() {
        // Test that a node is inserted correctly
        Flow flow = new Flow(null, null);
        WhileNode whileNode = new WhileNode();
        flow.insertByIndex(whileNode, 0);

        assertEquals(whileNode, flow.getByIndex(0));
    }

    @Test
    void removeByIndex() {
        // Test that a node is removed correctly
        Flow flow = new Flow(null, null);
        WhileNode whileNode = new WhileNode();
        flow.insertByIndex(whileNode, 0);
        flow.removeByIndex(0);

        assertEquals(0, flow.size());
    }

    @Test
    void size() {
        // Test that the size of the flow is returned correctly
        Flow flow = new Flow(null, null);
        WhileNode whileNode = new WhileNode();
        flow.insertByIndex(whileNode, 0);

        assertEquals(1, flow.size());

        flow.removeByIndex(0);
        assertEquals(0, flow.size());
    }

    @Test
    void getFlowNodes() {
        // Test that the flow nodes are returned correctly
        Flow flow = new Flow(null, null);
        WhileNode whileNode = new WhileNode();
        flow.insertByIndex(whileNode, 0);

        assertEquals(1, flow.getFlowNodes().size());
        assertEquals(whileNode, flow.getFlowNodes().get(0));

        // Test that the list of nodes cannot be modified
        assertThrows(UnsupportedOperationException.class, () -> flow.getFlowNodes().add(new WhileNode()));
    }
}