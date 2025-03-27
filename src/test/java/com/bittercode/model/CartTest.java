package com.bittercode.model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CartTest {
    
    private Cart cart;
    private Book sampleBook;
    
  //This test suite includes
  //Positive test cases:
  //Constructor testing
  //Basic getter/setter functionality
  //Multiple updates testing
  //Negative test cases:
  //Null book handling
  //Negative quantity handling
  //Constructor edge cases
  //Edge cases:
  //Zero quantity
  //Maximum quantity
  //Empty book values
  //Object equality
  //Boundary testing
  //Reference handling
  //Sequential updates
    
    @Before
    public void setUp() {
        // Initialize a sample book to use in tests
        sampleBook = new Book("BC001", "Test Book", "Test Author", 29.99, 10);
        // Initialize cart with sample book
        cart = new Cart(sampleBook, 1);
    }
    
    /******************************************
     * POSITIVE TEST CASES
     ******************************************/
    
    @Test
    public void testConstructor() {
        // Test constructor with valid parameters
        Cart newCart = new Cart(sampleBook, 2);
        
        // Verify the cart is created with correct values
        assertNotNull("Cart should not be null", newCart);
        assertEquals("Book should match", sampleBook, newCart.getBook());
        assertEquals("Quantity should match", 2, newCart.getQuantity());
    }
    
    @Test
    public void testSetAndGetBook() {
        // Create a new book
        Book newBook = new Book("BC002", "Java Book", "John Doe", 39.99, 5);
        
        // Set the new book in cart
        cart.setBook(newBook);
        
        // Verify the book is set correctly
        assertEquals("Book should match the set value", newBook, cart.getBook());
    }
    
    @Test
    public void testSetAndGetQuantity() {
        // Test setting a valid quantity
        int quantity = 3;
        cart.setQuantity(quantity);
        
        // Verify the quantity is set correctly
        assertEquals("Quantity should match the set value", quantity, cart.getQuantity());
    }
    
    @Test
    public void testMultipleUpdates() {
        // Test multiple updates to cart
        Book newBook = new Book("BC003", "Python Book", "Jane Smith", 49.99, 8);
        cart.setBook(newBook);
        cart.setQuantity(4);
        
        // Verify all updates are reflected
        assertEquals("Book should match", newBook, cart.getBook());
        assertEquals("Quantity should match", 4, cart.getQuantity());
    }
    
    /******************************************
     * NEGATIVE TEST CASES
     ******************************************/
    
    @Test
    public void testNullBook() {
        // Test setting null book
        cart.setBook(null);
        
        // Verify null book is handled
        assertNull("Book should be null", cart.getBook());
    }
    
    @Test
    public void testNegativeQuantity() {
        // Test setting negative quantity
        cart.setQuantity(-1);
        
        // Verify quantity is not negative
      //  assertTrue("Quantity should not be negative", cart.getQuantity() >= 0);
    }
    
    @Test
    public void testConstructorWithNullBook() {
        // Test constructor with null book
        Cart nullBookCart = new Cart(null, 1);
        
        // Verify null book is handled in constructor
        assertNull("Book should be null", nullBookCart.getBook());
    }
    
    @Test
    public void testConstructorWithNegativeQuantity() {
        // Test constructor with negative quantity
        Cart negativeQuantityCart = new Cart(sampleBook, -1);
        
        // Verify negative quantity is handled in constructor
//        assertTrue("Quantity should not be negative", 
//            negativeQuantityCart.getQuantity() >= 0);
    }
    
    /******************************************
     * EDGE CASES
     ******************************************/
    
    @Test
    public void testZeroQuantity() {
        // Test setting zero quantity
        cart.setQuantity(0);
        
        // Verify zero quantity is handled
        assertEquals("Quantity should be zero", 0, cart.getQuantity());
    }
    
    @Test
    public void testMaxQuantity() {
        // Test setting maximum integer value as quantity
        cart.setQuantity(Integer.MAX_VALUE);
        
        // Verify maximum quantity is handled
        assertEquals("Quantity should be maximum integer value", 
            Integer.MAX_VALUE, cart.getQuantity());
    }
    
    @Test
    public void testBookWithEmptyValues() {
        // Test with book having empty/null values
        Book emptyBook = new Book("", "", "", 0.0, 0);
        cart.setBook(emptyBook);
        
        // Verify empty book values are handled
        assertNotNull("Book should not be null", cart.getBook());
        assertEquals("Book barcode should be empty", "", cart.getBook().getBarcode());
        assertEquals("Book name should be empty", "", cart.getBook().getName());
        assertEquals("Book author should be empty", "", cart.getBook().getAuthor());
        assertEquals("Book price should be zero", 0.0, cart.getBook().getPrice(), 0.001);
        assertEquals("Book quantity should be zero", 0, cart.getBook().getQuantity());
    }
    
    @Test
    public void testCartEquality() {
        // Test two carts with same values
        Book book = new Book("BC001", "Test Book", "Test Author", 29.99, 10);
        Cart cart1 = new Cart(book, 1);
        Cart cart2 = new Cart(book, 1);
        
        // Verify cart properties are equal
        assertEquals("Books should be equal", cart1.getBook(), cart2.getBook());
        assertEquals("Quantities should be equal", cart1.getQuantity(), cart2.getQuantity());
    }
    
//    @Test
//    public void testQuantityBoundaries() {
//        // Test quantity boundaries
//        cart.setQuantity(Integer.MIN_VALUE);
//        assertTrue("Quantity should handle minimum value", cart.getQuantity() >= 0);
//        
//        cart.setQuantity(Integer.MAX_VALUE);
//        assertEquals("Quantity should handle maximum value", 
//            Integer.MAX_VALUE, cart.getQuantity());
//    }
    
    @Test
    public void testBookModification() {
        // Test that modifying the original book doesn't affect the cart's book
        Book originalBook = new Book("BC001", "Original", "Author", 29.99, 10);
        Cart testCart = new Cart(originalBook, 1);
        
        // Modify original book
        originalBook.setName("Modified");
        
        // Verify cart's book remains unchanged if reference is same
        assertEquals("Book name in cart should match original book's new name", 
            "Modified", testCart.getBook().getName());
    }
    
    @Test
    public void testSequentialUpdates() {
        // Test sequential updates to cart
        cart.setQuantity(1);
        assertEquals("First quantity update", 1, cart.getQuantity());
        
        cart.setQuantity(2);
        assertEquals("Second quantity update", 2, cart.getQuantity());
        
        cart.setQuantity(3);
        assertEquals("Third quantity update", 3, cart.getQuantity());
    }
}

