package com.smarthome.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.Models.YeeLightBulb.*;
import com.smarthome.Service.*;

@RestController
@RequestMapping("/homeScreen")
public class WidgetController {

	@Autowired
	@Qualifier("beanName1")
	private WidgetService _widgetService;
	
	@Autowired
	@Qualifier("yeelightService")
	private YeeLightDeviceService _yeeLightService; 
	
	@RequestMapping(value = "/getYeeLightBulbWidget", method = RequestMethod.POST)
	public YeeLightBulbResponse getYeeLightBulbWidget() {

		YeeLightBulbResponse response = new YeeLightBulbResponse();
		YeeLightBulbWidget device = _widgetService.PrepareYeeLightColorBulbWidget();
		response.setCode(000);
		response.setStatus("Succes");
		response.setYeeLight(device);

//TODO created for test- delete
		_yeeLightService.setTcpSetting(device.getYeeLightBulb());
		_yeeLightService.toogleBulb(device.getYeeLightBulb());

		//device.toogleBulb();
		
		return response;
	}
}
