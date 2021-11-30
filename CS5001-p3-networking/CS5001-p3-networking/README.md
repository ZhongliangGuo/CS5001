# README

Report for CS5001 Practice 3 networking

Matriculation number of author: **[210016568](mailto:zg34@st-andrews.ac.uk)**

## Abstract

In this assignment, I passed all the basic `stacscheck` tests. I mainly used the code and recordings **[Michael](mailto:mct25@st-andrews.ac.uk)** provided to finish this assignment. This report is to illustrate the advanced requirement I met in this assignment.

## The advanced requirements

Here I mainly illustrate the advanced requirements in PDF I met.

### Returning of binary images (GIF, JPEG and PNG)

To determine whether this project return binary images successfully, I print the result in the `ConnectionHandler` Class:

```java
System.out.println(feedbackString);
```

An example of returning results:

```
ConnectionHandler: GET /beer.jpg HTTP/1.1
HTTP/1.1 200 OK
Content-Type: image/jpeg
Content-Length: 30582
```

### Multithreading

To meet this requirement, I created some methods in `Server` Class to count the number of connections. The variable `MAX_NUMBER_OF_CONNECTION` in `Configuration` Class is the max number of connections (initial value is 50, set randomly by **[210016568](mailto:zg34@st-andrews.ac.uk)**).

When create a new connection, program calls for the method `addConn`, when close an existing connection, programs call for the method `delConn` (used in `cleanup` method).

The method `isFull` is used to determine whether the number of connections of this port reaches the max.

The details can be seen in the constructor of `Server` and `ConnectionHandler` Class.

### Logging

I logged the all the requests in the file `log.txt`. This file is encoded by `UTF-8`. For meeting the demand in different OS (Linux and Windows), I used `File.separator` to replace `\` or `/`.

```java
bw = new BufferedWriter(new FileWriter(".." + File.separator + "log.txt", true));
```

The details can be seen in `ConnectionHandler` Class where used the variable `bw`.

## Other task I did

In addition to teacher's demand, I also achieve some function according to the knowledge I learnt before.

### 404 Page

For 404 page, I wrote a file named `Error.html`, but I am afraid that teacher will test my code by using a common www resource, so I chosen to append the html text in the code in stead of write it as a file. It can be seen in the method `getFileContent` of `ConnectionHandler`.

### `Configuration` Class

Referred from the provided example, I write a Class to store the constant. Here are two variables `SCOPE_OF_PORT_NUMBER` and `MAX_NUMBER_OF_CONNECTION`. The first one is to restrict the value of port number, the second one I mentioned it in Multithreading section.

### Restrict the value of port number

According to `Computer Network` textbook, the port number is from 0 to 65535, and some port number are used in certain way (80 is used to www, 53 is used to DNS). so I set an array in the `Configuration` Class to restrict which port can be used.

I have described it in `Configuration.java` file.

## Summary

This assignment is about networking, I passed all the basic test and finished the advanced requirements. Besides, I also added some new function based on my previous knowledge. Overall, for me, this assignment can be regarded as a good job. 

## References

During this assignment, I referred some resources.

### From University

In this process, I referred some code which **[Michael](mailto:mct25@st-andrews.ac.uk)** provided, all the referred code is marked in `JavaDoc` or comments.

### From Internet

I used some websites to search the usage of method and API, I marked a part of them as comments, but it is hard for me to statistic every articles, so I list the websites which I used.

here is the list:

|    website     |          address           |
| :------------: | :------------------------: |
|    W3school    | https://www.w3schools.com/ |
|      CSDN      |   https://www.csdn.net/    |
|     Runoob     |  https://www.runoob.com/   |
| Stack Overflow | https://stackoverflow.com/ |
|     cnblog     |    https://cnblogs.com/    |

