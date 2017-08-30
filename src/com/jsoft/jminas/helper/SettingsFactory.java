package com.jsoft.jminas.helper;

import com.jsoft.jminas.vo.SettingsVO;

public class SettingsFactory {

	public static final String SETTINGS_LABEL_FACIL = "FACIL";
	public static final String SETTINGS_LABEL_MEDIO = "MEDIO";
	public static final String SETTINGS_LABEL_DIFICIL = "DIFICIL";
	
	private SettingsFactory() {
		
	}
	
	public static SettingsVO getSettings(String settingsLabel) {
		if (settingsLabel.equals(SETTINGS_LABEL_FACIL))
			return new SettingsVO(10, 10, 10);
		else
		if (settingsLabel.equals(SETTINGS_LABEL_MEDIO))
			return new SettingsVO(20, 20, 40);
		else
		if (settingsLabel.equals(SETTINGS_LABEL_DIFICIL))
			return new SettingsVO(40, 50, 300);
		return new SettingsVO(10, 10, 10);
	}
	
}
