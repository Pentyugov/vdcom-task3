package com.apentyugov;

import java.util.Objects;

public class Dimension {

    private String fromValue;
    private String toValue;
    private final String code;
    private Double coefficient;

    public Dimension(String fromValue, String toValue, Double coefficient) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.coefficient = coefficient;
        this.code = this.fromValue + "-" + this.toValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimension dimension = (Dimension) o;
        return code.equals(dimension.code) && coefficient.equals(dimension.coefficient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, coefficient);
    }

    @Override
    public String toString() {
        return "Dimension: " + code + " -> " + coefficient;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    public Double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Double coefficient) {
        this.coefficient = coefficient;
    }

    public String getCode() {
        return code;
    }
}
