package setcovering.antcolony;

import isula.aco.AcoProblemSolver;
import isula.aco.algorithms.antsystem.OfflinePheromoneUpdate;
import isula.aco.algorithms.antsystem.PerformEvaporation;
import isula.aco.algorithms.antsystem.RandomNodeSelection;
import isula.aco.algorithms.antsystem.StartPheromoneMatrix;
import setcovering.antcolony.isula.AntColonyForSetCovering;
import setcovering.antcolony.isula.ConfigurationForSetCovering;
import setcovering.antcolony.isula.EnvironmentForSetCovering;

import javax.naming.ConfigurationException;
import java.io.IOException;
import java.util.List;

public class AcoSetCovering {
    public List<Integer> solve(String problemDataFile) throws ConfigurationException, IOException {
        EnvironmentForSetCovering environment = new EnvironmentForSetCovering(problemDataFile);
        ConfigurationForSetCovering configuration = new ConfigurationForSetCovering();
        AntColonyForSetCovering antColony = new AntColonyForSetCovering(configuration.getNumberOfAnts());

        AcoProblemSolver<Integer, EnvironmentForSetCovering> acoProblemSolver = new AcoProblemSolver<>();
        acoProblemSolver.initialize(environment, antColony, configuration);
        acoProblemSolver.addDaemonActions(new StartPheromoneMatrix<>());
        acoProblemSolver.addDaemonActions(new PerformEvaporation<>());
        acoProblemSolver.addDaemonActions(new OfflinePheromoneUpdate<>());

        acoProblemSolver.getAntColony().addAntPolicies(new RandomNodeSelection<>());

        acoProblemSolver.solveProblem();

        return acoProblemSolver.getBestSolution();
    }
}
