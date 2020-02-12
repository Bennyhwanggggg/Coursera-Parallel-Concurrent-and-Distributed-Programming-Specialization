# Module 2 Quiz - Client-Server Programming
Question 1: When initializing sockets for the server and client, what type of Object should each side initialize?

A. Server initializes a Socket, Client initializes a ServerSocket.

B. Server initializes a ServerSocket, Client initializes a Socket.

C. The server and client must create both a Socket and ServerSocket or else they cannot both read and write.

D. None of the above.

A: B

Question 2: Which of the following statements is correct about communication between a client and server?

A. The Server uses an InputStream to get data from the Client, and the Client uses an OutputStream to give data to the Server.

B. The Server and Client effectively have "two connections", one for reading and the other for writing.

C. The Server uses an OutputStream to get data from the Client, and the Client uses an InputStream to give data to the Server.

D. The Server and Client each have an OutputStream and InputStream.

E. None of the above.

A: D

Question 3: What must a class extend/implement if you want to make it serializable?

A. Implement Serializable and Deserializable

B. Implement Serializable

C. Implement Deserializable on the Client, implement Serializable on the Server

D. Extend Deserializable on the Client, extend Serializable on the Server

A: B

Question 4: What does transient mean with respect to serializing objects?

A. It means we can now deserialize in any JVM, not just one.

B. It means that specific variable in the serialized object will not be initialized.

C. It means that we are sending a "generic" serialized object which the receiver can structure how they want.

D. Both B and C

A: B

Question 5: In remote method invocation, where object x is located on the server and the client is executing the instruction y = x.foo(), which objects must be serializable?

A. None

B. Only x

C. Only y

D. Both x and y

A: D

Question 6: What are the functions of the stub object in RMI?

A. Allows the client to remotely call methods on the server’s object.

B. It's a local object on the client's JVM created to represent the remote object that lives on the server’s JVM.

C. Stores the data that belongs to the skeleton object

D. Executes the code of the skeleton object’s methods

A: A, B

Question 7: What is the main advantage of using Multicast Sockets?

A. Multicast Sockets are easier to implement than Broadcast & Unicast Sockets

B. It is generally more efficient to use one Multicast Socket than multiple Unicast Sockets

C. Multicast Sockets, unlike Broadcast Sockets, touch all nodes/destinations

D. Multicast Sockets use more bandwidth/resources than Broadcast and Unicast

A: B

Question 8: What is true about DatagramPackets?

A. The DatagramPacket message can have unbounded length

B. A DatagramPacket message can be sent to all members of a given group

C. DatagramPackets are used only for sending messages, not receiving

D. DatagramPackets can only be used by Multicast Sockets

A: B

Question 9: What are the nodes in a distributed Publish-Subscribe system referred to as?

A. Workers.

B. Brokers.

C. Publishers.

D. Subscribers.

A: B

Question 10: Which of the following are benefits of the Publish-Subscribe paradigm?

A. Efficient implementation due to message batching.

B. Higher resilience due to message replication.

C. Higher throughput due to topic partitioning.

D. All of the above.

A: D
