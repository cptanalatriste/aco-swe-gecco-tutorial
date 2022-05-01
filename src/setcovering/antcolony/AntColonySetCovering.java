package setcovering.antcolony;

import isula.aco.AcoProblemSolver;
import setcovering.antcolony.isula.AntColonyForSetCovering;
import setcovering.antcolony.isula.ConfigurationForSetCovering;
import setcovering.antcolony.isula.EnvironmentForSetCovering;

import javax.naming.ConfigurationException;
import java.util.List;

public class AntColonySetCovering {
    public List<Integer> solve(String problemData) throws ConfigurationException {
        EnvironmentForSetCovering environment = new EnvironmentForSetCovering();
        ConfigurationForSetCovering configuration = new ConfigurationForSetCovering();
        AntColonyForSetCovering antColony = new AntColonyForSetCovering(configuration.getNumberOfAnts());

        AcoProblemSolver<Integer, EnvironmentForSetCovering> acoProblemSolver = new AcoProblemSolver<>();
        acoProblemSolver.initialize(environment, antColony, configuration);

        acoProblemSolver.solveProblem();

        return acoProblemSolver.getBestSolution();
    }
}
