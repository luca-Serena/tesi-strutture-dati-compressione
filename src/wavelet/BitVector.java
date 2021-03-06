package wavelet;

import java.util.ArrayList;

public class BitVector implements BitRankSelect {

    public ArrayList<Boolean> vector;

    @Override
    public int select(boolean isZero, int occurrance) throws IndexOutOfBoundsException {
        if (occurrance < 0 || occurrance > vector.size()) {
            throw new IndexOutOfBoundsException ("Index out of bounds");
        }
        int counter = 0, position = 0;
        for (boolean b : vector) {
            if (counter < occurrance) {
                position++;
                if (isZero == b) {
                    counter++;
                }
            } else {
                break;
            }
        }
        if (counter == occurrance) // if this is not true, that means that there are no n occurances in bitmap
        {
            return position;
        } else {
            return 0;
        }
    }

    @Override
    public int rank(boolean isZero, int index) throws IndexOutOfBoundsException {
        if (index >= vector.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        else if (index <0)
            return -1;
        int counter = -1;
        for (int i = 0; i <= index; i++) {
            if (vector.get(i) == isZero) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public BitRankSelect fromArray(ArrayList b) {
        this.vector = b;
        return this;
    }

    @Override
    public int getSize() {
        return vector.size();
    }

    @Override
    public boolean isLeaf() {
        boolean res = true;
        for (Object b : this.vector) {
            if (b.equals(Boolean.TRUE)) {
                res = false;
                break;
            }
        }
        return res;
    }

    @Override
    public Boolean getBit(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= vector.size()) {
            return null;
        }
        return this.vector.get(index);
    }

    @Override
    public String toString() {
        String result = "";
        for (Object b : this.vector) {
            if (b.equals(Boolean.FALSE)) {
                result += 1;
            } else {
                result += 0;
            }
        }
        return result;
    }
}
