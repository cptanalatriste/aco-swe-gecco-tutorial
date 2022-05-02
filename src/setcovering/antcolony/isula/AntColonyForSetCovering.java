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
        Set<Integer> allSets = environment.getElementsPerSet().keySet();
        Optional<Integer> initialSet = allSets.stream()
                .skip((int) (allSets.size() * Math.random()))
                .findFirst();

        return initialSet
                .map(setId -> new AntForSetCovering(environment, setId))
                .orElse(null);

    }


}
