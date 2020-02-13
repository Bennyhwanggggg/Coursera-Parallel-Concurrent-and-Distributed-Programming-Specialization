# Combining Distribution And MultiThreading
## 4.1 Combining Distribution and Multithreading
**Lecture Summary**: In this lecture, we introduced processes and threads, which serve as the fundamental building blocks of distributed computing software. Though the concepts of processes and threads have been around for decades, the ability to best map them on to hardware has changed in the context of data centers with multicore nodes. In the case of Java applications, a process corresponds to a single Java Virtual Machine (JVM) instance, and threads are created within a JVM instance. The advantages of creating multiple threads in a process include increased sharing of memory and per-process resources by threads, improved responsiveness due to multithreading, and improved performance since threads in the same process can communicate with each other through a shared address space. The advantages of creating multiple processes in a node include improved responsiveness (also) due to multiprocessing (e.g., when a JVM is paused during garbage collection), improved scalability (going past the scalability limitations of multithreading), and improved resilience to JVM failures within a node. However, processes can only communicate with each other through message-passing and other communication patterns for distributed computing.

In summary, processes are the basic units of distribution in a cluster of nodes - we can distribute processes across multiple nodes in a data center, and even create multiple processes within a node. Threads are the basic unit of parallelism and concurrency -- we can create multiple threads in a process that can share resources like memory, and contribute to improved performance. However, it is not possible for two threads belonging to the same process to be scheduled on different nodes.

Optional Reading:
1. Wikipedia articles on processes and threads.

## 4.2 Multithreaded Servers
**Lecture Summary**: In this lecture, we learned about multithreaded servers as an extension to the servers that we studied in client-server programming. As a motivating example, we studied the timeline for a single request sent to a standard sequential file server, which typically consists of four steps: A) accept the request, B) extract the necessary information from the request, C) read the file, and D) send the file. In practice, step C) is usually the most time-consuming step in this sequence. However, threads can be used to reduce this bottleneck. In particular, after step A) is performed, a new thread can be created to process steps B), C) and D) for a given request. In this way, it is possible to process multiple requests simultaneously because they are executing in different threads.

One challenge of following this approach literally is that there is a significant overhead in creating and starting a Java thread. However, since there is usually an upper bound on the number of threads that can be efficiently utilized within a node (often limited by the number of cores or hardware context), it is wasteful to create more threads than that number. There are two approaches that are commonly taken to address this challenge in Java applications. One is to use a thread pool, so that threads can be reused across multiple requests instead of creating a new thread for each request. Another is to use lightweight tasking (e.g., as in Java's ForkJoin framework) which execute on a thread pool with a bounded number of threads, and offer the advantage that the overhead of task creation is significantly smaller than that of thread creation. You can learn more about tasks and threads in the companion courses on "Parallel Programming in Java" and "Concurrent Programming in Java" in this specialization.

Optional Readings:
1. Wikipedia article on thread pools.

2. Tutorial on Java's fork/join framework (also covered in Parallelism course).

## 4.3 MPI and Multithreading
**Lecture Summary**: In this lecture, we learned how to extend the Message Passing Interface (MPI) with threads. As we learned earlier in the lecture on Processes and Threads, it can be inefficient to create one process per processor core in a multicore node since there is a lot of unnecessary duplication of memory, resources, and overheads when doing so. This same issue arises for MPI programs in which each rank corresponds to a single-threaded process by default. Thus, there are many motivations for creating multiple threads in an MPI process, including the fact that threads can communicate with each other much more efficiently using shared memory, compared with the message-passing that is used to communicate among processes.

One approach to enable multithreading in MPI applications is to create one MPI process (rank) per node, which starts execution in a single thread that is referred to as a master thread. This thread calls 𝙼𝙿𝙸_𝙸𝚗𝚒𝚝() and 𝙼𝙿𝙸_𝙵𝚒𝚗𝚊𝚕𝚒𝚣𝚎() for its rank, and creates a number of worker threads to assist in the computation to be performed within its MPI process. Further, all MPI calls are performed only by the master thread. This approach is referred to as the 𝙼𝙿𝙸_𝚃𝙷𝚁𝙴𝙰𝙳_𝙵𝚄𝙽𝙽𝙴𝙻𝙴𝙳 mode, since, even though there are multiple threads, all MPI calls are "funneled'' through the master thread. A second more general mode for MPI and multithreading is referred to as 𝙼𝙿𝙸_𝚃𝙷𝚁𝙴𝙰𝙳_𝚂𝙴𝚁𝙸𝙰𝙻𝙸𝚉𝙴𝙳; in this mode, multiple threads may make MPI calls but must do so one at a time using appropriate concurrency constructs so that the calls are "serialized''. The most general mode is called 𝙼𝙿𝙸_𝚃𝙷𝚁𝙴𝙰𝙳_𝙼𝚄𝙻𝚃𝙸𝙿𝙻𝙴 because it allows multiple threads to make MPI calls in parallel; though this mode offers more flexibility than the other modes, it puts an additional burden on the MPI implementation which usually gets reflected in larger overheads for MPI calls relative to the more restrictive modes. Further, even the 𝙼𝙿𝙸_𝚃𝙷𝚁𝙴𝙰𝙳_𝙼𝚄𝙻𝚃𝙸𝙿𝙻𝙴 mode has some notable restrictions, e.g., it is not permitted in this mode for two threads in the same process to wait on the same MPI request related to a nonblocking communication.

Optional Reading:
1. Lecture by Prof. William Gropp on MPI, Hybrid Programming, and Shared Memory.





