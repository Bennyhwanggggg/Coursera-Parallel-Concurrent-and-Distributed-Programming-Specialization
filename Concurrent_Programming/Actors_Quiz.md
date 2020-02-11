# Module 3 Quiz - Actors
Question 1: What advantage do actors have over object-based isolation?

A. Avoids data races

B. Less runtime overhead

C. Increased CPL

D. Reduces programmer error by making a variable isolated by default

A: D

Question 2: How does an actor interact with the local state?

A. Using parallel threads to access the local state variable

B. Using predefined message methods in that subclass of actor

C. Using only the methods INCREMENT, DECREMENT, EXIT

D. Using message methods the programmer defines for that subclass of actor

A: D

Question 3: In the Actor model, message ordering can be preserved in which of the following cases?

A. Never

B. Same sender, different receiver

C. Same receiver, different sender

D. Same sender and receiver

A: D

Question 4: How many actors do you need in an actor-based pipeline?

A. One

B. One actor per pipeline stage

C. One actor per actor sublcass

A: B

Question 5: For generating the primes less than n, how many actors will the Sieve of Eratosthenes use?

A. 1

B. n^(1/2)

C. n−1

D. n

E. Number of primes less than nn

A: E

Question 6: Which of the following statements is/are true regarding the sieve pipeline introduced in the video?

Please choose all options that are correct.

A. The pipeline grows dynamically

B. Each next actor in the pipeline is created and started by the previous actor

C. What numbers get filtered in the next stage of the pipeline is arbitrarily determined

A: A, B

Question 7: Which of the following would be good objects to use to implement an unbounded buffer in Java?

Please choose all options that are correct.

A. PriorityBlockingQueue

B. SynchronousQueue

C. ConcurrentLinkedQueue

D. ArrayBlockingQueue

A: A, C

Question 8: Why is it beneficial to model producers, consumers and their unbounded buffer as actors?

Please choose all options that are correct.

A. Actors are more efficient than alternatives like waiting on a while loop condition to evaluate to true.

B. There is no need for producers to check whether the buffer is full, or for consumers to check whether the buffer is empty.

C. The consumer can remove items from the buffer at will.

D. The producer must coordinate with the master actor when it's ready to insert items in the buffer.

A: A, B

Question 9: P is the number of producer actors and C is the number of consumer actors. What can we assume about the size of the buffer (B)?

A. B < P and B < C

B. B < P or B < C

C. P ≤ B

D. C ≤ B

A: C

Question 10: The bounded buffer master actor coordinates with the producer actors and consumer actors by...

Please choose all options that are correct.

A. Requesting data from a producer actor

B. Waiting for a producer actor to send it data

C. Requesting removal of data by consumer actors

D. Waiting for a consumer actor to tell the buffer that it is ready

A: A, D
