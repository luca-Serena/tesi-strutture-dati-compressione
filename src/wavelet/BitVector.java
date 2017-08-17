package wavelet;

import java.util.ArrayList;

public class BitVector implements BitRankSelect {

    ArrayList<Boolean> vector;

    @Override
    public int select(boolean isZero, int occurrance) {
        if (occurrance < 0 || occurrance > vector.size())
            return -1;
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
    public int rank(boolean isZero, int index) {
        if (index < 0 || index >= vector.size())
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
    public Boolean getBit(int index) {
        if (index >= vector.size() || index < 0) {
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
