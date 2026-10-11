//Austin Radloff
//p.387

public class NonFiction extends Book {
    public NonFiction(String bookTitle) {
        super(bookTitle);
        setPrice();
    }

    public void setPrice() {
        price = 37.99;
    }
}