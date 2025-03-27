package com.bittercode.model;
import static org.junit.Assert.*;
import org.junit.Test;
import com.bittercode.constant.ResponseCode;

/**
 * Test class for StoreException to verify exception handling functionality
 * Contains both positive and negative test scenarios along with edge cases
 */
public class StoreExceptionTest {

    /**
     * Tests the single parameter constructor with error message
     * Verifies default values and message assignment
     */
    @Test
    public void testStoreExceptionWithErrorMessage() {
        // Create test error message
        String errorMessage = "Invalid request";
        // Initialize exception with error message
        StoreException exception = new StoreException(errorMessage);
        // Verify default error code is set to "BAD_REQUEST"
        assertEquals("BAD_REQUEST", exception.getErrorCode());
        // Verify default status code is set to 400
        assertEquals(400, exception.getStatusCode());
        // Verify error message is correctly assigned
        assertEquals(errorMessage, exception.getErrorMessage());
        // Verify parent class (IOException) message is set
        assertEquals(errorMessage, exception.getMessage());
    }

    /**
     * Tests constructor with ResponseCode enum parameter
     * Verifies proper mapping of enum values to exception properties
     */
    @Test
    public void testStoreExceptionWithResponseCode() {
        // Create mock response code
        ResponseCode mockResponseCode = ResponseCode.ACCESS_DENIED;
        // Initialize exception with response code
        StoreException exception = new StoreException(mockResponseCode);
        
        // Verify enum name is set as error code
        assertEquals(mockResponseCode.name(), exception.getErrorCode());
        // Verify status code from enum is set
        assertEquals(mockResponseCode.getCode(), exception.getStatusCode());
        // Verify error message from enum is set
        assertEquals(mockResponseCode.getMessage(), exception.getErrorMessage());
        // Verify parent exception message is set from enum
        assertEquals(mockResponseCode.getMessage(), exception.getMessage());
    }

    /**
     * Tests constructor with error code and message parameters
     * Verifies custom error details and default status code
     */
    @Test
    public void testStoreExceptionWithErrorCodeAndMessage() {
        // Set test values
        String errorCode = "VALIDATION_ERROR";
        String errorMessage = "Validation failed";
        // Initialize exception with error code and message
        StoreException exception = new StoreException(errorCode, errorMessage);
        
        // Verify custom error code is set
        assertEquals(errorCode, exception.getErrorCode());
        // Verify default status code 422 is set
        assertEquals(422, exception.getStatusCode());
        // Verify custom error message is set
        assertEquals(errorMessage, exception.getErrorMessage());
    }

    /**
     * Tests constructor with all parameters
     * Verifies custom values for all properties
     */
    @Test
    public void testStoreExceptionWithAllParameters() {
        // Set test values
        int statusCode = 503;
        String errorCode = "SERVICE_UNAVAILABLE";
        String errorMessage = "Service temporarily unavailable";
        // Initialize exception with all parameters
        StoreException exception = new StoreException(statusCode, errorCode, errorMessage);
        
        // Verify all custom values are set correctly
        assertEquals(statusCode, exception.getStatusCode());
        assertEquals(errorCode, exception.getErrorCode());
        assertEquals(errorMessage, exception.getErrorMessage());
    }

    /**
     * Tests setter methods functionality
     * Verifies property values can be modified after initialization
     */
    @Test
    public void testSetterMethods() {
        // Create exception with initial values
        StoreException exception = new StoreException("Initial message");
        
        // Test setting new values
        exception.setErrorCode("NEW_ERROR_CODE");
        exception.setStatusCode(503);
        exception.setErrorMessage("New error message");
        
        // Verify new values are set correctly
        assertEquals("NEW_ERROR_CODE", exception.getErrorCode());
        assertEquals(503, exception.getStatusCode());
        assertEquals("New error message", exception.getErrorMessage());
    }

    // Additional Edge Cases

    /**
     * Tests exception behavior with extremely long error message
     */
    @Test
    public void testExtremelyLongErrorMessage() {
        // Create a very long error message
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            longMessage.append("a");
        }
        StoreException exception = new StoreException(longMessage.toString());
        
        // Verify long message is handled correctly
        assertEquals(longMessage.toString(), exception.getErrorMessage());
        assertEquals(10000, exception.getErrorMessage().length());
    }

    /**
     * Tests exception behavior with special characters
     */
    @Test
    public void testSpecialCharactersInMessage() {
        String specialChars = "!@#$%^&*()_+{}[]|\\:;\"'<>,.?/~`";
        StoreException exception = new StoreException(specialChars);
        
        // Verify special characters are preserved
        assertEquals(specialChars, exception.getErrorMessage());
    }

    /**
     * Tests exception behavior with negative status code
     */
    @Test
    public void testNegativeStatusCode() {
        StoreException exception = new StoreException(-1, "ERROR", "message");
        
        // Verify negative status code is stored
        assertEquals(-1, exception.getStatusCode());
    }

    /**
     * Tests exception behavior with extremely large status code
     */
    @Test
    public void testExtremelyLargeStatusCode() {
        StoreException exception = new StoreException("Test message");
        exception.setStatusCode(Integer.MAX_VALUE);
        
        // Verify large status code is handled
        assertEquals(Integer.MAX_VALUE, exception.getStatusCode());
    }

    /**
     * Tests multiple modifications to exception properties
     */
    @Test
    public void testMultiplePropertyModifications() {
        StoreException exception = new StoreException("Initial");
        
        // Modify properties multiple times
        for (int i = 0; i < 100; i++) {
            exception.setErrorMessage("Message " + i);
            exception.setErrorCode("Code " + i);
            exception.setStatusCode(i);
        }
        
        // Verify final values
        assertEquals("Message 99", exception.getErrorMessage());
        assertEquals("Code 99", exception.getErrorCode());
        assertEquals(99, exception.getStatusCode());
    }

    /**
     * Tests exception behavior with Unicode characters
     */
    @Test
    public void testUnicodeCharacters() {
        String unicodeMessage = "Unicode Test: 你好 こんにちは 안녕하세요";
        StoreException exception = new StoreException(unicodeMessage);
        
        // Verify Unicode characters are handled correctly
        assertEquals(unicodeMessage, exception.getErrorMessage());
    }

    /**
     * Tests exception behavior with whitespace-only message
     */
    @Test
    public void testWhitespaceMessage() {
        String whitespaceMessage = "    \t\n\r    ";
        StoreException exception = new StoreException(whitespaceMessage);
        
        // Verify whitespace is preserved
        assertEquals(whitespaceMessage, exception.getErrorMessage());
    }

    /**
     * Tests chaining of setter methods
     */
    @Test
    public void testNullResponseCode() {
        // This test expects an exception when passing null ResponseCode
        try {
            new StoreException((ResponseCode)null);
            fail("Expected NullPointerException");
        } catch (NullPointerException e) {
            // Expected behavior
            assertTrue(true);
        }
    }
}
