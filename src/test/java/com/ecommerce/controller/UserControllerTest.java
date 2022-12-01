package com.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.enums.Roles;
import com.ecommerce.exception.UsernameNotFoundException;
import com.ecommerce.jwt.JwtUtils;
import com.ecommerce.model.CancelOrder;
import com.ecommerce.model.LoginModel;
import com.ecommerce.model.UpdateUserDetails;
import com.ecommerce.model.UserSignupModel;
import com.ecommerce.security.UserDetailsServiceImpl;
import com.ecommerce.service.CartService;
import com.ecommerce.service.InvoiceService;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PaypalService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.TransactionService;
import com.ecommerce.service.UserService;
import com.ecommerce.service.WishlistService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private CartService cartService;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private PaypalService paypalService;

    @MockBean
    private ProductService productService;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private UserController userController;

    @MockBean
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @MockBean
    private UserService userService;

    @MockBean
    private WishlistService wishlistService;






    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getusers");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetAllUsers2() throws Exception {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setPhoneno("4105551212");
        user.setRole(Roles.ADMIN);
        user.setUserId(123);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        when(userService.getAllUsers()).thenReturn(userList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/getusers");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testAddToCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addToCart/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }



    @Test
    void testAddToWishlist() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addTowishlist/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testCancelPay() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pay/cancel");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("cancel"));
    }


    @Test
    void testCancelPay2() throws Exception {
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/pay/cancel");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("cancel"));
    }


    @Test
    void testDecreaseQuantityInCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/dec/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetCartByUser() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetListofProducts() throws Exception {
        when(productService.getActiveProducts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }


    @Test
    void testGetOrders() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetProductByIdAndActive() throws Exception {
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
        when(productService.getProductByIdAndIsActive(anyInt(), anyBoolean())).thenReturn(product);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"productId\":123,\"productName\":\"Product Name\",\"quantity\":1,\"price\":1,\"brand\":\"Brand\",\"model\":\"Model\""
                                        + ",\"description\":\"The characteristics of someone or something\",\"primaryImage\":\"QUFBQUFBQUE=\",\"active\""
                                        + ":true,\"category\":\"Category\"}"));
    }


    @Test
    void testGetSumOfQuantityOfProducOfUsert() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetSumOfQuantityOfProducOfUsertd() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetUserDetails() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetWLByUser() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testIncreaseQuantityInCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/inc/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testLogin() throws Exception {
        when(jwtUtils.generateJwtToken((Authentication) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any())).thenThrow(new UsernameNotFoundException("?"));

        LoginModel loginModel = new LoginModel();
        loginModel.setEmail("jane.doe@example.org");
        loginModel.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"error\":\"?\",\"statusCode\":\"BAD_REQUEST\",\"jwtToken\":null,\"userName\":null,\"role\":null}"));
    }


    @Test
    void testLogin2() throws Exception {
        when(jwtUtils.generateJwtToken((Authentication) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any())).thenThrow(new BadCredentialsException("?"));

        LoginModel loginModel = new LoginModel();
        loginModel.setEmail("jane.doe@example.org");
        loginModel.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"error\":\"Invalid credentials\",\"statusCode\":\"BAD_REQUEST\",\"jwtToken\":null,\"userName\":null,"
                                        + "\"role\":null}"));
    }


    @Test
    void testLogin3() throws Exception {
        when(jwtUtils.generateJwtToken((Authentication) any())).thenReturn("ABC123");
        when(authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        LoginModel loginModel = new LoginModel();
        loginModel.setEmail("");
        loginModel.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(loginModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"error\":\"Email is missing\",\"statusCode\":\"BAD_REQUEST\",\"jwtToken\":null,\"userName\":null,\"role\":null}"));
    }


    @Test
    void testPayment() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testRemoveProductInCart() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/cart/remove/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testRemoveProductInWishlist() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/wishlist/remove/{id}", "Uri Variables",
                "Uri Variables");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testSignup() throws Exception {
        UserSignupModel userSignupModel = new UserSignupModel();
        userSignupModel.setAptNo("Apt No");
        userSignupModel.setCity("Oxford");
        userSignupModel.setEmail("jane.doe@example.org");
        userSignupModel.setFirstName("Jane");
        userSignupModel.setLastName("Doe");
        userSignupModel.setPassword("iloveyou");
        userSignupModel.setPhone("4105551212");
        userSignupModel.setState("MD");
        userSignupModel.setStreet("Street");
        userSignupModel.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(userSignupModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 number"
                                        + " \",\"error\":\"Bad request\"}"));
    }


    @Test
    void testSignup2() throws Exception {
        UserSignupModel userSignupModel = new UserSignupModel();
        userSignupModel.setAptNo("Apt No");
        userSignupModel.setCity("Oxford");
        userSignupModel.setEmail("jane.doe@example.org");
        userSignupModel.setFirstName("Jane");
        userSignupModel.setLastName("Doe");
        userSignupModel.setPassword("iloveyou");
        userSignupModel.setPhone("4105551212");
        userSignupModel.setState("MD");
        userSignupModel.setStreet("Street");
        userSignupModel.setZipcode("21654");
        String content = (new ObjectMapper()).writeValueAsString(userSignupModel);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signup", "Uri Variables")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"message\":\"Password must contain atleast 1 uppercase, 1 lowercase, 1 special character and 1 number"
                                        + " \",\"error\":\"Bad request\"}"));
    }


    @Test
    void testSuccessPay() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/paypal/success")
                .param("payerId", "foo")
                .param("paymentId", "foo")
                .param("token", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testUpdateUserDetails() throws Exception {
        MockHttpServletRequestBuilder putResult = MockMvcRequestBuilders.put("/updatedetails");
        putResult.characterEncoding("Encoding");

        UpdateUserDetails updateUserDetails = new UpdateUserDetails();
        updateUserDetails.setFirstName("Jane");
        updateUserDetails.setLastName("Doe");
        updateUserDetails.setPhone("4105551212");
        String content = (new ObjectMapper()).writeValueAsString(updateUserDetails);
        MockHttpServletRequestBuilder requestBuilder = putResult.contentType(MediaType.APPLICATION_JSON).content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }
}

