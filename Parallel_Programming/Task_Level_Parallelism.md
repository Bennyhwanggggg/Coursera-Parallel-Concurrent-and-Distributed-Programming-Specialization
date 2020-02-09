# 1. Task-level Parallelism
## 1.1 Task Creation and Termination (Async, Finish)

**Lecture Summary**: In this lecture, we learned the concepts of task creation and task termination in parallel programs, using array-sum as an illustrative example. We learned the async notation for task creation: “async ⟨stmt1⟩”, causes the parent task (i.e., the task executing the async statement) to create a new child task to execute the body of the async, ⟨stmt1⟩, asynchronously (i.e., before, after, or in parallel) with the remainder of the parent task. We also learned the finish notation for task termination: “finish ⟨stmt2⟩” causes the parent task to execute ⟨stmt2⟩, and then wait until ⟨stmt2⟩ and all async tasks created within ⟨stmt2⟩ have completed. Async and finish constructs may be arbitrarily nested.

The example studied in the lecture can be abstracted by the following pseudocode:
```
finish {
  async S1; // asynchronously compute sum of the lower half of the array
  S2;       // compute sum of the upper half of the array in parallel with S1
}
S3; // combine the two partial sums after both S1 and S2 have finished
```
While async and finish notations are useful algorithmic/pseudocode notations, we also provide you access to a high-level open-source Java-8 library called PCDP (for Parallel, Concurrent, and Distributed Programming), for which the source code is available at https://github.com/habanero-rice/pcdp. PCDP contains APIs (application programming interfaces) that directly support async and finish constructs so that you can use them in real code as well. In the next lecture, you will learn how to to implement the async and finish functionality using Java’s standard Fork/Join (FJ) framework.

Optional Reading:

1. Wikipedia article on Asynchronous method invocation

## 1.2 Creating Tasks in Java's Fork/Join Framework

**Lecture Summary**: In this lecture, we learned how to implement the async and finish functionality using Java’s standard Fork/Join (FJ) framework. In this framework, a task can be specified in the 𝚌𝚘𝚖𝚙𝚞𝚝𝚎() method of a user-defined class that extends the standard RecursiveAction class in the FJ framework. In our Array Sum example, we created class 𝙰𝚂𝚞𝚖 with fields 𝙰 for the input array, 𝙻𝙾 and 𝙷𝙸 for the subrange for which the sum is to be computed, and 𝚂𝚄𝙼 for the result for that subrange. For an instance of this user-defined class (e.g., 𝙻 in the lecture), we learned that the method call, 𝙻.𝚏𝚘𝚛𝚔(), creates a new task that executes 𝙻’s 𝚌𝚘𝚖𝚙𝚞𝚝𝚎() method. This implements the functionality of the async construct that we learned earlier. The call to 𝙻.𝚓𝚘𝚒𝚗() then waits until the computation created by 𝙻.𝚏𝚘𝚛𝚔() has completed. Note that 𝚓𝚘𝚒𝚗() is a lower-level primitive than finish because 𝚓𝚘𝚒𝚗() waits for a specific task, whereas finish implicitly waits for all tasks created in its scope. To implement the finish construct using 𝚓𝚘𝚒𝚗() operations, you have to be sure to call 𝚓𝚘𝚒𝚗() on every task created in the finish scope.

A sketch of the Java code for the ASum class is as follows:
```
private static class ASum extends RecursiveAction {
  int[] A; // input array
  int LO, HI; // subrange
  int SUM; // return value
  . . .
  @Override
  protected void compute() {
    SUM = 0;
    for (int i = LO; i <= HI; i++) SUM += A[i];
  } // compute()
}
```
FJ tasks are executed in a ForkJoinPool, which is a pool of Java threads. This pool supports the invokeAll() method that combines both the 𝚏𝚘𝚛𝚔 and 𝚓𝚘𝚒𝚗 operations by executing a set of tasks in parallel, and waiting for their completion. For example, 𝙵𝚘𝚛𝚔𝙹𝚘𝚒𝚗𝚃𝚊𝚜𝚔.𝚒𝚗𝚟𝚘𝚔𝚎𝙰𝚕𝚕(𝚕𝚎𝚏𝚝,𝚛𝚒𝚐𝚑𝚝) implicitly performs 𝚏𝚘𝚛𝚔() operations on 𝚕𝚎𝚏𝚝 and 𝚛𝚒𝚐𝚑𝚝, followed by 𝚓𝚘𝚒𝚗() operations on both objects.

