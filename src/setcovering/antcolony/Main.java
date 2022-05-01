package setcovering.antcolony;

import javax.naming.ConfigurationException;

public class Main {

    public static void main(String... args) throws ConfigurationException {
        System.out.println("Holitas");

        AntColonySetCovering solver = new AntColonySetCovering();
        String problemData = "data/AC_10_cover.txt";
        solver.solve(problemData);
    }
}
