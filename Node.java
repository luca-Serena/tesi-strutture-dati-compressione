
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Node {

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
        if (this.alphabet.indexOf(ch) < this.alphabet.size() / 2) {
            return true;
        } else {
            return false;
        }
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

    public void createMap(char[] input) {
        for (char c : input) {
            boolean isLeft = true;
            int index = this.alphabet.indexOf((Character) c);
            if (index > this.alphabet.size() / 2) {
                isLeft = false;
            }
            this.characters.add(c);
            this.isInFirstHalf.add(isLeft);
        }
    }

    public void createAlphabet(char[] input) {
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

    public Node getLeaf(char ch, Node node) {
        if (node.left==null && node.right ==null)
            return node;
        boolean isLeft = false;
        if (node.isLeft(ch)) {
            return (getLeaf(ch, node.left));
        } else {
            return getLeaf(ch, node.right);
        }
    }
}
