package tudelft.wis.idm_tasks.boardGameTracker.implementations;

import tudelft.wis.idm_tasks.boardGameTracker.BgtException;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BgtDataManager;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.PlaySession;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class BgtDataManagerImplementation implements BgtDataManager {

    private final Connection connection;

    public BgtDataManagerImplementation(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void setUp() throws SQLException {

        if (connection != null) {
            Statement stmt = connection.createStatement();

//            String createDatabase = """
//                    DROP DATABASE IF EXISTS bgt;
//                    CREATE DATABASE bgt; """;
//            stmt.execute(createDatabase);

//            String useStmt="bgt;";
//
//            stmt.execute(useStmt);


            String createBoardGameTable = """
                    CREATE TABLE if not exists BoardGame (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    bggURL VARCHAR(255) NOT NULL
                    )""";
            stmt.execute(createBoardGameTable);

            String createPlayerTable = """ 
                    CREATE TABLE if not exists Player (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    nickName VARCHAR(255),
                    FOREIGN KEY (id) REFERENCES BoardGame(id)
                    )""";
            stmt.execute(createPlayerTable);
        }
    }

    @Override
    public Player createNewPlayer(String name, String nickname) throws BgtException {
        try {
            var query = connection.prepareStatement("""
                    insert into players(name, nickname)
                    values(?, ?)
                    """);
            query.setString(1, name);
            query.setString(2, nickname);
            var result = query.executeQuery();
            result.next();
            return result.getObject(1, Player.class);
        } catch (SQLException e) {
            throw new BgtException();
        }
    }

    @Override
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        try {
            var query = connection.prepareStatement("""
                    select * from players p
                    where p.name=?
                    """);
            query.setString(1, name);
            var rs = query.executeQuery();
            var list=new ArrayList<Player>();

            while(rs.next()){
                list.add(rs.getObject(1, Player.class));
            }
            return list;

        } catch (SQLException e) {
            throw new BgtException();
        }
    }

    @Override
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        return null;
    }

    @Override
    public Collection<BoardGame> findGamesByName(String name) throws BgtException {
        return null;
    }

    @Override
    public PlaySession createNewPlaySession(Date date, Player host, BoardGame game, int playtime, Collection<Player> players, Player winner) throws BgtException {
        return null;
    }

    @Override
    public Collection<PlaySession> findSessionByDate(Date date) throws BgtException {
        return null;
    }

    @Override
    public void persistPlayer(Player player) {

    }

    @Override
    public void persistPlaySession(PlaySession session) {

    }

    @Override
    public void persistBoardGame(BoardGame game) {

    }
}
