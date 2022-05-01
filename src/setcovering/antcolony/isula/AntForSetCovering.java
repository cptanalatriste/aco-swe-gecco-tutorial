package setcovering.antcolony.isula;

import isula.aco.Ant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AntForSetCovering extends Ant<Integer, EnvironmentForSetCovering> {

    private final EnvironmentForSetCovering environment;
    private final Integer startingPoint;

    private boolean[] samplesCovered;


    public AntForSetCovering(EnvironmentForSetCovering environment, Integer startingPoint) {
        super();
        this.environment = environment;
        this.startingPoint = startingPoint;

        this.samplesCovered = new boolean[environment.getNumberOfSamples()];

        this.setSolution(new ArrayList<>());

    }

    @Override
    public void clear() {
        this.samplesCovered = new boolean[environment.getNumberOfSamples()];
        super.clear();
        this.visitNode(startingPoint, environment);
    }

    @Override
    public void visitNode(Integer candidateIndex, EnvironmentForSetCovering environment) {
        super.visitNode(candidateIndex, environment);
        Set<Integer> candidateSamples = environment.getSamplesPerCandidate().get(candidateIndex);
        candidateSamples.forEach((sampleIndex) -> this.samplesCovered[sampleIndex] = true);
    }

    @Override
    public boolean isSolutionReady(EnvironmentForSetCovering environmentForSetCovering) {
        Set<Integer> uncoveredSamples = this.getUncoveredSamples();
        int pendingSamples = uncoveredSamples.size();
        return pendingSamples == 0;
    }

    @Override
    public double getSolutionCost(EnvironmentForSetCovering environmentForSetCovering, List<Integer> solution) {
        return solution.size();
    }

    @Override
    public Double getHeuristicValue(Integer candidateIndex, Integer positionInSolution,
                                    EnvironmentForSetCovering environment) {
        Set<Integer> uncoveredSamples = this.getUncoveredSamples();
        Set<Integer> coveredByCandidate = environment.getSamplesForCandidate(candidateIndex);

        Set<Integer> commonElements = uncoveredSamples
                .stream()
                .unordered()
                .filter(coveredByCandidate::contains)
                .collect(Collectors.toSet());

        return commonElements.size() / (double) this.environment.getNumberOfSamples();
    }

    private Set<Integer> getUncoveredSamples() {
        return IntStream.range(0, this.environment.getNumberOfSamples())
                .filter(sampleIndex -> !this.samplesCovered[sampleIndex])
                .boxed()
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public List<Integer> getNeighbourhood(EnvironmentForSetCovering environment) {

        return environment.getSamplesPerCandidate().keySet()
                .stream()
                .filter(candidateIndex -> !isNodeVisited(candidateIndex))
                .collect(Collectors.toList());
    }

    @Override
    public Double getPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
                                         EnvironmentForSetCovering environment) {

        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        return pheromoneMatrix[solutionComponent][0];
    }

    @Override
    public void setPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution, EnvironmentForSetCovering
            environment, Double value) {
        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        pheromoneMatrix[solutionComponent][0] = value;

    }
}
