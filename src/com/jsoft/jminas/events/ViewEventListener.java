package com.jsoft.jminas.events;

import com.jsoft.jminas.vo.CoordinatesVO;

/**
 * Created by joaquin on 9/12/16.
 */
public interface ViewEventListener {

    public void buttonLeftClicked(CoordinatesVO coordinatesVO);

    public void buttonRightClicked(CoordinatesVO coordinatesVO);
}
