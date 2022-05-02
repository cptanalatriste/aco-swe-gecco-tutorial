package setcovering.antcolony.isula;

import isula.aco.Environment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnvironmentForSetCovering extends Environment {

    private static final int UNASSIGNED = -1;

    private int numberOfSets;
    private int numberOfElements;


    private final Map<Integer, Set<Integer>> elementsPerSet = new HashMap<>();

    public EnvironmentForSetCovering(String fileName) throws IOException {
        super();
        processDataFile(fileName);
        this.setPheromoneMatrix(this.createPheromoneMatrix());

    }

    @Override
    protected double[][] createPheromoneMatrix() {
        if (this.numberOfSets != 0) {
            return new double[this.numberOfSets][1];
        }

        return null;
    }

    public Set<Integer> getElementsCovered(Integer setId) {
        return this.elementsPerSet.get(setId);
    }

    public Integer getNumberOfElements() {
        return this.numberOfElements;
    }

    public Map<Integer, Set<Integer>> getElementsPerSet() {
        return elementsPerSet;
    }

    private void processDataFile(String fileName) throws IOException {
        int lineCounter = 0;
        int elementId = UNASSIGNED;
        int setsForElement = UNASSIGNED;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");

                if (lineCounter == 0) {
                    this.numberOfElements = Integer.parseInt(tokens[0]);
                    this.numberOfSets = Integer.parseInt(tokens[1]);
                    IntStream.range(0, numberOfSets)
                            .forEachOrdered((setId) -> elementsPerSet.put(
                                    setId, new HashSet<>()));

                } else if (elementId == UNASSIGNED && tokens.length == 1) {
                    elementId = Integer.parseInt(tokens[0]);
                } else if (elementId != UNASSIGNED && setsForElement == UNASSIGNED && tokens.length == 1) {
                    setsForElement = Integer.parseInt(tokens[0]);
                } else if (elementId != UNASSIGNED && setsForElement != UNASSIGNED) {

                    Set<Integer> setList = Arrays.stream(tokens)
                            .map(Integer::parseInt)
                            .collect(Collectors.toUnmodifiableSet());
                    int finalElementId = elementId;
                    setList.stream()
                            .unordered()
                            .forEach((setId) -> elementsPerSet.get(setId).add(finalElementId));

                    elementId = UNASSIGNED;
                    setsForElement = UNASSIGNED;
                }
                lineCounter += 1;
            }
        }
    }
}
