package com.jsoft.jminas.helper;

import com.jsoft.jminas.vo.CoordinatesVO;

public class CoordinateHelper {

	public static CoordinatesVO getCoordinates(String text) {
		String[] coords = text.split(",");
		return new CoordinatesVO(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
	}
}
