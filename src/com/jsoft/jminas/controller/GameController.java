package com.jsoft.jminas.controller;

import com.jsoft.jminas.events.*;
import com.jsoft.jminas.model.JMinasModel;
import com.jsoft.jminas.vo.CoordinatesVO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joaquin on 9/12/16.
 */
public class GameController implements ModelEventListener, ViewEventListener {

    JMinasModel model;


    private List<ViewOrderListener> viewOrderListenerList;
    private List<GameEventListener> gameEventListenerList;


    public GameController() {
        viewOrderListenerList = new ArrayList<ViewOrderListener>();
        gameEventListenerList = new ArrayList<GameEventListener>();
    }

    @Override
    public void bombClicked(ModelEvent pe) {
        List<CoordinatesVO> bombs = model.getBombsPosition();
        for (CoordinatesVO coordinatesVO:bombs) {
            ViewOrderEvent event = new ViewOrderEvent();
            event.setCoordinatesVO(coordinatesVO);
            event.setIcon("/com/jsoft/jminas/gui/resources/mine.png");
            fireChangeButtonStatus(event);
        }
        fireGameEnd(new GameEvent());
    }

    @Override
    public void bombMarked(ModelEvent pe) {
        ViewOrderEvent event = new ViewOrderEvent(pe);
        event.setIcon("/com/jsoft/jminas/gui/resources/flag.png");
        fireChangeButtonStatus(event);
    }

    @Override
    public void emptyCellClicked(ModelEvent pe) {
        CoordinatesVO coords = pe.getCoords();
        ViewOrderEvent event = new ViewOrderEvent(pe);
        if (model.getNeighbourBombs(coords) == 0)
            clearEmptyCells(coords);
        else
            fireDisableButton(event);
    }


    public void clearEmptyCells(CoordinatesVO coords) {
        int row = coords.getRow();
        int col = coords.getCol();
        if (    row < 0 || row >= model.getHeight()
                || col < 0 || col >= model.getWidth()
                || model.isCellVisited(coords)
                )
            return;
        model.marCellVisited(coords);
        ViewOrderEvent event = new ViewOrderEvent();
        event.setCoordinatesVO(coords);
        int bombs = model.getNeighbourBombs(coords);
        event.setText(String.valueOf(bombs));
        fireDisableButton(event);
        if ( bombs== 0) {
            clearEmptyCells(new CoordinatesVO(row-1, col));
            clearEmptyCells(new CoordinatesVO(row, col-1));
            clearEmptyCells(new CoordinatesVO(row, col+1));
            clearEmptyCells(new CoordinatesVO(row+1, col));
        }
    }

    @Override
    public void allBombMarked(ModelEvent pe) {
        GameEvent event = new GameEvent();
        event.setWins(true);
        fireGameEnd(event);

    }

    @Override
    public void buttonLeftClicked(CoordinatesVO coordinatesVO) {
        model.discoverCell(coordinatesVO);

    }

    @Override
    public void buttonRightClicked(CoordinatesVO coordinatesVO) {
        model.markCell(coordinatesVO);
    }



    public JMinasModel getModel() {
        return model;
    }

    public void setModel(JMinasModel model) {
        this.model = model;
    }

    public void addViewOrderListener(ViewOrderListener listener) {
        viewOrderListenerList.add(listener);
    }

    public void removeViewOrderListener(ViewOrderListener listener) {
        viewOrderListenerList.remove(listener);
    }

    public void fireDisableButton(ViewOrderEvent event) {
        for (ViewOrderListener listener:viewOrderListenerList)
            listener.disableButton(event);
    }

    public void fireChangeButtonStatus(ViewOrderEvent event) {
        for (ViewOrderListener listener:viewOrderListenerList)
            listener.changeButtonStatus(event);

    }

    public void addGameEventListener(GameEventListener listener) {
        gameEventListenerList.add(listener);
    }

    public void removeGameEventListener(GameEventListener listener) {
        gameEventListenerList.remove(listener);

    }

    public void fireGameBegin() {
        for (GameEventListener listener:gameEventListenerList)
            listener.newGame(new GameEvent());

    }

    public void fireGameEnd(GameEvent event) {
        for (GameEventListener listener:gameEventListenerList)
            listener.gameEnd(new GameEvent());
    }
}
