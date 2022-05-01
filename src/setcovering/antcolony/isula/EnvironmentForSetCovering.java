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

    private int numberOfCandidates;
    private int numberOfSamples;


    private final Map<Integer, Set<Integer>> samplesPerCandidate = new HashMap<>();

    public EnvironmentForSetCovering(String fileName) throws IOException {
        super();
        processDataFile(fileName);
        this.setPheromoneMatrix(this.createPheromoneMatrix());

    }

    @Override
    protected double[][] createPheromoneMatrix() {
        if (this.numberOfCandidates != 0) {
            return new double[this.numberOfCandidates][1];
        }

        return null;
    }

    public Set<Integer> getSamplesForCandidate(Integer candidateIndex) {
        return this.samplesPerCandidate.get(candidateIndex);
    }

    public Integer getNumberOfSamples() {
        return this.numberOfSamples;
    }

    public Map<Integer, Set<Integer>> getSamplesPerCandidate() {
        return samplesPerCandidate;
    }

    private void processDataFile(String fileName) throws IOException {
        int lineCounter = 0;
        int sampleIndex = UNASSIGNED;
        int candidatesForSample = UNASSIGNED;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");

                if (lineCounter == 0) {
                    this.numberOfSamples = Integer.parseInt(tokens[0]);
                    this.numberOfCandidates = Integer.parseInt(tokens[1]);
                    IntStream.range(0, numberOfCandidates)
                            .forEachOrdered((candidateIndex) -> samplesPerCandidate.put(
                                    candidateIndex, new HashSet<>()));

                } else if (sampleIndex == UNASSIGNED && tokens.length == 1) {
                    sampleIndex = Integer.parseInt(tokens[0]);
                } else if (sampleIndex != UNASSIGNED && candidatesForSample == UNASSIGNED && tokens.length == 1) {
                    candidatesForSample = Integer.parseInt(tokens[0]);
                } else if (sampleIndex != UNASSIGNED && candidatesForSample != UNASSIGNED) {

                    Set<Integer> candidateList = Arrays.stream(tokens)
                            .map(Integer::parseInt)
                            .collect(Collectors.toUnmodifiableSet());
                    int finalSampleIndex = sampleIndex;
                    candidateList.stream()
                            .unordered()
                            .forEach((candidateIndex) -> samplesPerCandidate.get(candidateIndex).add(finalSampleIndex));

                    sampleIndex = UNASSIGNED;
                    candidatesForSample = UNASSIGNED;
                }
                lineCounter += 1;
            }
        }
    }
}
