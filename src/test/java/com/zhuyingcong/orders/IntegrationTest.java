package com.zhuyingcong.orders;

import com.zhuyingcong.orders.dao.OrderRepository;
import com.zhuyingcong.orders.entity.CreateRequest;
import com.zhuyingcong.orders.entity.StatusBody;
import com.zhuyingcong.orders.enums.OrderStatus;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void integrateTest() throws Exception {
        createOrders();
        queryOrders();
        takeOrder();
        queryOrders();
    }

    @Test
    public void createOrders() throws Exception {
        // create 5 orders
        CreateRequest request = new CreateRequest();
        request.setOrigin(Arrays.asList("22.3131", "114.2233"));
        request.setDestination(Arrays.asList("22.3086", "114.2599"));
        JSONObject body = new JSONObject(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").content(body.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value("UNASSIGNED"))
                .andDo(print());
        request.setOrigin(Arrays.asList("22.2994", "114.1740"));
        request.setDestination(Arrays.asList("22.2916", "114.2006"));
        body = new JSONObject(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").content(body.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(2))
                .andDo(print());
        request.setOrigin(Arrays.asList("22.3717", "114.1112"));
        request.setDestination(Arrays.asList("22.3812", "114.1885"));
        body = new JSONObject(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").content(body.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(3))
                .andDo(print());
        request.setOrigin(Arrays.asList("22.3901", "113.9711"));
        request.setDestination(Arrays.asList("22.4445", "114.0278"));
        body = new JSONObject(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").content(body.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(4))
                .andDo(print());
        request.setOrigin(Arrays.asList("22.2485", "114.1551"));
        request.setDestination(Arrays.asList("22.2387", "114.1961"));
        body = new JSONObject(request);
        mockMvc.perform(MockMvcRequestBuilders.post("/orders").content(body.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andDo(print());
    }

    @Test
    public void queryOrders() throws Exception {
        // page is full
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                .param("page", "1")
                .param("limit", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andDo(print());
        // page size is different
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .param("page", "1")
                        .param("limit", "2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andDo(print());
        // page is not full
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .param("page", "2")
                        .param("limit", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[1].id", is(5)))
                .andDo(print());
        // page is empty
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .param("page", "3")
                        .param("limit", "3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(0)))
                .andDo(print());
    }

    @Test
    public void takeOrder() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                        .param("page", "1")
                        .param("limit", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                // order is not assigned
                .andExpect(jsonPath("$[0].status", is("UNASSIGNED")));
        StatusBody body = new StatusBody();
        body.setStatus(OrderStatus.TAKEN.getDescription());
        JSONObject object = new JSONObject(body);
        mockMvc.perform(MockMvcRequestBuilders.patch("/orders")
                .param("id", "1")
                .content(object.toString())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result ->
                        mockMvc.perform(MockMvcRequestBuilders.patch("/orders")
                                .param("id", "1")
                                .content(object.toString())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON))
                                // order can only be taken once
                                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                ).andDo(result -> mockMvc.perform(MockMvcRequestBuilders.get("/orders")
                                .param("page", "1")
                                .param("limit", "1"))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                // check that order has been taken
                        .andExpect(jsonPath("$[0].status", is("TAKEN"))));
        mockMvc.perform(MockMvcRequestBuilders.patch("/orders")
                        .param("id", "2")
                        .content(object.toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                ;
    }

}
