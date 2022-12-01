package com.ecommerce.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ecommerce.enums.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;

import com.ecommerce.entity.Address;
import com.ecommerce.entity.User;
import com.ecommerce.model.AddressModel;
import com.ecommerce.model.ResponseModel;
import com.ecommerce.service.AddressService;
import com.ecommerce.service.UserService;

@ContextConfiguration(classes = {AddressController.class})
@ExtendWith(MockitoExtension.class)
public class AddressControllerTest {

    @MockBean
    private AddressService addressService;

    @Mock
    UserService userService;

    @Mock
    AddressService addService;

    @InjectMocks
    AddressController addressController;

    User user = new User();

    List<Address> addList = new ArrayList<>();

    Address address = new Address();

    @Mock
    Errors result;

    AddressModel addressModel = new AddressModel();

    @BeforeEach
    public void setup() {
        Authentication authentication = mock(Authentication.class);

        SecurityContext securityContext = mock(SecurityContext.class);

        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("Test@gmail.com");

        user.setFirstName("TestFirstName");

        address.setAddressId(1);
        address.setAptNo("Apt 1");
        address.setCity("");
        address.setUser(user);
        addList.add(address);

        addressModel.setAptNo("Apt 1");
        addressModel.setCity("");

    }

    @Test
    public void getAllAddressTest() {

        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(addService.getAllByUser(user)).thenReturn(addList);
        ResponseEntity<List<Address>> result = addressController.getAllAddress();
        assertNotNull(result);

    }

    @Test
    public void addAddressTest() {

        when(userService.getUserByEmail(anyString())).thenReturn(user);
        when(addService.addAddress(ArgumentMatchers.any())).thenReturn(null);
        ResponseEntity<ResponseModel> result2 = addressController.addAddress(addressModel, result);
        assertNotNull(result2);

    }




    @Test
    void testAddAddress() throws Exception {
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/address/add");
        postResult.characterEncoding("Encoding");

        AddressModel addressModel = new AddressModel();
        addressModel.setAptNo("Apt No");
        addressModel.setCity("Oxford");
        addressModel.setState("MD");
        addressModel.setStreet("Street");
        addressModel.setZip("21654");
        String content = (new ObjectMapper()).writeValueAsString(addressModel);
        MockHttpServletRequestBuilder requestBuilder = postResult.contentType(MediaType.APPLICATION_JSON).content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(addressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }


    @Test
    void testGetAddressById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/address/get")
                .param("id", "https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(addressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }


    @Test
    void testGetAllAddress() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(addressController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }


}
