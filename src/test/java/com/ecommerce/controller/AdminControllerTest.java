package com.ecommerce.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Errors;

@ContextConfiguration(classes = {AdminController.class})
@ExtendWith(SpringExtension.class)
class AdminControllerTest {
    @Autowired
    private AdminController adminController;

    @MockBean
    private ProductService productService;


    @Test
    void testActivateProduct() throws Exception {
        Product product = new Product();
        product.setActive(true);
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setModel("Model");
        product.setPrice(1);
        product.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product.setProductId(123);
        product.setProductName("Product Name");
        product.setQuantity(1);

        Product product1 = new Product();
        product1.setActive(true);
        product1.setBrand("Brand");
        product1.setCategory("Category");
        product1.setDescription("The characteristics of someone or something");
        product1.setModel("Model");
        product1.setPrice(1);
        product1.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product1.setProductId(123);
        product1.setProductName("Product Name");
        product1.setQuantity(1);
        when(productService.addProduct((Product) any())).thenReturn(product1);
        when(productService.getProductById(anyInt())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/activateProduct/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":123,\"productName\":\"Product Name\",\"quantity\":1,\"price\":1,\"brand\":\"Brand\",\"model\":\"Model\""
                                        + ",\"description\":\"The characteristics of someone or something\",\"primaryImage\":\"QUFBQUFBQUE=\",\"active\""
                                        + ":true,\"category\":\"Category\"}"));
    }


    @Test
    void testAddProduct() throws Exception {
        Product product = new Product();
        product.setActive(true);
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setModel("Model");
        product.setPrice(1);
        product.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product.setProductId(123);
        product.setProductName("Product Name");
        product.setQuantity(1);
        when(productService.addProduct((Product) any())).thenReturn(product);

        Product product1 = new Product();
        product1.setActive(true);
        product1.setBrand("Brand");
        product1.setCategory("Category");
        product1.setDescription("The characteristics of someone or something");
        product1.setModel("Model");
        product1.setPrice(1);
        product1.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product1.setProductId(123);
        product1.setProductName("Product Name");
        product1.setQuantity(1);
        String content = (new ObjectMapper()).writeValueAsString(product1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/addProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"message\":\"Product added successfully\",\"error\":null}"));
    }


    @Test
    void testGetAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getproducts");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetAllProducts2() throws Exception {
        when(productService.getAllProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/admin/getproducts");
        getResult.characterEncoding("Encoding");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController).build().perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetProductById() throws Exception {
        Product product = new Product();
        product.setActive(true);
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setModel("Model");
        product.setPrice(1);
        product.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product.setProductId(123);
        product.setProductName("Product Name");
        product.setQuantity(1);
        Optional<Product> ofResult = Optional.of(product);
        when(productService.findById(anyInt())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getproduct/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":123,\"productName\":\"Product Name\",\"quantity\":1,\"price\":1,\"brand\":\"Brand\",\"model\":\"Model\""
                                        + ",\"description\":\"The characteristics of someone or something\",\"primaryImage\":\"QUFBQUFBQUE=\",\"active\""
                                        + ":true,\"category\":\"Category\"}"));
    }


    @Test
    void testGetProductById2() throws Exception {
        when(productService.findById(anyInt())).thenReturn(Optional.empty());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getproduct/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testInActivateProduct() throws Exception {
        Product product = new Product();
        product.setActive(true);
        product.setBrand("Brand");
        product.setCategory("Category");
        product.setDescription("The characteristics of someone or something");
        product.setModel("Model");
        product.setPrice(1);
        product.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product.setProductId(123);
        product.setProductName("Product Name");
        product.setQuantity(1);

        Product product1 = new Product();
        product1.setActive(true);
        product1.setBrand("Brand");
        product1.setCategory("Category");
        product1.setDescription("The characteristics of someone or something");
        product1.setModel("Model");
        product1.setPrice(1);
        product1.setPrimaryImage("AAAAAAAA".getBytes("UTF-8"));
        product1.setProductId(123);
        product1.setProductName("Product Name");
        product1.setQuantity(1);
        when(productService.addProduct((Product) any())).thenReturn(product1);
        when(productService.getProductById(anyInt())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/admin/inActivateProduct/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(adminController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":123,\"productName\":\"Product Name\",\"quantity\":1,\"price\":1,\"brand\":\"Brand\",\"model\":\"Model\""
                                        + ",\"description\":\"The characteristics of someone or something\",\"primaryImage\":\"QUFBQUFBQUE=\",\"active\""
                                        + ":true,\"category\":\"Category\"}"));
    }
}

