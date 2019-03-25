package com.smarthome.Configuration;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
	private final String sharedKey = "SHARED_KEY";
	private static final String SUCCESS_STATUS = "success";
	private static final String ERROR_STATUS = "error";
	private static final int CODE_SUCCESS = 100;
	private static final int AUTH_FAILURE = 102;

	// http://localhost:8080/bulbcenter/rest/payment/pay?key=SHARED_KEY
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public BaseResponse expPost(@RequestParam(value = "key") String key, @RequestBody ExampleRequest request) {
		BaseResponse response = new BaseResponse();
		if (sharedKey.equalsIgnoreCase(key)) {
			int userId = request.getUserId();
			String itemId = request.getItemId();
			double discount = request.getDiscount();
			// Process the request
			// ....
			// Return success response to the client.
			response.setStatus(SUCCESS_STATUS);
			response.setCode(CODE_SUCCESS);
		} else {
			response.setStatus(ERROR_STATUS);
			response.setCode(AUTH_FAILURE);
		}
		return response;
	}

	@RequestMapping(value = "/first/{id}", method = RequestMethod.GET)
	public BaseResponse first(@PathVariable("id") String key) {
		BaseResponse response = new BaseResponse();
		if (sharedKey.equalsIgnoreCase(key)) {

			// Process the request
			// ....
			// Return success response to the client.
			response.setStatus(SUCCESS_STATUS);
			response.setCode(CODE_SUCCESS);
		} else {
			response.setStatus(ERROR_STATUS);
			response.setCode(AUTH_FAILURE);
		}
		return response;
	}

}