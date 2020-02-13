# Module 3 - Message Passing
Question 1: Say you have a logical 4-element array of data and 2 nodes to process that data with. The global view of this logical array is similarly a 4-element array storing the full dataset. How is node 0’s local view of that same array likely to be different from the global view, assuming that data is distributed as evenly as possible between the two nodes?

A. Node 0’s local view will also store the full 4 elements. By being local to node 0 performance will be improved when accessing that array.

B. Node 0’s local view will be half the size of the global view, and will only store 2 elements of the logical array. However, which two elements it will store is up to the programmer and is referred to as the data distribution.

C. Node 0’s local view will be half the size of the global view, and will only store 2 elements of the logical array. Node 0 must store the first 2 elements in the global logical array.

D. Node 0 will store zero elements from the global array because its rank is equal to zero.

A: B

Question 2: In the first lecture video, we see a global view XG split into two local views, each called XL and each stored in a separate node. 

True or false: Node 0 can directly access node 1’s XL, and vice versa?

A: False

Question 3: In MPI programs, how would you normally select the logic for different SPMD nodes to run?

A. By looking up the hostname of the current node

B. Through a global negotiation with the other nodes in the SPMD program

C. By querying for the MPI rank of the current node

D. Based on MPI tags

A: C

Question 4: Which of the below communication patterns are considered an example of point-to-point communication?

Choose all that apply

A. Send

B. Broadcast

C. Receive

D. Scatter

E. Gather

A: A, C

Question 5: Given the following three nodes and their send/receive schedules, which will finish its sends/receives first?

P0: Send X to P1; Recv Y from P2;

P1: Recv X from P0; Recv Z from P2;

P2: Send Y to P0; Send Z to P1;

A. P0

B. P1

C. P2

D. It can't be known because there's no guarantee of message order

E. It can't be known because this schedule will result in deadlock

A: A

Question 6: In the above node schedule (in question five), which operations can be blocking simultaneously? Assume there are no network delays.

Choose all that apply

A. Send Y to P0 and Recv Y from P2

B. Send X to P1 and Send Y to P0

C. Recv X from P0 and Recv Z from P2

D. Recv Y from P2 and Send Z to P1

E. Recv Z from P2 and Send Y to P0

A: B, E

Question 7: Which of the following are advantages to using ISend and IRecv (and Wait) instead of Send and Recv?

Choose all that apply

A. They're less likely to produce data races

B. They require writing less code to achieve the same result

C. They reduce the possibility of deadlock

D. They can increase parallelism

A: C, D

Question 8: Which of the following statements about non-blocking communication is correct?

A. It’s impossible to have deadlock if one only uses ISend, IRecv, and WaitAny

B. It’s impossible to have deadlock if one only uses ISend, IRecv, and WaitAll

C. Using the result of an IRecv before it has actually been received implicitly calls Wait on the request returned by IRecv

D. One can emulate blocking Send and Recv calls by immediately calling any variety of Wait after a call to ISend or IRecv

A: D

Question 9: Which of the following is true for MPI’s broadcast and reduce collectives?

1. A broadcast sends data from one node to all nodes, while a reduce sends data from all nodes to one node.

2. Both broadcast and reduce apply some mathematical transformation to their inputs to produce an output.

3. A broadcast can transmit many integers at once, but a reduce can only be applied to one integer at a time.

4. In both, the root parameter specifies a main process that either sends or receives all data.

A. 1 and 4

B. 1, 3, and 4

C. 1 and 3

D. 2 and 3

A: A

Question 10: What is one of the benefits of using MPI collectives?

A. The operations that MPI collectives implement can only be implemented in the operating system kernel, and so we must rely on lower levels of the stack for them.

B. They offer constant time cost for all collective operations and processor counts.

C. MPI collectives offer optimized and succinct implementations of common, distributed operations.

A: C

