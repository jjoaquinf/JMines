package com.jsoft.jminas.vo;

public class SettingsVO {

	int rows, cols, bombs;

	public SettingsVO() {
		super();
	}
	
	public SettingsVO(int rows, int cols, int bombs) {
		this();
		setRows(rows);
		setCols(cols);
		setBombs(bombs);
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}
	
}
