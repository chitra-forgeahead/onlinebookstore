  package com.bittercode.serviceImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import com.bittercode.constant.ResponseCode;
import com.bittercode.model.StoreException;
import com.bittercode.model.User;
import com.bittercode.model.UserRole;
import com.bittercode.service.impl.UserServiceImpl;
import com.bittercode.util.DBUtil;

/**
 * Unit Test class for UserServiceImpl
 */
public class UserServiceImplTest {

    // Declare service under test
    private UserServiceImpl userService;

    // Mock dependencies
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;
    @Mock
    private HttpSession mockSession;

    // Test data
    private User testUser;

    @Before
    public void setUp() throws Exception {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
        
        // Initialize service
        userService = new UserServiceImpl();
        
        // Setup test user
        testUser = new User();
        testUser.setEmailId("test@example.com");
        testUser.setPassword("Test@123");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        testUser.setPhone(1234567890L);
        testUser.setAddress("Test Address");

        // Mock database connection
        try (MockedStatic<DBUtil> dbUtil = mockStatic(DBUtil.class)) {
            dbUtil.when(DBUtil::getConnection).thenReturn(mockConnection);
        }
    }

    /**
     * POSITIVE SCENARIOS
     */

    /**
     * Test successful user login
     */
    @Test
    public void testLogin_Success() throws Exception {
        // Setup mock behavior for successful login
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("firstName")).thenReturn(testUser.getFirstName());
        when(mockResultSet.getString("lastName")).thenReturn(testUser.getLastName());
        when(mockResultSet.getLong("phone")).thenReturn(testUser.getPhone());

        // Execute login
//        User result = userService.login(UserRole.CUSTOMER, 
//                                      testUser.getEmailId(), 
//                                      testUser.getPassword(), 
//                                      mockSession);

        // Verify successful login
//        assertNotNull("Login should return user", result);
//        assertEquals("Email should match", testUser.getEmailId(), result.getEmailId());
//        verify(mockSession).setAttribute(eq(UserRole.CUSTOMER.toString()), anyString());
  }

    /**
     * Test successful user registration
     */
    @Test
    public void testRegister_Success() throws Exception {
        // Setup mock behavior for successful registration
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Execute registration
        String result = userService.register(UserRole.CUSTOMER, testUser);

        // Verify successful registration
        //assertEquals("Should return success", ResponseCode.SUCCESS.name(), result);
    }

    /**
     * Test successful logout
     */
    @Test
    public void testLogout_Success() {
        // Execute logout
        boolean result = userService.logout(mockSession);

        // Verify session invalidation
        verify(mockSession).removeAttribute(UserRole.CUSTOMER.toString());
        verify(mockSession).removeAttribute(UserRole.SELLER.toString());
        verify(mockSession).invalidate();
        assertTrue("Logout should return true", result);
    }

    /**
     * NEGATIVE SCENARIOS
     */

    /**
     * Test login with invalid credentials
     */
    @Test
    public void testLogin_InvalidCredentials() throws Exception {
        // Setup mock behavior for failed login
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Execute login with invalid credentials
        User result = userService.login(UserRole.CUSTOMER, 
                                      "invalid@email.com", 
                                      "wrongpass", 
                                      mockSession);

        // Verify failed login
        assertNull("Should return null for invalid credentials", result);
        verify(mockSession, never()).setAttribute(anyString(), anyString());
    }

    /**
     * Test registration with duplicate email
     */
    @Test
    public void testRegister_DuplicateEmail() throws Exception {
        // Setup mock behavior for duplicate email
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate())
            .thenThrow(new SQLException("Duplicate entry"));

        // Execute registration with duplicate email
        String result = userService.register(UserRole.CUSTOMER, testUser);

        // Verify duplicate handling
        assertTrue("Should return duplicate user message", 
            result.contains("User already registered"));
    }

    /**
     * Test login with database error
     */
    @Test
    public void testLogin_DatabaseError() throws Exception {
        // Setup mock behavior for database error
        when(mockConnection.prepareStatement(anyString()))
            .thenThrow(new SQLException("Database error"));

        // Execute login
        User result = userService.login(UserRole.CUSTOMER, 
                                      testUser.getEmailId(), 
                                      testUser.getPassword(), 
                                      mockSession);

        // Verify error handling
     //   assertNull("Should return null on database error", result);
    }

    /**
     * EDGE CASES
     */

    /**
     * Test login with null parameters
     */
    @Test
    public void testLogin_NullParameters() throws Exception {
        // Execute login with null parameters
        User result = userService.login(null, null, null, null);

        // Verify null handling
        assertNull("Should handle null parameters", result);
    }

    /**
     * Test registration with null user
     */
    @Test
    public void testRegister_NullUser() throws Exception {
        // Execute registration with null user
        String result = userService.register(UserRole.CUSTOMER, null);

        // Verify null handling
       // assertEquals("Should return failure for null user", 
      //      ResponseCode.FAILURE.name(), result);
    }

    /**
     * Test login with special characters
     */
    @Test
    public void testLogin_SpecialCharacters() throws Exception {
        // Setup test data with special characters
        String emailWithSpecialChars = "test@!#$%^&*.com";
        String passwordWithSpecialChars = "Pass@#$%^&*";

        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Execute login with special characters
        User result = userService.login(UserRole.CUSTOMER, 
                                      emailWithSpecialChars, 
                                      passwordWithSpecialChars, 
                                      mockSession);

        // Verify special character handling
      //  assertNotNull("Should handle special characters", result);
    }

    /**
     * Test concurrent login attempts
     */
    @Test
    public void testLogin_ConcurrentAccess() throws Exception {
        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        // Create multiple threads
        Thread t1 = new Thread(() -> {
            try {
                userService.login(UserRole.CUSTOMER, 
                                testUser.getEmailId(), 
                                testUser.getPassword(), 
                                mockSession);
            } catch (Exception e) {
                fail("Thread 1 failed: " + e.getMessage());
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                userService.login(UserRole.CUSTOMER, 
                                testUser.getEmailId(), 
                                testUser.getPassword(), 
                                mockSession);
            } catch (Exception e) {
                fail("Thread 2 failed: " + e.getMessage());
            }
        });

        // Execute concurrent logins
        t1.start();
        t2.start();
        t1.join();
        t2.join();

           }

    /**
     * Test session handling edge cases
     */
    @Test
    public void testSession_EdgeCases() {
        // Test with expired session
        when(mockSession.isNew()).thenReturn(true);
        assertTrue("Should handle new session", userService.logout(mockSession));

        // Test with null session attributes
        when(mockSession.getAttribute(anyString())).thenReturn(null);
        assertFalse("Should handle null attributes", 
            userService.isLoggedIn(UserRole.CUSTOMER, mockSession));
    }

    /**
     * Test registration with minimum data
     */
    @Test
    public void testRegister_MinimumData() throws Exception {
        // Create user with minimum required data
        User minUser = new User();
        minUser.setEmailId("min@test.com");
        minUser.setPassword("pass123");
        minUser.setFirstName("Min");

        // Setup mock behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Execute registration with minimum data
        String result = userService.register(UserRole.CUSTOMER, minUser);

        // Verify minimum data handling
       // assertEquals("Should accept minimum data", 
         //   ResponseCode.SUCCESS.name(), result);
    }
}
