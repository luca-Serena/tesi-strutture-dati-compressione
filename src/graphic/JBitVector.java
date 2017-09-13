package graphic;

import javax.swing.JLabel;
import wavelet.BitVector;

/**
 *
 * @author utente
 */
public class JBitVector extends BitVector {

    public void colorBit(int ind, JLabel bitLabel) {
        System.out.println(" da colorare indice " + ind);
        bitLabel.paintImmediately(bitLabel.getVisibleRect());     
        if (ind >= this.vector.size()) {
            return;
        }
        String text = "<html>";
        int index;
        for (index = 0; index < ind; index++) {
            if (this.vector.get(index) == true) {
                text += "0";
            } else {
                text += "1";
            }
        }
        if (this.vector.get(ind) != null) {
            if (this.vector.get(ind) == true) {
                text += "<font color = RED>0</font>";
            } else {
                text += "<font color = RED>1</font>";
            }

            for (index = index + 1; index < this.vector.size(); index++) {
                if (this.vector.get(index) == true) {
                    text += "0";
                } else {
                    text += "1";
                }
            }
            text += "</html>";
            bitLabel.setText(text);
        }
        bitLabel.repaint();
    }

    public void clean(JLabel bitLabel) {
        String text = "";
        for (int i = 0; i < this.vector.size(); i++) {
            if (this.vector.get(i) == true) {
                text += "0";
            } else {
                text += "1";
            }
        }
        bitLabel.setText(text);
    }

}
