
import java.util.Objects;

public class Wavelet_Tree {

    Node root;
    public String input;

    public Wavelet_Tree(String input) {
        this.input = input;
        root = new Node(input.toCharArray());
        createTree(input, root);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
   
      public static int select (Node node,  int occurrence, char ch){
            return select (occurrence,node.getLeaf(ch, node) );
      }
      
      private static int select(int occurrence, Node node ) {
        if (node.getParent() == null ) {
            return occurrence;
        }
        int counter=0;
        int index;
        for ( index =0; (counter<occurrence) && index<node.getIsInFirstHalf().size() ; index++){
            if (Objects.equals(node.getIsLeftChild(), node.getIsInFirstHalf().get(index)))
                counter++;
        }
        return select(index,node.getParent());
        
    } 
      
    public static int rank(Node node, int index, char character) {
        if (node.getLeft() == null && node.getRight() == null) {
            return index + 1;
        }
        boolean isLeft = false;
        if (node.isLeft(character)) {
            isLeft = true;
        }
        int counter = -1;
        for (int i = 0; i <= index; i++) {
            if (node.getIsInFirstHalf().get(i) == isLeft) {
                counter++;
            }
        }
        if (isLeft) {
            return rank(node.getLeft(), counter, character);
        } else {
            return rank(node.getRight(), counter, character);
        }

    }

    private static void createTree(String input, Node node) {
        if (input == null) {
            return;
        }
        if (node.getAlphabet().size() > 1) {
            String stringLeft = input, stringRight = input;
            for (int i = node.getAlphabet().size() / 2; i < node.getAlphabet().size(); i++) {
                String s = "";
                s += node.getAlphabet().get(i);
                stringLeft = stringLeft.replace(s, "");
                Node left = new Node(stringLeft.toCharArray());
                left.setIsLeftChild(Boolean.TRUE);
                node.setLeft(left);
                left.setParent(node);
                createTree(stringLeft, left);
            }
            for (int i = 0; i < node.getAlphabet().size() / 2; i++) {
                String s = "";
                s += node.getAlphabet().get(i);
                stringRight = stringRight.replace(s, "");
                Node right = new Node(stringRight.toCharArray());
                right.setParent(node);
                right.setIsLeftChild(Boolean.FALSE);
                node.setRight(right);
                createTree(stringRight, right);
            }
        }
    }
}
