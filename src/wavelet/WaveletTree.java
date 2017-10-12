package wavelet;

import java.util.ArrayList;
import java.util.Collections;

public class WaveletTree<T extends BitRankSelect> {

    protected T isInSecondHalf;                                              //sequenza di bit
    protected WaveletTree left, right;
    protected char chRight;                                                 //primo carattere dell'alfabeto da rappresentare con 1

    public WaveletTree() {
    }

    /*Costruttore per il solo nodo radice, che costruisce tutto l'albero*/
    public WaveletTree(String input, Class<T> vectorType) throws InstantiationException, IllegalAccessException {
        this.chRight = median(input.toCharArray());
        this.isInSecondHalf = (T) createBitRankSelect(input.toCharArray(), chRight, vectorType);
        if (!this.isInSecondHalf.isLeaf()) {
            String[] inputs = splitByChar(input, this.chRight);
            right = new WaveletTree(inputs[1], vectorType);
            left = new WaveletTree(inputs[0], vectorType);
        }
    }

    private boolean isLeft(char ch) {
        return ch < this.chRight;
    }

    private WaveletTree child(char ch) {
        if (isLeft(ch)) {
            return this.left;
        } else {
            return this.right;
        }
    }

    //dà i valori al vettore di bit (isInSecondtHalf) in base all'alfabeto 
    protected static BitRankSelect createBitRankSelect(char[] input, char ch, Class vectorType) throws InstantiationException, IllegalAccessException {
        ArrayList<Boolean> temp = new ArrayList();
        for (char c : input) {
            Boolean isLeft = true;
            if (c >= ch) {
                isLeft = false;
            }
            temp.add(isLeft);
        }
        return ((BitRankSelect) vectorType.newInstance()).fromArray(temp);
    }

    /*restituisce il primo carattere che si trova nella seconda metà dell'alfabeto del nodo*/
    public static char median(char[] input) {
        ArrayList<Character> alphabet = new ArrayList();
        for (char ch : input) {
            if (!alphabet.contains(ch)) {
                alphabet.add(ch);
            }
        }
        Collections.sort(alphabet, (Character ch2, Character ch1) -> ch2.compareTo(ch1));
        return alphabet.get(alphabet.size() / 2);
    }

    /*retorna la posizione (indice + 1) della occorrenza numero occurrence di ch*/
    public int select(char ch, int occurrence) throws CharacterNotFoundException {
        if (this.right != null) {
            int i = child(ch).select(ch, occurrence);
            return isInSecondHalf.select(isLeft(ch), i);
        } else {
            if (ch != this.chRight) {                                                    // in questo caso significa che ch non esiste nell'albero
                throw new CharacterNotFoundException();
            }
            return occurrence;
        }
    }

    protected static String[] splitByChar (String input, char chRight) {
        String[] res = new String[2];
        char[] inputChars = input.toCharArray();
        res[0] = "";
        res[1] = "";
        for (char c : inputChars) {
            if (c < chRight) {
                res[0] += c;
            } else {
                res[1] += c;
            }
        }
        return res;
    }

    /*ritorna quante occorrenze di character ci sono fino all'indice index*/
    public int rank(int index, char character) throws CharacterNotFoundException, IndexOutOfBoundsException {
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
        return child(character).rank(counter, character);
    }
}
