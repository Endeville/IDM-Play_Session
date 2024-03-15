package tudelft.wis.idm_tasks.boardGameTracker.implementations;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

import java.util.Collection;

public class PlayerImpl implements Player {
    private String playerName;
    private String playerNickName;
    private Collection<BoardGame> gameCollection;

    public PlayerImpl(String playerName, String playerNickName, Collection<BoardGame> gameCollection) {
        this.playerName = playerName;
        this.playerNickName = playerNickName;
        this.gameCollection = gameCollection;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String getPlayerNickName() {
        return playerNickName;
    }

    @Override
    public Collection<BoardGame> getGameCollection() {
        return gameCollection;
    }

    @Override
    public String toVerboseString() {
        return "PlayerImpl{" +
                "playerName='" + playerName + '\'' +
                ", playerNickName='" + playerNickName + '\'' +
                '}';
    }
}
