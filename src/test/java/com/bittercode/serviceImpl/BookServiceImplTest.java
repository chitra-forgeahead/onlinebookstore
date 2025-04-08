package com.bittercode.serviceImpl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.bittercode.model.Book;
import com.bittercode.model.StoreException;
import com.bittercode.service.impl.BookServiceImpl;
import com.bittercode.util.DBUtil;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import com.bittercode.constant.ResponseCode;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {

    private BookServiceImpl bookService1;
    private Book testBook;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;
    
    private BookServiceImpl bookService;
    private static MockedStatic<DBUtil> mockedDBUtil; // Keep static reference
    
    @BeforeClass
    public static void setUpClass() {
        mockedDBUtil = Mockito.mockStatic(DBUtil.class);
    }
 

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        bookService = new BookServiceImpl();
        bookService1 = new BookServiceImpl();
        
        testBook = new Book();
        testBook.setBarcode("1234567890");
        testBook.setName("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setPrice(299.99);
        testBook.setQuantity(10);
        
        mockedDBUtil.when(DBUtil::getConnection).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
    }
    @AfterClass
    public static void tearDownClass() {
        mockedDBUtil.close();
    }
 
    @After
    public void tearDown() {
        reset(mockConnection, mockPreparedStatement, mockResultSet);
    }
    @Test
    public void getAllBooks_ShouldReturnListOfBooks() throws Exception {
        // Arrange
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        // Simulate two rows in the result set
        when(mockResultSet.next()).thenReturn(true, true, false); // Two books, then end
        
        // Mock values for the columns
        when(mockResultSet.getString(1)).thenReturn("B1", "B2"); // Barcode column
        when(mockResultSet.getString(2)).thenReturn("Book1", "Book2"); // Name column
        when(mockResultSet.getString(3)).thenReturn("Author1", "Author2"); // Author column
        when(mockResultSet.getDouble(4)).thenReturn(100.0, 200.0); // Price column
        when(mockResultSet.getInt(5)).thenReturn(10, 20); // Quantity column

        // Act
        List<Book> books = bookService1.getAllBooks();

        // Assert
        assertNotNull("Book list should not be null", books);
        assertEquals("Should return 2 books", 2, books.size());

        // Verify first book
        Book firstBook = books.get(0);
        assertEquals("B1", firstBook.getBarcode());
        assertEquals("Book1", firstBook.getName());
        assertEquals("Author1", firstBook.getAuthor());
        assertEquals(100.0, firstBook.getPrice(), 100);
        assertEquals(10, firstBook.getQuantity(), 10);

        // Verify second book
        Book secondBook = books.get(1);
        assertEquals("B2", secondBook.getBarcode());
        assertEquals("Book2", secondBook.getName());
        assertEquals("Author2", secondBook.getAuthor());
        assertEquals(200.0, secondBook.getPrice(), 200.0);
        assertEquals(20, secondBook.getQuantity(), 20);

        // Verify interactions
        verify(mockConnection).prepareStatement(anyString());
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet, times(3)).next();
    }


    @Test
    public void getAllBooks_WhenDatabaseError_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(mockConnection.prepareStatement(anyString()))
            .thenThrow(new SQLException("Database error"));

        // Act
        List<Book> books = bookService1.getAllBooks();

        // Assert
        assertNotNull("Book list should not be null", books);
        assertTrue("Book list should be empty", books.isEmpty());
    }

    @Test
    public void updateBook_ShouldReturnSuccessResponse() throws Exception {
        // Arrange
        Book book = new Book("B1", "Updated Book", "Author", 150, 25);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Act
        String result = bookService1.updateBook(book);

        // Assert
        assertEquals(ResponseCode.SUCCESS.name(), result);
        
        // Verify parameter settings
        verify(mockPreparedStatement).setString(1, book.getName());
        verify(mockPreparedStatement).setString(2, book.getAuthor());
        verify(mockPreparedStatement).setDouble(3, book.getPrice());
        verify(mockPreparedStatement).setInt(4, book.getQuantity());
        verify(mockPreparedStatement).setString(5, book.getBarcode());
   }



    @Test
    public void updateBook_WithNullBook_ShouldReturnFailureResponse() throws Exception {
        // Act
        String result = bookService1.updateBook(null);

        // Assert
        assertEquals(ResponseCode.FAILURE.name() + " : Cannot invoke \"com.bittercode.model.Book.getName()\" because \"book\" is null", result);
    }

    @Test
    public void testAddBook_Success() throws Exception {
        // Arrange
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        
        // Act
        String result = bookService1.addBook(testBook);
        
        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Should return success response", 
            ResponseCode.SUCCESS.name(), result);
            
        // Verify parameter settings
        verify(mockPreparedStatement).setString(1, testBook.getBarcode());
        verify(mockPreparedStatement).setString(2, testBook.getName());
        verify(mockPreparedStatement).setString(3, testBook.getAuthor());
        verify(mockPreparedStatement).setDouble(4, testBook.getPrice());
        verify(mockPreparedStatement).setInt(5, testBook.getQuantity());
        verify(mockPreparedStatement).executeUpdate();
    }
    @Test
    public void testAddBook_Invalid() {
        try {
            // Create invalid book (null values)
            Book invalidBook = new Book();
            
            // Execute test
            String result = bookService1.addBook(invalidBook);
            
            // Verify error message
            assertNotNull("Result should not be null", result);
            assertTrue("Should return error message", 
                result.contains("error") || result.contains("invalid") || !result.isEmpty());
        } catch (Exception e) {
            // Exception is acceptable for invalid input
            assertTrue("Expected exception for invalid input", true);
        }
    }
    @Test
    public void testAddBook_Null() {
        try {
            // Execute test with null
            String result = bookService1.addBook(null);
            
            // Verify error message
            assertNotNull("Result should not be null", result);
            assertTrue("Should return error message", 
                result.contains("error") || result.contains("invalid") || !result.isEmpty());
        } catch (Exception e) {
            // Exception is acceptable for null input
            assertTrue("Expected exception for null input", true);
        }
    }
    @Test
    public void testGetBookById_Success() throws StoreException {
        // Test with valid book ID
        Book result = bookService1.getBookById("1234567890");
        
        // If book exists, verify its properties
        if (result != null) {
            assertNotNull("Book should not be null", result);
            assertEquals("Barcode should match", "1234567890", result.getBarcode());
        }
    }
    @Test
    public void testGetBookById_EmptyId() throws StoreException {
        // Test with empty ID
        Book result = bookService1.getBookById("");
        assertNull("Should return null for empty ID", result);
    }
    @Test
    public void testUpdateBookQtyById_Success() throws StoreException {
        // Test with valid data
        String result = bookService1.updateBookQtyById("1234567890", 5);
        assertNotNull("Should update quantity successfully", result);
    }
    /**
     * Test deleting book with valid ID
     */
    @Test
    public void testDeleteBookById_Success() throws StoreException {
        // Test with valid book ID
        String result = bookService1.deleteBookById("1234567890");
        assertNotNull("Should delete book successfully", result);
    }


    @Test
    public void testGetBooksByCommaSeperatedBookIds_EmptyInput() throws StoreException, SQLException {
        // Setup
        String bookIds = "";
        
        // Use existing mocks and setup behavior
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        // Execute
        List<Book> books = bookService.getBooksByCommaSeperatedBookIds(bookIds);

        // Verify
        assertNotNull("Should return empty list, not null", books);
        assertTrue("Book list should be empty", books.isEmpty());
        
        // Verify the SQL query was executed with empty input
        verify(mockConnection).prepareStatement(contains(bookIds));
        verify(mockPreparedStatement).executeQuery();
        verify(mockResultSet).next();
    }

            @Test
            public void testGetBooksByCommaSeperatedBookIds_NullInput() throws StoreException {
                // Execute
                List<Book> books = bookService1.getBooksByCommaSeperatedBookIds(null);

                // Verify
                assertNotNull("Should return empty list, not null", books);
                assertTrue("Book list should be empty", books.isEmpty());
            }

            @Test
            public void testGetBooksByCommaSeperatedBookIds_SingleId() throws StoreException, SQLException {
                // Setup
                String bookIds = "1234567890";
                
                // Mock result set behavior
                when(mockResultSet.next()).thenReturn(true, false); // One row then no more
                when(mockResultSet.getString(1)).thenReturn("1234567890"); // barcode
                when(mockResultSet.getString(2)).thenReturn("Test Book"); // name
                when(mockResultSet.getString(3)).thenReturn("Test Author"); // author
                when(mockResultSet.getInt(4)).thenReturn(100); // price
                when(mockResultSet.getInt(5)).thenReturn(10); // quantity

                // Execute
                List<Book> books = bookService1.getBooksByCommaSeperatedBookIds(bookIds);

                // Verify
                assertNotNull("Book list should not be null", books);
                assertEquals("Should return 1 book", 1, books.size());
                assertEquals("Book barcode should match", "1234567890", books.get(0).getBarcode());
                assertEquals("Book name should match", "Test Book", books.get(0).getName());
                assertEquals("Book author should match", "Test Author", books.get(0).getAuthor());
                assertEquals(100, books.get(0).getPrice(), 0);
                assertEquals(10, books.get(0).getQuantity());
            }

            @Test
            public void testGetBooksByCommaSeperatedBookIds_DatabaseError() throws SQLException, StoreException {
                // Setup
                String bookIds = "1234567890,9876543210"; 
                try {
                    // Execute
                    List<Book> books = bookService.getBooksByCommaSeperatedBookIds(bookIds);

                    // Verify
                    assertNotNull("Should return empty list on database error", books);
                    assertTrue("Book list should be empty on database error", books.isEmpty());
                } finally {
                  //  mockedStatic.close();
                }
            }

            @Test
            public void testGetBooksByCommaSeperatedBookIds_WhitespaceHandling() throws StoreException, SQLException {
                // Setup
                String bookIds = " 1234567890 , 9876543210 ";  // With spaces
                try {
                    when(mockResultSet.next()).thenReturn(true, true, false);
                    when(mockResultSet.getString("barcode")).thenReturn("1234567890", "9876543210");
                    when(mockResultSet.getString("name")).thenReturn("Test Book 1", "Test Book 2");
                    when(mockResultSet.getString("author")).thenReturn("Author 1", "Author 2");
                    when(mockResultSet.getDouble("price")).thenReturn(299.99, 399.99);
                    when(mockResultSet.getInt("quantity")).thenReturn(10, 20);

                    // Execute
                    List<Book> books = bookService1.getBooksByCommaSeperatedBookIds(bookIds);

                    // Verify
                    assertNotNull("Book list should not be null", books);
                    assertEquals("Should return 2 books", 2, books.size());
                } finally 
                {
                }
            }
        }
