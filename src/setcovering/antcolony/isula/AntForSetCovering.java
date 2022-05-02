package setcovering.antcolony.isula;

import isula.aco.Ant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AntForSetCovering extends Ant<Integer, EnvironmentForSetCovering> {

    private final EnvironmentForSetCovering environment;
    private final Integer initialSet;

    private boolean[] elementsCovered;


    public AntForSetCovering(EnvironmentForSetCovering environment, Integer initialSet) {
        super();
        this.environment = environment;
        this.initialSet = initialSet;

        this.elementsCovered = new boolean[environment.getNumberOfElements()];

        this.setSolution(new ArrayList<>());

    }

    @Override
    public void clear() {
        this.elementsCovered = new boolean[environment.getNumberOfElements()];
        super.clear();
        this.visitNode(initialSet, environment);
    }

    @Override
    public void visitNode(Integer setId, EnvironmentForSetCovering environment) {
        super.visitNode(setId, environment);
        Set<Integer> elementsInSet = environment.getElementsPerSet().get(setId);
        elementsInSet.forEach((elementId) -> this.elementsCovered[elementId] = true);
    }

    @Override
    public boolean isSolutionReady(EnvironmentForSetCovering environmentForSetCovering) {
        Set<Integer> uncoveredElements = this.getUncoveredElements();
        int pendingElements = uncoveredElements.size();
        return pendingElements == 0;
    }

    @Override
    public double getSolutionCost(EnvironmentForSetCovering environmentForSetCovering, List<Integer> solution) {
        return solution.size();
    }

    @Override
    public Double getHeuristicValue(Integer setId, Integer positionInSolution,
                                    EnvironmentForSetCovering environment) {
        Set<Integer> uncoveredElements = this.getUncoveredElements();
        Set<Integer> coveredBySet = environment.getElementsCovered(setId);

        Set<Integer> commonElements = uncoveredElements
                .stream()
                .unordered()
                .filter(coveredBySet::contains)
                .collect(Collectors.toSet());

        return commonElements.size() / (double) this.environment.getNumberOfElements();
    }

    private Set<Integer> getUncoveredElements() {
        return IntStream.range(0, this.environment.getNumberOfElements())
                .filter(elementId -> !this.elementsCovered[elementId])
                .boxed()
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public List<Integer> getNeighbourhood(EnvironmentForSetCovering environment) {

        return environment.getElementsPerSet().keySet()
                .stream()
                .filter(setId -> !isNodeVisited(setId))
                .collect(Collectors.toList());
    }

    @Override
    public Double getPheromoneTrailValue(Integer setId, Integer positionInSolution,
                                         EnvironmentForSetCovering environment) {

        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        return pheromoneMatrix[setId][0];
    }

    @Override
    public void setPheromoneTrailValue(Integer setId, Integer positionInSolution, EnvironmentForSetCovering
            environment, Double value) {
        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        pheromoneMatrix[setId][0] = value;

    }
}