Optional Reading:

1. Tutorial on Java’s Fork/Join framework

2. Documentation on Java’s RecursiveAction class

## 1.3 Computation Graphs, Work, Span, Ideal Parallelism

**Lecture Summary**: In this lecture, we learned about Computation Graphs (CGs), which model the execution of a parallel program as a partially ordered set. Specifically, a CG consists of:

A set of vertices or nodes, in which each node represents a step consisting of an arbitrary sequential computation.
A set of directed edges that represent ordering constraints among steps.
For fork–join programs, it is useful to partition the edges into three cases:

1. Continue edges that capture sequencing of steps within a task.

2. Fork edges that connect a fork operation to the first step of child tasks.

3. Join edges that connect the last step of a task to all join operations on that task.

CGs can be used to define data races, an important class of bugs in parallel programs. We say that a data race occurs on location L in a computation graph, G, if there exist steps S1 and S2 in G such that there is no path of directed edges from S1 to S2 or from S2 to S1 in G, and both S1 and S2 read or write L (with at least one of the accesses being a write, since two parallel reads do not pose a problem).

CGs can also be used to reason about the ideal parallelism of a parallel program as follows:  
- Define WORK(G) to be the sum of the execution times of all nodes in CG G,
0 Define SPAN(G) to be the length of a longest path in G, when adding up the execution times of all nodes in the path. The longest paths are known as critical paths, so SPAN also represents the critical path length (CPL) of G.

Given the above definitions of WORK and SPAN, we define the ideal parallelism of Computation Graph G as the ratio, WORK(G)/SPAN(G). The ideal parallelism is an upper limit on the speedup factor that can be obtained from parallel execution of nodes in computation graph G. Note that ideal parallelism is only a function of the parallel program, and does not depend on the actual parallelism available in a physical computer.

Optional Reading:

1. Wikipedia article on Analysis of parallel algorithms

## 1.4 Multiprocessor Scheduling, Parallel Speedup

**Lecture Summary**: In this lecture, we studied the possible executions of a Computation Graph (CG) on an idealized parallel machine with P processors. It is idealized because all processors are assumed to be identical, and the execution time of a node is assumed to be fixed, regardless of which processor it executes on. A legal schedule is one that obeys the dependence constraints in the CG, such that for every directed edge (A, B), the schedule guarantees that step B is only scheduled after step A completes. Unless otherwise specified, we will restrict our attention in this course to schedules that have no unforced idleness, i.e., schedules in which a processor is not permitted to be idle if a CG node is available to be scheduled on it. Such schedules are also referred to as "greedy" schedules.

We defined T<sub>P</sub> as the execution time of a CG on P processors, and observed that

T∞ ≤ T<sub>P</sub> ≤ T<sub>1</sub>

We also saw examples for which there could be different values of T<sub>P</sub> for different schedules of the same CG on P processors.

We then defined the parallel speedup for a given schedule of a CG on P processors as Speedup(P) = T<sub>1</sub>/T<sub>P</sub> , and observed that Speedup(P) must be ≤ the number of processors P , and also ≤ the ideal parallelism, WORK/SPAN.

## 1.5 Amdahl’s Law

Lecture Summary: In this lecture, we studied a simple observation made by Gene Amdahl in 1967: if q ≤ 1 is the fraction of WORK in a parallel program that must be executed sequentially, then the best speedup that can be obtained for that program for any number of processors, P, is Speedup(P) ≤ 1/q.

This observation follows directly from a lower bound on parallel execution time that you are familiar with, namely T<sub>P</sub> ≥ SPAN(G). If fraction q of WORK(G) is sequential, it must be the case that SPAN(G) ≥ q × WORK(G). Therefore, Speedup(P) = T<sub>1</sub> /T<sub>P</sub> must be ≤ WORK(G)/(q × WORK(G)) = 1/q since T<sub>1</sub> = WORK(G) for greedy schedulers.

Amdahl’s Law reminds us to watch out for sequential bottlenecks both when designing parallel algorithms and when implementing programs on real machines. As an example, if q = 10%, then Amdahl's Law reminds us that the best possible speedup must be ≤ 10 (which equals 1/q), regardless of the number of processors available.

Optional Reading:

1. Wikipedia article on Amdahl’s law.
