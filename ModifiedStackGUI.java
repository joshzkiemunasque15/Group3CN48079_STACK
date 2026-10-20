

/* Description: Swing GUI that visualizes two array-based bounded stacks (Stack A and
   Stack B). The user can push, pop, peek, clear, check the stack, and generate a random
   stack on the selected stack. The user can also transfer the top element of Stack A to
   Stack B, undo the most recent operation, view the operation history, and set the
   maximum capacity of the stacks. The display is redrawn after every operation.(CHALLENGES)

   Programmed by: Duezel Kurt N. Dioso, Joshua L. Muñasque & Leona Mae G. Hormachuelas BSIT 48079 DSA

   Last Modified: October 20, 2026

   Version: 2.0

   Acknowledgements: Ma’am Pauline Rivas, W3Schools, GeeksforGeeks, Abdul Bari, Claude
*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Random;

public class ModifiedStackGUI extends JFrame {
    private static final int DEFAULT_CAPACITY = 8;

    private StackManager stackA = new StackManager(DEFAULT_CAPACITY);
    private StackManager stackB = new StackManager(DEFAULT_CAPACITY);
    private Random random = new Random();

    private StackPanel panelA = new StackPanel(stackA, "Stack A");
    private StackPanel panelB = new StackPanel(stackB, "Stack B");

    private JComboBox<String> activeBox = new JComboBox<>(new String[]{"Stack A", "Stack B"});
    private JTextField inputField = new JTextField(6);
    private JSpinner capacitySpinner = new JSpinner(new SpinnerNumberModel(DEFAULT_CAPACITY, 1, 15, 1));
    private JButton undoBtn = new JButton("Undo");
    private JLabel statusLabel = new JLabel("Ready.");

    private JTextArea historyArea = new JTextArea(20, 22);
    private int historyCount = 0;

    // Challenge 1: every successful operation saves the state before it happened
    private ArrayDeque<Snapshot> undoStack = new ArrayDeque<>();

    // Saved state of both stacks (used by Undo)
    class Snapshot {
        int[] a;
        int[] b;
        int capacity;
        String description;

        Snapshot(int[] a, int[] b, int capacity) {
            this.a = a;
            this.b = b;
            this.capacity = capacity;
        }
    }

    public StackGUI() {
        setTitle("Stack Visualization");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        // ----- Top controls (two rows) -----
        JPanel row1 = new JPanel(new FlowLayout());
        JButton pushBtn = new JButton("Push");
        JButton popBtn = new JButton("Pop");
        JButton peekBtn = new JButton("Peek");
        JButton clearBtn = new JButton("Clear");
        JButton checkBtn = new JButton("Check Stack");
        JButton randomBtn = new JButton("Random");

        row1.add(new JLabel("Stack:"));
        row1.add(activeBox);
        row1.add(new JLabel("Value:"));
        row1.add(inputField);
        row1.add(pushBtn);
        row1.add(popBtn);
        row1.add(peekBtn);
        row1.add(clearBtn);
        row1.add(checkBtn);
        row1.add(randomBtn);

        JPanel row2 = new JPanel(new FlowLayout());
        JButton transferBtn = new JButton("Transfer A -> B");
        JButton setCapBtn = new JButton("Set Capacity");

        row2.add(undoBtn);
        row2.add(transferBtn);
        row2.add(new JLabel("Max capacity:"));
        row2.add(capacitySpinner);
        row2.add(setCapBtn);

        JPanel controls = new JPanel(new GridLayout(2, 1));
        controls.add(row1);
        controls.add(row2);

        // ----- Center: the two stacks side by side -----
        JPanel stacks = new JPanel(new GridLayout(1, 2, 5, 5));
        stacks.add(panelA);
        stacks.add(panelB);

        // ----- Right: operation history (Challenge 3) -----
        historyArea.setEditable(false);
        JScrollPane historyScroll = new JScrollPane(historyArea);
        historyScroll.setBorder(BorderFactory.createTitledBorder("Operation History"));

        add(controls, BorderLayout.NORTH);
        add(stacks, BorderLayout.CENTER);
        add(historyScroll, BorderLayout.EAST);
        add(statusLabel, BorderLayout.SOUTH);

        undoBtn.setEnabled(false);

        pushBtn.addActionListener(e -> doPush());
        popBtn.addActionListener(e -> doPop());
        peekBtn.addActionListener(e -> doPeek());
        clearBtn.addActionListener(e -> doClear());
        checkBtn.addActionListener(e -> doCheck());
        randomBtn.addActionListener(e -> doRandom());
        undoBtn.addActionListener(e -> doUndo());
        transferBtn.addActionListener(e -> doTransfer());
        setCapBtn.addActionListener(e -> doSetCapacity());

        setSize(1050, 620);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ----- Helpers for the selected stack -----
    StackManager getActive() {
        return activeBox.getSelectedIndex() == 0 ? stackA : stackB;
    }

    String getActiveName() {
        return activeBox.getSelectedIndex() == 0 ? "Stack A" : "Stack B";
    }

    void clearHighlights() {
        panelA.highlightTop = false;
        panelB.highlightTop = false;
    }

    void repaintAll() {
        panelA.repaint();
        panelB.repaint();
    }

    // ----- Undo and history helpers -----
    Snapshot takeSnapshot() {
        return new Snapshot(stackA.toArray(), stackB.toArray(), stackA.getCapacity());
    }

    // Call this only after an operation succeeded. 'before' is the state taken before it.
    void record(Snapshot before, String description) {
        before.description = description;
        undoStack.push(before);
        undoBtn.setEnabled(true);
        addHistory(description);
    }

    void addHistory(String text) {
        historyCount++;
        historyArea.append(historyCount + ". " + text + "\n");
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }

    // ----- Operations -----
    void doPush() {
        clearHighlights();
        int value;
        try {
            value = Integer.parseInt(inputField.getText().trim());
        } catch (NumberFormatException ex) {
            statusLabel.setText("INVALID INPUT: Enter an integer value.");
            repaintAll();
            return;
        }
        StackManager s = getActive();
        String name = getActiveName();
        Snapshot before = takeSnapshot();
        if (s.push(value)) {
            statusLabel.setText("Pushed " + value + " to " + name + ".");
            inputField.setText("");
            record(before, "Push " + value + " on " + name);
        } else {
            statusLabel.setText("STACK OVERFLOW: Cannot push, " + name + " is full");
        }
        repaintAll();
    }

    void doPop() {
        clearHighlights();
        StackManager s = getActive();
        String name = getActiveName();
        Snapshot before = takeSnapshot();
        Integer value = s.pop();
        if (value == null) {
            statusLabel.setText("STACK UNDERFLOW: Cannot pop, " + name + " is empty");
        } else {
            statusLabel.setText("Popped " + value + " from " + name + ".");
            record(before, "Pop " + value + " from " + name);
        }
        repaintAll();
    }

    void doPeek() {
        clearHighlights();
        StackManager s = getActive();
        String name = getActiveName();
        Integer value = s.peek();
        if (value == null) {
            statusLabel.setText("STACK EMPTY: Nothing to peek in " + name);
        } else {
            if (s == stackA) {
                panelA.highlightTop = true;
            } else {
                panelB.highlightTop = true;
            }
            statusLabel.setText("Top element of " + name + " is " + value + ".");
            addHistory("Peek " + value + " on " + name); // peek changes nothing, so no undo entry
        }
        repaintAll();
    }

    void doClear() {
        clearHighlights();
        StackManager s = getActive();
        String name = getActiveName();
        Snapshot before = takeSnapshot();
        s.clear();
        statusLabel.setText(name + " cleared.");
        record(before, "Clear " + name);
        repaintAll();
    }

    void doCheck() {
        StackManager s = getActive();
        String name = getActiveName();
        if (s.isEmpty()) {
            statusLabel.setText(name + " is EMPTY.");
        } else if (s.isFull()) {
            statusLabel.setText(name + " is FULL. Size: " + s.size() + " of " + s.getCapacity() + ".");
        } else {
            statusLabel.setText(name + " is NOT empty. Size: " + s.size()
                    + " of " + s.getCapacity() + ".");
        }
    }

    void doRandom() {
        clearHighlights();
        StackManager s = getActive();
        String name = getActiveName();
        Snapshot before = takeSnapshot();
        s.clear();
        int count = random.nextInt(s.getCapacity()) + 1; // 1 to capacity
        for (int i = 0; i < count; i++) {
            s.push(random.nextInt(100) + 1);             // 1 to 100
        }
        statusLabel.setText("Random " + name + " generated with " + count + " element(s).");
        record(before, "Random " + name + " (" + count + " element(s))");
        repaintAll();
    }

    // Challenge 1: undo the most recent operation
    void doUndo() {
        clearHighlights();
        if (undoStack.isEmpty()) {
            statusLabel.setText("NOTHING TO UNDO.");
            return;
        }
        Snapshot last = undoStack.pop();
        stackA.restore(last.capacity, last.a);
        stackB.restore(last.capacity, last.b);
        capacitySpinner.setValue(last.capacity);
        undoBtn.setEnabled(!undoStack.isEmpty());
        statusLabel.setText("Undid: " + last.description + ".");
        addHistory("Undo (" + last.description + ")");
        repaintAll();
    }

    // Challenge 2: move the top element of Stack A to Stack B
    void doTransfer() {
        clearHighlights();
        if (stackA.isEmpty()) {
            statusLabel.setText("STACK UNDERFLOW: Cannot transfer, Stack A is empty");
            repaintAll();
            return;
        }
        if (stackB.isFull()) {
            statusLabel.setText("STACK OVERFLOW: Cannot transfer, Stack B is full");
            repaintAll();
            return;
        }
        Snapshot before = takeSnapshot();
        int value = stackA.pop();
        stackB.push(value);
        statusLabel.setText("Transferred " + value + " from Stack A to Stack B.");
        record(before, "Transfer " + value + " from Stack A to Stack B");
        repaintAll();
    }

    // Challenge 4: change the maximum capacity of both stacks
    void doSetCapacity() {
        clearHighlights();
        int newCap = (Integer) capacitySpinner.getValue();
        int current = stackA.getCapacity();
        if (newCap == current) {
            statusLabel.setText("Capacity is already " + current + ".");
            return;
        }
        int biggest = Math.max(stackA.size(), stackB.size());
        if (newCap < biggest) {
            statusLabel.setText("CANNOT SET CAPACITY: a stack already has " + biggest
                    + " element(s). Pop some first.");
            capacitySpinner.setValue(current);
            return;
        }
        Snapshot before = takeSnapshot();
        stackA.setCapacity(newCap);
        stackB.setCapacity(newCap);
        statusLabel.setText("Maximum capacity set to " + newCap + ".");
        record(before, "Set capacity from " + current + " to " + newCap);
        repaintAll();
    }

    // Draws one stack: bottom block at the base, newest block on top
    class StackPanel extends JPanel {
        private StackManager stack;
        private String name;
        boolean highlightTop = false; // set by PEEK, cleared by the next operation

        StackPanel(StackManager stack, String name) {
            this.stack = stack;
            this.name = name;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int[] data = stack.toArray();

            int blockW = 120;
            int blockH = Math.min(36, (getHeight() - 70) / stack.getCapacity()); // shrink if capacity is large
            int x = (getWidth() - blockW) / 2;
            int baseY = getHeight() - 20;

            for (int i = 0; i < data.length; i++) {
                int y = baseY - (i + 1) * blockH;
                boolean isTop = (i == data.length - 1);

                g.setColor(isTop && highlightTop ? new Color(255, 200, 80)
                        : new Color(135, 190, 235));
                g.fillRect(x, y, blockW, blockH);
                g.setColor(Color.BLACK);
                g.drawRect(x, y, blockW, blockH);

                String s = String.valueOf(data[i]);
                FontMetrics fm = g.getFontMetrics();
                g.drawString(s, x + (blockW - fm.stringWidth(s)) / 2,
                        y + (blockH + fm.getAscent()) / 2 - 2);

                if (isTop) {
                    g.drawString("TOP ->", x - 55, y + blockH / 2 + 5);
                }
            }
            g.drawLine(x - 10, baseY, x + blockW + 10, baseY);
            g.drawString(name + "   Size: " + data.length + " / " + stack.getCapacity(), 10, 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StackGUI::new);
    }
}
