package com.jsoft.jminas.events;

import com.jsoft.jminas.vo.CoordinatesVO;

/**
 * Created by joaquin on 10/12/16.
 */
public class ViewEvent {

    CoordinatesVO coordinatesVO;
    int bombs;
    String icon;

    public CoordinatesVO getCoordinatesVO() {
        return coordinatesVO;
    }

    public void setCoordinatesVO(CoordinatesVO coordinatesVO) {
        this.coordinatesVO = coordinatesVO;
    }

    public int getBombs() {
        return bombs;
    }

    public void setBombs(int bombs) {
        this.bombs = bombs;
    }
}
