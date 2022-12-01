import java.util.Scanner;

public class Hangman {

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        // 1. Print welcome message.
        System.out.println("\nLet's play a game of Hangman!\n");

        System.out.println("We are guessing an animal.\n");

        // 2. Set required variables for game to start:
        int countMisses = 0;
        String gameWord = randomWord();
        char[] gameWordArray = gameWord.toCharArray();
        String hits = " ";
        String misses = "";

        // 3. Game sequence for each turn:
        // a. Print the gallows
        // b. - Print an equivalent number of '_' (placeholder) for each char in the random word, OR
        //    - Update placeholders for each letter guessed correctly.
        // c. Print the missed characters for each wrong guess for player to reference each turn.
        // d. Check for hits and misses
        // e. Check for win condition: if the word is complete, OR lose condition: if Hangman is reach after 6 wrong guesses.

        while(true) {
            printGallows(countMisses);
            printPlaceholders(hits, gameWordArray);

            System.out.println("\nMisses: " + misses);

            if (hits.length() == 1 && countMisses == 0) {
                System.out.print("\nGuess your first letter: ");
            } else {
                System.out.print("\nGuess your next letter: ");
            }

            char guess = scan.next().charAt(0);

            System.out.print("\n");

            boolean checkCorrect = checkGuess(guess, gameWord);

            if (!checkCorrect) {
                misses += guess;
                countMisses++;
            } else if (checkCorrect) {
                hits += guess;
            }

            if (countMisses == gallows.length - 1) {
                printGallows(gallows.length - 1);
                System.out.println("RIP! The word was: " + gameWord + ".");
                System.out.println("Better luck next time!\n");
                break;
                
            } else if (checkWin(hits, gameWord)) {
                printPlaceholders(hits, gameWordArray);
                System.out.println("\nGOOD WORK! The word is: " + gameWord + ".");
                System.out.println("You have guessed it correctly!\n");
                break;
            }

        }

        scan.close();
    }

    /*  ==============
     *  Functions List
     *  ==============
     */

    /**
     * Function name: printGallows
     * 
     * @param countMisses (int)
     * 
     * This function basically prints the gallows at each 'stage' based on the current number of wrong guesses by player.
     * 
     */

    public static void printGallows(int countMisses) {

        System.out.println(gallows[countMisses]);
    }

    /**
     * Function name: randomWord
     * 
     * @return gameWord (string)
     * 
     * - Generate a random number using the length of array in Words[].
     * - Use the random number to return a word from the Words[] array.
     * 
     */

    public static String randomWord(){

        int randomWordIndex = (int)(Math.random() * words.length);
        String gameWord = words[randomWordIndex];

        return gameWord;
    }

    /**
     * Function name: printPlaceholders
     * 
     * @param misses (String)
     * @param gameWordArray (char[])
     * 
     * Print a set of '_' based on the number of characters in gameWord.
     * 
     */

    public static void printPlaceholders(String hits, char[]gameWordArray) {

        System.out.print("Word: ");

        char[] printArray = new char[gameWordArray.length];
        char[] hitsArray = hits.toCharArray();

        for (int i = 0; i < gameWordArray.length; i++) {
            for (int j = 0; j < hitsArray.length; j++) {
                if (gameWordArray[i] != hitsArray[j]) {
                    printArray[i] = '_';
                }
            }
        }

        for (int i = 0; i < gameWordArray.length; i++) {
            for (int j = 0; j < hitsArray.length; j++) {
                if (gameWordArray[i] == hitsArray[j]) {
                    printArray[i] = hitsArray[j];
                }
            }
        }

        for (int k = 0; k < printArray.length; k++) {
            System.out.print(printArray[k] + " ");
        }
 
        System.out.print("\n");
    }

    /**
     * Function name: checkGuess
     * 
     * @param playerGuess (char)
     * @param gameWord (String)
     * @return boolean 
     * 
     * Takes a char an returns a true if the user guessed a letter from the word correctly.
     * 
     */

    public static boolean checkGuess(char playerGuess, String gameWord) {

        String letter = Character.toString(playerGuess);
        return gameWord.contains(letter);
    }

    /**
     * Function name: checkWin
     * 
     * @param hits (String)
     * @param gameWord (String)
     * @return (boolean)
     * 
     * Function to check if win condition is reached and returns a boolean.
     * 
     */
    
     public static boolean checkWin(String hits, String gameWord) {

        String[] gameWordArray = gameWord.split("");

        for (int j = 0; j < gameWordArray.length; j++) {
            if (!hits.contains(gameWordArray[j])) {
                return false;
            }
        }

        return true;
     }
}





