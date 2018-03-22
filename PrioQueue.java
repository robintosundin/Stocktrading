/**
 * Contracting 4 essential operations for a priority queue implementation
 * @author Robin Sundin
 */
public interface PrioQueue<E> extends Iterable<E> {
    void add(E e);
    E peek();
    E poll();
    void remove(E e);
}
