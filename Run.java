import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Perform manual interactive testing of a Stock trade priority queue implementation.
 * @author Chalmers
 */
public class Run {

    private static class nameComparator implements Comparator<Bid>{
        @Override
        public int compare(Bid o1, Bid o2) {
            return o1.name.compareTo(o2.name);
        }
    }

    private static PrioQueue<Bid> makeAlphabeticalQueue(Iterator<Bid> iter) {
        PrioQueue<Bid> q = new BinHeap<>(new nameComparator());
        while (iter.hasNext()) {
            q.add(iter.next());
        }
        return q;
    }
    
    public static void main(String[] args) {
        Pattern parsePattern =	// compiled pattern used to check if allowed parameter format
            Pattern.compile(
                "\\s*(?:"+ 
                "(?:(\\S+)\\s+(s|b)\\s+(\\d+))|" +
                "(l)|" +
                "q)" +
                "\\s*"
            ); 

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        StockTrade st = new StockTrade();
        
        for (;;) {
            String inputLine = null;
            try {
                inputLine = r.readLine();
            } catch (IOException e) {
                System.err.println("reading from standard input failed");
                e.printStackTrace();
                System.exit(1);
            }
            Matcher m = parsePattern.matcher(inputLine);
            if (m.matches()) {	// true if inputLine matches compiled format
                if (m.group(1) != null) {  // new bid
                    Bid b = new Bid(m.group(1), Integer.parseInt(m.group(3)));
                    Transaction t = null;
                    if (m.group(2).equals("s")) {
                        t = st.placeSellBid(b);
                    } else {  // equals "b"
                        t = st.placeBuyBid(b);
                    }
                    if (t != null) {
                        System.out.println(t.sellerName + " sells to " + t.buyerName + " at " + t.price + " SEK");
                    }
                } else if (m.group(4) != null) {  // list bids
                    PrioQueue<Bid> sellersQueue = makeAlphabeticalQueue(st.sellBidsIterator());
                    System.out.println("sellers:");
                    while (sellersQueue.peek() != null) {
                        Bid b = sellersQueue.poll();
                        System.out.println(b.name + " " + b.price + " SEK");
                    }

                    PrioQueue<Bid> buyersQueue = makeAlphabeticalQueue(st.buyBidsIterator());
                    System.out.println("buyers:");
                    while (buyersQueue.peek() != null) {
                        Bid b = buyersQueue.poll();
                        System.out.println(b.name + " " + b.price + " SEK");
                    }
                } else {  // quit
                    break;
                }
            } else {
                System.err.println("failed parsing input line");
                System.err.println("expected syntax:");
                System.err.println("<name> s <price>  -- place/update selling bid for <name> at <price> SEK");
                System.err.println("<name> b <price>  -- place/update buying bid for <name> at <price> SEK");
                System.err.println("l  -- show all selling and buying bids in name order");
                System.err.println("q  -- quit");
                System.exit(1);
            }
        }
    }

}
