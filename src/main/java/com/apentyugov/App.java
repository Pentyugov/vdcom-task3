package com.apentyugov;

import java.util.Scanner;
public class App {

    private static final DimensionService dimensionService = new DimensionService();
    private static final String PATTERN_ERROR = "Input string doesn't match pattern";
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isFilling = true;

    public static void main(String[] args) {
        startFilling();
    }

    private static void startFilling() {

        while(isFilling) {
            System.out.println("Enter dimension or enter \"continue\" to stop add new dimensions");
            String input = scanner.nextLine();
            if ("continue".equalsIgnoreCase(input)) {
                isFilling = false;
            } else {
                if (AppUtil.isStringMatches(input, AppUtil.INPUT_PATTERN)) {
                    String[] parsedString = AppUtil.parseString(input);
                    dimensionService.createNewDimension(parsedString);
                } else {
                    System.out.println(PATTERN_ERROR);
                }
            }
        }

        dimensionService.createGraphs();
        enterQuestion();
    }

    private static void enterQuestion() {
        boolean isFilling = true;
        while(isFilling) {
            System.out.println("Enter question enter \"exit\" to stop enter question and exit");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                isFilling = false;
            } else {
                if (AppUtil.isStringMatches(input, AppUtil.QUESTION_PATTERN)) {
                    String[] parsedString = AppUtil.parseString(input);
                    String result = dimensionService.calculate(parsedString);
                    System.out.println(result);
                } else {
                    System.out.println(PATTERN_ERROR);
                }
            }
        }
        scanner.close();
    }

}
