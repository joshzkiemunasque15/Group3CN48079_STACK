/* Description: Swing GUI that visualizes an array-based bounded stack. The user can
   push, pop, peek, clear, check the stack, and generate a random stack. The display
   is redrawn after every operation and the top element is always marked.

   Programmed by: Muñasque, Hormachuelas, Jangad, Dioso, Lamayon BSIT 48079 DSA

   Last Modified: October 20, 2026

   Version: 1.0

   Acknowledgements: Ma’am Pauline Rivas, W3Schools, GeeksforGeeks, Abdul Bari, Claude
*/

import java.util.Arrays;

public class StackManager {
    public static final int CAPACITY = 10;

    private int[] data = new int[CAPACITY];
    private int top = -1; // index of the top element, -1 when empty

    // Adds a value to the top. Returns false (overflow) if the stack is full.
    public boolean push(int value) {
        if (isFull()) return false;
        data[++top] = value;
        return true;
    }

    // Removes and returns the top value. Returns null (underflow) if the stack is empty.
    public Integer pop() {
        if (isEmpty()) return null;
        return data[top--];
    }

    // Returns the top value without removing it. Returns null if the stack is empty.
    public Integer peek() {
        if (isEmpty()) return null;
        return data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == CAPACITY - 1;
    }

    public int size() {
        return top + 1;
    }

    public void clear() {
        top = -1;
    }

    // Copy of the current elements (bottom to top) so the GUI can draw them
    // without access to the internal array.
    public int[] toArray() {
        return Arrays.copyOf(data, size());
    }
}
