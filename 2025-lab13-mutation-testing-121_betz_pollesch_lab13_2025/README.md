[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19435076)

# SWE2721 Lab 13: Mutation Testing

This lab explores **mutation testing**, a method used to evaluate the effectiveness of unit test cases by introducing small changes to the source code. These changes, or **mutations**, mimic common programming mistakes.

## Objectives
In this lab, you will use the **PIT mutation testing tool** to:
* Investigate the quality of an existing test suite using mutation testing.
* Analyze the effectiveness of existing test cases.
* Critique a method's implementation based on how easily it can be tested.

---

## The Mutation Testing Process
The general idea behind mutation testing is to modify the source code in small ways. The mutations are based on well-defined **mutation operators**. A test suite is then run against the original program and the modified, or **mutant**, programs.

* A **killed mutant** is a mutant program that produces a test result different from the original program, indicating that your test suite detected the change.
* A **live mutant** is a mutant program that produces the same test result as the original program, meaning the change went unnoticed by the test suite.

Your goal is to improve the test suite to kill as many live mutants as possible. The **mutation score** is the percentage of mutants that were killed.

---

## System Under Test
For this lab, you are provided with a complete implementation of a project and a set of test cases. The project, named `transcriptAnalyzer`, includes classes such as `CompletedCourse`, `Term`, and `Transcript`.

---

## Lab Deliverables
You will submit one report in PDF format and your completed code. The report should include the following sections:

### 1. Initial State Analysis and Discussion
* Include a screenshot of the initial code coverage summary from Jacoco. Explain whether the test set is of good quality based on this data.
* Include a screenshot of the initial Pit Test Coverage report. Based on your understanding of mutation testing, explain how good you feel the test cases are from this report.

### 2. Final State
* Include a summary showing the final code coverage for the project after adding all your tests.
* Include a final screenshot for the Pit Test Coverage report after you have killed as many mutants as you can.

### 3. Discussion and Analysis
* For each mutant that was not killed, explain whether it is an **equivalent mutant** or a **stubborn mutant**. Create a table to justify your reasoning.
    * **Equivalent mutants** are functionally identical to the original code, making them impossible to kill with a test.
    * **Stubborn mutants** are those that could be killed but for which you could not create a test case to do so.

### 4. Class Design Analysis
* For the `CompletedCourse`, `Term`, and `Transcript` classes, explain whether their construction made mutation testing easier or harder. Describe what you had to do to achieve the highest possible mutation score for each.

### 5. Things Gone Right / Things Gone Wrong
* Discuss what went correctly and what posed problems during the lab.

### 6. Conclusions
* Explain what you learned from this experience and what improvements could be made in the future.
