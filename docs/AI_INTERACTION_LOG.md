Prompt used to create Bag class:

"I have the attached interface. Generate a generic class called Bag that implements the container interface attached. The Bag implementation must use 
the java arraylist as a backing structure. Do not change or add to the container interface. implement the forEach and spliterator methods of the 
Iterable interface. ensure iterator hasNext() and next() work perfectly and properly use the generic type parameter <E> throughout. Include comments 
to describe what the code means and provide reasoning on why it is done that specific way."

Not a requirement but asked Claude to make a Driver class to demonstrate usage. The prompt for that is:

"Can you create a driver class that can demonstrate how the bag class works"

Prompts to question null handling:

"Do arraylists in java usually allow nulls as elements?"

"Since I am using an array list as a backing structure, is that why you allowed nulls in the bag and driver class? If I were not using an ArrayList as a backing structure would you still have 
allowed nulls and empty bags or would you have thrown exceptions in these scenarios?"

Prompt used to create unit tests for Bag class:

"can you create a comprehensive unit test BagTest for my Bag implementation. The test should cover edge cases, normal operations, and the iterator 
functionality. include test for empty bag operations."

Prompt used to update the test class to add more edge cases. It didn't look like the first test class threw an exception when elements were null:

"can you make sure that the test tests for edge cases (null handling, remove non-existent items, etc.) for null cases throw an exception"

I then had to modify the bag class and the driver class to handle nulls properly. AI asked follow-up questions and I just confirmed what I wanted it to do.
For the bag class it updated the add, remove, and contains methods to throw nullPointerExceptions if the item is null. In the driver class it added try-catch
blocks around the calls that added, removed, or checked for nulls.







    