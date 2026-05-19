package com.itk.entity;

public enum OperationType {
    DEPOSIT("DEPOSIT"), WITHDRAW("WITHDRAW");
    
    private final String type;
    
    OperationType(String type) {
        this.type = type;
    }
    
    public String getWeight() {
        return type;
    }
}
