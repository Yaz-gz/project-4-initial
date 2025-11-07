package com.example.iterable;

import java.util.Iterator;
import java.util.Spliterator;

/**
 * Driver class to demonstrate the functionality of the Bag class.
 * This class exercises all methods of the Container interface and
 * demonstrates the Iterable capabilities.
 */
public class BagDriver {

    public static void main(String[] args) {
        System.out.println("=== Bag Class Demonstration ===\n");

        // Demonstrate with String type
        demonstrateStringBag();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demonstrate with Integer type
        demonstrateIntegerBag();

        System.out.println("\n" + "=".repeat(50) + "\n");

        // Demonstrate with custom object type
        demonstrateCustomObjectBag();
    }

    /**
     * Demonstrates Bag functionality with String elements.
     */
    private static void demonstrateStringBag() {
        System.out.println("--- String Bag Demo ---\n");

        // Create a new Bag for Strings
        Bag<String> fruits = new Bag<>();

        // 1. Test add() method
        System.out.println("1. Adding elements:");
        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Cherry");
        fruits.add("Apple");  // Bags allow duplicates
        fruits.add("Date");
        System.out.println("   Added: Apple, Banana, Cherry, Apple, Date");
        System.out.println("   Bag contents: " + fruits);

        // 2. Test size() method
        System.out.println("\n2. Checking size:");
        System.out.println("   Size: " + fruits.size());

        // 3. Test isEmpty() method
        System.out.println("\n3. Checking if empty:");
        System.out.println("   Is empty? " + fruits.isEmpty());

        // 4. Test contains() method
        System.out.println("\n4. Checking containment:");
        System.out.println("   Contains 'Apple'? " + fruits.contains("Apple"));
        System.out.println("   Contains 'Grape'? " + fruits.contains("Grape"));

        // 5. Test iterator with while loop
        System.out.println("\n5. Iterating with Iterator (while loop):");
        Iterator<String> iterator = fruits.iterator();
        System.out.print("   Elements: ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();

        // 6. Test for-each loop (uses iterator internally)
        System.out.println("\n6. Iterating with for-each loop:");
        System.out.print("   Elements: ");
        for (String fruit : fruits) {
            System.out.print(fruit + " ");
        }
        System.out.println();

        // 7. Test forEach() method with lambda
        System.out.println("\n7. Using forEach() with lambda:");
        System.out.print("   Elements (uppercase): ");
        fruits.forEach(fruit -> System.out.print(fruit.toUpperCase() + " "));
        System.out.println();

        // 8. Test remove() method
        System.out.println("\n8. Removing elements:");
        boolean removed = fruits.remove("Apple");  // Removes first occurrence
        System.out.println("   Removed 'Apple'? " + removed);
        System.out.println("   Bag after removal: " + fruits);
        System.out.println("   Still contains 'Apple'? " + fruits.contains("Apple"));

        // 9. Test removing non-existent element
        System.out.println("\n9. Attempting to remove non-existent element:");
        removed = fruits.remove("Grape");
        System.out.println("   Removed 'Grape'? " + removed);

        // 10. Test iterator remove() method
        System.out.println("\n10. Removing via iterator:");
        iterator = fruits.iterator();
        while (iterator.hasNext()) {
            String fruit = iterator.next();
            if (fruit.equals("Banana")) {
                iterator.remove();
                System.out.println("    Removed 'Banana' via iterator");
            }
        }
        System.out.println("    Bag after iterator removal: " + fruits);

        // 11. Test null rejection
        System.out.println("\n11. Testing null rejection:");
        try {
            fruits.add(null);
            System.out.println("    ERROR: Should have thrown NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("    ✓ Correctly rejected null in add(): " + e.getMessage());
        }

        try {
            fruits.contains(null);
            System.out.println("    ERROR: Should have thrown NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("    ✓ Correctly rejected null in contains(): " + e.getMessage());
        }

        try {
            fruits.remove(null);
            System.out.println("    ERROR: Should have thrown NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("    ✓ Correctly rejected null in remove(): " + e.getMessage());
        }
    }

    /**
     * Demonstrates Bag functionality with Integer elements.
     */
    private static void demonstrateIntegerBag() {
        System.out.println("--- Integer Bag Demo ---\n");

        // Create a Bag with initial capacity
        Bag<Integer> numbers = new Bag<>(5);

        // Add numbers
        System.out.println("1. Adding integers:");
        for (int i = 1; i <= 10; i++) {
            numbers.add(i * 10);
        }
        System.out.println("   Bag contents: " + numbers);

        // Use forEach to perform calculations
        System.out.println("\n2. Using forEach() to calculate sum:");
        final int[] sum = {0};  // Use array to allow modification in lambda
        numbers.forEach(num -> sum[0] += num);
        System.out.println("   Sum of all elements: " + sum[0]);

        // Filter and display even numbers
        System.out.println("\n3. Filtering even numbers (divisible by 20):");
        System.out.print("   Even numbers: ");
        for (Integer num : numbers) {
            if (num % 20 == 0) {
                System.out.print(num + " ");
            }
        }
        System.out.println();

        // Test with duplicates
        System.out.println("\n4. Testing with duplicates:");
        numbers.add(50);
        numbers.add(50);
        System.out.println("   Added two 50s");
        System.out.println("   Bag contents: " + numbers);
        System.out.println("   Size: " + numbers.size());

        // Remove one duplicate
        numbers.remove(50);
        System.out.println("   Removed one 50");
        System.out.println("   Bag contents: " + numbers);
        System.out.println("   Size: " + numbers.size());
    }

    /**
     * Demonstrates Bag functionality with custom objects.
     */
    private static void demonstrateCustomObjectBag() {
        System.out.println("--- Custom Object Bag Demo ---\n");

        // Create a Bag for Person objects
        Bag<Person> people = new Bag<>();

        // Add people
        System.out.println("1. Adding Person objects:");
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 25));
        people.add(new Person("Charlie", 35));
        people.add(new Person("Alice", 30));  // Duplicate based on equals()
        System.out.println("   Added 4 people");
        System.out.println("   Bag size: " + people.size());

        // Display all people
        System.out.println("\n2. Displaying all people:");
        people.forEach(person -> System.out.println("   " + person));

        // Test contains with equals()
        System.out.println("\n3. Testing contains():");
        Person searchPerson = new Person("Alice", 30);
        System.out.println("   Contains " + searchPerson + "? " + people.contains(searchPerson));

        // Use iterator to find specific people
        System.out.println("\n4. Finding people over 30:");
        for (Person person : people) {
            if (person.getAge() > 30) {
                System.out.println("   " + person);
            }
        }

        // Test spliterator
        System.out.println("\n5. Using Spliterator:");
        Spliterator<Person> spliterator = people.spliterator();
        System.out.println("   Spliterator characteristics: " + spliterator.characteristics());
        System.out.println("   Estimated size: " + spliterator.estimateSize());
        System.out.print("   First few elements: ");
        spliterator.tryAdvance(person -> System.out.print(person.getName() + " "));
        spliterator.tryAdvance(person -> System.out.print(person.getName() + " "));
        System.out.println();

        // Remove a person
        System.out.println("\n6. Removing a person:");
        Person toRemove = new Person("Bob", 25);
        boolean removed = people.remove(toRemove);
        System.out.println("   Removed " + toRemove + "? " + removed);
        System.out.println("   Remaining people:");
        people.forEach(person -> System.out.println("   " + person));
    }

    /**
     * Simple Person class to demonstrate Bag with custom objects.
     */
    private static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        /**
         * Two Person objects are equal if they have the same name and age.
         * This is important for contains() and remove() methods.
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Person person = (Person) obj;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode() * 31 + age;
        }

        @Override
        public String toString() {
            return name + " (age " + age + ")";
        }
    }
}