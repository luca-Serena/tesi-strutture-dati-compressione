import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class PanelNode extends JPanel {

    private final ArrayList<Boolean> isInFirstHalf = new ArrayList();
    private PanelNode parent;
    private PanelNode left;
    private PanelNode right;
    JLabel jl = new JLabel();

    public PanelNode() {
        super();
        jl.setVisible(true);
        this.add(jl);
    }

    public PanelNode getParent() {
        return parent;
    }

    public void setParent(PanelNode parent) {
        this.parent = parent;
    }

    public PanelNode getLeft() {
        return left;
    }

    public void setLeft(PanelNode left) {
        this.left = left;
    }

    public PanelNode getRight() {
        return right;
    }

    public void setRight(PanelNode right) {
        this.right = right;
    }

    public boolean isLeft(char ch, ArrayList<Character> alphabet) {
        return alphabet.indexOf(ch) < alphabet.size() / 2;
    }

    public ArrayList<Boolean> getIsInFirstHalf() {
        return isInFirstHalf;
    }

    /*inizializza la lista che contiene i caratteri del nodo (characters)
          inizializza la lista che contiene i valori binari del carattere (isInfFirstHalf)
     */
    private void createMap(char[] input, ArrayList<Character> alphabet) {
        for (char c : input) {
            Boolean isLeft = true;
            int index = alphabet.indexOf((Character) c);
            if (index >= alphabet.size() / 2) {
                isLeft = false;
            }
            this.isInFirstHalf.add(isLeft);
        }
    }

    /*inizializza la lista che tiene traccia dell'alfabeto del nodo*/
    ArrayList<Character> createAlphabet(char[] input) {
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

    public int select(PanelNode n, char ch, int occurrence, ArrayList<Character> alph) {
        int leftInd = 0, rightInd = alph.size() - 1;
        boolean isZero = true;
        int indexOfCharInAlph = alph.indexOf((Character) ch);
        while (rightInd - leftInd > 1) {  // getting the leaf
            if (rightInd - leftInd == 2) { //if there are just three nodes
                if (rightInd == indexOfCharInAlph) {
                    isZero = false;
                    break;
                } else if (leftInd == indexOfCharInAlph) {
                    break;
                }
            }
            if (indexOfCharInAlph < (leftInd + rightInd) / 2) {
                n = n.getLeft();
                rightInd = rightInd - (rightInd - leftInd) / 2 - 1;
            } else {
                n = n.getRight();
                leftInd = leftInd + (rightInd - leftInd + 1) / 2;
            }
        }

        if (isZero) {
            isZero = leftInd == indexOfCharInAlph;
        }

        int position = getPosition(n.isInFirstHalf, occurrence, isZero);
        n.colorBit(position-1);
        PanelNode child = n;
        n = n.getParent();

        while (n != null) {
            if (n.getLeft().equals(child)) {
                position = getPosition(n.isInFirstHalf, position, true);
            } else {
                position = getPosition(n.isInFirstHalf, position, false);
            }
            n.colorBit(position-1);
            System.out.println(n);
            n = n.getParent();
            child = child.getParent();
        }

        return position;
    }

    public int getPosition(ArrayList<Boolean> a, int occurrance, boolean isZero) {
        int counter = 0, position = 0;
        for (Boolean b : a) {
            if (counter < occurrance) {
                position++;
                if (isZero == b) {
                    counter++;
                }
            } else {
                break;
            }
        }
        if (counter == occurrance) // if this is not true, that means that there are no n occurances in bitmap!
        {
            return position;
        } else {
            return 0;
        }
    }

    /*restituisce la sequenza di valori binari che rappresentano la stringa del nodo*/
    @Override
    public String toString() {
        String result = "";
        for (Object b : this.getIsInFirstHalf()) {
            if (b.equals(Boolean.FALSE)) {
                result += 1;
            } else {
                result += 0;
            }
        }
        return result;
    }

    public void createTree(String input, PanelNode node, ArrayList<Character> alphabet) {
        node.createMap(input.toCharArray(), alphabet);
        node.jl.setText(node.toString());
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
            PanelNode rightChild = new PanelNode();
            node.setRight(rightChild);
            rightChild.setParent(node);
            createTree(stringRight, rightChild, rightAlph);

            for (int i = 0; i < rightAlph.size(); i++) {
                String s = "";
                s += rightAlph.get(i);
                stringLeft = stringLeft.replace(s, "");
            }
            PanelNode leftChild = new PanelNode();
            leftChild.setParent(node);
            node.setLeft(leftChild);
            createTree(stringLeft, leftChild, leftAlph);
        }
    }

    public int rank(PanelNode node, int index, char character, ArrayList<Character> alphabet) {
        System.out.println(node);
        node.colorBit(index);
        if (node.getLeft() == null && node.getRight() == null) {
            return index + 1;
        }
        Boolean isLeft = false;
        if (node.isLeft(character, alphabet)) {
            isLeft = true;
        }
        int counter = -1;
        for (int i = 0; i <= index; i++) {
            if (node.getIsInFirstHalf().get(i).equals(isLeft)) {
                counter++;
            }
        }
        ArrayList<Character> newAlphabet;
        int middle = alphabet.size() / 2;
        if (isLeft) {
            newAlphabet = new ArrayList(alphabet.subList(0, middle));
            return rank(node.getLeft(), counter, character, newAlphabet);
        } else {
            newAlphabet = new ArrayList(alphabet.subList(middle, alphabet.size()));
            return rank(node.getRight(), counter, character, newAlphabet);
        }
    }

    public void colorBit(int ind) {
        if (ind >= this.isInFirstHalf.size()) {
            return;
        }
        String text = "<html>";
        int index;
        for (index = 0; index < ind; index++) {
            if (this.isInFirstHalf.get(index) == true) {
                text += "0";
            } else {
                text += "1";
            }
        }
        if (this.isInFirstHalf.get(ind) == true) {
            text += "<font color = RED>0</font>";
        } else {
            text += "<font color = RED>1</font>";
        }
        for (index = index+1; index < this.isInFirstHalf.size(); index++) {
            if (this.isInFirstHalf.get(index) == true) {
                text += "0";
            } else {
                text += "1";
            }
        }
        text += "</html>";
        this.jl.setText(text + "r");
        repaint();
    }
}
