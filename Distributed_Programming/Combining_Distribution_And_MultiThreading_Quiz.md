# Module 4 Quiz - Combining Distribution and MultiThreading
Question 1: Using multiple threads per process can help with:

A. Resource sharing

B. Performance

C. Responsiveness to JVM delays

D. Scalability

E. Responsiveness to network delays

F. Resource availability

A: A, B, E

Question 2: True or false: on a node with 16 cores, running 16 processes with 1 thread each will always be faster than running one process with 16 threads?

A: False

Question 3: The benefits of using a multithreaded server vs. a single-threaded one are:

A. Increased throughput of completed requests

B. Reduced time it takes to service an individual request

C. Reduced delay between request submission and processing of a request

D. Elimination of data races and contention between requests

A: A, C

4.Question 4
In the following multithreaded file server pseudo-code:
```
listener = new ServerSocket(…);
while(true){
   s = listener.accept(…);                     // A
   t = new Thread( () -> {
      read file request from s.getInputStream; // B
      access the file;                         // C
      send file to s.getOutputStream;          // D
   });
   t.start();
}
```
Which of the operations in the algorithm have to ensure that the concurrent access to memory or resources is handled correctly?

A. None, the implementation does not have to worry about concurrency

B. All of them: A, B, C and D have to ensure a safe concurrent thread access

C. A and C

D. Only C

A: D

Question 5: Which of the following is not a valid MPI mode?

A. Funneled

B. Multiple

C. Single

D. Serialized

A: C

Question 6: I have a program with threads T<sub>0</sub>, T<sub>1</sub>, T<sub>2</sub>, T<sub>3</sub>. I want to make all communications to the MPI go through T<sub>0</sub>. Which of the MPI modes would I want to use?

A. Funneled

B. Multiple

C. Single

D. Serialized

A: A

Question 7: Which of the following statement is false?:

A. Remote actors residing on different nodes cannot exchange object references because they can only communicate through message passing.

B. All messages sent from an actor must be serialized and be passed by copy in a distributed actor program.

C. Multiple actors in an actor-based program can run on different physical nodes without change to the program logic.

D. In a distributed actor system, actors maintain a logical name that can be remotely referenced by other actors across the node boundaries.

A: B

8.Question 8
Consider a distributed actor-based implementation of the Sieve of Eratosthenes as follows:
```
SieveActor{
  int local_prime;
  SieveActor next;
  SieveActor(int prime) { local_prime = prime;}
  void process(Message message){
    if  ( 0 != message.val % local_prime) {
      if ( NULL != next ){
        next.send(message);        
      } else {
        //create the next sieve actor at local node
        next = newActor(class:=SieveActor.class, arguments:=[message.val]);
      } 
    }  
  }
}
```
Assuming there are two physical nodes in the network, with 32 bit nodeID with integer values 0 and 1, which of the following programs that replaces line 11 can maximize the number of messages crossing the node boundary?

A.
```
SieveActor{
  int local_prime;
  SieveActor next;
  SieveActor(int prime) { local_prime = prime;}
  void process(Message message){
    if  ( 0 != message.val % local_prime) {
      if ( NULL != next ){
        next.send(message);        
      } else {
        //create the next sieve actor at local node
        next = newActor(class:=SieveActor.class, arguments:=[message.val]);
      } 
    }  
  }
}
```
B.
```
next = newRemoteActor(class:=SieveActor.class, arguments:=[message.val], node:=(localNodeId ^1)
```
C.
```
next = newRemoteActor(class:= SieveActor.class, arguments :=[message.val], node:=  (localNodeId + 1) %2)
```
D.
```
next = newRemoteActor(class:=SieveActor.class, arguments:=[message.val], node:= (~localNodeId))
```

A: B, C

Question 9: Which of the following statements is true?

A. An advantage of the actor model is the ability of the actor to specify when to receive data.

B. A polling model where the consumer requests items periodically reduces delays in receiving information.

C. In reactive programming, producers propagate events to subscribers to trigger reactions.

D. In reactive programming, the subscriber has no way to specify how frequently it will receive data.

A: C

Question 10: What is the expected output of the following piece of Java-based pseudocode?
```
Publisher pub = new Publisher();
Subscriber sub = new Subscriber() {
  int x = 0;
  void onNext(int item) {
    x += item;
    System.out.print(x + “ ,”);
  }
};
pub.subscribe(sub);
pub.submit(3);
pub.submit(30);
```
A. There will be no output

B. 3, 30,

C. 0, 3,

D. 3, 33,

A: D
