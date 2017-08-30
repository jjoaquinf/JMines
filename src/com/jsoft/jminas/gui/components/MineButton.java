package com.jsoft.jminas.gui.components;

import com.jsoft.jminas.vo.CoordinatesVO;

import javax.swing.*;
import java.awt.*;

/**
 * Created by joaquin on 9/12/16.
 */
public class MineButton extends JButton {

    public CoordinatesVO coordinatesVO;

    public MineButton(String text) {
        super(text);
        setBackground(Color.gray);
    }

    public CoordinatesVO getCoordinatesVO() {
        return coordinatesVO;
    }

    public void setCoordinatesVO(CoordinatesVO coordinatesVO) {
        this.coordinatesVO = coordinatesVO;
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);

    }
}
