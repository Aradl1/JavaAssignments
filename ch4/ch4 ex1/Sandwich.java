//Austin Radloff
//p.156 ex 1
public class Sandwich {
    private String mainIngredient;
    private String breadType;
    private double price;

    public void setMainIngredient(String ingredient) {
        mainIngredient = ingredient;
    }

    public String getMainIngredient() {
        return mainIngredient;
    }

    public void setBreadType(String bread) {
        breadType = bread;
    }

    public String getBreadType() {
        return breadType;
    }

    public void setPrice(double sandwichPrice) {
        price = sandwichPrice;
    }

    public double getPrice() {
        return price;
    }
}