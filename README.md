# Stack Visualization

## Group Name

Group 3 (CN 48079)

## Group Members

- Joshua L. Muñasque
- Leona Mae G. Hormachuelas
- Duezel Kurt N. Dioso
- Eduardo Lamayon jr.
- Harold Niño R. Jangad

## Description of the Program

A Java Swing application that shows an array-based stack as a 2D drawing. Each element is a rectangular block, the newest element is on top, and the top block is marked with `TOP ->`. The drawing is updated after every operation and always reflects the real contents of the stack.

The program has three Java files:

- **StackManager** keeps the stack data and performs the stack operations. It is shared by both GUIs.
- **StackGUI** is the basic program (Task 1). It shows one stack with a fixed capacity of 10, receives user input, calls the stack's methods, and draws the stack.
- **ModifiedStackGUI** is the challenge version. It shows two stacks (Stack A and Stack B) and adds undo, transfer, operation history, and a maximum capacity selector.

## Features

### StackGUI (basic version)

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

### ModifiedStackGUI (challenges)

It keeps every feature of StackGUI and adds the four challenges. The **Stack** drop-down chooses whether Push, Pop, Peek, Clear, Check Stack, and Random act on Stack A or Stack B.

1. **Undo button** reverses the most recent operation. It can be pressed repeatedly to go back further, and it also undoes Transfer, Random, Clear, and Set Capacity.
2. **Second stack and transfer**: the **Transfer A -> B** button moves the top element of Stack A to Stack B. It checks first that Stack A is not empty and Stack B is not full, so no element is lost.
3. **Stack operation history**: a numbered list on the right records every successful operation (for example `Push 5 on Stack A`) and every undo.
4. **Maximum capacity selector**: a spinner (1 to 15) and the **Set Capacity** button change the capacity of both stacks. The default is 8. A capacity smaller than the number of elements already in a stack is rejected.

Additional status messages:

- `NOTHING TO UNDO.`
- `STACK UNDERFLOW: Cannot transfer, Stack A is empty`
- `STACK OVERFLOW: Cannot transfer, Stack B is full`
- `CANNOT SET CAPACITY: a stack already has N element(s). Pop some first.`

## Requirements

- Java Development Kit (JDK) 11 or newer
- No external libraries (uses only `javax.swing`, `java.awt`, `java.util`)

## How to Run the Program

Run these commands from the folder that contains the three `.java` files:

```
javac StackManager.java StackGUI.java ModifiedStackGUI.java
java StackGUI
java ModifiedStackGUI
```

`java StackGUI` opens the basic version. `java ModifiedStackGUI` opens the challenge version.

## Project Structure

```
Group3CN48079_STACK/
├── StackManager.java       (stack component, shared by both GUIs)
├── StackGUI.java           (GUI component, basic version)
├── ModifiedStackGUI.java   (GUI component, challenges version)
├── StackGUI_class_diagram.png
└── README.md
```

## Stack Implementation

The stack is implemented with our own class, `StackManager`, using an `int[]` array and an index `top` that is `-1` when the stack is empty. The default capacity is 10 (`CAPACITY`), which `StackGUI` uses. `ModifiedStackGUI` creates its stacks with a chosen capacity instead.

| Method | What it does |
| --- | --- |
| `StackManager()` | Creates a stack with the default capacity of 10. |
| `StackManager(int capacity)` | Creates a stack with the given capacity. |
| `push(int value)` | Stores the value at the top. Returns `false` if the stack is full (overflow). |
| `pop()` | Removes and returns the top value. Returns `null` if the stack is empty (underflow). |
| `peek()` | Returns the top value without removing it. Returns `null` if empty. |
| `isEmpty()` | `true` when `top == -1`. |
| `isFull()` | `true` when `top == data.length - 1`. |
| `size()` | Returns `top + 1`. |
| `getCapacity()` | Returns the current capacity. |
| `setCapacity(int newCapacity)` | Changes the capacity. Returns `false` if it is smaller than 1 or smaller than the current size. |
| `restore(int capacity, int[] values)` | Rebuilds the stack from saved values. Used by Undo. |
| `clear()` | Sets `top` back to `-1`. |
| `toArray()` | Returns a copy of the current elements so the GUI can draw them without touching the internal array. |

Both GUIs only use these methods. They never read or change the internal array directly.

## Class Diagram

See `StackGUI_class_diagram.png`. It shows `ModifiedStackGUI` (labelled `StackGUI` in the picture) using two `StackManager` objects, containing two `StackPanel` objects, and containing the `Snapshot` objects used by Undo.

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
- **Task performed:** Code drafting StackManager.java, StackGUI.java, and ModifiedStackGUI.java (challenges), and the class diagram
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
| ALL MEMBERS  | [ReadME] |
| Joshua L. Muñasque & Eduardo JR. Lamayon  | [Documentation] |
