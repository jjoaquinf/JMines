package com.jsoft.jminas.events;

import com.jsoft.jminas.helper.SettingsFactory;

/**
 * Created by joaquin on 8/12/16.
 */
public class GameEvent {

    int time;
    boolean record;
    boolean wins;
    SettingsFactory settingsFactory;

    public GameEvent() {

    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isRecord() {
        return record;
    }

    public void setRecord(boolean record) {
        this.record = record;
    }

    public boolean isWins() {
        return wins;
    }

    public void setWins(boolean wins) {
        this.wins = wins;
    }

    public SettingsFactory getSettingsFactory() {
        return settingsFactory;
    }

    public void setSettingsFactory(SettingsFactory settingsFactory) {
        this.settingsFactory = settingsFactory;
    }
}
