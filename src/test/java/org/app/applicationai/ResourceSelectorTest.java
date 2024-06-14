package org.app.applicationai;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceSelectorTest {

    @Test
    void getKeywordTest() {
        List<String> expectedKeywords = new ArrayList<>(Arrays.asList(
                "python", "programming-language", "java", "valorant", "gaming"
        ));
        List<String> actualKeywords = ResourceSelector.getKeywords("src/main/resources/org/app/applicationai/resourceSelector.json");

        assertTrue(actualKeywords.containsAll(expectedKeywords) && expectedKeywords.containsAll(actualKeywords),
                "De keywords komen niet overeen");
    }

    @Test
    void documentationTest(){
        List<String> expectedKeywords = new ArrayList<>(Arrays.asList("python"));
        assertEquals("Python is an interpreted language, meaning that code is executed line by line by the Python interpreter. This allows for rapid development and easy debugging. Python is dynamically typed, which means that variable types are determined at runtime. This allows for flexible and concise code but may lead to runtime errors if not used carefully. Although Python is dynamically typed, it is also strongly typed, meaning that type errors are caught during runtime. This helps in writing more reliable and robust code. Python comes with a rich standard library that provides modules and packages for various tasks such as file I/O, networking, regular expressions, and data manipulation. This reduces the need for third-party libraries in many cases. Python is a versatile language suitable for a wide range of applications, including web development, data analysis, scientific computing, artificial intelligence, machine learning, automation, and scripting. NumPy is a fundamental package for scientific computing in Python. It provides support for multidimensional arrays, mathematical functions, linear algebra operations, and random number generation. Pandas is a powerful library for data manipulation and analysis in Python. It provides data structures like DataFrame and Series, along with tools for reading and writing data from various sources. Django is a high-level web framework for building web applications in Python. It follows the Model-View-Controller (MVC) architectural pattern and provides features like ORM, URL routing, form handling, and authentication. Python developers often use IDEs such as PyCharm, Visual Studio Code, and Sublime Text for writing, debugging, and testing Python code. These IDEs offer features like code completion, syntax highlighting, and project management. Package managers like pip and conda are used to install, manage, and update Python packages and dependencies. They simplify the process of managing project dependencies and ensure reproducible environments. Python developers use testing frameworks like pytest, unittest, and doctest to write and execute tests for their code. These frameworks help in ensuring code quality, identifying bugs, and facilitating code maintenance.", ResourceSelector.searchDocumentation("src/main/resources/org/app/applicationai/resourceSelector.json", expectedKeywords));
    }

    @Test
    void documentationTest2(){
        List<String> expectedKeywords = new ArrayList<>(Arrays.asList("java"));
        assertEquals("Java is an object-oriented language, which means it supports the concepts of classes, objects, inheritance, encapsulation, and polymorphism. Java programs are compiled into bytecode, which can be executed on any platform with a Java Virtual Machine (JVM). This allows Java applications to be highly portable. Java manages memory allocation and deallocation automatically through a process called garbage collection. This helps in preventing memory leaks and manual memory management errors. Java has built-in support for exception handling, allowing developers to write robust code that can gracefully handle errors and exceptions. Java supports multithreading, allowing concurrent execution of multiple threads within a single process. This feature is useful for developing applications that require concurrent processing. The Java Standard Library provides a rich set of classes and interfaces for common programming tasks such as I/O operations, networking, data structures, and utilities. Java EE is a set of specifications that extend the Java SE platform to support enterprise-level application development. It includes APIs for building web-based applications, enterprise messaging, and distributed computing. Apache Commons is a collection of reusable Java components and utilities developed by the Apache Software Foundation. It provides implementations for common programming tasks not covered by the standard Java libraries. Java developers often use IDEs such as Eclipse, IntelliJ IDEA, and NetBeans for writing, debugging, and testing Java code. These IDEs provide features like code auto-completion, syntax highlighting, and project management. Build tools like Apache Maven and Gradle are used to automate the process of compiling, testing, and packaging Java applications. They manage dependencies, handle project configurations, and facilitate continuous integration. Java developers use debugging tools like Java Debugger (JDB), Eclipse Debugger, and IntelliJ Debugger to identify and fix errors in their code. These tools allow developers to set breakpoints, inspect variables, and step through code execution.", ResourceSelector.searchDocumentation("src/main/resources/org/app/applicationai/resourceSelector.json", expectedKeywords));
    }

}
