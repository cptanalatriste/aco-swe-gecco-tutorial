package setcovering.antcolony;

import javax.naming.ConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws ConfigurationException, IOException {

        AcoSetCovering solver = new AcoSetCovering();
        String problemData = "data/AC_01_cover.txt";
        solver.solve(problemData);
    }
}
