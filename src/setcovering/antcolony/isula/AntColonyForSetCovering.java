package setcovering.antcolony.isula;

import isula.aco.Ant;
import isula.aco.AntColony;

import java.util.Optional;
import java.util.Set;

public class AntColonyForSetCovering extends AntColony<Integer, EnvironmentForSetCovering> {
    public AntColonyForSetCovering(int numberOfAnts) {
        super(numberOfAnts);
    }

    @Override
    protected Ant<Integer, EnvironmentForSetCovering> createAnt(EnvironmentForSetCovering environment) {
        Set<Integer> allCandidates = environment.getSamplesPerCandidate().keySet();
        Optional<Integer> startingPoint = allCandidates.stream()
                .skip((int) (allCandidates.size() * Math.random()))
                .findFirst();

        return startingPoint
                .map(integer -> new AntForSetCovering(environment, integer))
                .orElse(null);

    }


}
