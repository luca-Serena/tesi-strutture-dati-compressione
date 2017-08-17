package wavelet;

import java.util.ArrayList;

public interface BitRankSelect {
    BitRankSelect fromArray(ArrayList <Boolean> v);
    int rank(boolean bit, int index);
    int select(boolean bit, int occurrance);
    int getSize();
    Boolean getBit(int index);
}
