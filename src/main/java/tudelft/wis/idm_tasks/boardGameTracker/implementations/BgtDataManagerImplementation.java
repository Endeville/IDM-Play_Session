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
                    name VARCHAR(255) unique NOT NULL,
                    bggURL VARCHAR(255) NOT NULL
                    )""";
            stmt.execute(createBoardGameTable);

            String createPlayerTable = """ 
                    CREATE TABLE if not exists Player (
                    name VARCHAR(255) NOT NULL,
                    nickName VARCHAR(255) unique
                    )""";
            stmt.execute(createPlayerTable);

            String createPlayerBoardGameConnectionTable = """
                    CREATE TABLE IF NOT EXISTS Player_BoardGame (
                                                  player_nickname VARCHAR(255),
                                                  boardgame_name VARCHAR(255),
                                                  FOREIGN KEY (player_nickname) REFERENCES Player(nickname),
                                                  FOREIGN KEY (boardgame_name) REFERENCES BoardGame(name),
                                                  PRIMARY KEY (player_nickname, boardgame_name)
                    )""";
            stmt.execute(createPlayerBoardGameConnectionTable);
        }
    }

    @Override
    public void drop() throws SQLException {
        Statement stmt = connection.createStatement();
        String drop = """
        drop table if exists player_boardgame;
        drop table if exists boardgame;
        drop table if exists player;
        """;
        stmt.execute(drop);
    }

    @Override
    public Player createNewPlayer(String name, String nickname) throws BgtException {
        try {
            var query = connection.prepareStatement("""
                    insert into player(name, nickname)
                    values(?, ?)
                    """);
            query.setString(1, name);
            query.setString(2, nickname);
            query.executeUpdate();
            return new PlayerImpl(name, nickname, new ArrayList<>());
        } catch (SQLException e) {

            throw new BgtException();
        }
    }

    @Override
    public Collection<Player> findPlayersByName(String name) throws BgtException {
        try {
            var query = connection.prepareStatement("""
                    select * from player p
                    where p.name=?
                    """);
            query.setString(1, name);
            var rs = query.executeQuery();
            var list=new ArrayList<Player>();

            while(rs.next()){
                String name1 = rs.getString(1);
                String nickname = rs.getString(2);
                Collection boardGames = new ArrayList<BoardGame>();
                var query2 = connection.prepareStatement("""
                        select * from player_boardgame pb
                        where pb.player_nickname=?
                        """);
                query2.setString(1, nickname);
                var rs2 = query2.executeQuery();
                while(rs2.next()){
                    String gameName = rs2.getString(2);
                    var query3 = connection.prepareStatement("""
                            select * from boardgame bg
                            where bg.name=?
                            """);
                    query3.setString(1, gameName);
                    var rs3 = query3.executeQuery();
                    while(rs3.next()){
                        boardGames.add(new BoardGameImpl(rs3.getString(1), rs3.getString(2)));
                    }
                }

                list.add(new PlayerImpl(name1, nickname, boardGames));
            }
            return list;

        } catch (SQLException e) {
            throw new BgtException();
        }
    }

    @Override
    public BoardGame createNewBoardgame(String name, String bggURL) throws BgtException {
        try {
            var query= connection.prepareStatement("""
                insert into boardgame(name, bggURL)
                values(?, ?)
                """);
            query.setString(1, name);
            query.setString(2, bggURL);
            query.executeUpdate();
            return new BoardGameImpl(name, bggURL);
        } catch (SQLException throwables) {
            throw new BgtException();
        }
    }

    @Override
    public Collection<BoardGame> findGamesByName(String name) throws BgtException {
        try {
            var query = connection.prepareStatement("""
                    select * from boardgame bg
                    where bg.name=?
                    """);
            query.setString(1, name);
            var rs = query.executeQuery();
            var list=new ArrayList<BoardGame>();

            while(rs.next()){
                String name1 = rs.getString(1);
                String bggURL = rs.getString(2);
                list.add(new BoardGameImpl(name1, bggURL));


            }
            return list;

        } catch (SQLException e) {
            throw new BgtException();
        }
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
    public void persistPlayer(Player player) throws BgtException {
        try {
//            var query = connection.prepareStatement("""
//                    insert into player(name, nickname)
//                    values(?, ?)
//                    """);
//            query.setString(1, player.getPlayerName());
//            query.setString(2, player.getPlayerNickName());
//            query.executeUpdate();
//
//            var query2 = connection.prepareStatement("""
//                    insert into player_boardgame(player_id, boardgame_name)
//                    values (?, ?)
//                    """);
//            query2.setString(1, player.getPlayerNickName());
//            for (BoardGame boardGame : player.getGameCollection()) {
//                query2.setString(2, boardGame.getName());
//                query2.executeUpdate();
//            }
            // delete all the games from the player
            var query = connection.prepareStatement("""
                    delete from player_boardgame
                    where player_nickname=?
                    """);
            query.setString(1, player.getPlayerNickName());
            query.executeUpdate();

            // udpate the player
            var query2 = connection.prepareStatement("""
                    update player
                    set name=?
                    where nickname=?
                    """);
            query2.setString(1, player.getPlayerName());
            query2.setString(2, player.getPlayerNickName());
            query2.executeUpdate();

            // insert the games again
            var query3 = connection.prepareStatement("""
                    insert into player_boardgame(player_nickname, boardgame_name)
                    values (?, ?)
                    """);
            query3.setString(1, player.getPlayerNickName());
            for (BoardGame boardGame : player.getGameCollection()) {
                query3.setString(2, boardGame.getName());
                query3.executeUpdate();
            }

        } catch (SQLException e) {
            throw new BgtException();
        }
    }

    @Override
    public void persistPlaySession(PlaySession session) {

    }

    @Override
    public void persistBoardGame(BoardGame game) throws BgtException {
        try {
            var query = connection.prepareStatement("""
                    insert into boardgame(name, bggURL)
                    values(?, ?)
                    """);
            query.setString(1, game.getName());
            query.setString(2, game.getBGG_URL());
            query.executeQuery();
        } catch (SQLException e) {
            throw new BgtException();
        }
    }
}
