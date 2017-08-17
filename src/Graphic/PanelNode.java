package Graphic;

import wavelet.*;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class PanelNode<T extends BitVector> extends JPanel {

    private T isInFirstHalf;
    private PanelNode parent;
    private PanelNode left;
    private PanelNode right;
    private char chRight;
    JLabel jl = new JLabel();

    public PanelNode(char ch) {
        super();
        jl.setVisible(true);
        this.add(jl);
        this.chRight = ch;
    }

    public PanelNode(String input, ArrayList<Character> alphabet, char ch) {
        super();
        jl.setVisible(true);
        this.add(jl);
        this.createTree(input, alphabet);
        this.chRight = ch;
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


    /*inizializza la lista che contiene i caratteri del nodo (characters)
          inizializza la lista che contiene i valori binari del carattere (isInfFirstHalf)
     */
    private void createMap(char[] input, ArrayList<Character> alphabet) {
        ArrayList<Boolean> temp = new ArrayList();
        for (char c : input) {
            Boolean isLeft = true;
            int index = alphabet.indexOf((Character) c);
            if (index >= alphabet.size() / 2) {
                isLeft = false;
            }
            temp.add(isLeft);
        }
        this.isInFirstHalf = (T) new BitVector().fromArray(temp);//.fromArray(temp);//.fromArray(temp);
    }

    /*inizializza la lista che tiene traccia dell'alfabeto del nodo*/
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

    public int select(char ch, int occurrence) {
        PanelNode n = this;
        while (n.right != null) {
            if (ch >= n.chRight) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        int position = n.isInFirstHalf.select(false, occurrence);
        n.colorBit(position - 1);
        PanelNode child = n;
        n = n.parent;

        while (n != null) {
            if (n.left.equals(child)) {
                position = n.isInFirstHalf.select(true, position);
                n.colorBit(position - 1);
            } else {
                position = n.isInFirstHalf.select(false, position);
                n.colorBit(position - 1);
            }
            n = n.parent;
            child = child.parent;
        }
        return position;
    }

    public int rank(int index, char character) {
        if (index >= this.isInFirstHalf.getSize()) {
            return -1;
        }
        this.colorBit(index + 1);
        if (this.left == null && this.right == null) {
            return index + 1;
        }
        Boolean isLeft = false;
        if (this.isLeft(character)) {
            isLeft = true;
        }
        int counter = this.isInFirstHalf.rank(isLeft, index);
        if (isLeft) {
            return this.left.rank(counter, character);
        } else {
            return this.right.rank(counter, character);
        }
    }

    public void createTree(String input, ArrayList<Character> alphabet) {
        this.createMap(input.toCharArray(), alphabet);
        if (input == null) {
            return;
        }
        if (alphabet.size() > 1) {
            ArrayList<Character> leftAlph, rightAlph;
            leftAlph = new ArrayList(alphabet.subList(0, alphabet.size() / 2));
            rightAlph = new ArrayList(alphabet.subList(alphabet.size() / 2, alphabet.size()));
            String stringLeft = input, stringRight = input;
            for (int i = 0; i < leftAlph.size(); i++) {
                String s = "";
                s += leftAlph.get(i);
                stringRight = stringRight.replace(s, "");
            }
            PanelNode rightChild = new PanelNode(rightAlph.get(rightAlph.size() / 2));
            this.right = rightChild;
            rightChild.parent = this;
            this.right.createTree(stringRight, rightAlph);

            for (int i = 0; i < rightAlph.size(); i++) {
                String s = "";
                s += rightAlph.get(i);
                stringLeft = stringLeft.replace(s, "");
            }
            PanelNode leftChild = new PanelNode(leftAlph.get(leftAlph.size() / 2));
            leftChild.parent = this;
            this.left = leftChild;
            this.left.createTree(stringLeft, leftAlph);
        }
    }

    @Override
    public String toString() {
        return this.isInFirstHalf.toString();
    }

    public void colorBit(int ind) {
        if (ind >= this.isInFirstHalf.getSize()) {
            return;
        }
        String text = "<html>";
        int index;
        for (index = 0; index < ind; index++) {
            if (this.isInFirstHalf.getBit(index) == true) {
                text += "0";
            } else {
                text += "1";
            }
        }
        if (this.isInFirstHalf.getBit(ind) != null) {
            if (this.isInFirstHalf.getBit(ind) == true) {
                text += "<font color = RED>0</font>";
            } else {
                text += "<font color = RED>1</font>";
            }

            for (index = index + 1; index < this.isInFirstHalf.getSize(); index++) {
                if (this.isInFirstHalf.getBit(index) == true) {
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
