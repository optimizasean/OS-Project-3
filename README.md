# OS-Project-3

Operating Systems Project 3, Distributed Reading/Writing

- Sean Harding
- Christopher Sparks
- Wataru Takahashi

## Group Project 3: Distributed File Reading and Writing

### Purpose

In this project, you will learn and simulate how to read and write files and how to achieve mutual exclusion in
the distributed system.

### Simulation Requirements

In this simulation, you should first understand the global timestamp vector to determine the global ordering so
that you can determine which process will enter the critical section first. Please read through the handout
sections 5.2, 5.3, and 5.5 to understand the mutual exclusion in the distributed system.

1. File writing
  In the file writing simulation, you are required to achieve the mutual exclusion by exchanging the timestamp. Please check the simulation requirements in Group Project 1 to get exchange the timestamp. The file is stored in PC1 and the other four PCs want to access the file randomly. When one PCi wants to access the file, it will send out the “request” message to all other PCs including PC1. When the PCj receives the message, it will determine based on the following rules:

    1. If the PCj does not want to access the file, it will send back “OK Writing” message to PCi.

    2. If the PCj also wants to access the file but its request is later than PCi, it will also send back “OK Writing” message.

    3. If the PCj wants to access the file and its request is earlier than PCi, it will delay the “OK Writing” message until it finishes the critical section process. When PCi gets all “OK” messages from all other PCs, it will access the file in PC1 by sending “Write” message to PC1, then downloading the file from PC1, adding one line “PCi write to file” into the downloaded file, sending back the file to PC1, and deleting the file in PCi.

2. File reading

  We have two kinds of file reading: totally consistent reading and partially consistent reading. For the totally consistent reading, the system requires that the process can read the file if and only if there is no PC requesting writing. When there is no writing request, multiple reading can be processed. This one can guarantee that the file is consistent at any time. However, one PC reading request may delay a long time especially when too many writing requests come. When PC reads the file, it will send “Reading” request to all other PCs and should get “OK Reading” message from all other PCs before entering reading.
  
  In order to make the reading request process quickly, we have partially consistent reading. Before starting processing writing, PC1 will generate the duplicated file (read only copy) for reading. When the reading request comes, it will read the duplicated copy. When the writing finishes, the duplicated file will be updated and replaced by the new file. Since the file reading during some other PC writing, you will read the old copy (inconsistent copy). Only when there is no writing processing, you will get the consistent copy. Therefore, we say this reading is partially consistent reading. When PC reads the file, it will send “Reading request to PC1 and both PCs will store this request in their log file.

  For the simulation, each PC other than PC1 will generate the writing and reading requests (20% writing and 80% reading, you may change this ratio if you want to compare performance between different reading strategies.) When the PC finishes one request, it will wait a random amount of time to generate the next request. Do not sleep between two requests since the PC needs to send to and receive from other PCs.

### Record and Design Requirements

All PCs and controller in your simulation should maintain a log file (e.g. client1.log) to show the proper steps in the simulation. Especially, PCs should record the sending message and receive message with time table vectors.

You should design the messages (e.g. start message, finish message, acknowledge message, OK message,
request message, and other necessary message) so that PCs and controller can work properly.

### Submission Requirements

You are required to give a demo at the last week of the class (date and location will be decided before
Thanksgiving). Also you should submit the source code, the log files, and design documents (such as the
message development, code explanation, and any help from the website or other persons except instructors).
