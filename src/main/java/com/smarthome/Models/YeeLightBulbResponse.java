package com.smarthome.Models;

import com.smarthome.Configuration.BaseResponse;

public class YeeLightBulbResponse extends BaseResponse {

	private YeeLightBulbWidget yeeLight;

	public YeeLightBulbWidget getYeeLight() {
		return yeeLight;
	}

	public void setYeeLight(YeeLightBulbWidget yeeLight) {
		this.yeeLight = yeeLight;
	}

}
