package com.example.flowdemo.view.editor.expr;

import com.example.flowdemo.view.editor.expr.ExprPlaceholder;

import java.util.List;

/**
 * Interface for UI classes that contain ExprPlaceholders
 */
public interface UIExprContainer {

    List<ExprPlaceholder> getExprPlaceholders();
}
