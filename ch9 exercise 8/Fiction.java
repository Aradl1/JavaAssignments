//Austin Radloff
//p.387

public class Fiction extends Book {
    public Fiction(String bookTitle) {
        super(bookTitle);
        setPrice();
    }

    public void setPrice() {
        price = 24.99;
    }
}