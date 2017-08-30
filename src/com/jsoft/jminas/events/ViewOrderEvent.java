package com.jsoft.jminas.events;

import com.jsoft.jminas.vo.CoordinatesVO;

import javax.swing.text.View;

/**
 * Created by joaquin on 9/12/16.
 */
public class ViewOrderEvent {

    CoordinatesVO coordinatesVO;
    String text;
    String icon;

    public ViewOrderEvent() {

    }

    public ViewOrderEvent(ModelEvent event) {
        setCoordinatesVO(event.getCoords());
        setText(event.getBombs() == 0 ? "" : String.valueOf(event.getBombs()));
    }

    public CoordinatesVO getCoordinatesVO() {
        return coordinatesVO;
    }

    public void setCoordinatesVO(CoordinatesVO coordinatesVO) {
        this.coordinatesVO = coordinatesVO;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

//    public static ViewOrderBuilder getViewOrder() {
//        return new ViewOrderBuilder();
//    }
//
//    public static class ViewOrderBuilder {
//
//        ViewOrderEvent viewOrder;
//
//        public ViewOrderBuilder() {
//
//        }
//
//        public ViewOrderEvent build() {
//            viewOrder = new ViewOrderEvent();
//            return viewOrder;
//        }
//
//        public ViewOrderEvent setCoordinatesVO(CoordinatesVO coordinatesVO) {
//            viewOrder.setCoordinatesVO(coordinatesVO);
//            return viewOrder;
//        }
//
//        public
//
//    }
}
