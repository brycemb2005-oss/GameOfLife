import java.util.Scanner;

public class Parser {

    // Variables
    private int[] birth;
    private int[] survive;
    private char cLive;
    private char cDead;
    private boolean infinite;
    private String fileName = null;
    boolean seenG = false;
    boolean seenL = false;
    boolean seenD = false;
    boolean seenI = false;
    boolean seenFile = false;

    public Parser() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter command options (e.g., java GameOfLife -g B3/S23 -l '*' -d \" \" -i):");

        String input = scanner.nextLine();

        if (!input.startsWith("java GameOfLife")) {
            throw new IllegalArgumentException("Please begin any arguments with 'java GameOfLife'");
        }

        String[] tokens = input.split("\\s+");

        // Default Values
        this.birth = new int[]{3};
        this.survive = new int[]{2, 3};
        this.cLive = 'O';
        this.cDead = '.';
        this.infinite = false;

        // Infinite mode
        int iCount = 0;

        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i].equals("-i")) {

                iCount++;

                if (iCount > 1) {
                    System.err.println("Error: -i option provided multiple times");
                    System.exit(1);
                }

                seenI = true;
                this.infinite = true;
            }
        }

        // Rules for Birth and Survival
        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i].equals("-g")) {

                if (seenG) {
                    System.err.println("Error: -g option provided multiple times");
                    System.exit(1);
                }

                seenG = true;

                if (i + 1 < tokens.length) {
                    String rule = tokens[i + 1]; // B3/S23

                    int slashIndex = rule.indexOf("/");

                    if (slashIndex != -1) {
                        String bString = rule.substring(1, slashIndex); // skip 'B'
                        String sString = rule.substring(slashIndex + 2); // skip 'S'

                        this.birth = parseAndRules(bString);
                        this.survive = parseAndRules(sString);
                    }
                }
            }
        }

        // live character
        int lCount = 0;

        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i].equals("-l")) {

                lCount++;

                if (lCount > 1) {
                    System.err.println("Error: -l option provided multiple times");
                    System.exit(1);
                }

                seenL = true;

                if (i + 1 < tokens.length) {

                    String value = tokens[i + 1];

                    // rebuild full value if quotes got split
                    if (value.equals("\"") && i + 2 < tokens.length) {
                        value = " " ; // assume this pattern: -d " "
                    }

                    // strip quotes if they survived
                    value = value.replace("\"", "").replace("'", "");

                    // FINAL RULE
                    if (value.length() == 0 || value.equals(" ")) {
                        this.cLive = ' ';
                    } else {
                        this.cLive = value.charAt(0);
                    }
                }
            }
        }

        // dead character
        int dCount = 0;

        for (int i = 0; i < tokens.length; i++) {

            if (tokens[i].equals("-d")) {

                dCount++;

                if (dCount > 1) {
                    System.err.println("Error: -d option provided multiple times");
                    System.exit(1);
                }

                seenD = true;

                if (i + 1 < tokens.length) {

                    String value = tokens[i + 1];

                    // rebuild full value if quotes got split
                    if (value.equals("\"") && i + 2 < tokens.length) {
                        value = " " ; // assume this pattern: -d " "
                    }

                    // strip quotes if they survived
                    value = value.replace("\"", "").replace("'", "");

                    // FINAL RULE
                    if (value.length() == 0 || value.equals(" ")) {
                        this.cDead = ' ';
                    } else {
                        this.cDead = value.charAt(0);
                    }
                }
            }
        }

//        System.out.println("DEBUG cDead raw = [" + cDead + "] code=" + (int)cDead);

        int fileCount = 0;

        // Extract file candidates from tokens
        for (int i = 0; i < tokens.length; i++) {

            String token = tokens[i];

            // skip known flags and values
            if (token.equals("-i") ||
                    token.equals("-g") ||
                    token.equals("-l") ||
                    token.equals("-d") ||
                    token.equals("java") ||
                    token.equals("GameOfLife")) {
                continue;
            }

            // skip rule string or quoted chars
            boolean isRuleString = token.startsWith("B") && token.contains("/");
            boolean isQuoted = token.startsWith("'") || token.startsWith("\"");

            if (isRuleString || isQuoted) {
                continue;
            }

            // treat remaining tokens as potential file names
            fileCount++;

            if (fileCount > 1) {
                System.err.println("Error: More than one file argument provided");
                System.exit(1);
            }

            this.fileName = token;
            seenFile = true;
        }

        // --- Print parsed results to verify ---
//        System.out.println("\nBirth rules (B)   : " + Arrays.toString(this.birth));
//        System.out.println("Survive rules (S) : " + Arrays.toString(this.survive));
//        System.out.println("Live character (-l): [" + this.cLive + "]");
//        System.out.println("Dead character (-d): [" + this.cDead + "]");
//        System.out.println("Infinite mode (-i) : " + this.infinite);
    }

    // Getters

    public int[] getBirth() {
        return this.birth;
    }

    public int[] getSurvive() {
        return this.survive;
    }

    public char getcLive() {
        return this.cLive;
    }

    public char getcDead() {
        return this.cDead;
    }

    public boolean getInfinity() {
        return this.infinite;
    }

    public String getFile() {return this.fileName; }



    // Parses a string of digits into an int array and validates that they are increasing sequentially
    public static int[] parseAndRules(String digits) {
        int[] result = new int[digits.length()];
        int previousDigit = -1;

        for (int i = 0; i < digits.length(); i++) {
            int currentDigit = Character.getNumericValue(digits.charAt(i));
            if (currentDigit <= previousDigit) {
                throw new IllegalArgumentException("Digits must be strictly ascending. Found invalid sequence in: " + digits);
            }

            result[i] = currentDigit;
            previousDigit = currentDigit;
        }

        return result;
    }
}