import java.util.ArrayList;
import java.util.Scanner;

class Universe {

    public char[][] cell;
    int columns;
    ArrayList<String> rows = new ArrayList<>();

    // --- Added Instance Variables ---
    final char cLive;
    final char cDead;

    public Universe(char cLive, char cDead) {
        this.cLive = cLive;
        this.cDead = cDead;
    }

    public void readFile(Scanner sc) {
        int maxLen = 0; // Track the maximum row length

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
//            System.out.println(line);

            for (char c : line.toCharArray()) {
                if (c != this.cLive && c != this.cDead) {
                    System.err.println("Error: Invalid character detected: '" + c + "'");
                    System.exit(1);
                }
            }

            maxLen = Math.max(maxLen, line.length());
            rows.add(line);
        }

        errorCheck();

        // Pad shorter rows with cDead to restore stripped trailing spaces
        padRows(maxLen);

        // Safely set columns now that rows are normalized
        columns = rows.isEmpty() ? 0 : rows.getFirst().length();

        checkColumns();
        buildGrid();

        System.out.println("\nGrid successfully loaded.");
        sc.close();
    }

    public void standardInput() {

        System.out.println("\nType the configuration you would like for the cells line by line with zero spaces in\nbetween each cell, dead cells = '" + this.cDead + "' and live cells = '" + this.cLive + "' when you are finished type the word 'done'\n");

        Scanner sc = new Scanner(System.in);
        int maxLen = 0;

        while (true) {
            String line = sc.nextLine();

            if (line.equals("done")) {
                break;
            }

            for (char c : line.toCharArray()) {
                if (c != this.cLive && c != this.cDead) {
                    System.err.println("Error: Invalid character detected: '" + c + "'");
                    System.exit(1);
                }
            }

            maxLen = Math.max(maxLen, line.length());
            rows.add(line);
        }

        errorCheck();

        padRows(maxLen);

        columns = rows.isEmpty() ? 0 : rows.getFirst().length();

        checkColumns();
        buildGrid();

        System.out.println("\nGrid successfully loaded.");
    }

    // --- Helper Method to restore dropped trailing spaces ---
    private void padRows(int maxLen) {
        for (int i = 0; i < rows.size(); i++) {
            StringBuilder sb = new StringBuilder(rows.get(i));
            while (sb.length() < maxLen) {
                sb.append(this.cDead);
            }
            rows.set(i, sb.toString());
        }
    }

    // --- Helper Method to populate the 2D array ---
    private void buildGrid() {
        cell = new char[rows.size()][columns];
        for (int i = 0; i < rows.size(); i++) {
            cell[i] = rows.get(i).toCharArray();
        }
    }

    public void errorCheck() {

        if (rows.size() < 2) {
            System.err.println("Error: Grid must be at least 2x2");
            System.exit(1);
        }

        int expectedLength = rows.getFirst().length();

        for (int i = 1; i < rows.size(); i++) {

            if (this.cDead == ' ' || this.cLive == ' ') {
                break;
            }
            else if (rows.get(i).length() != expectedLength) {

                System.err.println("Error: Rows have inconsistent lengths.");

                System.exit(1);
            }
        }
    }

    public void checkColumns() {
        if (columns < 2) {
            System.err.println("Error: Grid must be at least 2x2");
            System.exit(1);
        }
    }

    public void printGrid() {
        for (int i = 0; i < rows.size(); i++) {
            System.out.println();
            for (int j = 0; j < columns; j++) {
                System.out.print(cell[i][j]);
            }
        }
        System.out.println();
    }
}