import java.util.List;
import java.util.SortedSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;
import java.util.Stack;
import java.util.Arrays;
import java.util.Deque;
import java.util.ArrayDeque;

public class Implementation implements WordSearchGame {

    TreeSet<String> lexicon = new TreeSet<String>();
    Boolean lexiconLoaded = false;
    String[][] gameBoard = new String[0][0];
    Boolean[][] visited = new Boolean[0][0];
    int n = 0;
    int numRows;
    int numCols;
    int order = 0;
    int[][] orderGrid = new int[0][0];
    TreeSet<String> validWords = new TreeSet<String>();

    /**
     * Loads the lexicon into a data structure for later use.
     *
     * @param fileName A string containing the name of the file to be opened.
     * @throws IllegalArgumentException if fileName is null
     * @throws IllegalArgumentException if fileName cannot be opened.
     */
    @Override
    public void loadLexicon(String fileName) throws IllegalArgumentException {
        try {
            Scanner fileInput = new Scanner(new File(fileName));
            while (fileInput.hasNextLine()) {
                lexicon.add(fileInput.nextLine().toUpperCase());
            }
            lexiconLoaded = true;
        }
        catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void setBoard(String[] letterArray) {
        n = (int) Math.sqrt(letterArray.length);
        int i = 0;
        gameBoard = new String[n][n];
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                gameBoard[row][col] = letterArray[i];
                i++;
            }
        }
        numCols = n;
        numRows = n;
        visited = new Boolean[n][n];
        orderGrid = new int[n][n];
    }

    @Override
    public String getBoard() {
        String output = "";
        for (String[] row : gameBoard) {
            for (String letter : row) {
                output += letter;
            }
        }
        return output;
    }

    /**
     * Retrieves all valid words on the game board, according to the stated game
     * rules.
     *
     * @param minimumWordLength The minimum allowed length (i.e., number of
     *     characters) for any word found on the board.
     * @return java.util.SortedSet which contains all the words of minimum length
     *     found on the game board and in the lexicon.
     * @throws IllegalArgumentException if minimumWordLength < 1
     * @throws IllegalStateException if loadLexicon has not been called.
     */
//    @Override
//    public SortedSet<String> getAllValidWords(int minimumWordLength) {
//        if (minimumWordLength < 1) {
//            throw new IllegalArgumentException("Minimum word length is too low");
//        }
//        else if (lexiconLoaded == false) {
//            throw new IllegalStateException("Lexicon has not been loaded");
//        }
//        else {
//            TreeSet<String> output = new TreeSet<String>();
//            for (String word : lexicon) {
//                if (word.length() >= minimumWordLength) {
//                    output.add(word);
//                }
//            }
//            return output;
//        }
//    }

    @Override
    public SortedSet<String> getAllValidWords(int minimumWordLength) {
        if (minimumWordLength < 1) {
            throw new IllegalArgumentException("Minimum word length is too low");
        }
        else if (lexiconLoaded == false) {
            throw new IllegalStateException("Lexicon has not been loaded");
        }
        else {
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols; col++) {
                    Position p = new Position(row, col);
                    p.dfs(p);
                }
            }
        }
        return validWords;
    }


    @Override
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
        Stack<String> S = new Stack<String>();
        return 0;

    }

    /**
     * Determines if the given word is in the lexicon.
     *
     * @param wordToCheck The word to validate
     * @return true if wordToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if wordToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public boolean isValidWord(String wordToCheck) {
        return lexicon.contains(wordToCheck.toUpperCase());
    }

    /**
     * Determines if there is at least one word in the lexicon with the
     * given prefix.
     *
     * @param prefixToCheck The prefix to validate
     * @return true if prefixToCheck appears in lexicon, false otherwise.
     * @throws IllegalArgumentException if prefixToCheck is null.
     * @throws IllegalStateException if loadLexicon has not been called.
     */
    @Override
    public boolean isValidPrefix(String prefixToCheck) {
        if (prefixToCheck == null) {
            throw new IllegalArgumentException("Prefix can't be null");
        }
        else if (lexiconLoaded == false) {
            throw new IllegalStateException("Lexicon has not been loaded");
        }
        else if (isValidWord(prefixToCheck)) {
            return true;
        }
        else {
            for (String str : lexicon) {
                if (str.startsWith(prefixToCheck.toUpperCase())) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public List<Integer> isOnBoard(String wordToCheck) {
        Stack<String> S = new Stack<String>();
        return null;
    }

    class Position {
        int x;
        int y;
        String letter;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
            this.letter = gameBoard[x][y];
        }

        public boolean isValid(Position p) {
            return (p.x >= 0) && (p.x < numRows) && (p.y >= 0) && (p.y < numCols);
        }

        public boolean isVisited(Position p) {
            return visited[p.x][p.y];
        }

        public void visit(Position p) {
            visited[p.x][p.y] = true;
        }

        private void process(Position p) {
            orderGrid[p.x][p.y] = order++;
        }

        public Position[] neighbors() {
            Position[] nbrs = new Position[8];
            int count = 0;
            Position p;

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (!((i == 0) && (j == 0))) {
                        p = new Position(x + i, y + j);
                        if (isValid(p)) {
                            nbrs[count++] = p;
                        }
                    }
                }
            }
            return Arrays.copyOf(nbrs, count);
        }

        public void dfs(Position start) {
            String word = "";
            Deque<Position> stack = new ArrayDeque<>();
            visit(start);
            stack.addFirst(start);
            while (!stack.isEmpty()) {
                Position position = stack.removeFirst();
                word += position.letter;
                if (isValidWord(word)) {
                    validWords.add(word);
                    if (isValidPrefix(word)) {
                        //IDK
                    }
                    else {
                        word = "";
                    }
                }
                process(position);
                for (Position neighbor : position.neighbors()) {
                    if (!isVisited(neighbor)) {
                        visit(neighbor);
                        stack.addFirst(neighbor);
                    }
                }
            }
        }
    }

}
