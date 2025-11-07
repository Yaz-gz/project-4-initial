package com.example.iterable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for the Bag class.
 * Tests cover normal operations, edge cases, iterator functionality,
 * and empty bag operations.
 */
@DisplayName("Bag Tests")
class BagTest {

    private Bag<String> bag;

    /**
     * Set up a fresh bag before each test.
     */
    @BeforeEach
    void setUp() {
        bag = new Bag<>();
    }

    // ========== Constructor Tests ==========

    @Nested
    @DisplayName("Constructor Tests")
    class ConstructorTests {

        @Test
        @DisplayName("Default constructor creates empty bag")
        void testDefaultConstructor() {
            Bag<String> newBag = new Bag<>();
            assertTrue(newBag.isEmpty());
            assertEquals(0, newBag.size());
        }

        @Test
        @DisplayName("Constructor with initial capacity creates empty bag")
        void testConstructorWithInitialCapacity() {
            Bag<String> newBag = new Bag<>(20);
            assertTrue(newBag.isEmpty());
            assertEquals(0, newBag.size());
        }

        @Test
        @DisplayName("Constructor with zero capacity works")
        void testConstructorWithZeroCapacity() {
            Bag<String> newBag = new Bag<>(0);
            assertTrue(newBag.isEmpty());
            // Should still be able to add elements
            newBag.add("test");
            assertEquals(1, newBag.size());
        }
    }

    // ========== Empty Bag Operations Tests ==========

    @Nested
    @DisplayName("Empty Bag Operations")
    class EmptyBagTests {

        @Test
        @DisplayName("New bag is empty")
        void testNewBagIsEmpty() {
            assertTrue(bag.isEmpty());
        }

        @Test
        @DisplayName("Empty bag has size zero")
        void testEmptyBagSizeIsZero() {
            assertEquals(0, bag.size());
        }

        @Test
        @DisplayName("Empty bag does not contain any element including null")
        void testEmptyBagContains() {
            assertFalse(bag.contains("anything"));
            assertThrows(NullPointerException.class, () -> bag.contains(null));
        }

        @Test
        @DisplayName("Removing from empty bag returns false")
        void testRemoveFromEmptyBag() {
            assertFalse(bag.remove("anything"));
        }

        @Test
        @DisplayName("Iterator on empty bag has no elements")
        void testEmptyBagIterator() {
            Iterator<String> iterator = bag.iterator();
            assertFalse(iterator.hasNext());
        }

        @Test
        @DisplayName("Calling next() on empty bag iterator throws NoSuchElementException")
        void testEmptyBagIteratorNext() {
            Iterator<String> iterator = bag.iterator();
            assertThrows(NoSuchElementException.class, iterator::next);
        }

        @Test
        @DisplayName("forEach on empty bag does nothing")
        void testEmptyBagForEach() {
            AtomicInteger count = new AtomicInteger(0);
            bag.forEach(item -> count.incrementAndGet());
            assertEquals(0, count.get());
        }

        @Test
        @DisplayName("Spliterator on empty bag has size zero")
        void testEmptyBagSpliterator() {
            Spliterator<String> spliterator = bag.spliterator();
            assertEquals(0, spliterator.estimateSize());
        }
    }

    // ========== Add Operation Tests ==========

    @Nested
    @DisplayName("Add Operation Tests")
    class AddTests {

        @Test
        @DisplayName("Adding single element increases size")
        void testAddSingleElement() {
            bag.add("apple");
            assertEquals(1, bag.size());
            assertFalse(bag.isEmpty());
        }

        @Test
        @DisplayName("Adding multiple elements increases size correctly")
        void testAddMultipleElements() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");
            assertEquals(3, bag.size());
        }

        @Test
        @DisplayName("Adding null element throws NullPointerException")
        void testAddNullElement() {
            assertThrows(NullPointerException.class, () -> bag.add(null));
        }

        @Test
        @DisplayName("Adding duplicate elements is allowed")
        void testAddDuplicates() {
            bag.add("apple");
            bag.add("apple");
            bag.add("apple");
            assertEquals(3, bag.size());
        }

