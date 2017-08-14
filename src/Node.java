import java.util.ArrayList;
import java.util.Collections;

public final class Node<T> {

    private final ArrayList<T> isInFirstHalf = new ArrayList();
    private Node parent, left, right;
    private char chLeft, chRight;

    public Node(char ch) {
        this.chRight = ch;
    }

    public Node(String input, ArrayList<Character> alphabet, char ch) {
        this.createTree(input, alphabet);
        this.chRight = ch;
    }

    public boolean isLeft(char ch) {
        return ch < this.chRight;
    }

    public ArrayList<T> getIsInFirstHalf() {
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
            this.isInFirstHalf.add((T) isLeft);
        }
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
        boolean isZero;
        Node n = this;
        //  int indexOfCharInAlph = alph.indexOf((Character) ch);
        while (n.right.right != null) {
            if (ch >= n.chRight) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        isZero = ch < n.chRight;
        int position = getPosition(n.isInFirstHalf, occurrence, isZero);
        Node child = n;
        n = n.parent;

        while (n != null) {
            if (n.left.equals(child)) {
                position = getPosition(n.isInFirstHalf, position, true);
            } else {
                position = getPosition(n.isInFirstHalf, position, false);
            }
            n = n.parent;
            child = child.parent;
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
            Node rightChild = new Node(rightAlph.get(rightAlph.size() / 2));
            this.right = rightChild;
            rightChild.parent = this;
            this.right.createTree(stringRight, rightAlph);

            for (int i = 0; i < rightAlph.size(); i++) {
                String s = "";
                s += rightAlph.get(i);
                stringLeft = stringLeft.replace(s, "");
            }
            Node leftChild = new Node(leftAlph.get(leftAlph.size() / 2));
            leftChild.parent = this;
            this.left = leftChild;
            this.left.createTree(stringLeft, leftAlph);
        }
    }

    public int rank(int index, char character) {
        if (index >= this.isInFirstHalf.size()) {// || index < 0 || !alphabet.contains(character)) {
            return -1;
        }
        if (this.left == null && this.right == null) {
            return index + 1;
        }
        Boolean isLeft = false;
        if (this.isLeft(character)) {
            isLeft = true;
        }
        int counter = -1;
        for (int i = 0; i <= index; i++) {
            if (this.getIsInFirstHalf().get(i).equals(isLeft)) {
                counter++;
            }
        }
        if (isLeft) {
            return this.left.rank(counter, character);
        } else {
            return this.right.rank(counter, character);
        }
    }

    public static char[] getLeftAndRightCh(char[] input) {
        ArrayList al = createAlphabet(input);
        char[] res = new char[2];
        res[1] = (char) al.get(al.size() / 2);
        res[0] = (char) al.get(al.size() / 2 - 1);
        return res;
    }
}
