package com.jsoft.jminas.gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

import com.jsoft.jminas.events.*;
import com.jsoft.jminas.gui.components.MineButton;
import com.jsoft.jminas.helper.CoordinateHelper;
import com.jsoft.jminas.model.JMinasModel;
import com.jsoft.jminas.vo.CoordinatesVO;
import com.jsoft.jminas.vo.SettingsVO;

public class BoardPanel extends JPanel implements ViewOrderListener {

	SettingsVO settings;
	private MouseAdapter mouseAdapter;
	private MineButton [][] buttonMap;
	private JMinasModel model;

	private ArrayList<ViewEventListener> viewEventListenersList;


	private enum ActionType {
		MARK_CELL,
		DISCOVER_CELL;
	}

	private class GuiEvent implements Runnable {

		private final CoordinatesVO coordinatesVO;
		private final ActionType actionType;

		public GuiEvent(CoordinatesVO coordinatesVO, ActionType action) {
			this.coordinatesVO = coordinatesVO;
			this.actionType = action;
		}

		@Override
		public void run() {
			if (ActionType.MARK_CELL == actionType)
				model.markCell(coordinatesVO);
			else
				model.discoverCell(coordinatesVO);
		}
	}

	private void buildController() {
		mouseAdapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.out.println("Hola");
				// Capturamos el boton derecho
				MineButton button = (MineButton)e.getSource();
				if (e.getButton() == MouseEvent.BUTTON3) {
					System.out.println("Boton Derecho ...");
//					model.markCell(CoordinateHelper.getCoordinates(((JButton)e.getSource()).getName()));
//					SwingUtilities.invokeLater(new GuiEvent(button.getCoordinatesVO(), ActionType.MARK_CELL));
//					model.markCell(((MineButton)e.getSource()).getCoordinatesVO());
					fireButtonRightClicked(button.getCoordinatesVO());
				} else {
					// Suponemos que es el boton izquiedo
//					SwingUtilities.invokeLater(new GuiEvent(button.getCoordinatesVO(), ActionType.DISCOVER_CELL));
					fireButtonLeftClicked(button.getCoordinatesVO());
				}
			}
		};
	}

	private void buildLayout() {
		buttonMap = new MineButton[settings.getRows()][settings.getCols()];
		for (int rows = 0; rows < settings.getRows(); rows++) {
			for (int cols = 0; cols < settings.getCols(); cols++) {
				MineButton cell = new MineButton(" ");
				cell.setName(rows+","+cols);
				cell.setPreferredSize(new Dimension(30,30));
				cell.addMouseListener(mouseAdapter);
				cell.setCoordinatesVO(new CoordinatesVO(rows, cols));
				buttonMap[rows][cols] = cell;
				add(cell);
			}
		}
	}

	public BoardPanel(SettingsVO settings) {
		super(new GridLayout(settings.getRows(), settings.getCols()));
		this.settings = settings;
		buildController();
		buildLayout();
		viewEventListenersList = new ArrayList<ViewEventListener>();
	}


	public void setModel(JMinasModel model) {
		this.model = model;
	}

	public JMinasModel getModel() {
		return model;
	}


	public void addViewEventListener(ViewEventListener listener) {
		viewEventListenersList.add(listener);
	}

	public void removeViewEventListener(ViewEventListener listener) {
		viewEventListenersList.remove(listener);
	}

	public void fireButtonLeftClicked(CoordinatesVO coordinatesVO) {
		for (ViewEventListener listener: viewEventListenersList) {
			listener.buttonLeftClicked(coordinatesVO);
		}
	}

	public void fireButtonRightClicked(CoordinatesVO coordinatesVO) {
		for (ViewEventListener listener: viewEventListenersList) {
			listener.buttonRightClicked(coordinatesVO);
		}
	}


	@Override
	public void disableButton(ViewOrderEvent order) {
		CoordinatesVO coords = order.getCoordinatesVO();
		String bombs = order.getText();
		JButton button = buttonMap[coords.getRow()][coords.getCol()];
		button.setText(bombs);
		button.setEnabled(false);
	}

	@Override
	public void changeButtonStatus(ViewOrderEvent order) {
		CoordinatesVO coords = order.getCoordinatesVO();
		String bombs = order.getText();
		MineButton button = buttonMap[coords.getRow()][coords.getCol()];
		button.setText("");
		ImageIcon icon = new ImageIcon(this.getClass().getResource(order.getIcon()));
		button.setIcon(icon);
	}
}
