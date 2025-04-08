package com.bittercode.util;
    import static org.junit.Assert.*;
    import static org.mockito.Mockito.*;

    import java.io.PrintWriter;
    import java.io.StringWriter;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpSession;

    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.mockito.Mock;
    import org.mockito.junit.MockitoJUnitRunner;

    import com.bittercode.model.UserRole;

    @RunWith(MockitoJUnitRunner.class)
    public class StoreUtilTest {

        @Mock
        private HttpSession mockSession;

        @Mock
        private HttpServletRequest mockRequest;

        private PrintWriter printWriter;
        private StringWriter stringWriter;

        @Before
        public void setUp() {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
        }

        @Test
        public void testIsLoggedIn_WhenUserIsLoggedIn_ReturnsTrue() {
            // Arrange
            when(mockSession.getAttribute(UserRole.CUSTOMER.toString())).thenReturn("someValue");

            // Act
            boolean result = StoreUtil.isLoggedIn(UserRole.CUSTOMER, mockSession);

            // Assert
            assertTrue("Should return true when user is logged in", result);
            verify(mockSession).getAttribute(UserRole.CUSTOMER.toString());
        }

        @Test
        public void testIsLoggedIn_WhenUserIsNotLoggedIn_ReturnsFalse() {
            // Arrange
            when(mockSession.getAttribute(UserRole.CUSTOMER.toString())).thenReturn(null);

            // Act
            boolean result = StoreUtil.isLoggedIn(UserRole.CUSTOMER, mockSession);

            // Assert
            assertFalse("Should return false when user is not logged in", result);
            verify(mockSession).getAttribute(UserRole.CUSTOMER.toString());
        }

        @Test
        public void testSetActiveTab() {
            // Arrange
            String activeTab = "homeTab";

            // Act
            StoreUtil.setActiveTab(printWriter, activeTab);
            String result = stringWriter.toString();

            // Assert
            assertTrue("Should contain script to remove active class", 
                result.contains("document.getElementById(activeTab).classList.remove(\"active\")"));
            assertTrue("Should contain script to add active class", 
                result.contains("document.getElementById('homeTab').classList.add(\"active\")"));
        }

        @Test
        public void testUpdateCartItems_WhenAddingNewItem() {
            // Arrange
            String bookId = "book123";
            when(mockRequest.getParameter("selectedBookId")).thenReturn(bookId);
            when(mockRequest.getParameter("addToCart")).thenReturn("true");
            when(mockRequest.getSession()).thenReturn(mockSession);
            when(mockSession.getAttribute("items")).thenReturn(null);

            // Act
            StoreUtil.updateCartItems(mockRequest);

            // Assert
            verify(mockSession).setAttribute("items", bookId);
            verify(mockSession).setAttribute("qty_" + bookId, 1);
        }

        @Test
        public void testUpdateCartItems_WhenAddingExistingItem() {
            // Arrange
            String bookId = "book123";
            when(mockRequest.getParameter("selectedBookId")).thenReturn(bookId);
            when(mockRequest.getParameter("addToCart")).thenReturn("true");
            when(mockRequest.getSession()).thenReturn(mockSession);
            when(mockSession.getAttribute("items")).thenReturn(bookId);
            when(mockSession.getAttribute("qty_" + bookId)).thenReturn(1);

            // Act
            StoreUtil.updateCartItems(mockRequest);

            // Assert
            verify(mockSession).setAttribute("items", bookId);
            verify(mockSession).setAttribute("qty_" + bookId, 2);
        }

        @Test
        public void testUpdateCartItems_WhenRemovingItemWithQuantityGreaterThanOne() {
            // Arrange
            String bookId = "book123";
            when(mockRequest.getParameter("selectedBookId")).thenReturn(bookId);
            when(mockRequest.getParameter("addToCart")).thenReturn(null);
            when(mockRequest.getSession()).thenReturn(mockSession);
            when(mockSession.getAttribute("items")).thenReturn(bookId);
            when(mockSession.getAttribute("qty_" + bookId)).thenReturn(2);

            // Act
            StoreUtil.updateCartItems(mockRequest);

            // Assert
            verify(mockSession).setAttribute("qty_" + bookId, 1);
        }

        @Test
        public void testUpdateCartItems_WhenRemovingLastItem() {
            // Arrange
            String bookId = "book123";
            when(mockRequest.getParameter("selectedBookId")).thenReturn(bookId);
            when(mockRequest.getParameter("addToCart")).thenReturn(null);
            when(mockRequest.getSession()).thenReturn(mockSession);
            when(mockSession.getAttribute("items")).thenReturn(bookId);
            when(mockSession.getAttribute("qty_" + bookId)).thenReturn(1);

            // Act
            StoreUtil.updateCartItems(mockRequest);

            // Assert
            verify(mockSession).removeAttribute("qty_" + bookId);
            verify(mockSession).setAttribute("items", "");
        }

        @Test
        public void testUpdateCartItems_WhenRemovingItemFromMultipleItems() {
            // Arrange
            String bookId = "book123";
            String items = "book123,book456";
            when(mockRequest.getParameter("selectedBookId")).thenReturn(bookId);
            when(mockRequest.getParameter("addToCart")).thenReturn(null);
            when(mockRequest.getSession()).thenReturn(mockSession);
            when(mockSession.getAttribute("items")).thenReturn(items);
            when(mockSession.getAttribute("qty_" + bookId)).thenReturn(1);

            // Act
            StoreUtil.updateCartItems(mockRequest);

            // Assert
            verify(mockSession).removeAttribute("qty_" + bookId);
            verify(mockSession).setAttribute("items", "book456");
        }

        @Test
        public void testUpdateCartItems_WhenNoSelectedBookId() {
            // Arrange
            when(mockRequest.getParameter("selectedBookId")).thenReturn(null);
            when(mockRequest.getSession()).thenReturn(mockSession);

            // Act
            StoreUtil.updateCartItems(mockRequest);

            // Assert
            verify(mockSession, never()).setAttribute(anyString(), any());
            verify(mockSession, never()).removeAttribute(anyString());
        }
    }

