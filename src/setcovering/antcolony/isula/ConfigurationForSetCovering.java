package setcovering.antcolony.isula;

import isula.aco.algorithms.antsystem.AntSystemConfigurationProvider;

public class ConfigurationForSetCovering implements AntSystemConfigurationProvider {
    @Override
    public double getPheromoneDepositFactor() {
        return 0;
    }

    @Override
    public int getNumberOfAnts() {
        return 0;
    }

    @Override
    public double getEvaporationRatio() {
        return 0;
    }

    @Override
    public int getNumberOfIterations() {
        return 0;
    }

    @Override
    public double getInitialPheromoneValue() {
        return 0;
    }

    @Override
    public double getHeuristicImportance() {
        return 0;
    }

    @Override
    public double getPheromoneImportance() {
        return 0;
    }
}
