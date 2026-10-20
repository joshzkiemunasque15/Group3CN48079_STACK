# Stack Visualization

## Group Name

Group 3 (CN 48079)

## Group Members

- Joshua L. Muñasque
- Leona Mae G. Hormachuelas
- Duezel Kurt Dioso
- Eduardo Lamayon jr.
- Harold Nino Jangad

## Description of the Program

A Java Swing application that shows an array-based stack as a 2D drawing. Each element is a rectangular block, the newest element is on top, and the top block is marked with `TOP ->`. The drawing is updated after every operation and always reflects the real contents of the stack.

The program has two separate components:

- **StackManager** keeps the stack data and performs the stack operations.
- **StackGUI** receives user input, calls the stack's methods, and draws the stack.

## Features

- Enter an integer and **Push** it onto the stack
- **Pop** removes only the top element
- **Peek** highlights the top element without removing it
- **Clear** empties the stack
- **Check Stack** reports whether the stack is empty (and its size if not)
- **Random** clears the stack, then pushes 1 to 10 random integers between 1 and 100
- Status messages, including:
  - `STACK UNDERFLOW: Cannot pop, stack is empty`
  - `STACK OVERFLOW: Cannot push, stack is full`
  - `STACK EMPTY: Nothing to peek`
  - `INVALID INPUT: Enter an integer value.`

## Requirements

- Java Development Kit (JDK) 11 or later
- No external libraries (uses only `javax.swing`, `java.awt`, `java.util`)

## How to Run the Program

```
javac StackManager.java StackGUI.java
java StackGUI
```

Run both commands from the folder that contains the two `.java` files.

## Project Structure

```
Group3CN48079_STACK/
├── StackManager.java   (stack component)
├── StackGUI.java       (GUI component)
└── README.md
```

## Stack Implementation

The stack is implemented with our own class, `StackManager`, using an `int[]` array with a fixed capacity of 10 (`CAPACITY`) and an index `top` that is `-1` when the stack is empty.

| Method | What it does |
| --- | --- |
| `push(int value)` | Stores the value at the top. Returns `false` if the stack is full (overflow). |
| `pop()` | Removes and returns the top value. Returns `null` if the stack is empty (underflow). |
| `peek()` | Returns the top value without removing it. Returns `null` if empty. |
| `isEmpty()` | `true` when `top == -1`. |
| `isFull()` | `true` when `top == CAPACITY - 1`. |
| `size()` | Returns `top + 1`. |
| `clear()` | Sets `top` back to `-1`. |
| `toArray()` | Returns a copy of the current elements so the GUI can draw them without touching the internal array. |

The GUI only uses these methods. It never reads or changes the internal array directly.

## Screenshots

TOP element is identified in each caption.

<img width="298" height="232" alt="image" src="https://github.com/user-attachments/assets/5849a991-bab6-4b21-b862-1e056e0efba2" />

Pop on an empty stack: STACK UNDERFLOW message.

<img width="298" height="232" alt="image" src="https://github.com/user-attachments/assets/1dfc1f56-0e38-481c-aa0c-11b2a5f371e4" />

After Push 10, 20, 30, 40: TOP = 40.

<img width="298" height="232" alt="image" src="https://github.com/user-attachments/assets/709115af-0c15-4c7d-9499-6997810684f2" />

After Pop: TOP = 30.

<img width="298" height="232" alt="image" src="https://github.com/user-attachments/assets/5876a0d9-1055-4d3a-a714-fa3d164b1ef8" />

After Push 50 and Peek: TOP = 50 (highlighted).

<img width="298" height="232" alt="image" src="https://github.com/user-attachments/assets/72113ea2-6666-4a56-b769-3f741b8495e6" />

After Random: TOP = 80.

<img width="298" height="232" alt="image" src="https://github.com/user-attachments/assets/2d720225-a675-4202-9b39-65cc40602f7c" />

Push on a full stack: STACK OVERFLOW message. TOP = 50.

## AI Disclosure

- **AI tool and version:** Claude Sonnet 5.5 (Anthropic)
- **Task performed:** Code drafting StackManager.java and StackGUI.java
- **Prompts used:** draftme a code using this activity and explain line by line
- **Extent of use:** Substantial drafting
- **Verified by a human author:** Confirmed we reviewed and ran the codes
- **Why and how AI was used:** By asking some questions how/why do things works and 
completed.
- **Estimated percentage of work assisted by AI:** 30%
- **Changes made to the AI-generated output:** We changed the logic and the functions of the output.
- **What we learned from using the tool:** We learned that Ai is not the one who controls the project, but we control the AI to guide us to make the project

## Contributors

| Member | Contribution |
| --- | --- |
| Duezel Kurt N. Dioso, Eduardo JR. Lamayon & Joshua L. Muñasque | [StackManager.java] |
| Joshua L. Muñasque, Harold Niño R. Jangad & Leona Mae G. Hormachuelas | [StackGUI.java] |
| Duezel Kurt N. Dioso & Eduardo JR. Lamayon | [Testing Outputs for Task 1] |
|  Joshua L. Muñasque & Leona Mae G. Hormachuelas | [Task 2] |
| Harold Nino Jangad | [Task 3] |
| Leona Mae G. Hormachuelas | [Creating Visualization] |
| ALL MEMBERS | [ANSWERING ALL QUESTIONS] |
| Leona Mae G. Hormachuelas & Harold Niño R. Jangad | [ReadME] |
| Joshua L. Muñasque & Eduardo JR. Lamayon  | [Documentation] |
