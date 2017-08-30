package com.jsoft.jminas.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import com.jsoft.jminas.controller.GameController;
import com.jsoft.jminas.events.GameEvent;
import com.jsoft.jminas.events.GameEventListener;
import com.jsoft.jminas.events.ModelEvent;
import com.jsoft.jminas.events.ModelEventListener;
import com.jsoft.jminas.helper.SettingsFactory;
import com.jsoft.jminas.model.JMinasModel;
import com.jsoft.jminas.vo.SettingsVO;




public class MainWindow extends JFrame implements ActionListener, Runnable, GameEventListener {

	private JPanel mainPanel;
	private JPanel gamePanel;
	private JPanel statusBar;
	private JMinasModel model;
	private MouseAdapter controller;
	private boolean inGameFlag;
	private SettingsVO settings;
	private JLabel statusLabel;
	private JLabel timeClock;
	private long startTime;
	private Thread clock;
	private boolean flagClock = true;

	private static final String EXIT_LABEL = "SALIR";

	private GameController gameController;

	private void buildMenuPanel() {
		gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(4, 1));
		JButton facil = new JButton("Fácil");
		facil.setName(SettingsFactory.SETTINGS_LABEL_FACIL);
		facil.addActionListener(this);
		JButton medio = new JButton("Medio");
		medio.setName(SettingsFactory.SETTINGS_LABEL_MEDIO);
		medio.addActionListener(this);
		JButton dificil = new JButton("Difícil");
		dificil.setName(SettingsFactory.SETTINGS_LABEL_DIFICIL);
		dificil.addActionListener(this);
		JButton salir = new JButton("Salir");
		salir.setName(EXIT_LABEL);
		salir.addActionListener(this);
		gamePanel.add(facil);
		gamePanel.add(medio);
		gamePanel.add(dificil);
		gamePanel.add(salir);
	}

	public void start() {
		startTime = System.currentTimeMillis();
		if (clock == null)
			clock = new Thread(this);
		clock.start();
		// method to start thread
	}

	public void run() {
		while (clock == Thread.currentThread() && flagClock) {
			repaint();
			timeClock.setText(new SimpleDateFormat("mm:ss").format(new Date(System.currentTimeMillis() - startTime)));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Thread failed");
			}

		}
		clock = null;
	}

	public MainWindow() {
		super();
		inGameFlag = false;
//		setModel(new JMinasModel());
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		this.add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();
		UIManager.put("Button.disabled", Color.GREEN);
		setVisible(true);
	}

	public void rebuildGUI() {
		clock = null;
		buildGUI();
	}

	public void buildGUI() {

		if (inGameFlag) {
			mainPanel.remove(gamePanel);
			gamePanel = null;
			BoardPanel boardPanel = new BoardPanel(settings);
			boardPanel.setModel(getModel());
			gamePanel = boardPanel;
			mainPanel.add(gamePanel, BorderLayout.CENTER);
//			statusLabel.setText("Tiempo: ");
			timeClock.setText("00:00:00");
			statusBar.add(timeClock, BorderLayout.EAST);
			boardPanel.addViewEventListener(gameController);
			gameController.addViewOrderListener(boardPanel);
			start();
		} else {
			if (gamePanel != null)
				mainPanel.remove(gamePanel);
			gamePanel = null;
			buildMenuPanel();
			mainPanel.add(gamePanel, BorderLayout.CENTER);
		}
		if (statusBar == null) {
			statusBar = new JPanel(new BorderLayout());
			statusLabel = new JLabel("Seleccione una opci�n");
			statusBar.add(statusLabel, BorderLayout.WEST);
			mainPanel.add(statusBar, BorderLayout.SOUTH);
			timeClock = new JLabel("00:00:00");
		}
		this.pack();
	}

	public void setModel(JMinasModel model) {
		this.model = model;
		// debug
		model.print();
	}

	public JMinasModel getModel() {
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String name = ((JButton) e.getSource()).getName();
		if (name.equals(EXIT_LABEL))
			this.dispose();
		else {
			settings = SettingsFactory.getSettings(name);
			setModel(new JMinasModel(settings.getRows(), settings.getCols(), settings.getBombs()));
			buildGameController();
			inGameFlag = true;
			buildGUI();
		}
	}

	private void buildGameController() {
		gameController = new GameController();
		// el gameController escucha los eventos del modelo..
		getModel().addModelEventListener(gameController);
		gameController.setModel(getModel());
		gameController.addGameEventListener(this);
	}

	@Override
	public void gameEnd(GameEvent event) {
		if (!event.isWins()) {
			System.out.println("BOOOM!!!");
			JOptionPane.showMessageDialog(this, "Fin del juego ...");
		}
		inGameFlag = false;
		rebuildGUI();

 	}

	@Override
	public void newGame(GameEvent event) {

	}

}
