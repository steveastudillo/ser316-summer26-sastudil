import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Sample White-Box tests for the Checkout system.
 * This class demonstrates how to write white-box tests using:
 * - Control Flow Graph (CFG) analysis
 * - Statement coverage
 * - Branch coverage
 * - Path coverage
 *
 * White-box testing focuses on testing the IMPLEMENTATION by
 * examining the code structure and ensuring all paths are tested.
 */
public class CheckoutWhiteBoxSample {

    private Checkout checkout;

    @BeforeEach
    public void setUp() {
        checkout = new Checkout();
    }

    @Test
    @DisplayName("WB Test: countBooksByType - null type branch")
    public void testCountBooksByType_NullType() {
        // Branch: type == null → TRUE
        int result = checkout.countBooksByType(null, false);
        assertEquals(0, result, "Should return 0 for null type");
    }
    @Test
    @DisplayName("WB Sequence B: matching available book")
    public void testAvailableMatchingBook() {
        Book book = new Book("1111111111", "Book", "Author", Book.BookType.FICTION, 1);
        checkout.addBook(book);
        int result = checkout.countBooksByType(Book.BookType.FICTION, true);
        assertEquals(1, result);
    }
    @Test
    @DisplayName("WB Sequence C: count all books")
    public void testCountAllBooks() {
        Book book = new Book("2222222222", "Book", "Author", Book.BookType.FICTION, 1);
        checkout.addBook(book);
        int result = checkout.countBooksByType(Book.BookType.FICTION, false);
        assertEquals(1, result);
    }
    @Test
    @DisplayName("WB Sequence D: non-matching type")
    public void testNonMatchingType() {
        Book book = new Book("3333333333", "Book", "Author", Book.BookType.CHILDREN, 1);
        checkout.addBook(book);
        int result = checkout.countBooksByType(Book.BookType.FICTION, true);
        assertEquals(0, result);
    }
    @Test
    public void testCalculateFineNoOverdue() {
        assertEquals(0.0, checkout.calculateFine(
                0, Book.BookType.FICTION));
    }
    @Test
    public void testCalculateFineFirstTier() {
        assertEquals(1.25, checkout.calculateFine(
                5, Book.BookType.FICTION), 0.01);
    }
    @Test
    public void testCalculateFineTextbook() {
        assertEquals(6.50, checkout.calculateFine(10, Book.BookType.TEXTBOOK), 0.01);
    }
    @Test
    public void testCalculateFineMaxCap() {
        assertEquals(25.0, checkout.calculateFine(50, Book.BookType.FICTION), 0.01);
    }
    // isvalidsbn()
    @Test
    public void testValidISBN10() {
        assertTrue(checkout.isValidISBN("1234567890"));
    }
    @Test
    public void testValidISBN13() {
        assertTrue(checkout.isValidISBN("9781234567890"));
    }
    @Test
    public void testNullISBN() {assertFalse(checkout.isValidISBN(null));
    }
    @Test
    public void testISBNWithLetters() {
        assertFalse(checkout.isValidISBN("ABC123"));
    }
    // isPatronType()
    @Test
    public void testMatchingPatronType() {
        assertTrue(checkout.isPatronType("STUDENT", Patron.PatronType.STUDENT));
    }
    @Test
    public void testNonMatchingPatronType() {
        assertFalse(checkout.isPatronType("FACULTY", Patron.PatronType.STUDENT));
    }
    @Test
    public void testNullPatronTypeString() {
        assertFalse(checkout.isPatronType(null, Patron.PatronType.STUDENT));
    }
    // validatePatronElgibility()
    @Test
    public void testPatronOverdueLimit() {
        Patron patron = new Patron("P1", "Test", "test@test.com", Patron.PatronType.STUDENT);
        patron.setOverdueCount(3);
        assertEquals(4.0, checkout.validatePatronEligibility(patron), 0.01);
    }
}
