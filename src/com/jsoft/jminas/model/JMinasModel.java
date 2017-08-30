package com.jsoft.jminas.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.jsoft.jminas.events.ModelEvent;
import com.jsoft.jminas.events.ModelEventListener;
import com.jsoft.jminas.vo.CoordinatesVO;

public class JMinasModel {

	private int width, height, bombs, nroMarkedBombs;
	
	private int mineField[][];

	private char markedBombsMap[][];

	private ArrayList<ModelEventListener> modelEventListeners;

	public static final char MARKED_BOMB = 'M';
	public static final char VISITED_CELL = 'V';
	public static final char EMPTY_CELL = 'E';




	
	private void buildModel(){
		for (int row = 0; row < mineField.length; row++) {
			for (int col = 0; col < mineField[0].length; col++) {
				mineField[row][col] = 0;
				markedBombsMap[row][col] = EMPTY_CELL;
			}
			
		}
		Random distribucion = new Random(System.currentTimeMillis());
		for (int i = 0; i < bombs; i++) {
			int row = distribucion.nextInt(width - 1);
			int col = distribucion.nextInt(height - 1);
			if (isEmptyCell(row, col))
				mineField[row][col] = 1;
		}
	}
	
	public JMinasModel() {
		this(10, 10, 10);
	}
	
	public JMinasModel(int width, int height, int bombs) {
		super();
		this.width = width;
		this.height = height;
		this.bombs = bombs;
		nroMarkedBombs = 0;
		mineField = new int[height][width];
		markedBombsMap = new char[height][width];
		modelEventListeners = new ArrayList<ModelEventListener>();
		buildModel();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public boolean isBombCell(int row, int col) {
		return mineField[row][col] != 0;
	}

	public boolean isEmptyCell(int row, int col) {
		return mineField[row][col] == 0;
	}

	public List<CoordinatesVO> getBombsPosition() {
		ArrayList bombsList = new ArrayList();
		for (int row = 0; row < height; row ++)
			for (int col = 0; col < width; col++)
				if (isBombCell(row, col))
					bombsList.add(new CoordinatesVO(row, col));
		return bombsList;
	}

	public int getNeighbourBombs(CoordinatesVO coordinatesVO) {
		int nroBombs = 0;
		for (int row = coordinatesVO.getRow() -1 ; row <= coordinatesVO.getRow() + 1; row ++) {
			if (row < 0 || row >= height)
				continue;
			for (int col = coordinatesVO.getCol() -1 ; col <= coordinatesVO.getCol() +1; col++) {
				if (col < 0 || col >= width)
					continue;
				nroBombs += mineField[row][col];
			}
		}
		return nroBombs;
	}

	public void markCell(CoordinatesVO coords) {
		if (isBombCell(coords.getRow(), coords.getCol())) {
			if (markedBombsMap[coords.getRow()][coords.getCol()] == EMPTY_CELL) {
				markedBombsMap[coords.getRow()][coords.getCol()] = MARKED_BOMB;
				nroMarkedBombs++;
				// Condición de victoria ...
				if (nroMarkedBombs == bombs) {
					fireAllBombMarkedEvent(coords);
					return;
				}
			}
		}
		fireBombMarkedEvent(coords);
	}

	public void marCellVisited(CoordinatesVO coords) {
		markedBombsMap[coords.getRow()][coords.getCol()] = VISITED_CELL;
	}

	public boolean isCellVisited(CoordinatesVO coords) {
		return markedBombsMap[coords.getRow()][coords.getCol()] == VISITED_CELL;
	}


	public void discoverCell(CoordinatesVO coords) {
		if (isEmptyCell(coords.getRow(), coords.getCol()))
			fireEmptyCellClickedEvent(coords, getNeighbourBombs(coords));
		else
			fireBombClickedEvent(coords);
	}

	public void addModelEventListener(ModelEventListener listener){
		modelEventListeners.add(listener);
	}

	public void removeModelEventListener(ModelEventListener listener) {
		modelEventListeners.remove(listener);
	}

	/*
	 *
	 *  EN ESTA SECCION SE DISPARAN LOS EVENTOS ....
	 *
	 */

	private void fireBombClickedEvent(CoordinatesVO coords) {
		for (ModelEventListener listener : modelEventListeners) {
			ModelEvent event = new ModelEvent(this, coords, 0);
			listener.bombClicked(event);
		}
	}

	private void fireBombMarkedEvent(CoordinatesVO coords) {
		for (ModelEventListener listener : modelEventListeners) {
			ModelEvent event = new ModelEvent(this, coords, 0);
			listener.bombMarked(event);
		}
	}

	private void fireAllBombMarkedEvent(CoordinatesVO coords) {
		for (ModelEventListener listener : modelEventListeners) {
			ModelEvent event = new ModelEvent(this, coords, 0);
			listener.allBombMarked(event);
		}
	}

	private void fireEmptyCellClickedEvent(CoordinatesVO coords, int bombs) {
		for (ModelEventListener listener : modelEventListeners) {
			ModelEvent event = new ModelEvent(this, coords, bombs);
			listener.emptyCellClicked(event);
		}
	}


	public void print() {
		System.out.println(String.format("Width [%d], Height [%d] Bombs [%d]", width, height, bombs));
		for (int i = 0; i < height; i++) {
			System.out.println(String.format("[%s]", Arrays.toString(mineField[i])));
		}
	}
}
