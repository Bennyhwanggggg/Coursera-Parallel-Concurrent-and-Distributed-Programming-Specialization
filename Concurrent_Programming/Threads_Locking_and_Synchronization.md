# Threads
## Java Threads
**Lecture Summary**: In this lecture, we learned the concept of threads as lower-level building blocks for concurrent programs. A unique aspect of Java compared to prior mainstream programming languages is that Java included the notions of threads (as instances of the 𝚓𝚊𝚟𝚊.𝚕𝚊𝚗𝚐.𝚃𝚑𝚛𝚎𝚊𝚍 class) in its language definition right from the start.

When an instance of 𝚃𝚑𝚛𝚎𝚊𝚍 is created (via a 𝚗𝚎𝚠 operation), it does not start executing right away; instead, it can only start executing when its 𝚜𝚝𝚊𝚛𝚝() method is invoked. The statement or computation to be executed by the thread is specified as a parameter to the constructor.

The Thread class also includes a wait operation in the form of a 𝚓𝚘𝚒𝚗() method. If thread 𝚝𝟶 performs a 𝚝𝟷.𝚓𝚘𝚒𝚗() call, thread 𝚝𝟶 will be forced to wait until thread 𝚝𝟷 completes, after which point it can safely access any values computed by thread 𝚝𝟷. Since there is no restriction on which thread can perform a 𝚓𝚘𝚒𝚗 on which other thread, it is possible for a programmer to erroneously create a deadlock cycle with 𝚓𝚘𝚒𝚗 operations. (A deadlock occurs when two threads wait for each other indefinitely, so that neither can make any progress.)

Further Reading:
Wikipedia article on Threads
Tutorial on Java threads
Documentation on Thread class in Java 8

## 1.2 Structured Locks
**Lecture Summary**: In this lecture, we learned about structured locks, and how they can be implemented using 𝚜𝚢𝚗𝚌𝚑𝚛𝚘𝚗𝚒𝚣𝚎𝚍 statements and methods in Java. Structured locks can be used to enforce mutual exclusion and avoid data races, as illustrated by the 𝚒𝚗𝚌𝚛() method in the 𝙰.𝚌𝚘𝚞𝚗𝚝 example, and the 𝚒𝚗𝚜𝚎𝚛𝚝() and 𝚛𝚎𝚖𝚘𝚟𝚎() methods in the the 𝙱𝚞𝚏𝚏𝚎𝚛 example. A major benefit of structured locks is that their acquire and release operations are implicit, since these operations are automatically performed by the Java runtime environment when entering and exiting the scope of a 𝚜𝚢𝚗𝚌𝚑𝚛𝚘𝚗𝚒𝚣𝚎𝚍 statement or method, even if an exception is thrown in the middle.

We also learned about 𝚠𝚊𝚒𝚝() and 𝚗𝚘𝚝𝚒𝚏𝚢() operations that can be used to block and resume threads that need to wait for specific conditions. For example, a producer thread performing an 𝚒𝚗𝚜𝚎𝚛𝚝() operation on a bounded buffer can call 𝚠𝚊𝚒𝚝() when the buffer is full, so that it is only unblocked when a consumer thread performing a 𝚛𝚎𝚖𝚘𝚟𝚎() operation calls 𝚗𝚘𝚝𝚒𝚏𝚢(). Likewise, a consumer thread performing a 𝚛𝚎𝚖𝚘𝚟𝚎() operation on a bounded buffer can call 𝚠𝚊𝚒𝚝() when the buffer is empty, so that it is only unblocked when a producer thread performing an 𝚒𝚗𝚜𝚎𝚛𝚝() operation calls 𝚗𝚘𝚝𝚒𝚏𝚢(). Structured locks are also referred to as intrinsic locks or monitors.

Optional Reading:
1. Tutorial on Intrinsic Locks and Synchronization in Java

2. Tutorial on Guarded Blocks in Java

