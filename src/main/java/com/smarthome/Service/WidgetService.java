package com.smarthome.Service;

import com.smarthome.Models.YeeLightBulb.*;

public class WidgetService {

//	public YeeLightBulbWidget GetWidgets() {
//
//	}

	public static YeeLightBulbWidget PrepareYeeLightColorBulbWidget() {

		YeeLightBulbWidget widget = new YeeLightBulbWidget();
		widget.setName("YeeLightColorBulb");
		widget.setDescription("YeeLight Color Bulb");
		widget.setTitle("My Color Bulb");
		
		return widget;
	}

}
