package graphic;

import wavelet.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class PanelNode<T extends BitVector> extends JPanel {

    private final T isInSecondHalf;
    private PanelNode parent;
    private PanelNode left;
    private PanelNode right;
    private char chRight;
    JLabel jl = new JLabel();

    public PanelNode(String input, Class<T> vectorType) {
        super();
        jl.setVisible(true);
        this.add(jl);
        this.chRight = WaveletTree.median(input.toCharArray());
        this.isInSecondHalf = createMap(input.toCharArray(), chRight, vectorType);
        char[] inputChars = input.toCharArray();
        if (!this.isInSecondHalf.isLeaf()) {
            String stringLeft = "", stringRight = "";
            for (char c : inputChars) {
                if (c < this.chRight) {
                    stringLeft += c;
                } else {
                    stringRight += c;
                }
            }

            PanelNode rightChild = new PanelNode(stringRight, vectorType);
            this.right = rightChild;
            rightChild.parent = this;
            PanelNode leftChild = new PanelNode(stringLeft, vectorType);
            leftChild.parent = this;
            this.left = leftChild;
        }
    }

    public PanelNode getParent() {
        return parent;
    }

    public void setParent(PanelNode parent) {
        this.parent = parent;
    }

    public PanelNode getRight() {
        return right;
    }

    public void setRight(PanelNode right) {
        this.right = right;
    }

    public char getChRight() {
        return chRight;
    }

    public void setChRight(char chRight) {
        this.chRight = chRight;
    }

    public PanelNode getLeft() {
        return left;
    }

    public void setLeft(PanelNode left) {
        this.left = left;
    }

    public boolean isLeft(char ch, ArrayList<Character> alphabet) {
        return alphabet.indexOf(ch) < alphabet.size() / 2;
    }

    public boolean isLeft(char ch) {
        return ch < this.chRight;
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
    private T createMap(char[] input, char ch, Class<T> vectorType) {
        ArrayList<Boolean> temp = new ArrayList();
        for (char c : input) {
            Boolean isLeft = true;
            if (c >= ch) {
                isLeft = false;
            }
            temp.add(isLeft);
        }
        T ret = null;
        try {
            ret = (T) vectorType.newInstance().fromArray(temp);
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(WaveletTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public int select(char ch, int occurrence) {
        PanelNode n = this;
        while (n.right != null) {
            if (ch >= n.chRight) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        int position = n.isInSecondHalf.select(false, occurrence);
        n.colorBit(position - 1);
        PanelNode child = n;
        n = n.parent;

        while (n != null) {
            if (n.left.equals(child)) {
                position = n.isInSecondHalf.select(true, position);
                n.colorBit(position - 1);
            } else {
                position = n.isInSecondHalf.select(false, position);
                n.colorBit(position - 1);
            }
            n = n.parent;
            child = child.parent;
        }
        return position;
    }

    public int rank(int index, char character) {
        if (index >= this.isInSecondHalf.getSize()) {
            return -1;
        }
        this.colorBit(index);
        if (this.left == null && this.right == null) {
            return index + 1;
        }
        Boolean isLeft = false;
        if (this.isLeft(character)) {
            isLeft = true;
        }
        int counter = this.isInSecondHalf.rank(isLeft, index);
        if (isLeft) {
            return this.left.rank(counter, character);
        } else {
            return this.right.rank(counter, character);
        }
    }

    @Override
    public String toString() {
        return this.isInSecondHalf.toString();
    }

    public void colorBit(int ind) {
        if (ind >= this.isInSecondHalf.getSize()) {
            return;
        }
        String text = "<html>";
        int index;
        for (index = 0; index < ind; index++) {
            if (this.isInSecondHalf.getBit(index) == true) {
                text += "0";
            } else {
                text += "1";
            }
        }
        if (this.isInSecondHalf.getBit(ind) != null) {
            if (this.isInSecondHalf.getBit(ind) == true) {
                text += "<font color = RED>0</font>";
            } else {
                text += "<font color = RED>1</font>";
            }

            for (index = index + 1; index < this.isInSecondHalf.getSize(); index++) {
                if (this.isInSecondHalf.getBit(index) == true) {
                    text += "0";
                } else {
                    text += "1";
                }
            }
            text += "</html>";
            this.jl.setText(text + "r");
        }
        repaint();
    }
}
