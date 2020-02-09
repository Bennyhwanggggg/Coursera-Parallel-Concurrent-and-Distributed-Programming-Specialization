# 2 Functional Parallelism

## 2.1 Future Tasks

**Lecture Summary**: In this lecture, we learned how to extend the concept of asynchronous tasks to future tasks and future objects (also known as promise objects). Future tasks are tasks with return values, and a future object is a “handle” for accessing a task’s return value. There are two key operations that can be performed on a future object, A:

Assignment — A can be assigned a reference to a future object returned by a task of the form, future { ⟨ task-with-return-value ⟩ } (using pseudocode notation). The content of the future object is constrained to be single assignment (similar to a final variable in Java), and cannot be modified after the future task has returned.
Blocking read — the operation, A.get(), waits until the task associated with future object A has completed, and then propagates the task’s return value as the value returned by A.get(). Any statement, S, executed after A.get() can be assured that the task associated with future object A must have completed before S starts execution.
These operations are carefully defined to avoid the possibility of a race condition on a task’s return value, which is why futures are well suited for functional parallelism. In fact, one of the earliest use of futures for parallel computing was in an extension to Lisp known as MultiLisp.

Optional Reading:

1. Wikipedia article on Futures and promises.

## 2.2 Creating Future Tasks in Java’s Fork/Join Framework

*Lecture Summary*: In this lecture, we learned how to express future tasks in Java’s Fork/Join (FJ) framework. Some key differences between future tasks and regular tasks in the FJ framework are as follows:

1. A future task extends the RecursiveTask class in the FJ framework, instead of RecursiveAction as in regular tasks.

2. The 𝚌𝚘𝚖𝚙𝚞𝚝𝚎() method of a future task must have a non-void return type, whereas it has a void return type for regular tasks.

3. A method call like 𝚕𝚎𝚏𝚝.𝚓𝚘𝚒𝚗() waits for the task referred to by object 𝚕𝚎𝚏𝚝 in both cases, but also provides the task’s return value in the case of future tasks.

Optional Reading:

1. Documentation on Java’s RecursiveTask class

## 2.3 Memoization

*Lecture Summary*: In this lecture, we learned the basic idea of “memoization”, which is to remember results of function calls f(x) as follows:

Create a data structure that stores the set {(x<sub>1</sub>, y<sub>1</sub> = f(x<sub>1</sub>)), (x<sub>2</sub>, y<sub>2</sub> = f(x<sub>2</sub>)), . . .} for each call f(x<sub>i</sub>) that returns y<sub>i</sub>

Perform look ups in that data structure when processing calls of the form f(x') when x' equals one of the x<sub>i</sub> inputs for which f(x<sub>i</sub>) has already been computed.
Memoization can be especially helpful for algorithms based on dynamic programming. In the lecture, we used Pascal’s triangle as an illustrative example to motivate memoization.

The memoization pattern lends itself easily to parallelization using futures by modifying the memoized data structure to store {(x_1 
{(x<sub>1</sub>, y<sub>1</sub> = future(f(x<sub>1</sub>))), (x<sub>2</sub>, y<sub>2</sub> = future(f(x<sub>2</sub>))), . . .}. The lookup operation can then be replaced by a get() operation on the future value, if a future has already been created for the result of a given input.

Optional Reading:

1. Wikipedia article on Memoization.
