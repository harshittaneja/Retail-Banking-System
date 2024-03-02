package com.project.customermicroservice.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.project.customermicroservice.model.CustomerCreationStatus;
import com.project.customermicroservice.model.CustomerRequest;
import com.project.customermicroservice.model.CustomerResponse;
import com.project.customermicroservice.service.CustomerService;
import com.project.customermicroservice.utils.JWTUtil;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {
	@MockBean
	private CustomerService customerService;
	@MockBean
	private JWTUtil util;
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void shouldCreateCustomer() throws Exception {
		CustomerCreationStatus status = new CustomerCreationStatus();
		status.setMsg("Account Created Successfully");
		status.setAccountNumbers(List.of(1, 2));
		status.setCustomerId(1);
		String token = "fhibwehfbwefbhiebfiefbief";
		String username = "deep";
		CustomerRequest request = new CustomerRequest();
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(customerService.createCustomer(token, username, request)).thenReturn(status);

		mockMvc.perform(MockMvcRequestBuilders.post("/customer/createCustomer").header("Authorization", token)
				.header("Username", username).contentType(MediaType.APPLICATION_JSON)
				.content("{   \r\n" + "    \"name\":\"Deeptarko  Roy\",\r\n" + "    \"username\":\"deeptarko\",\r\n"
						+ "    \"address\":\"Bombay\",\r\n" + "    \"dob\":\"25-09-2000\",\r\n"
						+ "    \"panNumber\":\"334333333\"\r\n" + "}"))
				.andExpect(MockMvcResultMatchers.status().is(201)).andDo(print());

	}

	@Test
	public void shouldReturnCustomerDetails() throws Exception {
		CustomerResponse response = new CustomerResponse();
		String token = "fhibwehfbwefbhiebfiefbief";
		String username="deep";
		Mockito.when(util.validateToken(token, username)).thenReturn(true);
		Mockito.when(customerService.getCustomerDetails(1)).thenReturn(response);
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/getCustomerDetails/{customerId}", 1)
				.header("Authorization", token).header("Username", username)).andExpect(MockMvcResultMatchers.status().is(202)).andDo(print());

	}

}
