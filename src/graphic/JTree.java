package graphic;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import wavelet.CharacterNotFoundException;

/**
 *
 * @author utente
 */
public class JTree extends javax.swing.JFrame {

    String input;
   JWaveletTree root;
    ArrayList<Character> alphabet = new ArrayList();

    public JTree() {
        initComponents();
    }

    private void setInput(String input) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (root != null) {
            root.removeTree(this);
        }
        this.alphabet =JWaveletTree.getAlphabet(input.toCharArray() );
        this.input = input;
        root = new JWaveletTree(input);
        comboChar.removeAllItems();
        comboIndex.removeAllItems();
        root.drawTree(this, 0, 700, null);
        repaint();
        alphabet.forEach((ch) -> {
            comboChar.addItem(ch.toString());
        });
        for (Integer i = 1; i < input.length(); i++) {
            comboIndex.addItem(i.toString());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        opCombo = new javax.swing.JComboBox<>();
        comboIndex = new javax.swing.JComboBox<>();
        buttonPerform = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        inputTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        outputLabel = new javax.swing.JLabel();
        comboChar = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        opCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "select", "rank", " " }));
        opCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opComboActionPerformed(evt);
            }
        });

        buttonPerform.setText("GO");
        buttonPerform.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPerformActionPerformed(evt);
            }
        });

        inputTextArea.setColumns(20);
        inputTextArea.setRows(5);
        jScrollPane1.setViewportView(inputTextArea);

        jButton1.setText("Select String");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71)
                        .addComponent(comboChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(buttonPerform, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(opCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 971, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)))
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(outputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(29, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(opCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboChar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboIndex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(582, 582, 582))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(buttonPerform, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(outputLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opComboActionPerformed

    }//GEN-LAST:event_opComboActionPerformed

    private void buttonPerformActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPerformActionPerformed
        String strChar = (String) comboChar.getSelectedItem();
        int i = Integer.parseInt((String) comboIndex.getSelectedItem());
        char ch = strChar.charAt(0);
        String operation = (String) opCombo.getSelectedItem();
        int result;
        if (root != null) {
            root.removeTree(this);
        }
        root.drawTree(this, 0, 700, null);
        paint(this.getGraphics());
        try {
            if (operation.equals("rank")) {
                result = root.rank(i, ch);
                outputLabel.setText("There are " + result + " occurrences of " + ch + " until index " + i);

            } else {
                result = root.select(ch, i);
                if (result <= 0) {
                    outputLabel.setText("There aren't " + i + " occurrences of " + ch);
                } else {
                    outputLabel.setText("The occurrence number " + i + " of " + ch + " is in position " + result);
                }
            }
        } catch (CharacterNotFoundException ex) {
            Logger.getLogger(JTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        repaint();
    }//GEN-LAST:event_buttonPerformActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            setInput(inputTextArea.getText());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(JTree.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (root != null) {
            root.drawLines(g);
        }
    }

    /*Drawing the lines between the nodes and then two small lines for making an arrow*/
    

    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            JTree frame = new JTree();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonPerform;
    private javax.swing.JComboBox<String> comboChar;
    private javax.swing.JComboBox<String> comboIndex;
    private javax.swing.JTextArea inputTextArea;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> opCombo;
    private javax.swing.JLabel outputLabel;
    // End of variables declaration//GEN-END:variables
}