3. Wikipedia article on Monitors

## 1.3 Unstructured Locks
**Lecture Summary**: In this lecture, we introduced unstructured locks (which can be obtained in Java by creating instances of 𝚁𝚎𝚎𝚗𝚝𝚛𝚊𝚗𝚝𝙻𝚘𝚌𝚔()), and used three examples to demonstrate their generality relative to structured locks. The first example showed how explicit 𝚕𝚘𝚌𝚔() and 𝚞𝚗𝚕𝚘𝚌𝚔() operations on unstructured locks can be used to support a hand-over-hand locking pattern that implements a non-nested pairing of lock/unlock operations which cannot be achieved with synchronized statements/methods. The second example showed how the 𝚝𝚛𝚢𝙻𝚘𝚌𝚔() operations in unstructured locks can enable a thread to check the availability of a lock, and thereby acquire it if it is available or do something else if it is not. The third example illustrated the value of read-write locks (which can be obtained in Java by creating instances of 𝚁𝚎𝚎𝚗𝚝𝚛𝚊𝚗𝚝𝚁𝚎𝚊𝚍𝚆𝚛𝚒𝚝𝚎𝙻𝚘𝚌𝚔()), whereby multiple threads are permitted to acquire a lock 𝙻 in “read mode”, 𝙻.𝚛𝚎𝚊𝚍𝙻𝚘𝚌𝚔().𝚕𝚘𝚌𝚔(), but only one thread is permitted to acquire the lock in “write mode”, 𝙻.𝚠𝚛𝚒𝚝𝚎𝙻𝚘𝚌𝚔().𝚕𝚘𝚌𝚔().

However, it is also important to remember that the generality and power of unstructured locks is accompanied by an extra responsibility on the part of the programmer, e.g., ensuring that calls to 𝚞𝚗𝚕𝚘𝚌𝚔() are not forgotten, even in the presence of exceptions.

Optional Reading:
1. Tutorial on Lock Objects in Java

2. Documentation on Java’s Lock interfaces

## 1.4 Liveness and Progress Guarantees
**Lecture Summary**: In this lecture, we studied three ways in which a parallel program may enter a state in which it stops making forward progress. For sequential programs, an “infinite loop” is a common way for a program to stop making forward progress, but there are other ways to obtain an absence of progress in a parallel program. The first is deadlock, in which all threads are blocked indefinitely, thereby preventing any forward progress. The second is livelock, in which all threads repeatedly perform an interaction that prevents forward progress, e.g., an infinite “loop” of repeating lock acquire/release patterns. The third is starvation, in which at least one thread is prevented from making any forward progress.

The term “liveness” refers to a progress guarantee. The three progress guarantees that correspond to the absence of the conditions listed above are deadlock freedom, livelock freedom, and starvation freedom.

Optional Reading:
1. Deadlock example with synchronized methods in Java

2. Starvation and Livelock examples in Java

3. Wikipedia article on Deadlock and Livelock

## 1.5 Dining Philosophers Problem
Lecture Summary: In this lecture, we studied a classical concurrent programming example that is referred to as the Dining Philosophers Problem. In this problem, there are five threads, each of which models a “philosopher” that repeatedly performs a sequence of actions which include think, pick up chopsticks, eat, and put down chopsticks.

First, we examined a solution to this problem using structured locks, and demonstrated how this solution could lead to a deadlock scenario (but not livelock). Second, we examined a solution using unstructured locks with 𝚝𝚛𝚢𝙻𝚘𝚌𝚔() and 𝚞𝚗𝚕𝚘𝚌𝚔() operations that never block, and demonstrated how this solution could lead to a livelock scenario (but not deadlock). Finally, we observed how a simple modification to the first solution with structured locks, in which one philosopher picks up their right chopstick and their left, while the others pick up their left chopstick first and then their right, can guarantee an absence of deadlock.

Optional Reading:
1. Wikipedia article on the Dining Philosophers Problem

