package wavelet;

import java.util.ArrayList;
import java.util.Collections;

public final class Node <T extends BitRankSelect> {

    private T isInFirstHalf;                                                    //sequenza di bit
    private Node parent, left, right;                                           
    private final char chRight;                                                 //ultimo carattere dell'alfabeto da rappresentare con 0

    public Node(char chRight) {
        this.chRight = chRight;
    }

    /*Costruttore per il solo nodo radice, che costruisce tutto l'albero*/
    public Node(String input, ArrayList<Character> alphabet, char chRight) {  
        this.createTree(input, alphabet);
        this.chRight = chRight;
    }

    public boolean isLeft(char ch) {
        return ch < this.chRight;
    }


    /*
          inizializza la lista che contiene i valori binari del carattere (isInfFirstHalf) in base all'alfabeto
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
        this.isInFirstHalf = (T) new BitVector().fromArray(temp);
    }

    /*inizializza la lista che tiene traccia dell'alfabeto del nodo*/
    public static ArrayList<Character> createAlphabet(char[] input) {
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

    /*retorna la posizione (indice + 1) della occorrenza numero occurrence di ch*/
    public int select(char ch, int occurrence) {
        Node n = this;
        while (n.right != null) {                                               //alla fine del while n sarà la foglia con i caratteri ch
            if (ch >= n.chRight) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        if (ch != n.chRight){                                                    // in questo caso significa che ch non esiste nell'albero
            return -1;
        }

        int position = n.isInFirstHalf.select(false, occurrence);               // nelle foglie ci sono solo 1
        Node child = n;
        n = n.parent;

        while (n != null) {
            if (n.left.equals(child)) {
                position = n.isInFirstHalf.select(true, position);
            } else {
                position = n.isInFirstHalf.select(false, position);
            }
            n = n.parent;
            child = child.parent;
        }
        return position;
    }

    /*crea il wavelet Tree a partire dalla radice*/
    public void createTree(String input, ArrayList<Character> alphabet) {
        this.createMap(input.toCharArray(), alphabet);
        if (input == null || input.equals("")) {
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

    /*ritorna il numero di occorrenze di character che ci sono fino all'indice index*/
    public int rank(int index, char character) {
        if (index >= this.isInFirstHalf.getSize()) {
            return -1;
        }

        if (this.left == null && this.right == null) {
            if (character == this.chRight) {
                return index + 1;
            } else {                                                             //nel caso il carattere non esista
                return -1;
            }
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
}