        @Test
        @DisplayName("Adding many elements works correctly")
        void testAddManyElements() {
            for (int i = 0; i < 1000; i++) {
                bag.add("item" + i);
            }
            assertEquals(1000, bag.size());
        }
    }

    // ========== Contains Operation Tests ==========

    @Nested
    @DisplayName("Contains Operation Tests")
    class ContainsTests {

        @Test
        @DisplayName("Contains returns true for existing element")
        void testContainsExistingElement() {
            bag.add("apple");
            assertTrue(bag.contains("apple"));
        }

        @Test
        @DisplayName("Contains returns false for non-existing element")
        void testContainsNonExistingElement() {
            bag.add("apple");
            assertFalse(bag.contains("banana"));
        }

        @Test
        @DisplayName("Contains with null throws NullPointerException")
        void testContainsNull() {
            bag.add("apple");
            assertThrows(NullPointerException.class, () -> bag.contains(null));
        }

        @Test
        @DisplayName("Contains works with duplicates")
        void testContainsWithDuplicates() {
            bag.add("apple");
            bag.add("apple");
            bag.add("banana");
            assertTrue(bag.contains("apple"));
            assertTrue(bag.contains("banana"));
        }

        @Test
        @DisplayName("Contains returns false after removing element")
        void testContainsAfterRemoval() {
            bag.add("apple");
            bag.remove("apple");
            assertFalse(bag.contains("apple"));
        }
    }

    // ========== Remove Operation Tests ==========

    @Nested
    @DisplayName("Remove Operation Tests")
    class RemoveTests {

        @Test
        @DisplayName("Removing existing element returns true and decreases size")
        void testRemoveExistingElement() {
            bag.add("apple");
            bag.add("banana");
            assertTrue(bag.remove("apple"));
            assertEquals(1, bag.size());
            assertFalse(bag.contains("apple"));
        }

        @Test
        @DisplayName("Removing non-existing element returns false")
        void testRemoveNonExistingElement() {
            bag.add("apple");
            assertFalse(bag.remove("banana"));
            assertEquals(1, bag.size());
        }

        @Test
        @DisplayName("Removing null element throws NullPointerException")
        void testRemoveNull() {
            bag.add("apple");
            assertThrows(NullPointerException.class, () -> bag.remove(null));
        }

        @Test
        @DisplayName("Removing only removes first occurrence of duplicate")
        void testRemoveDuplicateOnlyRemovesFirst() {
            bag.add("apple");
            bag.add("apple");
            bag.add("apple");
            assertTrue(bag.remove("apple"));
            assertEquals(2, bag.size());
            assertTrue(bag.contains("apple"));
        }

        @Test
        @DisplayName("Removing all elements makes bag empty")
        void testRemoveAllElements() {
            bag.add("apple");
            bag.add("banana");
            bag.remove("apple");
            bag.remove("banana");
            assertTrue(bag.isEmpty());
            assertEquals(0, bag.size());
        }

        @Test
        @DisplayName("Removing last element makes bag empty")
        void testRemoveLastElement() {
            bag.add("apple");
            bag.remove("apple");
            assertTrue(bag.isEmpty());
        }
    }

    // ========== Size and IsEmpty Tests ==========

    @Nested
    @DisplayName("Size and IsEmpty Tests")
    class SizeTests {

        @Test
        @DisplayName("Size increases with additions")
        void testSizeIncreasesWithAdditions() {
            assertEquals(0, bag.size());
            bag.add("a");
            assertEquals(1, bag.size());
            bag.add("b");
            assertEquals(2, bag.size());
        }

        @Test
        @DisplayName("Size decreases with removals")
        void testSizeDecreasesWithRemovals() {
            bag.add("a");
            bag.add("b");
            bag.add("c");
            bag.remove("b");
            assertEquals(2, bag.size());
            bag.remove("a");
            assertEquals(1, bag.size());
        }

        @Test
        @DisplayName("isEmpty returns false when bag has elements")
        void testIsEmptyReturnsFalseWhenNotEmpty() {
            bag.add("apple");
            assertFalse(bag.isEmpty());
        }

        @Test
        @DisplayName("isEmpty returns true after removing all elements")
        void testIsEmptyAfterRemovingAll() {
            bag.add("apple");
            bag.add("banana");
            bag.remove("apple");
            bag.remove("banana");
            assertTrue(bag.isEmpty());
        }
    }

    // ========== Iterator Tests ==========

    @Nested
    @DisplayName("Iterator Tests")
    class IteratorTests {

        @Test
        @DisplayName("Iterator hasNext returns true when elements exist")
        void testIteratorHasNext() {
            bag.add("apple");
            bag.add("banana");
            Iterator<String> iterator = bag.iterator();
            assertTrue(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator next returns elements in order")
        void testIteratorNextOrder() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");

            Iterator<String> iterator = bag.iterator();
            assertEquals("apple", iterator.next());
            assertEquals("banana", iterator.next());
            assertEquals("cherry", iterator.next());
        }

        @Test
        @DisplayName("Iterator hasNext returns false after all elements consumed")
        void testIteratorHasNextAfterConsumption() {
            bag.add("apple");
            Iterator<String> iterator = bag.iterator();
            iterator.next();
            assertFalse(iterator.hasNext());
        }

        @Test
        @DisplayName("Iterator next throws NoSuchElementException when no more elements")
        void testIteratorNextThrowsException() {
            bag.add("apple");
            Iterator<String> iterator = bag.iterator();
            iterator.next();
            assertThrows(NoSuchElementException.class, iterator::next);
        }

        @Test
        @DisplayName("Multiple iterators can be used independently")
        void testMultipleIterators() {
            bag.add("apple");
            bag.add("banana");

            Iterator<String> iter1 = bag.iterator();
            Iterator<String> iter2 = bag.iterator();

            assertEquals("apple", iter1.next());
            assertEquals("apple", iter2.next());
            assertEquals("banana", iter1.next());
            assertEquals("banana", iter2.next());
        }

        @Test
        @DisplayName("Iterator remove removes current element")
        void testIteratorRemove() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");

            Iterator<String> iterator = bag.iterator();
            iterator.next(); // apple
            iterator.next(); // banana
            iterator.remove(); // remove banana

            assertEquals(2, bag.size());
            assertFalse(bag.contains("banana"));
            assertTrue(bag.contains("apple"));
            assertTrue(bag.contains("cherry"));
        }

        @Test
        @DisplayName("Iterator remove throws IllegalStateException if next not called")
        void testIteratorRemoveWithoutNext() {
            bag.add("apple");
            Iterator<String> iterator = bag.iterator();
            assertThrows(IllegalStateException.class, iterator::remove);
        }

        @Test
        @DisplayName("Iterator remove throws IllegalStateException if called twice")
        void testIteratorRemoveCalledTwice() {
            bag.add("apple");
            Iterator<String> iterator = bag.iterator();
            iterator.next();
            iterator.remove();
            assertThrows(IllegalStateException.class, iterator::remove);
        }

        @Test
        @DisplayName("Iterator does not work with null elements")
        void testIteratorWithNull() {
            bag.add("apple");
            assertThrows(NullPointerException.class, () -> bag.add(null));
            bag.add("banana");

            Iterator<String> iterator = bag.iterator();
            assertEquals("apple", iterator.next());
            assertEquals("banana", iterator.next());
        }

        @Test
        @DisplayName("For-each loop works correctly")
        void testForEachLoop() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");

            List<String> items = new ArrayList<>();
            for (String item : bag) {
                items.add(item);
            }

            assertEquals(3, items.size());
            assertTrue(items.contains("apple"));
            assertTrue(items.contains("banana"));
            assertTrue(items.contains("cherry"));
        }
    }

    // ========== forEach Method Tests ==========

    @Nested
    @DisplayName("forEach Method Tests")
    class ForEachTests {

        @Test
        @DisplayName("forEach processes all elements")
        void testForEachProcessesAllElements() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");

            List<String> items = new ArrayList<>();
            bag.forEach(items::add);

            assertEquals(3, items.size());
            assertTrue(items.contains("apple"));
            assertTrue(items.contains("banana"));
            assertTrue(items.contains("cherry"));
        }

        @Test
        @DisplayName("forEach with counter counts correctly")
        void testForEachWithCounter() {
            bag.add("a");
            bag.add("b");
            bag.add("c");

            AtomicInteger count = new AtomicInteger(0);
            bag.forEach(item -> count.incrementAndGet());

            assertEquals(3, count.get());
        }

        @Test
        @DisplayName("forEach throws exception when processing null elements")
        void testForEachWithNull() {
            bag.add("apple");
            assertThrows(NullPointerException.class, () -> bag.add(null));
            bag.add("banana");

            AtomicInteger count = new AtomicInteger(0);
            bag.forEach(item -> count.incrementAndGet());

            assertEquals(2, count.get());
        }

        @Test
        @DisplayName("forEach throws NullPointerException for null action")
        void testForEachWithNullAction() {
            bag.add("apple");
            assertThrows(NullPointerException.class, () -> bag.forEach(null));
        }
    }

    // ========== Spliterator Tests ==========

    @Nested
    @DisplayName("Spliterator Tests")
    class SpliteratorTests {

        @Test
        @DisplayName("Spliterator estimateSize returns correct size")
        void testSpliteratorEstimateSize() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");

            Spliterator<String> spliterator = bag.spliterator();
            assertEquals(3, spliterator.estimateSize());
        }

        @Test
        @DisplayName("Spliterator tryAdvance processes elements")
        void testSpliteratorTryAdvance() {
            bag.add("apple");
            bag.add("banana");

            Spliterator<String> spliterator = bag.spliterator();
            List<String> items = new ArrayList<>();

            spliterator.tryAdvance(items::add);
            spliterator.tryAdvance(items::add);

            assertEquals(2, items.size());
            assertTrue(items.contains("apple"));
            assertTrue(items.contains("banana"));
        }

        @Test
        @DisplayName("Spliterator tryAdvance returns false when no more elements")
        void testSpliteratorTryAdvanceReturnsFalse() {
            bag.add("apple");

            Spliterator<String> spliterator = bag.spliterator();
            assertTrue(spliterator.tryAdvance(item -> {}));
            assertFalse(spliterator.tryAdvance(item -> {}));
        }

        @Test
        @DisplayName("Spliterator forEachRemaining processes all elements")
        void testSpliteratorForEachRemaining() {
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");

            Spliterator<String> spliterator = bag.spliterator();
            List<String> items = new ArrayList<>();
            spliterator.forEachRemaining(items::add);

            assertEquals(3, items.size());
        }
    }

    // ========== Edge Case Tests ==========

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Bag works with different generic types")
        void testDifferentGenericTypes() {
            Bag<Integer> intBag = new Bag<>();
            intBag.add(1);
            intBag.add(2);
            assertEquals(2, intBag.size());

            Bag<Double> doubleBag = new Bag<>();
            doubleBag.add(1.5);
            doubleBag.add(2.5);
            assertEquals(2, doubleBag.size());
        }

        @Test
        @DisplayName("Bag handles many add and remove operations")
        void testManyOperations() {
            for (int i = 0; i < 100; i++) {
                bag.add("item" + i);
            }
            assertEquals(100, bag.size());

            for (int i = 0; i < 50; i++) {
                bag.remove("item" + i);
            }
            assertEquals(50, bag.size());
        }

        @Test
        @DisplayName("Bag does not allow null elements")
        void testBagWithOnlyNulls() {
            assertThrows(NullPointerException.class, () -> bag.add(null));
            assertThrows(NullPointerException.class, () -> bag.add(null));
            assertThrows(NullPointerException.class, () -> bag.add(null));

            assertEquals(0, bag.size());
            assertThrows(NullPointerException.class, () -> bag.contains(null));
        }

        @Test
        @DisplayName("ToString returns meaningful representation")
        void testToString() {
            bag.add("apple");
            bag.add("banana");
            String result = bag.toString();
            assertNotNull(result);
            assertTrue(result.contains("apple"));
            assertTrue(result.contains("banana"));
        }

        @Test
        @DisplayName("Bag maintains elements after multiple iterator creations")
        void testMultipleIteratorCreations() {
            bag.add("apple");
            bag.add("banana");

            bag.iterator();
            bag.iterator();
            bag.iterator();

            assertEquals(2, bag.size());
            assertTrue(bag.contains("apple"));
            assertTrue(bag.contains("banana"));
        }
    }

    // ========== Integration Tests ==========

    @Nested
    @DisplayName("Integration Tests")
    class IntegrationTests {

        @Test
        @DisplayName("Complex scenario: add, iterate, remove, iterate again")
        void testComplexScenario() {
            // Add elements
            bag.add("apple");
            bag.add("banana");
            bag.add("cherry");
            bag.add("date");

            // First iteration - collect all
            List<String> firstPass = new ArrayList<>();
            for (String item : bag) {
                firstPass.add(item);
            }
            assertEquals(4, firstPass.size());

            // Remove some elements
            bag.remove("banana");
            bag.remove("date");

            // Second iteration - should have fewer elements
            List<String> secondPass = new ArrayList<>();
            bag.forEach(secondPass::add);
            assertEquals(2, secondPass.size());
            assertTrue(secondPass.contains("apple"));
            assertTrue(secondPass.contains("cherry"));
        }

        @Test
        @DisplayName("Stress test: many operations in sequence")
        void testStressTest() {
            // Add 1000 elements
            for (int i = 0; i < 1000; i++) {
                bag.add("item" + (i % 100)); // Creates duplicates
            }

            // Verify size
            assertEquals(1000, bag.size());

            // Iterate and count
            AtomicInteger count = new AtomicInteger(0);
            bag.forEach(item -> count.incrementAndGet());
            assertEquals(1000, count.get());

            // Remove half
            for (int i = 0; i < 50; i++) {
                bag.remove("item" + i);
            }

            assertTrue(bag.size() < 1000);
            assertFalse(bag.isEmpty());
        }
    }
}