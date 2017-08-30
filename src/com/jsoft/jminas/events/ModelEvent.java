package com.jsoft.jminas.events;

import java.awt.AWTEvent;

import com.jsoft.jminas.vo.CoordinatesVO;

public class ModelEvent extends AWTEvent {

	int bombs;
	CoordinatesVO coords;

	public ModelEvent(Object source, CoordinatesVO coords, int bombs){
		super(source, 0);
		this.bombs = bombs;
		this.coords = coords;
	}

	public CoordinatesVO getCoords() {
		return coords;
	}

	public void setCoords(CoordinatesVO coords) {
		this.coords = coords;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	
	
}
