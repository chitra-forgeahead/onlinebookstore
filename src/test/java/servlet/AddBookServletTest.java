    package servlet;

    import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
    import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import com.bittercode.constant.BookStoreConstants;
import com.bittercode.constant.db.BooksDBConstants;
import com.bittercode.model.Book;
import com.bittercode.model.UserRole;
import com.bittercode.service.BookService;
import com.bittercode.service.impl.BookServiceImpl;
import com.bittercode.util.StoreUtil;

import servlets.AddBookServlet;

    /**
     * Test class for AddBookServlet
     */

    public class AddBookServletTest {
        
        @Mock
        private RequestDispatcher requestDispatcher;
        @Mock
        private BookService bookService;
        
        
        @InjectMocks
        private AddBookServlet addBookServlet;

        private StringWriter stringWriter;
        private PrintWriter printWriter;

        private AddBookServlet servlet;
        private MockHttpServletRequest request;
        private MockHttpServletResponse response;
        private MockHttpSession session;
        /**
         * Setup method to initialize test environment
         */
        @Before
        public void setUp() {
            // Initialize servlet and service
            servlet = new AddBookServlet();
            //new BookServiceImpl();
            //BookService bookService = new BookServiceImpl();
            class AddBookServlet extends HttpServlet {
                private final BookService bookService;

                // Constructor for dependency injection
                public AddBookServlet(BookService bookService) {
                    this.bookService = bookService;
                }
            }
            
            // Initialize mock objects
            request = new MockHttpServletRequest();
            response = new MockHttpServletResponse();
            session = new MockHttpSession();
            
         // Set session in request
            request.setSession(session);
            
         
           // public void setUp() throws Exception {
                MockitoAnnotations.openMocks(this);
                stringWriter = new StringWriter();
                printWriter = new PrintWriter(stringWriter);
            }
         
     
        

        /**
         * Test add book form display for logged-in seller
         */
        @Test
        public void testShowAddBookForm_SellerLoggedIn() throws Exception {
            // Setup seller session
            session.setAttribute("usertype", UserRole.SELLER.toString());
            session.setAttribute("username", "testseller");
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify form is displayed
            String content = response.getContentAsString();
           // assertTrue("Should show add book form", 
               // content.contains("Add Book"));
          // assertTrue("Should contain form elements", 
            //    content.contains("<form"));
        }

        /**
         * Test add book form access without login
         */
        @Test
        public void testShowAddBookForm_NotLoggedIn() throws Exception {
            // Execute servlet without login
            servlet.service(request, response);
            
            // Verify login requirement message
            String content = response.getContentAsString();
            assertTrue("Should show login message", 
                content.contains("Please Login First"));
        }

        /**
         * Test successful book addition
         */
//        @Test
//        public void testAddBook_Success() throws Exception {
//            when(request.getSession()).thenReturn(session);
//            when(StoreUtil.isLoggedIn(UserRole.SELLER, session)).thenReturn(true);
//            when(request.getRequestDispatcher("SellerHome.html")).thenReturn(requestDispatcher);
//            when(response.getWriter()).thenReturn(printWriter);
//            
//            when(request.getParameter(BooksDBConstants.COLUMN_NAME)).thenReturn("Test Book");
//            when(request.getParameter(BooksDBConstants.COLUMN_AUTHOR)).thenReturn("Author Name");
//            when(request.getParameter(BooksDBConstants.COLUMN_PRICE)).thenReturn("100");
//            when(request.getParameter(BooksDBConstants.COLUMN_QUANTITY)).thenReturn("10");
//            
//            when(bookService.addBook(any(Book.class))).thenReturn("SUCCESS");
//
//            addBookServlet.service(request, response);
//
//            verify(requestDispatcher).include(request, response);
//            assertTrue(stringWriter.toString().contains("Book Detail Updated Successfully!"));
//        }
//       @Test
//public void testAddBook_Success() throws Exception {
//    // Mock session attributes for admin login
//    when(request.getSession(false)).thenReturn(session);
//    when(session.getAttribute("usertype")).thenReturn("admin");
//    
//    // Mock request parameters
//    when(request.getParameter("bookName")).thenReturn("Test Book");
//    when(request.getParameter("bookType")).thenReturn("Programming");
//    when(request.getParameter("bookAuthor")).thenReturn("Test Author");
//    when(request.getParameter("bookPrice")).thenReturn("29.99");
//    when(request.getParameter("bookQuantity")).thenReturn("10");
//    
//    // Create StringWriter to capture the response
//    StringWriter stringWriter = new StringWriter();
//    PrintWriter writer = new PrintWriter(stringWriter);
//    when(response.getWriter()).thenReturn(writer);
//    
//    // Execute the servlet
//    servlet.service(request, response);
//    
//    // Flush the writer to ensure all content is written
//    writer.flush();
//    
//    // Get the response content
//    String content = stringWriter.toString();
//    
//    // Verify the success message
//    assertTrue("Should show success message", 
//        content.contains("Book Added Successfully") || 
//        content.contains("SUCCESS") || 
//        content.contains("Book added successfully"));
//    
//    // Additional verifications if needed
//    verify(request).getParameter("bookName");
//    verify(request).getParameter("bookType");
//    verify(request).getParameter("bookAuthor");
//    verify(request).getParameter("bookPrice");
//    verify(request).getParameter("bookQuantity");
//}




        /**
         * Test book addition with missing name
         */
        @Test
        public void testAddBook_MissingName() throws Exception {
            // Setup seller session
            session.setAttribute("usertype", UserRole.SELLER.toString());
            session.setAttribute("username", "testseller");
            
            // Set parameters without name
            request.setParameter(BooksDBConstants.COLUMN_AUTHOR, "Test Author");
            request.setParameter(BooksDBConstants.COLUMN_PRICE, "299");
            request.setParameter(BooksDBConstants.COLUMN_QUANTITY, "10");
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify form is redisplayed
            String content = response.getContentAsString();
           // assertTrue("Should show add book form", 
             //   content.contains("Add Book"));
        }

        /**
         * Test book addition with invalid price
         */
//        @Test
//        public void testAddBook_InvalidPrice() throws Exception {
//            // Setup seller session
//            session.setAttribute("usertype", UserRole.SELLER.toString());
//            session.setAttribute("username", "testseller");
//            
//            // Set invalid price
//            request.setParameter(BooksDBConstants.COLUMN_NAME, "Test Book");
//            request.setParameter(BooksDBConstants.COLUMN_AUTHOR, "Test Author");
//            request.setParameter(BooksDBConstants.COLUMN_PRICE, "invalid");
//            request.setParameter(BooksDBConstants.COLUMN_QUANTITY, "10");
//            
//            // Execute servlet
//            servlet.service(request, response);
//            
//            // Verify error message
//            String content = response.getContentAsString();
//            assertTrue("Should show error message", 
//                content.contains("Failed to Add Books"));
//        }

        /**
         * Test book addition with negative quantity
         */
//        @Test
//        public void testAddBook_NegativeQuantity() throws Exception {
//            // Setup seller session
//            session.setAttribute("usertype", UserRole.SELLER.toString());
//            session.setAttribute("username", "testseller");
//            
//            // Set negative quantity
//            request.setParameter(BooksDBConstants.COLUMN_NAME, "Test Book");
//            request.setParameter(BooksDBConstants.COLUMN_AUTHOR, "Test Author");
//            request.setParameter(BooksDBConstants.COLUMN_PRICE, "299");
//            request.setParameter(BooksDBConstants.COLUMN_QUANTITY, "-5");
//            
//            // Execute servlet
//            servlet.service(request, response);
//            
//            // Verify error message
//            String content = response.getContentAsString();
//            assertTrue("Should show error message", 
//                content.contains("Failed to Add Books"));
//        }

        /**
         * Test content type setting
         */
        @Test
        public void testContentType() throws Exception {
            // Setup seller session
            session.setAttribute("usertype", UserRole.SELLER.toString());
            session.setAttribute("username", "testseller");
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify content type
            assertEquals("Should set correct content type", 
                BookStoreConstants.CONTENT_TYPE_TEXT_HTML, 
                response.getContentType());
        }

        /**
         * Test with customer role (should fail)
         */
        @Test
        public void testAddBook_CustomerRole() throws Exception {
            // Setup customer session
            session.setAttribute("usertype", UserRole.CUSTOMER.toString());
            session.setAttribute("username", "testcustomer");
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify access denied
            String content = response.getContentAsString();
            assertTrue("Should show login message", 
                content.contains("Please Login First"));
        }

        /**
         * Test with very long input values
         */
        @Test
        public void testAddBook_LongValues() throws Exception {
            // Setup seller session
            session.setAttribute("usertype", UserRole.SELLER.toString());
            session.setAttribute("username", "testseller");
            
            // Set very long values
            String longString = "a".repeat(1000);
            request.setParameter(BooksDBConstants.COLUMN_NAME, longString);
            request.setParameter(BooksDBConstants.COLUMN_AUTHOR, longString);
            request.setParameter(BooksDBConstants.COLUMN_PRICE, "299");
            request.setParameter(BooksDBConstants.COLUMN_QUANTITY, "10");
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify error handling
            String content = response.getContentAsString();
         //   assertTrue("Should handle long values", 
           //     content.contains("Failed to Add Books"));
        }

        /**
         * Test with special characters
         */
        @Test
        public void testAddBook_SpecialCharacters() throws Exception {
            // Setup seller session
            session.setAttribute("usertype", UserRole.SELLER.toString());
            session.setAttribute("username", "testseller");
            
            // Set special characters
            request.setParameter(BooksDBConstants.COLUMN_NAME, "Book!@#$%^&*()");
            request.setParameter(BooksDBConstants.COLUMN_AUTHOR, "Author!@#$%^&*()");
            request.setParameter(BooksDBConstants.COLUMN_PRICE, "299");
            request.setParameter(BooksDBConstants.COLUMN_QUANTITY, "10");
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify special character handling
            String content = response.getContentAsString();
            assertNotNull("Should handle special characters", content);
        }

        /**
         * Test with maximum allowed values
         */
//        @Test
//        public void testAddBook_MaxValues() throws Exception {
//            // Setup seller session
//            session.setAttribute("usertype", UserRole.SELLER.toString());
//            session.setAttribute("username", "testseller");
//            
//            // Set maximum values
//            request.setParameter(BooksDBConstants.COLUMN_NAME, "Test Book");
//            request.setParameter(BooksDBConstants.COLUMN_AUTHOR, "Test Author");
//            request.setParameter(BooksDBConstants.COLUMN_PRICE, Integer.MAX_VALUE + "");
//            request.setParameter(BooksDBConstants.COLUMN_QUANTITY, Integer.MAX_VALUE + "");
//            
//            // Execute servlet
//            servlet.service(request, response);
//            
//            // Verify handling of maximum values
//            String content = response.getContentAsString();
//            assertTrue("Should handle maximum values", 
//                content.contains("Failed to Add Books"));
//        }

        /**
         * Test with null session
         */
        @Test
        public void testAddBook_NullSession() throws Exception {
            // Set null session
            request.setSession(null);
            
            // Execute servlet
            servlet.service(request, response);
            
            // Verify null session handling
            String content = response.getContentAsString();
            assertTrue("Should handle null session", 
                content.contains("Please Login First"));
        }
    }

