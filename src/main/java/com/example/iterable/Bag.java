package com.example.iterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * A generic Bag collection that implements the Container interface.
 * This class uses an ArrayList as the underlying data structure to store elements.
 *
 * @param <E> the type of elements stored in this bag
 */
public class Bag<E> implements Container<E> {

    // ArrayList is used as the backing structure because it provides:
    // 1. Dynamic resizing (no need to manage capacity manually)
    // 2. O(1) average-case add operations
    // 3. Built-in implementation of iteration and spliterator
    // 4. Type-safe generic support
    private ArrayList<E> items;

    /**
     * Constructs an empty Bag with an initial capacity of 10.
     * We delegate to ArrayList's default constructor for efficiency.
     */
    public Bag() {
        this.items = new ArrayList<>();
    }

    /**
     * Constructs an empty Bag with the specified initial capacity.
     * This constructor is useful when the approximate size is known in advance,
     * reducing the number of resizing operations.
     *
     * @param initialCapacity the initial capacity of the bag
     */
    public Bag(int initialCapacity) {
        this.items = new ArrayList<>(initialCapacity);
    }

    /**
     * Adds an item to the bag.
     * Bags allow duplicate elements but do not allow null values.
     * Time complexity: O(1) amortized.
     *
     * @param item the item to add to the bag
     * @throws NullPointerException if the specified item is null
     */
    @Override
    public void add(E item) {
        // Validate that the item is not null
        // This prevents NullPointerException later and makes the bag null-hostile
        // Null-hostile collections are safer and prevent subtle bugs
        if (item == null) {
            throw new NullPointerException("Bag does not allow null elements");
        }
        // ArrayList.add() appends to the end, providing efficient insertion
        items.add(item);
    }

    /**
     * Removes the first occurrence of the specified item from the bag.
     * Uses object equality (via equals() method) to find the item.
     *
     * @param item the item to remove
     * @return true if the item was found and removed, false otherwise
     * @throws NullPointerException if the specified item is null
     */
    @Override
    public boolean remove(E item) {
        // Validate that the item is not null
        // Consistent with add() method's null-hostile behavior
        if (item == null) {
            throw new NullPointerException("Bag does not allow null elements");
        }
        // ArrayList.remove(Object) removes the first occurrence and returns success status
        // Time complexity: O(n) because it must search for the element and shift subsequent elements
        return items.remove(item);
    }

    /**
     * Checks if the bag contains the specified item.
     * Uses object equality (via equals() method) for comparison.
     *
     * @param item the item to check for
     * @return true if the item is in the bag, false otherwise
     * @throws NullPointerException if the specified item is null
     */
    @Override
    public boolean contains(E item) {
        // Validate that the item is not null
        // Consistent with add() and remove() methods' null-hostile behavior
        if (item == null) {
            throw new NullPointerException("Bag does not allow null elements");
        }
        // ArrayList.contains() uses indexOf() internally, which iterates through elements
        // Time complexity: O(n) in the worst case
        return items.contains(item);
    }

    /**
     * Returns the number of elements in the bag.
     *
     * @return the size of the bag
     */
    @Override
    public int size() {
        // ArrayList maintains a size field, so this is O(1)
        return items.size();
    }

    /**
     * Checks if the bag is empty.
     *
     * @return true if the bag contains no elements, false otherwise
     */
    @Override
    public boolean isEmpty() {
        // More readable than checking size() == 0
        // ArrayList.isEmpty() is O(1)
        return items.isEmpty();
    }

    /**
     * Returns an iterator over the elements in this bag.
     * The iterator supports traversal in the order elements were added.
     *
     * This method is required by the Iterable interface and enables
     * for-each loop syntax: for (E item : bag) { ... }
     *
     * @return an Iterator over the elements in this bag
     */
    @Override
    public Iterator<E> iterator() {
        // We create a custom iterator instead of directly returning items.iterator()
        // This gives us control over the iteration behavior and encapsulates
        // the internal ArrayList implementation detail
        return new BagIterator();
    }

    /**
     * Performs the given action for each element in the bag until all elements
     * have been processed or the action throws an exception.
     *
     * This method is part of the Iterable interface (added in Java 8).
     * It provides a functional approach to iteration.
     *
     * @param action the action to be performed for each element
     */
    @Override
    public void forEach(Consumer<? super E> action) {
        // We delegate to ArrayList's forEach implementation because:
        // 1. It's optimized for ArrayList's internal structure
        // 2. It properly handles concurrent modification checks
        // 3. It validates that action is not null
        items.forEach(action);
    }

    /**
     * Creates a Spliterator over the elements in this bag.
     *
     * Spliterators are used for parallel stream operations and provide
     * better performance characteristics than iterators for bulk operations.
     *
     * @return a Spliterator over the elements in this bag
     */
    @Override
    public Spliterator<E> spliterator() {
        // We delegate to ArrayList's spliterator because:
        // 1. ArrayList provides an optimized Spliterator implementation
        // 2. The ArrayList Spliterator is late-binding and fail-fast
        // 3. It reports SIZED and SUBSIZED characteristics for better performance
        return items.spliterator();
    }

    /**
     * Custom iterator implementation for the Bag class.
     * This iterator provides fail-fast behavior through ArrayList's iterator.
     */
    private class BagIterator implements Iterator<E> {

        // We delegate to ArrayList's iterator to leverage its:
        // 1. Fail-fast behavior (throws ConcurrentModificationException)
        // 2. Proper state management
        // 3. Tested and optimized implementation
        private Iterator<E> listIterator;

        /**
         * Constructs a new BagIterator.
         * Initializes the underlying ArrayList iterator.
         */
        public BagIterator() {
            // Get the iterator from the ArrayList at construction time
            // This captures the current state of the list
            this.listIterator = items.iterator();
        }

        /**
         * Returns true if the iteration has more elements.
         *
         * @return true if there are more elements to iterate over
         */
        @Override
        public boolean hasNext() {
            // Delegate to ArrayList's iterator hasNext() method
            // This checks if the current position is less than the size
            return listIterator.hasNext();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element
         * @throws java.util.NoSuchElementException if no more elements exist
         */
        @Override
        public E next() {
            // Delegate to ArrayList's iterator next() method
            // This returns the element at the current position and advances the cursor
            // Throws NoSuchElementException if hasNext() would return false
            return listIterator.next();
        }

        /**
         * Removes the last element returned by next() from the bag.
         * This is an optional operation supported by our iterator.
         *
         * @throws IllegalStateException if next() hasn't been called or
         *         remove() has already been called after the last next()
         */
        @Override
        public void remove() {
            // ArrayList's iterator supports removal of the current element
            // This properly maintains the internal structure and modification count
            listIterator.remove();
        }
    }

    /**
     * Returns a string representation of this bag.
     * Useful for debugging and logging purposes.
     *
     * @return a string representation of the bag
     */
    @Override
    public String toString() {
        // Delegate to ArrayList's toString() which formats as [elem1, elem2, ...]
        return "Bag" + items.toString();
    }
}