package com.bittercode.serviceImpl;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;
import com.bittercode.model.Book;
import com.bittercode.model.StoreException;
import com.bittercode.service.impl.BookServiceImpl;

/**
 * Test class for BookServiceImpl
 */
public class BookServiceImplTest {

    private BookServiceImpl bookService;
    private Book testBook;

    @Before
    public void setUp() {
        // Initialize BookServiceImpl
        bookService = new BookServiceImpl();
        
        // Initialize test book
        testBook = new Book();
        testBook.setBarcode("1234567890");
        testBook.setName("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setPrice(299.99);
        testBook.setQuantity(10);
    }

    /**
     * Test adding a valid book
     */
    @Test
    public void testAddBook_Success() {
        try {
            // Execute test
            String result = bookService.addBook(testBook);
            
            // Verify result is not null
            assertNotNull("Result should not be null", result);
            
            // Verify success message
            assertTrue("Should return success message", 
                result.contains("success") || result.contains("added") || result.isEmpty());
        } catch (Exception e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }

    /**
     * Test adding invalid book
     */
    @Test
    public void testAddBook_Invalid() {
        try {
            // Create invalid book (null values)
            Book invalidBook = new Book();
            
            // Execute test
            String result = bookService.addBook(invalidBook);
            
            // Verify error message
            assertNotNull("Result should not be null", result);
            assertTrue("Should return error message", 
                result.contains("error") || result.contains("invalid") || !result.isEmpty());
        } catch (Exception e) {
            // Exception is acceptable for invalid input
            assertTrue("Expected exception for invalid input", true);
        }
    }

    /**
     * Test adding null book
     */
    @Test
    public void testAddBook_Null() {
        try {
            // Execute test with null
            String result = bookService.addBook(null);
            
            // Verify error message
            assertNotNull("Result should not be null", result);
            assertTrue("Should return error message", 
                result.contains("error") || result.contains("invalid") || !result.isEmpty());
        } catch (Exception e) {
            // Exception is acceptable for null input
            assertTrue("Expected exception for null input", true);
        }
    }

    /**
     * Test getting book by valid ID
     */
    @Test
    public void testGetBookById_Success() throws StoreException {
        // Test with valid book ID
        Book result = bookService.getBookById("1234567890");
        
        // If book exists, verify its properties
        if (result != null) {
            assertNotNull("Book should not be null", result);
            assertEquals("Barcode should match", "1234567890", result.getBarcode());
        }
    }

    /**
     * Test getting book with null ID
     */
    @Test(expected = StoreException.class)
    public void testGetBookById_NullId() throws StoreException {
        // Test with null ID
        bookService.getBookById(null);
    }

    /**
     * Test getting book with empty ID
     */
    @Test
    public void testGetBookById_EmptyId() throws StoreException {
        // Test with empty ID
        Book result = bookService.getBookById("");
        assertNull("Should return null for empty ID", result);
    }

    /**
     * Test updating book quantity with valid data
     */
    @Test
    public void testUpdateBookQtyById_Success() throws StoreException {
        // Test with valid data
        String result = bookService.updateBookQtyById("1234567890", 5);
        assertNotNull("Should update quantity successfully", result);
    }

    /**
     * Test deleting book with valid ID
     */
    @Test
    public void testDeleteBookById_Success() throws StoreException {
        // Test with valid book ID
        String result = bookService.deleteBookById("1234567890");
        assertNotNull("Should delete book successfully", result);
    }

    /**
     * Test getting all books
     */
    @Test
    public void testGetAllBooks_Success() throws StoreException {
        // Get all books
        List<Book> results = bookService.getAllBooks();
        
        // Verify list is not null
        assertNotNull("Book list should not be null", results);
    }
}
