package com.example.lv1_a.model;

public class CalculatorResponse{
    private int result;
    private int parameter1;
    private int parameter2;

    //Getter & Setter ..
    public CalculatorResponse(int result, int parameter1, int parameter2) {
        this.result = result;
        this.parameter1 = parameter1;
        this.parameter2 = parameter2;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getParameter1() {
        return parameter1;
    }

    public void setParameter1(int parameter1) {
        this.parameter1 = parameter1;
    }

    public int getParameter2() {
        return parameter2;
    }

    public void setParameter2(int parameter2) {
        this.parameter2 = parameter2;
    }
}
