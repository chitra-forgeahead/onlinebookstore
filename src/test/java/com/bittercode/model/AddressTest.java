package com.bittercode.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

//Clear separation between positive and negative test cases
//Detailed comments explaining each test's purpose
//Multiple assertions to test different scenarios [2]
//Tests for:
//Valid input values
//Null values
//Empty strings
//Boundary conditions
//Mismatched values
//Invalid data formats
//Meaningful test method names
//Descriptive assertion messages

public class AddressTest {
    
    private Address address;
    
    @Before
    public void setUp() {
        // Initialize a new Address object before each test
        address = new Address();
    }
    
    /******************************************
     * POSITIVE TEST CASES
     ******************************************/
    
    @Test
    public void testAddressLine1_Positive() {
        // Test valid address line 1 with mixed characters
        String addressLine1 = "123 Main Street #A-1";
        address.setAddressLine1(addressLine1);
        assertEquals("Address line 1 should match the set value", 
            addressLine1, address.getAddressLine1());
    }
    
    @Test
    public void testAddressLine2_Positive() {
        // Test valid address line 2 with apartment number
        String addressLine2 = "Apartment 4B, Building 7";
        address.setAddressLine2(addressLine2);
        assertEquals("Address line 2 should match the set value", 
            addressLine2, address.getAddressLine2());
        System.out.println("Run as test");
    }
    
    @Test
    public void testCity_Positive() {
        // Test valid city name with spaces
        String city = "New York City";
        address.setCity(city);
        assertEquals("City should match the set value", 
            city, address.getCity());
    }
    
    @Test
    public void testState_Positive() {
        // Test valid state name
        String state = "California";
        address.setState(state);
        assertEquals("State should match the set value", 
            state, address.getState());
    }
    
    @Test
    public void testPinCode_Positive() {
        // Test valid 6-digit pin code
        long pinCode = 123456L;
        address.setPinCode(pinCode);
        assertEquals("Pin code should match the set value", 
            pinCode, address.getPinCode());
    }
    
    @Test
    public void testPhone_Positive() {
        // Test valid phone number with hyphens
        String phone = "123-456-7890";
        address.setPhone(phone);
        assertEquals("Phone number should match the set value", 
            phone, address.getPhone());
    }
    
    @Test
    public void testAllValidFields_Positive() {
        // Test setting all fields with valid values
        String addressLine1 = "123 Main St";
        String addressLine2 = "Apt 4B";
        String city = "Boston";
        String state = "MA";
        String country = "USA";
        long pinCode = 123456L;
        String phone = "123-456-7890";
        
        // Set all fields
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setCity(city);
        address.setState(state);
        address.setCountry(country);
        address.setPinCode(pinCode);
        address.setPhone(phone);
        
        // Verify all fields
        assertEquals("Address line 1 should match", addressLine1, address.getAddressLine1());
        assertEquals("Address line 2 should match", addressLine2, address.getAddressLine2());
        assertEquals("City should match", city, address.getCity());
        assertEquals("State should match", state, address.getState());
        assertEquals("Country should match", country, address.getCountry());
        assertEquals("Pin code should match", pinCode, address.getPinCode());
        assertEquals("Phone should match", phone, address.getPhone());
    }

    /******************************************
     * NEGATIVE TEST CASES
     ******************************************/
    
    @Test
    public void testAddressLine1_Negative() {
        // Test with null value
        address.setAddressLine1(null);
        assertNull("Address line 1 should be null", address.getAddressLine1());
        
        // Test with empty string
        address.setAddressLine1("");
        assertNotEquals("Address line 1 should not match non-empty string", 
            "Some Address", address.getAddressLine1());
    }
    
    @Test
    public void testPinCode_Negative() {
        // Test with invalid pin code (negative value)
        address.setPinCode(-123456L);
        assertNotEquals("Pin code should not be negative", 
            123456L, address.getPinCode());
        
        // Test with zero pin code
        address.setPinCode(0L);
        assertEquals("Pin code should be zero", 0L, address.getPinCode());
    }
    
    @Test
    public void testPhone_Negative() {
        // Test with invalid phone format
        String invalidPhone = "abcd123xyz";
        address.setPhone(invalidPhone);
        assertNotEquals("Phone should not match valid format", 
            "123-456-7890", address.getPhone());
        
        // Test with null phone
        address.setPhone(null);
        assertNull("Phone should be null", address.getPhone());
    }
    
    @Test
    public void testMismatchedValues_Negative() {
        // Test with mismatched values
        address.setCity("New York");
        assertNotEquals("City should not match different value", 
            "Boston", address.getCity());
        
        address.setState("NY");
        assertNotEquals("State should not match different value", 
            "CA", address.getState());
    }
    
    @Test
    public void testEmptyValues_Negative() {
        // Test all fields with empty strings
        address.setAddressLine1("");
        address.setAddressLine2("");
        address.setCity("");
        address.setState("");
        address.setCountry("");
        address.setPhone("");
        
        // Verify empty strings
        assertTrue("Address line 1 should be empty", address.getAddressLine1().isEmpty());
        assertTrue("Address line 2 should be empty", address.getAddressLine2().isEmpty());
        assertTrue("City should be empty", address.getCity().isEmpty());
        assertTrue("State should be empty", address.getState().isEmpty());
        assertTrue("Country should be empty", address.getCountry().isEmpty());
        assertTrue("Phone should be empty", address.getPhone().isEmpty());
    }
    
    @Test
    public void testNullValues_Negative() {
        // Test all fields with null values
        address.setAddressLine1(null);
        address.setAddressLine2(null);
        address.setCity(null);
        address.setState(null);
        address.setCountry(null);
        address.setPhone(null);
        
        // Verify null values
        assertNull("Address line 1 should be null", address.getAddressLine1());
        assertNull("Address line 2 should be null", address.getAddressLine2());
        assertNull("City should be null", address.getCity());
        assertNull("State should be null", address.getState());
        assertNull("Country should be null", address.getCountry());
        assertNull("Phone should be null", address.getPhone());
    }
    
    @Test
    public void testBoundaryValues_Negative() {
        // Test pin code with maximum long value
        address.setPinCode(Long.MAX_VALUE);
        assertEquals("Pin code should handle maximum long value", 
            Long.MAX_VALUE, address.getPinCode());
        
        // Test pin code with minimum long value
        address.setPinCode(Long.MIN_VALUE);
        assertEquals("Pin code should handle minimum long value", 
            Long.MIN_VALUE, address.getPinCode());
    }
}

//Clear separation between positive and negative test cases
//Detailed comments explaining each test's purpose
//Multiple assertions to test different scenarios [2]
//Tests for:
//Valid input values
//Null values
//Empty strings
//Boundary conditions
//Mismatched values
//Invalid data formats
//Meaningful test method names
//Descriptive assertion messages