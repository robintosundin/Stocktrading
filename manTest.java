/**
 * Primitive manual testing of a Binary Heap implementation.
 * @author Robin Sundin
 */
class manTest{
	public static void main(String[] args){
		NaturalOrderComparator<Integer> comp = new NaturalOrderComparator<Integer>();
		BinHeap<Integer> bh = new BinHeap<Integer>(comp);
		bh.add(2);
		bh.poll();	
		bh.add(4);
		System.out.println("");
	}
}
