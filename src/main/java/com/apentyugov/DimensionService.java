package com.apentyugov;

import java.util.HashSet;
import java.util.Set;

public class DimensionService {

    private final Set<Dimension> dimensions = new HashSet<>();
    private boolean displayDimensions = true;

    public void createNewDimension(String[] parsedInput) {
        Dimension dimension = createDimension(parsedInput);
        dimensions.add(dimension);
        if (displayDimensions) {
            displayAllDimensions();
        }
    }

    private Dimension createDimension(String[] parsedInput) {
        double valueOne;
        double valueTwo;
        String valueOneName;
        String valueTwoName;

        valueOneName = parsedInput[1];
        valueTwoName = parsedInput[3];

        try {
            valueOne = Double.parseDouble(parsedInput[0]);
            valueTwo = Double.parseDouble(parsedInput[2]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }

        // swipe values
        if (valueTwo < valueOne) {
            double tmpValue = valueOne;
            String tmpName = valueOneName;
            valueOne = valueTwo;
            valueTwo = tmpValue;
            valueOneName = valueTwoName;
            valueTwoName = tmpName;
        }

        valueTwo = valueTwo / valueOne;

        return new Dimension(valueOneName, valueTwoName, valueTwo);
    }

    public void createGraphs() {
        for (Dimension dimension : new HashSet<>(dimensions)) {
            Dimension founded = dimensions
                    .stream()
                    .filter(d -> !d.equals(dimension) &&
                            (d.getCode().contains(dimension.getFromValue()) || d.getCode().contains(dimension.getToValue())))
                    .findFirst()
                    .orElse(null);

            if (founded != null) {
                Dimension graph;
                if (!dimension.getToValue().equals(founded.getToValue())) {
                    if (!dimension.getFromValue().equals(founded.getToValue())) {
                        graph = new Dimension(
                                dimension.getFromValue(),
                                founded.getToValue(),
                                dimension.getCoefficient() * founded.getCoefficient()
                        );
                    } else {
                        graph = new Dimension(
                                founded.getFromValue(),
                                dimension.getToValue(),
                                dimension.getCoefficient() * founded.getCoefficient()
                        );
                    }
                } else {
                    graph = new Dimension(
                            dimension.getFromValue(),
                            founded.getFromValue(),
                            dimension.getCoefficient() / founded.getCoefficient()
                    );
                }
                dimensions.add(graph);
            }

        }
    }

    public String calculate(String[] parsedString) {
        String fromValue = parsedString[1];
        String toValue = parsedString[3];
        Double amount = Double.parseDouble(parsedString[0]);
        String code = fromValue + "-" + toValue;
        StringBuilder builder = new StringBuilder();
        Double result = null;

        Dimension dimension = findDimension(code);
        if (dimension != null) {
            result = amount * dimension.getCoefficient();
        } else {
            String reverseCode = toValue + "-" + fromValue;
            Dimension reverseDimension = findDimension(reverseCode);
            if (reverseDimension != null) {
                result = amount / reverseDimension.getCoefficient();
            }
        }
        if (result != null) {
            builder
                    .append(amount)
                    .append(" ")
                    .append(fromValue)
                    .append(" = ")
                    .append(result)
                    .append(" ")
                    .append(toValue);
            return builder.toString();
        }

        return builder
                .append("Conversion not possible.")
                .toString();
    }

    public Set<Dimension> getDimensions() {
        return dimensions;
    }

    public void setDisplayDimensions(boolean displayDimensions) {
        this.displayDimensions = displayDimensions;
    }

    private void displayAllDimensions() {
        dimensions.forEach(System.out::println);
    }

    private Dimension findDimension(String code) {
        return dimensions
                .stream()
                .filter(d -> d.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
