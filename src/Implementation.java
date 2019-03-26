import java.util.List;
import java.util.SortedSet;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TreeSet;

public class Implementation implements WordSearchGame {

    TreeSet<String> lexicon = new TreeSet<String>();
    Boolean lexiconLoaded = false;
    String[] gameBoard = new String[0];
    int n = 0;

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
        gameBoard = letterArray;
        n = (int) Math.sqrt(letterArray.length);
    }

    @Override
    public String getBoard() {
        String output = "";
        for (String letter: gameBoard) {
            output += letter;
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
    @Override
    public SortedSet<String> getAllValidWords(int minimumWordLength) {
        if (minimumWordLength < 1) {
            throw new IllegalArgumentException("Minimum word length is too low");
        }
        else if (lexiconLoaded == false) {
            throw new IllegalStateException("Lexicon has not been loaded");
        }
        else {
            TreeSet<String> output = new TreeSet<String>();
            for (String word : lexicon) {
                if (word.length() >= minimumWordLength) {
                    output.add(word);
                }
            }
            return output;
        }
    }

    @Override
    public int getScoreForWords(SortedSet<String> words, int minimumWordLength) {
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
        return null;
    }
}
