package com.zhuyingcong.orders;

import com.zhuyingcong.orders.entity.CreateRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class CreateOrderTest {

	@Autowired
	private MockMvc mockMvc;


	@Test
	@Transactional
	public void createOrder() throws Exception {
		CreateRequest request = new CreateRequest();
		List<String> origin = Arrays.asList("22.2916", "114.2006");
		List<String> destination = Arrays.asList("22.2994", "114.1740");
		request.setOrigin(origin);
		request.setDestination(destination);
		JSONObject body = new JSONObject(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString());
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(print());
	}

	@Test
	public void createOrderFail1() throws Exception {
		emptyRequest();
		missingCoordinate();
		invalidCoordinate();
		tooManyCoordinates();
		notNumeric();
	}

	@Test
	public void emptyRequest() throws Exception {
		CreateRequest request = new CreateRequest();
		JSONObject body = new JSONObject(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString());
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
	}

	@Test
	public void missingCoordinate() throws Exception {
		CreateRequest request = new CreateRequest();
		request.setOrigin(Arrays.asList("22.2916", "114.2006"));
		request.setDestination(Collections.singletonList("22.2916"));
		JSONObject body = new JSONObject(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString());
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
	}

	@Test
	public void invalidCoordinate() throws Exception {
		CreateRequest request = new CreateRequest();
		request.setOrigin(Arrays.asList("200", "114.2006"));
		request.setDestination(Arrays.asList("22.2916", "114.2006"));
		JSONObject body = new JSONObject(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString());
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
	}

	@Test
	public void tooManyCoordinates() throws Exception {
		CreateRequest request = new CreateRequest();
		request.setOrigin(Arrays.asList("22.2916", "114.2006", "1"));
		request.setDestination(Arrays.asList("22.2916", "114.2006"));
		JSONObject body = new JSONObject(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString());
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
	}

	@Test
	public void notNumeric() throws Exception {
		CreateRequest request = new CreateRequest();
		request.setOrigin(Arrays.asList("22.2916", "114.2006"));
		request.setDestination(Arrays.asList("22.2916", "hello"));
		JSONObject body = new JSONObject(request);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/orders")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString());
		mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andDo(print());
	}

}
