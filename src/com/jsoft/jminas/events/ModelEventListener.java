package com.jsoft.jminas.events;

import java.util.EventListener;

public interface ModelEventListener extends EventListener {

	public void bombClicked(ModelEvent pe);
	
	public void bombMarked(ModelEvent pe);
	
	public void emptyCellClicked(ModelEvent pe);
	
	public void allBombMarked(ModelEvent pe);
}
