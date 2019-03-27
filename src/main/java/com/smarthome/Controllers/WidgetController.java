package com.smarthome.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.smarthome.Models.YeeLightBulb.*;
import com.smarthome.Service.WidgetService;

@RestController
@RequestMapping("/homeScreen")
public class WidgetController {

	@RequestMapping(value = "/getYeeLightBulbWidget", method = RequestMethod.POST)
	public YeeLightBulbResponse getYeeLightBulbWidget() {

		YeeLightBulbResponse response = new YeeLightBulbResponse();
		YeeLightBulbWidget device = WidgetService.PrepareYeeLightColorBulbWidget();
		response.setYeeLight(device);

//TODO created for test- delete
		device.setTcpSetting();
		
		device.toogleBulb();
		
		return response;
	}
}
