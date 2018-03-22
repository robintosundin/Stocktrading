# Stocktrading - an implementation with priority queues

In its most simplistic form, the stock market can be modeled as two lists containing buy and sell orders. This collection of code is an implementation of this. For the model to be plausible in a large system, much detail has went into making sure critical methods perform at good complexity.

## Getting up to speed
No frontend functionality has been implemented which means it is not difficult to try the software. Simply clone the directory and compile all files with Java 8+.

Once compiled with javac command, trying it out is as simple as typing below in your favorite terminal where A (a string) is the name of the entity to put up a offer, B is the type (b for buy bid, s for sell bid) and C is the price (an integer).

```bash
	java run A B C
```


## Performance 
The binary heap is implemented in BinHeap and is utilized for the self-maintaining priority property when modifying the lists.

### Complexity
| Method  |Amortization|
|---------|-----------:|
|add:	  |O(logn)     |
|peek:	  |O(1)	       |
|poll:	  |O(logn)     |
|remove:  |O(n)	       |
|iterator:|O(1)	       |

Both add and poll are very critical operations that would be performed at least once during a transaction. Remove has abyssmal complexity relatively speaking but is not utilized much except for in special cases.

## Testing
Automatic unit testing is performed with TestBinHeap and TestStockTrade. ManTest provides an example for manual testing or checking functionality.
