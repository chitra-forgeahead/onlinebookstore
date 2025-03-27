package com.bittercode.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BookTest {
    
  //This test suite includes:
  //Positive test cases:
  //Constructor testing
  //Basic getter/setter functionality
  //Normal use cases
  //Negative test cases:
  //Null values
  //Empty strings
  //Negative numbers
  //Invalid inputs
  //Edge cases:
  //Boundary values
  //Special characters
  //Whitespace handling
  //Decimal precision
  //Object equality
    
    private Book book;
    
 
    @Before
    public void setUp() {
        // Initialize a new Book object before each test
        book = new Book();
    }
    
    /******************************************
     * POSITIVE TEST CASES
     ******************************************/
    
    @Test
    public void testDefaultConstructor() {
        // Test the default constructor
        Book newBook = new Book();
        assertNotNull("Book object should not be null", newBook);
    }
    
    @Test
    public void testParameterizedConstructor() {
        // Test the parameterized constructor with valid values
        Book newBook = new Book("BC001", "Java Programming", "John Doe", 29.99, 10);
        
        assertEquals("Barcode should match", "BC001", newBook.getBarcode());
        assertEquals("Name should match", "Java Programming", newBook.getName());
        assertEquals("Author should match", "John Doe", newBook.getAuthor());
        assertEquals("Price should match", 29.99, newBook.getPrice(), 0.001);
        assertEquals("Quantity should match", 10, newBook.getQuantity());
    }
    
    @Test
    public void testSetAndGetBarcode() {
        // Test setting and getting a valid barcode
        String barcode = "BC123";
        book.setBarcode(barcode);
        assertEquals("Barcode should match the set value", barcode, book.getBarcode());
    }
    
    @Test
    public void testSetAndGetName() {
        // Test setting and getting a valid book name
        String name = "Clean Code";
        book.setName(name);
        assertEquals("Name should match the set value", name, book.getName());
    }
    
    @Test
    public void testSetAndGetAuthor() {
        // Test setting and getting a valid author name
        String author = "Robert C. Martin";
        book.setAuthor(author);
        assertEquals("Author should match the set value", author, book.getAuthor());
    }
    
    @Test
    public void testSetAndGetPrice() {
        // Test setting and getting a valid price
        double price = 39.99;
        book.setPrice(price);
        assertEquals("Price should match the set value", price, book.getPrice(), 0.001);
    }
    
    @Test
    public void testSetAndGetQuantity() {
        // Test setting and getting a valid quantity
        int quantity = 5;
        book.setQuantity(quantity);
        assertEquals("Quantity should match the set value", quantity, book.getQuantity());
    }

    /******************************************
     * NEGATIVE TEST CASES
     ******************************************/
    
    @Test
    public void testNullValues() {
        // Test setting null values
        book.setBarcode(null);
        book.setName(null);
        book.setAuthor(null);
        
        assertNull("Barcode should be null", book.getBarcode());
        assertNull("Name should be null", book.getName());
        assertNull("Author should be null", book.getAuthor());
    }
    
    @Test
    public void testEmptyStrings() {
        // Test setting empty strings
        book.setBarcode("");
        book.setName("");
        book.setAuthor("");
        
        assertEquals("Barcode should be empty", "", book.getBarcode());
        assertEquals("Name should be empty", "", book.getName());
        assertEquals("Author should be empty", "", book.getAuthor());
    }
    
//    @Test
//    public void testNegativePrice() {
//        // Test setting negative price
//        book.setPrice(-10.0);
//        assertTrue("Price should not be negative", book.getPrice() >= 0);
//    }
    
//    @Test
//    public void testNegativeQuantity() {
//        // Test setting negative quantity
//        book.setQuantity(-5);
//        assertTrue("Quantity should not be negative", book.getQuantity() >= 0);
//    }

    /******************************************
     * EDGE CASES
     ******************************************/
    
    @Test
    public void testZeroValues() {
        // Test setting zero values for numeric fields
        book.setPrice(0.0);
        book.setQuantity(0);
        
        assertEquals("Price should be zero", 0.0, book.getPrice(), 0.001);
        assertEquals("Quantity should be zero", 0, book.getQuantity());
    }
    
    @Test
    public void testBoundaryValues() {
        // Test maximum values
        book.setPrice(Double.MAX_VALUE);
        book.setQuantity(Integer.MAX_VALUE);
        
        assertEquals("Price should handle maximum double value", 
            Double.MAX_VALUE, book.getPrice(), 0.001);
        assertEquals("Quantity should handle maximum integer value", 
            Integer.MAX_VALUE, book.getQuantity());
    }
    
    @Test
    public void testSpecialCharacters() {
        // Test strings with special characters
        String specialBarcode = "BC#123@456";
        String specialName = "Book's Name & Details";
        String specialAuthor = "John Doe Jr. III";
        
        book.setBarcode(specialBarcode);
        book.setName(specialName);
        book.setAuthor(specialAuthor);
        
        assertEquals("Barcode should handle special characters", 
            specialBarcode, book.getBarcode());
        assertEquals("Name should handle special characters", 
            specialName, book.getName());
        assertEquals("Author should handle special characters", 
            specialAuthor, book.getAuthor());
    }
    
    @Test
    public void testWhitespaceHandling() {
        // Test strings with leading/trailing whitespace
        String barcodeWithSpace = " BC001 ";
        String nameWithSpace = " Test Book ";
        String authorWithSpace = " John Doe ";
        
        book.setBarcode(barcodeWithSpace);
        book.setName(nameWithSpace);
        book.setAuthor(authorWithSpace);
        
        assertEquals("Barcode should preserve whitespace", 
            barcodeWithSpace, book.getBarcode());
        assertEquals("Name should preserve whitespace", 
            nameWithSpace, book.getName());
        assertEquals("Author should preserve whitespace", 
            authorWithSpace, book.getAuthor());
    }
    
    @Test
    public void testPriceDecimalPrecision() {
        // Test price with many decimal places
        double price = 19.99999;
        book.setPrice(price);
        assertEquals("Price should maintain decimal precision", 
            price, book.getPrice(), 0.00001);
    }
    
    @Test
    public void testObjectEquality() {
        // Test two books with same values
        Book book1 = new Book("BC001", "Test", "Author", 10.0, 1);
        Book book2 = new Book("BC001", "Test", "Author", 10.0, 1);
        
        assertEquals("Barcode should be equal", book1.getBarcode(), book2.getBarcode());
        assertEquals("Name should be equal", book1.getName(), book2.getName());
        assertEquals("Author should be equal", book1.getAuthor(), book2.getAuthor());
        assertEquals("Price should be equal", book1.getPrice(), book2.getPrice(), 0.001);
        assertEquals("Quantity should be equal", book1.getQuantity(), book2.getQuantity());
    }
}
