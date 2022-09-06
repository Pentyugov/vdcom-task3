package com.apentyugov;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DimensionServiceTest {

    private final DimensionService dimensionService = new DimensionService();

    @Test
    void createNewDimension() {
        String input = "1024 byte = 1 kilobyte";
        String[] parsed = AppUtil.parseString(input);
        dimensionService.setDisplayDimensions(false);
        dimensionService.createNewDimension(parsed);
        Set<Dimension> dimensions = dimensionService.getDimensions();
        Dimension dimension = dimensions.iterator().next();
        assertAll(
                () -> assertNotNull(dimensions),
                () -> assertEquals(1, dimensions.size()),
                () -> assertNotNull(dimensions),
                () -> assertEquals(1024.0, dimension.getCoefficient()),
                () -> assertEquals("kilobyte-byte", dimension.getCode())
        );

    }

    @Test
    void createGraphs() throws IOException {
        fillDimensions();
        dimensionService.createGraphs();
        Dimension graph = new Dimension("giraffe", "hare", 40.0);
        Dimension createdGraph = dimensionService.getDimensions()
                .stream()
                .filter(d -> d.getCode().equals(graph.getCode()))
                .findFirst()
                .orElse(null);

        assertAll(
                () -> assertNotNull(createdGraph),
                () -> assertEquals(graph, createdGraph)
        );
    }

    @Test
    void calculate() throws IOException {
        fillDimensions();
        dimensionService.createGraphs();
        InputStream inputStream = DimensionServiceTest.class.getClassLoader().getResourceAsStream("questions.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        dimensionService.setDisplayDimensions(false);
        String[] parsed = AppUtil.parseString(line);
        String result = dimensionService.calculate(parsed);
        String expected = "1.0 giraffe = 40.0 hare";
        reader.close();
        assertEquals(expected, result);

    }

    void fillDimensions() throws IOException {
        InputStream inputStream = DimensionServiceTest.class.getClassLoader().getResourceAsStream("dimensions.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        dimensionService.setDisplayDimensions(false);
        while (line != null) {
            String[] parsed = AppUtil.parseString(line);
            dimensionService.createNewDimension(parsed);
            line = reader.readLine();
        }
        reader.close();
    }
}