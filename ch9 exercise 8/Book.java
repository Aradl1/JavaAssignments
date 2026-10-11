//Austin Radloff
//p.387

public abstract class Book {
    protected String title;
    protected double price;

    public Book(String bookTitle) {
        title = bookTitle;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public abstract void setPrice();
}