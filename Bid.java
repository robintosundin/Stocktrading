import java.util.Objects;

public class Bid {
    public Bid(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public final String name;
    public final int price;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Bid)) return false;
        Bid b = (Bid)o;
        return name.equals(b.name) &&
               price == b.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "new Bid(\"" + name + "\", " + price + ")";
    }
}
