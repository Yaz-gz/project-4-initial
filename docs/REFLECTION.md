Another successful project completed with the help of AI! I think the bag class was created correctly the first time but then when I started to create the 
bag test class I realized that I didn't have any null handling in the bag class. I went back to the bag class and modified the add, remove, and 
contains methods to throw NullPointerExceptions. I didn't catch this the first time because usually when creating the test using claude, it catches null cases and 
throws an exception. This time it allowed nulls and just returned false when checking for contains or remove. Once I realized this I updated the bag class and 
then updated the test class to include tests for nulls. I also had to update the driver class to include try-catch blocks 
around the calls that added, removed, or checked for nulls because it was allowing nulls and just working around them as if they were normal cases.
In my test I did not throw exceptions for empty bag operations because I felt that it might be important to be able to check if the bag is empty, if we were using this in a 
real application. 

Claude did mention that nulls are allowed in the ArrayList collection and that's why it didn't throw exceptions for them in the first go around but I just felt that it makes more sense to not allow nulls in the bag. 
Claude did recommend that I revert my code to allow nulls and stay consistent with Java conventions but I kind of thought of this project as building a grocery list and that's why I chose to not allow nulls, 
because it doesn't make sense to have a null item in a grocery list. I did add the comments to my notes for future reference in other projects where we use ArrayLists as backing structures. 

I think AI did a good job in explaining why it did things a certain way. I also liked that it was reminding me of best practices when I was customizing the code to my liking. Another win for Claude!
