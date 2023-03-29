package com.example.flowdemo.model.transpiler;

public class FlowException extends RuntimeException {
    private String functionIdentifier;
    private int id;
    private ErrorType errorParent; // Determines whether an error came from a node or an expr

    public FlowException(String functionIdentifier, int id, ErrorType errorParent, String message) {
        super(message);
        this.functionIdentifier = functionIdentifier;
        this.id = id;
        this.errorParent = errorParent;
    }

    public int getId() {
        return id;
    }

    public ErrorType getErrorType() {
        return errorParent;
    }

    @Override
    public String toString() {
        return "FlowException{" +
                "message='" + getMessage() + '\'' +
                "functionIdentifier='" + functionIdentifier + '\'' +
                ", id=" + id +
                ", errorParent=" + errorParent +
                '}';
    }
}
