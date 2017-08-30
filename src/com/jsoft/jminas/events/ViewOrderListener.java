package com.jsoft.jminas.events;

/**
 * Created by joaquin on 10/12/16.
 */
public interface ViewOrderListener {

    public void disableButton(ViewOrderEvent order);


    public void changeButtonStatus(ViewOrderEvent order);
}
