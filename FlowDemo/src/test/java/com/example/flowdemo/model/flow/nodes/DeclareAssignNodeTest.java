package com.example.flowdemo.model.flow.nodes;

import com.example.flowdemo.model.flow.DataType;
import com.example.flowdemo.model.flow.Signature;
import com.example.flowdemo.model.flow.expression.IntLit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeclareAssignNodeTest {

    @Test
    void getSignature() {
        // Test that the signature is returned correctly
        DeclareAssignNode declareAssign = new DeclareAssignNode();
        Signature signature = new Signature("test", DataType.IntType);
        declareAssign.setSignature(signature);
        assertEquals(signature, declareAssign.getSignature());
    }

    @Test
    void getExprs() {
        // Test that the expression is returned correctly
        DeclareAssignNode declareAssign = new DeclareAssignNode();
        IntLit intLit = new IntLit(0);
        declareAssign.setExpr(intLit, 0);
        assertEquals(intLit, declareAssign.getExprs().get(0));
    }
}