import java.util.Iterator;
import java.util.Comparator;
import java.util.ArrayList;

/**
 * A generic binary heap implementation of a prioritized queue with elementary functionality.
 * @author Robin Sundin
 */
public class BinHeap<E> implements PrioQueue<E>{
	public Comparator<? super E> comp;
	public ArrayList<E> binHeap = new ArrayList<E>();
	public int currentSize; // physical size of binheap
	public Iterator<E> itr;
	public int size(){
		return currentSize;
	}
	/**
	 * Construct a BinHeap using a specific ordering
	 * @param comp a comparator to be written as generic E.
	 */
	public BinHeap(Comparator<? super E> comp){
		this.comp = comp;	
		this.currentSize = 0; 
		binHeap.add(null); // Makes index for added elements start at 1 for percolation implementation
	}
	class MyIterator implements Iterator<E>{
			public int currentIndex=1;

			@Override
			public boolean hasNext(){
				return currentIndex <= currentSize;
			}
			@Override
			public E next(){
				return binHeap.get(currentIndex++);
			}

			@Override
			public void remove(){
				throw new UnsupportedOperationException();
			}
	}
	@Override
	public Iterator<E> iterator(){
		return new MyIterator();
	}
	/**
	 * Adds element e to binary heap while maintaining heap order performing percolation upwards.
	 * @param e the element to be added to binary heap.
	 */
	public void add(E e){ // Adds e at proper position with up-percolation
		int insertIndex=++currentSize;
		binHeap.add(insertIndex,e);
		percUp(insertIndex);
		return;
	}
	/** Return the element in the heap with highest priority; the element with an index of 1.
	 */
	public E peek(){
		if(currentSize==0) return null;
		return binHeap.get(1);
	}
	/**
	 * Removes and returns element in heap with highest priority.
	 * @return the element in heap with highest priority.
	 */
	public E poll(){
		if(currentSize==0) return null;
		if(currentSize==1){
			currentSize--;
			return binHeap.remove(1);
		}
		E minItem = binHeap.get(1);
		binHeap.set(1,binHeap.get(currentSize--));
		percDown(1); // Percolates down binary heap starting at index 1 in arraylist
		return minItem;
	}
	/**
	 * Percolate upwards in heap.
	 * @param insertIndex the starting index at which upward percolation begins
	 */
	private void percUp(int insertIndex){
		E tmp = binHeap.get(insertIndex);
		for(binHeap.set(0,tmp);;insertIndex/=2){
			if(comp.compare(tmp,binHeap.get(insertIndex/2))>=0)
				break;
			binHeap.set(insertIndex,binHeap.get(insertIndex/2));
			binHeap.set(insertIndex/2,tmp);
		}
	}
	/**
	 * Percolate downwards in heap.
	 * @param insertIndex the starting index at which downward percolation begins
	 */
	private void percDown(int insertIndex){
		int child; // the index of a child node element 
		E tmp = binHeap.get(insertIndex);
		for(;insertIndex*2<=currentSize;insertIndex=child){ // iterates down leafs in heap
			child=insertIndex*2;
			if(child!=currentSize && comp.compare(binHeap.get(child),binHeap.get(child+1))>0)
				child++;
			if(comp.compare(binHeap.get(child),tmp)<0){ // true if child node is less than perc index
				binHeap.set(insertIndex,binHeap.get(child)); // moves ins item to child node
				binHeap.set(child,tmp);
			} else break;
		}
		binHeap.set(insertIndex,tmp);
	}
	/**
	 * Remove element of highest priroity in the heap that is equal argument.
	 * @param e the element to be removed in heap.
	 */
	public void remove(E e){
		for(int i=1;i<=currentSize;i++){
			if(comp.compare(e,binHeap.get(i))==0){ // TRUE if argument e is equal to an element
				binHeap.set(0,binHeap.get(i)); // required since potential number at index=0
				binHeap.set(i,binHeap.get(currentSize--));
				if(comp.compare(binHeap.get(i),binHeap.get(i/2))<0) // bh(i) has larger mother
					percUp(i);
				else 
					percDown(i);
				break;
			}
		}
	}
}
