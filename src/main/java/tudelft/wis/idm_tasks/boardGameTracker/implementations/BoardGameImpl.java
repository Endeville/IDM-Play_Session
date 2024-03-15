package tudelft.wis.idm_tasks.boardGameTracker.implementations;

import tudelft.wis.idm_tasks.boardGameTracker.interfaces.BoardGame;
import tudelft.wis.idm_tasks.boardGameTracker.interfaces.Player;

public class BoardGameImpl implements BoardGame {
    private String name;
    private String BGG_URL;

    public BoardGameImpl(String name, String BGG_URL) {
        this.name = name;
        this.BGG_URL = BGG_URL;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBGG_URL() {
        return BGG_URL;
    }
    @Override
    public String toVerboseString() {
        return "BoardGameImpl{" +
                "name='" + name + '\'' +
                ", BGG_URL='" + BGG_URL +
                '}';
    }

}
