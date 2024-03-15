package tudelft.wis.idm_tasks.boardGameTracker.implementations;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

public class BoardGameImpl implements BoardGame {
    private String name;
    private String BGG_URL;
    private Player player;

    public BoardGameImpl(String name, String BGG_URL,Player player) {
        this.name = name;
        this.BGG_URL = BGG_URL;
        this.player = player;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBGG_URL() {
        return BGG_URL;
    }

    public Player getPlayer() {
        return player;
    }
    @Override
    public String toVerboseString() {
        return "BoardGameImpl{" +
                "name='" + name + '\'' +
                ", BGG_URL='" + BGG_URL + '\'' +
                ", player=" + player.toVerboseString()+
                '}';
    }

}
