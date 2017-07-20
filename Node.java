
import java.util.ArrayList;
import java.util.Collections;

public final class Node {

    private final ArrayList<Character> characters = new ArrayList<>();
    private final ArrayList<Boolean> isInFirstHalf = new ArrayList();
    private Node parent;
    private Node left;
    private Node right;
    private ArrayList<Character> alphabet = new ArrayList();
    private Boolean isLeftChild;

    public Node(char[] input) {
        this.createAlphabet(input);
        this.createMap(input);
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public ArrayList<Character> getAlphabet() {
        return alphabet;
    }

    public boolean isLeft(char ch) {
        return this.alphabet.indexOf(ch) < this.alphabet.size() / 2;
    }

    public void setAlphabet(ArrayList<Character> alphabet) {
        this.alphabet = alphabet;
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public ArrayList<Boolean> getIsInFirstHalf() {
        return isInFirstHalf;
    }

    public Boolean getIsLeftChild() {
        return isLeftChild;
    }

    public void setIsLeftChild(Boolean isLeftChild) {
        this.isLeftChild = isLeftChild;
    }

        /*inizializza la lista che contiene i caratteri del nodo (characters)
          inizializza la lista che contiene i valori binari del carattere (isInfFirstHalf)
         */
    private void createMap(char[] input) {
        for (char c : input) {
            boolean isLeft = true;
            int index = this.alphabet.indexOf((Character) c);
            if (index >= this.alphabet.size() / 2) {
                isLeft = false;
            }
            this.characters.add(c);
            this.isInFirstHalf.add(isLeft);
        }
    }

    /*inizializza la lista che tiene traccia dell'alfabeto del nodo*/
    private void createAlphabet(char[] input) {
        for (char ch : input) {
            boolean found = false;
            for (char alphachar : this.alphabet) {
                if (alphachar == ch) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                this.alphabet.add(ch);
            }
        }
        Collections.sort(this.alphabet, (Character ch2, Character ch1) -> ch2.compareTo(ch1));
    }

    /* restituisce il nodo dove si trovano le sequenze del carattere indicato*/
    public Node getLeaf(char ch, Node node) {
        if (node.left == null && node.right == null) {
            return node;
        }
        if (node.isLeft(ch)) {
            return (getLeaf(ch, node.left));
        } else {
            return getLeaf(ch, node.right);
        }
    }

    /*restituisce la sequenza di valori binari che rappresentano la stringa del nodo*/
    @Override
    public String toString() {
        String result = "";
        for (boolean b : this.getIsInFirstHalf()) {
            if (b == false) {
                result += 1;
            } else {
                result += 0;
            }
        }
        return result;
    }
}
