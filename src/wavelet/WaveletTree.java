package wavelet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WaveletTree<T extends BitRankSelect> {

    private final T isInSecondHalf;                                              //sequenza di bit
    private WaveletTree parent, left, right;
    private final char chRight;                                                 //primo carattere dell'alfabeto da rappresentare con 1

    /*Costruttore per il solo nodo radice, che costruisce tutto l'albero*/
    public WaveletTree(String input, Class<T> vectorType) {
        this.chRight = median(input.toCharArray());
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

            WaveletTree rightChild = new WaveletTree(stringRight, vectorType);
            this.right = rightChild;
            rightChild.parent = this;
            WaveletTree leftChild = new WaveletTree(stringLeft, vectorType);
            leftChild.parent = this;
            this.left = leftChild;
        }
    }

    public boolean isLeft(char ch) {
        return ch < this.chRight;
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

    /*restituisce il primo carattere che si trova nella seconda metà dell'alfabeto del nodo*/
    public static char median(char[] input) {
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
        return alphabet.get(alphabet.size() / 2);
    }

    /*retorna la posizione (indice + 1) della occorrenza numero occurrence di ch*/
    public int select(char ch, int occurrence) throws CharacterNotFoundException {
        WaveletTree n = this;
        while (n.right != null) {                                               //alla fine del while n sarà la foglia con i caratteri ch
            if (ch >= n.chRight) {
                n = n.right;
            } else {
                n = n.left;
            }
        }
        if (ch != n.chRight) {                                                    // in questo caso significa che ch non esiste nell'albero
            throw new CharacterNotFoundException();
        } 
        return n.selectFromLeafToRoot(occurrence);
    }

    private int selectFromLeafToRoot(int occurrence) {
        WaveletTree n = this;
        n = n.parent;

        if (n != null) {
            int position;
            if (n.left.equals(this)) {
                position = n.isInSecondHalf.select(true, occurrence);
            } else {
                position = n.isInSecondHalf.select(false, occurrence);
            }
            return n.selectFromLeafToRoot(position);
        }
        return occurrence;
    }

    /*ritorna quante occorrenze di character ci sono fino all'indice index*/
    public int rank(int index, char character) throws CharacterNotFoundException, IndexOutOfBoundsException{
        if (index >= this.isInSecondHalf.getSize()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }

        if (this.left == null && this.right == null) {
            if (character == this.chRight) {
                return index + 1;
            } else {                                                             
                throw new CharacterNotFoundException();
            }
        }

        int counter = this.isInSecondHalf.rank(isLeft(character), index);
        if (isLeft(character)) {
            return this.left.rank(counter, character);
        } else {
            return this.right.rank(counter, character);
        }
    }
}
