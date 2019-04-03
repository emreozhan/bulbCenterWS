package com.smarthome.Models.YeeLightBulb;

import com.smarthome.Models.BaseWidget;

public class YeeLightBulbWidget extends BaseWidget {

	private YeeLightDevice yeeLightBulb;
	
	public YeeLightDevice getYeeLightBulb() {
		return yeeLightBulb;
	}

	public void setYeeLightBulb(YeeLightDevice yeeLightBulb) {
		this.yeeLightBulb = yeeLightBulb;
		
	}
	
}
