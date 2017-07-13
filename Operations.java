
import java.util.Scanner;

public class Operations {

    public static Wavelet_Tree createTree() {
        System.out.println("Inserisci una stringa");
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        return new Wavelet_Tree(str);
    }

    public static void rank(Node node) {
        System.out.println("Quale carattere cercare?");
        Scanner sc = new Scanner(System.in);
        char ch = sc.next().charAt(0);
        System.out.println("Fino a che indice considerare la stringa?");
        int index = sc.nextInt();
        if (index >= 0 && index < node.getCharacters().size() && node.getAlphabet().contains(ch)) {
            int occurrences = Wavelet_Tree.rank(node, index, ch);
            System.out.println(occurrences + " occorrenze");
        }
        else System.err.println ("Output error");
    }

    public static void main(String[] args) {
        Wavelet_Tree tree = createTree();
        rank(tree.getRoot());

    }
}
