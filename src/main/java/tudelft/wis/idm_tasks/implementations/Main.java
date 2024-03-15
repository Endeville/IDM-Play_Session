package tudelft.wis.idm_tasks.implementations;
import tudelft.wis.idm_tasks.boardGameTracker.implementations.BgtDataManagerImplementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Manager m = new Manager();
        Connection c = m.getConnection();

        var bgt=new BgtDataManagerImplementation(c);
        bgt.setUp();
    }
}
