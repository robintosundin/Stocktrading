import java.util.Objects;

public class Transaction {
    public Transaction(String sellerName, String buyerName, int price) {
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.price = price;
    }
    public final String sellerName, buyerName;
    public final int price;
    
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Transaction)) return false;
        Transaction t = (Transaction)o;
        return sellerName.equals(t.sellerName) &&
               buyerName.equals(t.buyerName) &&
               price == t.price;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(sellerName, buyerName, price);
    }

    @Override
    public String toString() {
        return "new Transaction(\"" + sellerName + "\", \"" + buyerName + "\", " + price + ")";
    }
}
