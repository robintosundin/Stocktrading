import java.util.Comparator;
import java.util.Iterator;

/** Structure for a stock trading implementation that maintains two prioritized
 * queues for purchases and offers with basic functionality for performing transactions.
 * @author Robin Sundin
 */
public class StockTrade {
    private PrioQueue<Bid> sellersQueue;
    private PrioQueue<Bid> buyersQueue;

    public StockTrade() {
    	this.sellersQueue = new BinHeap<Bid>(new Comparator<Bid>(){
		public int compare(Bid bid1, Bid bid2){
			return ((Integer) bid1.price).compareTo((Integer) bid2.price);
		}
	});
    	this.buyersQueue= new BinHeap<Bid>(new Comparator<Bid>(){
		public int compare(Bid bid1, Bid bid2){
			return -((Integer) bid1.price).compareTo((Integer) bid2.price); // - because buyer has opposite prio
		}
	});
    }
    /**
     * Tries to place a selling bid, if no transaction possible, add to sellersQueue.
     * @param bid the selling bid to place.
     * @return the transaction that took place or null if bid was added to sellersQueue.
     */
    public Transaction placeSellBid(Bid bid) {
	Iterator<Bid> bidItr = sellBidsIterator(); // fetch iterator for sellersQueue
	while(bidItr.hasNext()){ // iterate through sellersQueue and maybe find sell bid with same name
		Bid tmp = bidItr.next();
		if(tmp.name.equals(bid.name)){
			sellersQueue.remove(tmp); // remove previous bid from same client
			break;
		}
	}
	/** Tries to perform a transaction with existing buy bid from buyersQueue, if incoming
	* sell bid is lower than some existing buy bid, performs transaction with previously existing buy bid.
	*/ 
	if(buyersQueue.peek()!=null&&bid.price<=buyersQueue.peek().price){
		Bid buyBid = buyersQueue.poll();
		return new Transaction(bid.name,buyBid.name,buyBid.price);
	}
	sellersQueue.add(bid);
	return null;
    }
    /**
     * Tries to place a buying bid, if no transaction possible, add to buyersQueue.
     * @param bid the selling bid to place.
     * @return the transaction that took place or null if bid was added to buyersQueue.
     */
    public Transaction placeBuyBid(Bid bid) {
	Iterator<Bid> bidItr = buyBidsIterator();
	while(bidItr.hasNext()){
		Bid tmp = bidItr.next();
		if(tmp.name.equals(bid.name)){
			buyersQueue.remove(tmp);
			break;
		}
	}
	if(sellersQueue.peek()!=null&&bid.price>=sellersQueue.peek().price){
		Bid sellBid = sellersQueue.poll();
		return new Transaction(sellBid.name,bid.name,bid.price);
	}
	buyersQueue.add(bid);
	return null;
    } 
    public Iterator<Bid> sellBidsIterator() {
        return sellersQueue.iterator();
    }
    public Iterator<Bid> buyBidsIterator() {
        return buyersQueue.iterator();
    }
}
