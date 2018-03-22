import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class TestStockTrade {
    enum BidKind {SELL, BUY};
    
    static class Operation {
        final BidKind kind;
        final Bid bid;
        final Transaction expectedTransaction;

        Operation(BidKind kind, Bid bid, Transaction expectedTransaction) {
            this.kind = kind;
            this.bid = bid;
            this.expectedTransaction = expectedTransaction;
        }
    }

    static class Test {
        ArrayList<Operation> operations = new ArrayList<>();
        HashSet<Bid> sellBidsAtEnd = new HashSet<>();
        HashSet<Bid> buyBidsAtEnd = new HashSet<>();
    }

    static int nopdone = 0;
    static int lastnopdone = -1;
    static StringBuilder log = new StringBuilder();

    static void showException(Exception e, StringBuilder log) {
        System.out.println("The operation on the last line of the following code causes an exception:\n");
        System.out.print(log.toString());
        System.out.println("\nThe exception is: " + e.toString());
        System.exit(1);
    }

    static void test(Test t) {
        log = new StringBuilder();

        log.append("StockTrade st = new StockTrade();\n");
        StockTrade st = new StockTrade();

        for (int j = 0; j < t.operations.size(); j++) {
            Operation op = t.operations.get(j);
            log.append("st.place" + (op.kind == BidKind.SELL ? "Sell" : "Buy") + "Bid(" + op.bid.toString() + ");");
            Transaction res = null;
            try {
                res = op.kind == BidKind.SELL ? st.placeSellBid(op.bid) : st.placeBuyBid(op.bid);
            } catch (Exception e) {
                showException(e, log);
            }
            log.append("  // result: " + (res == null ? "null" : res.toString()) + "\n");
            if (res == null && op.expectedTransaction != null ||
                    res != null && !res.equals(op.expectedTransaction)) {
                System.out.println("The result on the last line of the following code is incorrect:\n");
                System.out.print(log.toString());
                System.out.println("The result should be: " + op.expectedTransaction);
                System.exit(1);
            }
            nopdone++;
        }
    
        Iterator<Bid> sellBidsIter = st.sellBidsIterator();
        HashSet<Bid> sellers = new HashSet<>();
        while (sellBidsIter.hasNext()) sellers.add(sellBidsIter.next());

        Iterator<Bid> buyBidsIter = st.buyBidsIterator();
        HashSet<Bid> buyers = new HashSet<>();
        while (buyBidsIter.hasNext()) buyers.add(buyBidsIter.next());

        if (!sellers.equals(t.sellBidsAtEnd) || !buyers.equals(t.buyBidsAtEnd)) {
            System.out.println("The remaining sets of bids was not correct after the following operations:\n");
            System.out.print(log.toString());
            System.out.println();
            System.out.println("The set iterated over by sellBidsIterator() is:");
            System.out.println(sellers.toString());
            if (!sellers.equals(t.sellBidsAtEnd)) {
                System.out.println("The set should be:");
                System.out.println(t.sellBidsAtEnd);
            } else {
                System.out.println("This is correct.");
            }
            System.out.println();
            System.out.println("The set iterated over by buyBidsIterator() is:");
            System.out.println(buyers.toString());
            if (!buyers.equals(t.buyBidsAtEnd)) {
                System.out.println("The set should be:");
                System.out.println(t.buyBidsAtEnd);
            } else {
                System.out.println("This is correct.");
            }
            System.exit(1);
        }
    }

    private static class Monitor implements Runnable {
        @Override
        public void run() {
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    return;
                }
                if (nopdone == lastnopdone) {
                    System.out.println("No new operation completed during the last second.");
                    System.out.println("Your program seems to loop (or be very slow) at the operation on the last line of the following code:\n");
                    System.out.print(log.toString());
                    System.exit(1);
                }
                lastnopdone = nopdone;
                System.out.println(nopdone + " operations executed. (No bugs found so far.)");
            }
        }
        
    }

    public static void main(String[] args) {
        new Thread(new Monitor()).start();
        
        Test t = null;
        int ntest = 0;

        /////////////// Start auto generated
        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B0", 0)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), new Transaction("S0", "B0", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B1", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), new Transaction("S1", "B1", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B1", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B1", 0)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B0", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B0", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), new Transaction("S1", "B0", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S2", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.sellBidsAtEnd.add(new Bid("S2", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B0", 1)));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S2", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B0", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.buyBidsAtEnd.add(new Bid("B1", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), new Transaction("S0", "B1", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B2", 0)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), new Transaction("S0", "B1", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), new Transaction("S2", "B2", 1)));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S2", "B2", 2)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), new Transaction("S1", "B1", 1)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B2", 1)));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), new Transaction("S2", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.buyBidsAtEnd.add(new Bid("B1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B2", 0)));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B1", 1)));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B0", 1)));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), new Transaction("S0", "B2", 1)));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), new Transaction("S2", "B0", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B2", 2)));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), new Transaction("S0", "B0", 2)));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), new Transaction("S1", "B0", 1)));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S0", "B2", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.buyBidsAtEnd.add(new Bid("B1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), new Transaction("S1", "B3", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.sellBidsAtEnd.add(new Bid("S3", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), new Transaction("S2", "B3", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B2", 2)));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), new Transaction("S3", "B2", 0)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B3", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.buyBidsAtEnd.add(new Bid("B1", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S2", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S2", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B2", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), new Transaction("S0", "B2", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), new Transaction("S3", "B2", 3)));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), new Transaction("S0", "B3", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), new Transaction("S2", "B3", 2)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.buyBidsAtEnd.add(new Bid("B1", 3));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), new Transaction("S3", "B1", 2)));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S0", "B2", 2)));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B2", 1)));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), new Transaction("S0", "B1", 2)));
        t.sellBidsAtEnd.add(new Bid("S2", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), new Transaction("S0", "B2", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), new Transaction("S2", "B0", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S0", "B1", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), new Transaction("S1", "B3", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B1", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), new Transaction("S0", "B0", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B3", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S0", "B2", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.sellBidsAtEnd.add(new Bid("S3", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), new Transaction("S2", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.sellBidsAtEnd.add(new Bid("S0", 3));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), new Transaction("S3", "B0", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S0", "B1", 3)));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S2", "B1", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B0", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), new Transaction("S3", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), new Transaction("S2", "B3", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), new Transaction("S3", "B3", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), new Transaction("S2", "B2", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B0", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), new Transaction("S3", "B0", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.buyBidsAtEnd.add(new Bid("B2", 3));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S0", "B2", 2)));
        t.sellBidsAtEnd.add(new Bid("S2", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), new Transaction("S3", "B4", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B1", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B0", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), new Transaction("S2", "B3", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.sellBidsAtEnd.add(new Bid("S4", 2));
        t.sellBidsAtEnd.add(new Bid("S2", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), new Transaction("S3", "B4", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.sellBidsAtEnd.add(new Bid("S4", 3));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), null));
        t.buyBidsAtEnd.add(new Bid("B1", 4));
        t.buyBidsAtEnd.add(new Bid("B0", 3));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.buyBidsAtEnd.add(new Bid("B4", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        t.buyBidsAtEnd.add(new Bid("B2", 3));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), new Transaction("S4", "B2", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), new Transaction("S2", "B4", 4)));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S4", "B1", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), new Transaction("S2", "B0", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), new Transaction("S2", "B2", 0)));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        t.sellBidsAtEnd.add(new Bid("S4", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S4", "B1", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B2", 1)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 4), new Transaction("S0", "B1", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), new Transaction("S4", "B4", 1)));
        t.sellBidsAtEnd.add(new Bid("S0", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.buyBidsAtEnd.add(new Bid("B4", 4));
        t.buyBidsAtEnd.add(new Bid("B1", 3));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B4", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.sellBidsAtEnd.add(new Bid("S2", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 3));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B4", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.buyBidsAtEnd.add(new Bid("B4", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), new Transaction("S4", "B4", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B2", 0)));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B0", 2)));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        t.sellBidsAtEnd.add(new Bid("S4", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), new Transaction("S3", "B2", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), new Transaction("S0", "B3", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), new Transaction("S2", "B3", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B2", 1)));
        t.sellBidsAtEnd.add(new Bid("S0", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.buyBidsAtEnd.add(new Bid("B4", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), new Transaction("S4", "B0", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S2", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), new Transaction("S1", "B0", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), new Transaction("S2", "B0", 1)));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S1", "B1", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B1", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), new Transaction("S1", "B2", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), new Transaction("S2", "B4", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), new Transaction("S4", "B0", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S0", "B1", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.sellBidsAtEnd.add(new Bid("S4", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.sellBidsAtEnd.add(new Bid("S2", 4));
        t.buyBidsAtEnd.add(new Bid("B0", 3));
        t.buyBidsAtEnd.add(new Bid("B4", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B3", 0)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S2", "B1", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S2", "B1", 4)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B1", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), new Transaction("S2", "B2", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.buyBidsAtEnd.add(new Bid("B4", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S2", "B1", 4)));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S3", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), new Transaction("S4", "B0", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), null));
        t.sellBidsAtEnd.add(new Bid("S4", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S2", "B2", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        t.sellBidsAtEnd.add(new Bid("S3", 2));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), new Transaction("S3", "B0", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.sellBidsAtEnd.add(new Bid("S2", 1));
        t.sellBidsAtEnd.add(new Bid("S1", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B3", 2)));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), new Transaction("S0", "B3", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), new Transaction("S3", "B0", 4)));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S3", "B1", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), new Transaction("S2", "B4", 4)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.sellBidsAtEnd.add(new Bid("S3", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.sellBidsAtEnd.add(new Bid("S0", 3));
        t.sellBidsAtEnd.add(new Bid("S4", 2));
        t.sellBidsAtEnd.add(new Bid("S2", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S1", "B2", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.sellBidsAtEnd.add(new Bid("S4", 0));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B3", 0)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), new Transaction("S3", "B2", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), new Transaction("S2", "B1", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), new Transaction("S3", "B3", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B3", 1)));
        t.sellBidsAtEnd.add(new Bid("S4", 4));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S4", "B2", 2)));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S1", "B1", 4)));
        t.sellBidsAtEnd.add(new Bid("S3", 4));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), new Transaction("S3", "B1", 1)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 0));
        t.sellBidsAtEnd.add(new Bid("S2", 3));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B0", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), new Transaction("S0", "B2", 4)));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), new Transaction("S2", "B0", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.sellBidsAtEnd.add(new Bid("S3", 4));
        t.sellBidsAtEnd.add(new Bid("S4", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), new Transaction("S0", "B2", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), null));
        t.buyBidsAtEnd.add(new Bid("B4", 3));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.sellBidsAtEnd.add(new Bid("S4", 0));
        t.sellBidsAtEnd.add(new Bid("S1", 1));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B1", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        t.sellBidsAtEnd.add(new Bid("S0", 4));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), new Transaction("S3", "B1", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.buyBidsAtEnd.add(new Bid("B1", 4));
        t.buyBidsAtEnd.add(new Bid("B4", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), new Transaction("S4", "B1", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), new Transaction("S1", "B3", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), null));
        t.sellBidsAtEnd.add(new Bid("S4", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B2", 4)));
        t.buyBidsAtEnd.add(new Bid("B0", 3));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), new Transaction("S4", "B2", 3)));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B2", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B0", 2)));
        t.sellBidsAtEnd.add(new Bid("S4", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 4), new Transaction("S5", "B0", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), new Transaction("S4", "B4", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.buyBidsAtEnd.add(new Bid("B1", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), new Transaction("S5", "B3", 3)));
        t.buyBidsAtEnd.add(new Bid("B5", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), new Transaction("S1", "B1", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.sellBidsAtEnd.add(new Bid("S2", 3));
        t.sellBidsAtEnd.add(new Bid("S3", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), new Transaction("S1", "B4", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        t.buyBidsAtEnd.add(new Bid("B5", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B5", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), new Transaction("S2", "B5", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 1), new Transaction("S5", "B4", 5)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), new Transaction("S2", "B0", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 5), new Transaction("S4", "B1", 5)));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), new Transaction("S1", "B3", 5)));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        t.sellBidsAtEnd.add(new Bid("S5", 4));
        t.sellBidsAtEnd.add(new Bid("S2", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.sellBidsAtEnd.add(new Bid("S4", 5));
        t.buyBidsAtEnd.add(new Bid("B5", 2));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 0));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.sellBidsAtEnd.add(new Bid("S4", 1));
        t.sellBidsAtEnd.add(new Bid("S0", 5));
        t.sellBidsAtEnd.add(new Bid("S5", 3));
        t.sellBidsAtEnd.add(new Bid("S3", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), new Transaction("S1", "B3", 1)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.sellBidsAtEnd.add(new Bid("S2", 4));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S0", "B1", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        t.sellBidsAtEnd.add(new Bid("S2", 4));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), null));
        t.sellBidsAtEnd.add(new Bid("S3", 0));
        t.sellBidsAtEnd.add(new Bid("S4", 2));
        t.sellBidsAtEnd.add(new Bid("S5", 3));
        t.sellBidsAtEnd.add(new Bid("S1", 4));
        t.sellBidsAtEnd.add(new Bid("S0", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), new Transaction("S4", "B3", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B4", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 5), null));
        t.sellBidsAtEnd.add(new Bid("S3", 5));
        t.buyBidsAtEnd.add(new Bid("B1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), new Transaction("S0", "B5", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B3", 2)));
        t.sellBidsAtEnd.add(new Bid("S2", 5));
        t.buyBidsAtEnd.add(new Bid("B4", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B5", 1)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 0), new Transaction("S5", "B4", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S1", "B1", 4)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), new Transaction("S3", "B5", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        t.buyBidsAtEnd.add(new Bid("B5", 1));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 2), new Transaction("S5", "B4", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.buyBidsAtEnd.add(new Bid("B3", 4));
        t.buyBidsAtEnd.add(new Bid("B2", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), new Transaction("S4", "B2", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B4", 0)));
        t.sellBidsAtEnd.add(new Bid("S0", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B0", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), new Transaction("S0", "B3", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), new Transaction("S4", "B5", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        t.sellBidsAtEnd.add(new Bid("S0", 5));
        t.sellBidsAtEnd.add(new Bid("S5", 3));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 5), new Transaction("S0", "B0", 5)));
        t.sellBidsAtEnd.add(new Bid("S2", 3));
        t.sellBidsAtEnd.add(new Bid("S4", 4));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 3), new Transaction("S4", "B5", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.buyBidsAtEnd.add(new Bid("B4", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), new Transaction("S3", "B2", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 3), null));
        t.sellBidsAtEnd.add(new Bid("S4", 5));
        t.buyBidsAtEnd.add(new Bid("B4", 3));
        t.buyBidsAtEnd.add(new Bid("B5", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B4", 2)));
        t.sellBidsAtEnd.add(new Bid("S4", 3));
        t.sellBidsAtEnd.add(new Bid("S0", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), new Transaction("S2", "B1", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.sellBidsAtEnd.add(new Bid("S4", 2));
        t.sellBidsAtEnd.add(new Bid("S5", 5));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), new Transaction("S5", "B4", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S0", "B1", 4)));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), new Transaction("S1", "B4", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), new Transaction("S2", "B0", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), new Transaction("S4", "B1", 3)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), new Transaction("S4", "B5", 1)));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), new Transaction("S5", "B1", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), new Transaction("S4", "B3", 5)));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.sellBidsAtEnd.add(new Bid("S1", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), new Transaction("S3", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S0", 5));
        t.buyBidsAtEnd.add(new Bid("B3", 3));
        t.buyBidsAtEnd.add(new Bid("B1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 5), new Transaction("S2", "B1", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), new Transaction("S0", "B4", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.buyBidsAtEnd.add(new Bid("B0", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 0), new Transaction("S5", "B0", 0)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 5), null));
        t.sellBidsAtEnd.add(new Bid("S0", 1));
        t.sellBidsAtEnd.add(new Bid("S5", 2));
        t.sellBidsAtEnd.add(new Bid("S2", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 4), new Transaction("S3", "B3", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.sellBidsAtEnd.add(new Bid("S3", 0));
        t.sellBidsAtEnd.add(new Bid("S0", 5));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), new Transaction("S2", "B3", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B5", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 0), null));
        t.sellBidsAtEnd.add(new Bid("S4", 5));
        t.buyBidsAtEnd.add(new Bid("B0", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 5), new Transaction("S5", "B1", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), new Transaction("S5", "B0", 4)));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 3), new Transaction("S1", "B5", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), new Transaction("S0", "B3", 4)));
        t.sellBidsAtEnd.add(new Bid("S3", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), new Transaction("S3", "B3", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), null));
        t.buyBidsAtEnd.add(new Bid("B0", 3));
        t.buyBidsAtEnd.add(new Bid("B2", 2));
        t.buyBidsAtEnd.add(new Bid("B1", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), new Transaction("S3", "B3", 1)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), new Transaction("S3", "B2", 5)));
        t.sellBidsAtEnd.add(new Bid("S4", 4));
        t.sellBidsAtEnd.add(new Bid("S1", 5));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), new Transaction("S5", "B4", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.sellBidsAtEnd.add(new Bid("S0", 2));
        t.sellBidsAtEnd.add(new Bid("S3", 4));
        t.sellBidsAtEnd.add(new Bid("S5", 3));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), new Transaction("S2", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), new Transaction("S0", "B0", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.sellBidsAtEnd.add(new Bid("S3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 0), new Transaction("S2", "B2", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), new Transaction("S1", "B2", 5)));
        t.buyBidsAtEnd.add(new Bid("B5", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 0), new Transaction("S5", "B1", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), null));
        t.buyBidsAtEnd.add(new Bid("B2", 5));
        t.buyBidsAtEnd.add(new Bid("B1", 3));
        t.buyBidsAtEnd.add(new Bid("B0", 1));
        t.buyBidsAtEnd.add(new Bid("B5", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), new Transaction("S4", "B4", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), new Transaction("S2", "B3", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 4), null));
        t.buyBidsAtEnd.add(new Bid("B3", 4));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), new Transaction("S1", "B5", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), null));
        t.buyBidsAtEnd.add(new Bid("B3", 5));
        t.buyBidsAtEnd.add(new Bid("B5", 4));
        t.buyBidsAtEnd.add(new Bid("B1", 1));
        t.buyBidsAtEnd.add(new Bid("B2", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 5), new Transaction("S3", "B1", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), new Transaction("S3", "B2", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.sellBidsAtEnd.add(new Bid("S4", 5));
        t.buyBidsAtEnd.add(new Bid("B5", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 5), new Transaction("S1", "B5", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), new Transaction("S5", "B2", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 5), null));
        t.buyBidsAtEnd.add(new Bid("B1", 5));
        t.buyBidsAtEnd.add(new Bid("B3", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 2), new Transaction("S5", "B0", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 3), null));
        t.buyBidsAtEnd.add(new Bid("B4", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 0));
        t.buyBidsAtEnd.add(new Bid("B1", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), new Transaction("S3", "B3", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), new Transaction("S1", "B4", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), new Transaction("S4", "B0", 3)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), new Transaction("S3", "B3", 2)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 5), null));
        t.sellBidsAtEnd.add(new Bid("S2", 5));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 2), new Transaction("S2", "B1", 2)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 5), new Transaction("S1", "B0", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 5), null));
        t.buyBidsAtEnd.add(new Bid("B1", 5));
        t.buyBidsAtEnd.add(new Bid("B2", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B3", 0)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), new Transaction("S1", "B2", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.sellBidsAtEnd.add(new Bid("S3", 0));
        t.sellBidsAtEnd.add(new Bid("S5", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), new Transaction("S4", "B0", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.sellBidsAtEnd.add(new Bid("S2", 2));
        t.sellBidsAtEnd.add(new Bid("S1", 5));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.buyBidsAtEnd.add(new Bid("B4", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 4), new Transaction("S0", "B5", 4)));
        t.sellBidsAtEnd.add(new Bid("S3", 3));
        t.sellBidsAtEnd.add(new Bid("S4", 5));
        t.sellBidsAtEnd.add(new Bid("S2", 4));
        t.buyBidsAtEnd.add(new Bid("B3", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 5), new Transaction("S3", "B0", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), new Transaction("S3", "B2", 3)));
        t.buyBidsAtEnd.add(new Bid("B4", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 1), new Transaction("S0", "B2", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 4), new Transaction("S3", "B0", 4)));
        t.buyBidsAtEnd.add(new Bid("B5", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 1), new Transaction("S4", "B5", 3)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 0), new Transaction("S3", "B1", 0)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 2), new Transaction("S1", "B2", 2)));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 4), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B0", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 3), new Transaction("S3", "B5", 4)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 0), new Transaction("S3", "B2", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 5), null));
        t.buyBidsAtEnd.add(new Bid("B2", 5));
        t.buyBidsAtEnd.add(new Bid("B0", 2));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 5), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S2", 3), new Transaction("S2", "B1", 4)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 3), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B3", 2)));
        t.sellBidsAtEnd.add(new Bid("S0", 3));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B3", 5), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 1), new Transaction("S3", "B3", 5)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B1", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S0", 0), new Transaction("S0", "B1", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 0), null));
        t.sellBidsAtEnd.add(new Bid("S1", 2));
        t.buyBidsAtEnd.add(new Bid("B5", 0));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S1", 1), new Transaction("S1", "B4", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 0), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B2", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 4), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 3), new Transaction("S5", "B4", 4)));
        t.buyBidsAtEnd.add(new Bid("B2", 1));
        test(t);
        ntest++;

        t = new Test();
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 0), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S4", 2), null));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S5", 1), null));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B4", 1), new Transaction("S5", "B4", 1)));
        t.operations.add(new Operation(BidKind.BUY, new Bid("B5", 5), new Transaction("S4", "B5", 5)));
        t.operations.add(new Operation(BidKind.SELL, new Bid("S3", 4), null));
        t.sellBidsAtEnd.add(new Bid("S3", 4));
        test(t);
        ntest++;

        /////////////// End auto generated

        System.out.println("Ran " + ntest + " tests. No bugs found.");
        System.exit(0);
    }
}