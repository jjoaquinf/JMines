package com.jsoft.jminas.vo;

public class CoordinatesVO {
	int row, col;
	
	public CoordinatesVO() {
		super();
	}
	
	public CoordinatesVO(int row, int col) {
		this();
		setRow(row);
		setCol(col);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
