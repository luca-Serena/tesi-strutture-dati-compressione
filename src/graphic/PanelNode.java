package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import wavelet.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class PanelNode extends WaveletTree<JBitVector> {

    JPanel panel;
    JLabel stringLabel = new JLabel();           
    JLabel jl = new JLabel();

    public PanelNode(String input) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.panel = new JPanel();
        panel.add(stringLabel);
        stringLabel.setText(input);
        stringLabel.setBounds(5,-10,100,50);
        panel.add(jl);
        this.chRight = WaveletTree.median(input.toCharArray());
        this.isInSecondHalf = (JBitVector) createMap(input.toCharArray(), chRight, JBitVector.class);
        String[] inputs = WaveletTree.getConstructorParameters(input, this.chRight);
        if (!this.isInSecondHalf.isLeaf()) {
            right = new PanelNode(inputs[1]);
            left = new PanelNode(inputs[0]);
        }
    }


    static ArrayList<Character> createAlphabet(char[] input) {
        ArrayList<Character> alphabet = new ArrayList();
        for (char ch : input) {
            boolean found = false;
            for (char alphachar : alphabet) {
                if (alphachar == ch) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                alphabet.add(ch);
            }
        }
        Collections.sort(alphabet, (Character ch2, Character ch1) -> ch2.compareTo(ch1));
        return alphabet;
    }

    /* dà i valori al vettore di bit (isInSecondtHalf) in base all'alfabeto */
    @Override
    public int select(char ch, int occurrence) throws CharacterNotFoundException {
        cleanColors();
        this.isInSecondHalf.colorBit(occurrence, jl);
        return super.select(ch, occurrence);
    }

    @Override
    public int rank(int index, char character) throws CharacterNotFoundException {
        cleanColors();
        this.isInSecondHalf.colorBit(index, jl);
        return super.rank(index, character);
    }

    private void cleanColors() {
        this.isInSecondHalf.clean(this.jl);
        if (this.left != null) {
            PanelNode l = (PanelNode) this.left;
            l.cleanColors();
            PanelNode r = (PanelNode) this.left;
            r.cleanColors();
        }
    }

    @Override
    public String toString() {
        return this.isInSecondHalf.toString();
    }

    protected void removeTree(JFrame frame) {
        frame.remove(this.panel);
        if (this.left != null) {
            PanelNode l = (PanelNode) this.left;
            l.removeTree(frame);
        }
        if (this.right != null) {
            PanelNode r = (PanelNode) this.right;
            r.removeTree(frame);
        }
    }

    protected void drawTree(JFrame frame, int times, int xPos, Boolean isLeft) {
        //PanelNode yourPanel = new PanelNode(); // create your JPanel
        this.jl.setText(this.toString());
        this.panel.setLayout(null); // set the layout null for this JPanel !
        frame.add(this.panel);
        //  nl = new NodeLabel(n); // create some stuff
        int leng = toString().length() * 10 + 10;
        this.jl.setBounds(5, 10, 100, 50); // set your position of your elements inside your JPanel
        this.panel.setBorder(BorderFactory.createLineBorder(Color.black)); // set a testing border to help you position the elements better
        if (times > 0) {
            int x = isLeft ? xPos - leng / 2 : xPos - leng / 2;
            int y = 20 + 100 * times;
            this.panel.setBounds(x, y, leng, 50);
        } else { // set the location of the JPanel
            this.panel.setBounds(xPos - leng / 2, 20, leng, 50);
        }
        if (this.left != null) {
            int leftPos = (xPos > 700) ? xPos - (200 - 50 * times) : xPos - (200 - 50 * times);
            PanelNode l = (PanelNode) this.left;
            l.drawTree(frame, times + 1, leftPos, true);
        }
        if (this.right != null) {
            int rightPos = (xPos > 700) ? xPos + (200 - 50 * times) : xPos + (200 - 50 * times);
            PanelNode r = (PanelNode) this.right;
            r.drawTree(frame, times + 1, rightPos, false);
        }
    }

    public void drawLines(Graphics g) {
        int leng = (this.toString().length() * 10 + 30) / 2;
        if (this.left != null) {
            PanelNode l = (PanelNode) this.left;
            int lengLeft = (l.toString().length() * 10 + 30) / 2;
            g.drawLine(this.panel.getX() + leng, this.panel.getY() + 80, l.panel.getX() + lengLeft, l.panel.getY() + 30);
            g.drawLine(l.panel.getX() + lengLeft, l.panel.getY() + 30, l.panel.getX() + lengLeft + 23, l.panel.getY() + 35);
            g.drawLine(l.panel.getX() + lengLeft, l.panel.getY() + 30, l.panel.getX() + lengLeft + 5, l.panel.getY() + 10);
            l.drawLines(g);
        }
        if (this.right != null) {
            PanelNode r = (PanelNode) this.right;
            int lengRight = (this.right.toString().length() * 10 + 30) / 2;
            g.drawLine(this.panel.getX() + leng, this.panel.getY() + 80, r.panel.getX() + lengRight, r.panel.getY() + 30);
            g.drawLine(r.panel.getX() + lengRight, r.panel.getY() + 30, r.panel.getX() + lengRight - 23, r.panel.getY() + 35);
            g.drawLine(r.panel.getX() + lengRight, r.panel.getY() + 30, r.panel.getX() + lengRight - 5, r.panel.getY() + 10);
            r.drawLines(g);
        }
    }
}
