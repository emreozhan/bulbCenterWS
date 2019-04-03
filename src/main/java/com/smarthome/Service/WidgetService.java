package com.smarthome.Service;

import org.springframework.stereotype.Component;

import com.smarthome.Models.YeeLightBulb.*;

@Component("beanName1")
public class WidgetService {

	public YeeLightDeviceService _yeeLightDeviceService;
	
	public WidgetService() {
		_yeeLightDeviceService = new YeeLightDeviceService();
	}

	public YeeLightBulbWidget PrepareYeeLightColorBulbWidget() {

		YeeLightBulbWidget widget = new YeeLightBulbWidget();
		
		YeeLightDevice yeeLightDevice= new YeeLightDevice();
		yeeLightDevice = _yeeLightDeviceService.DiscoverDeviceOnNetwork(yeeLightDevice);
		widget.setYeeLightBulb(yeeLightDevice);
		
		widget.setName("YeeLightColorBulb");
		widget.setDescription("YeeLight Color Bulb");
		widget.setTitle("My Color Bulb");
		
		return widget;
	}

}
