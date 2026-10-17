/* Description: Swing GUI that visualizes an array-based bounded stack. The user can
   push, pop, peek, clear, check the stack, and generate a random stack. The display
   is redrawn after every operation and the top element is always marked.

   Programmed by: Muñasque, Hormachuelas, Jangad, Dioso, Lamayon BSIT 48079 DSA

   Last Modified: October 17, 2026

   Version: 1.0

   Acknowledgements: Ma’am Pauline Rivas, W3Schools, GeeksforGeeks, Wikipedia, Claude
*/

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StackGUI extends JFrame {
    private StackManager stack = new StackManager();
    private Random random = new Random();

    private StackPanel stackPanel = new StackPanel();
    private JTextField inputField = new JTextField(8);
    private JLabel statusLabel = new JLabel("Ready.");
    private boolean highlightTop = false; // set by PEEK, cleared by the next operation

    public StackGUI() {
        setTitle("Stack Visualization");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel controls = new JPanel(new FlowLayout());
        JButton pushBtn = new JButton("Push");
        JButton popBtn = new JButton("Pop");
        JButton peekBtn = new JButton("Peek");
        JButton clearBtn = new JButton("Clear");
        JButton checkBtn = new JButton("Check Stack");
        JButton randomBtn = new JButton("Random");

        controls.add(new JLabel("Value:"));
        controls.add(inputField);
        controls.add(pushBtn);
        controls.add(popBtn);
        controls.add(peekBtn);
        controls.add(clearBtn);
        controls.add(checkBtn);
        controls.add(randomBtn);

        add(controls, BorderLayout.NORTH);
        add(stackPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        pushBtn.addActionListener(e -> doPush());
        popBtn.addActionListener(e -> doPop());
        peekBtn.addActionListener(e -> doPeek());
        clearBtn.addActionListener(e -> doClear());
        checkBtn.addActionListener(e -> doCheck());
        randomBtn.addActionListener(e -> doRandom());

        setSize(720, 560);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void doPush() {
        highlightTop = false;
        int value;
        try {
            value = Integer.parseInt(inputField.getText().trim());
        } catch (NumberFormatException ex) {
            statusLabel.setText("INVALID INPUT: Enter an integer value.");
            stackPanel.repaint();
            return;
        }
        if (stack.push(value)) {
            statusLabel.setText("Pushed " + value + ".");
            inputField.setText("");
        } else {
            statusLabel.setText("STACK OVERFLOW: Cannot push, stack is full");
        }
        stackPanel.repaint();
    }

    void doPop() {
        highlightTop = false;
        Integer value = stack.pop();
        if (value == null) {
            statusLabel.setText("STACK UNDERFLOW: Cannot pop, stack is empty");
        } else {
            statusLabel.setText("Popped " + value + ".");
        }
        stackPanel.repaint();
    }

    void doPeek() {
        Integer value = stack.peek();
        if (value == null) {
            highlightTop = false;
            statusLabel.setText("STACK EMPTY: Nothing to peek");
        } else {
            highlightTop = true;
            statusLabel.setText("Top element is " + value + ".");
        }
        stackPanel.repaint();
    }

    void doClear() {
        highlightTop = false;
        stack.clear();
        statusLabel.setText("Stack cleared.");
        stackPanel.repaint();
    }

    void doCheck() {
        if (stack.isEmpty()) {
            statusLabel.setText("The stack is EMPTY.");
        } else {
            statusLabel.setText("The stack is NOT empty. Size: " + stack.size()
                    + " of " + StackManager.CAPACITY + ".");
        }
    }

    void doRandom() {
        highlightTop = false;
        stack.clear();
        int count = random.nextInt(StackManager.CAPACITY) + 1; // 1 to capacity
        for (int i = 0; i < count; i++) {
            stack.push(random.nextInt(100) + 1);               // 1 to 100
        }
        statusLabel.setText("Random stack generated with " + count + " element(s).");
        stackPanel.repaint();
    }

    // Draws the stack: bottom block at the base, newest block on top
    class StackPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int[] data = stack.toArray();

            int blockW = 120;
            int blockH = 36;
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
            g.drawString("Size: " + data.length + " / " + StackManager.CAPACITY, 10, 20);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StackGUI::new);
    }
}
