package setcovering.antcolony.isula;

import isula.aco.algorithms.antsystem.AntSystemConfigurationProvider;

public class ConfigurationForSetCovering implements AntSystemConfigurationProvider {
    @Override
    public double getPheromoneDepositFactor() {
        return 1.0;
    }

    @Override
    public int getNumberOfAnts() {
        return 5;
    }

    @Override
    public double getEvaporationRatio() {
        return 0.2;
    }

    @Override
    public int getNumberOfIterations() {
        return 30;
    }

    @Override
    public double getInitialPheromoneValue() {
        return 1.0;
    }

    @Override
    public double getHeuristicImportance() {
        return 1.5;
    }

    @Override
    public double getPheromoneImportance() {
        return 4;
    }
}
