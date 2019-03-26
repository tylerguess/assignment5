public class Main {

    public static void main(String[] args) {
        Implementation imp = new Implementation();
        imp.loadLexicon("words_tiny.txt");
        System.out.println(imp.getAllValidWords(5));
    }
}
