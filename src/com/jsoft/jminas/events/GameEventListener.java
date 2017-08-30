package com.jsoft.jminas.events;

/**
 * Created by joaquin on 8/12/16.
 */
public interface GameEventListener {

    public void gameEnd(GameEvent event);

    public void newGame(GameEvent event);
}
