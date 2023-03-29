package com.example.flowdemo.view.editor.menu;

public enum ItemType {
    For                 ("For Loop"),
    While               ("While Loop"),
    If                  ("If-Else Statement"),
    Output              ("Output"),
    DeclareAssign       ("Declare & Assign Variable"),
    Assign              ("Assign Variable"),
    AssignArray("Assign Array Variable Index"),
    Call                ("Call Function"),
    Return              ("Return Value"),
    BoolLit             ("Boolean"),
    CharLit             ("Character"),
    IntLit              ("Integer"),
    VarExpr             ("Variable"),
    OpExpr              ("Operator Expression"),
    ModifierExpr        ("Modifier Expression"),
    ArrayLit            ("Array"),
    CallExpr            ("Call Function In Expression");

    private String label;

    ItemType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
