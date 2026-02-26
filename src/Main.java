import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Conways_Game {

    boolean standard = true;

    public char[][] cell;

    int columns;

    ArrayList<String> rows = new ArrayList<>();

    public void File_or_Input() {

        System.out.print("\nHello! Decide what input method you would prefer, please type either 's' for standard or 'f' for file: \n");

        Scanner sc = new Scanner(System.in);

        while (true) {

            String file = sc.nextLine();

            try {
                if (file.equals("s")) {
                    break;
                }
                if (file.equals("f")) {
                    this.standard = false;
                    break;
                }
                else {System.out.print("Please type either 's' or 'f': \n");}
            }
            catch (NumberFormatException e) {
                System.out.println("Try again");
            }

        }
    }

    public void importFile() {
        try {
            File file = new File("input.txt");
            Scanner sc = new Scanner(file);

            while (true) {
                String line = sc.nextLine();

                System.out.println(line);

                if (line.equals("done")) {
                    break;
                }

                String cleaned = line.replace(" ", "");

                for (char c : cleaned.toCharArray()) {
                    if (c != 'O' && c != '.') {
                        System.err.println("Error: Invalid character detected");
                        System.exit(1);
                    }
                }

                rows.add(cleaned);

            }

                if (rows.size() < 2) {
                    System.out.println("Error: Grid must be at least 2x2");
                    System.exit(1);
                }

                columns = rows.getFirst().length();

                if (columns < 2) {
                    System.out.println("Error: Grid must be at least 2x2");
                    System.exit(1);
                }

                for (String row : rows) {
                    if (row.length() != columns) {
                        System.out.println("\nError: Rows have inconsistent lengths.");
                        System.exit(1);
                    }
                }

                cell = new char[rows.size()][columns];

                for (int i = 0; i < rows.size(); i++) {
                    cell[i] = rows.get(i).toCharArray();
                }

                System.out.println("\nGrid successfully loaded.");

            sc.close();
        }
        catch (FileNotFoundException e) {
            System.err.println("File not found.");
        }
    }

    public void standardInput() {

        System.out.println("\nType the configuration you would like for the cells line by line with zero or one space in between each cell,\ndead cells = '.' and live cells = 'O' when you are finished type the word 'done'\n");

        Scanner sc = new Scanner(System.in);

        while (true) {

            String line = sc.nextLine();

            if (line.equals("done")) {
                break;
            }

            String cleaned = line.replace(" ", "");

            for (char c : cleaned.toCharArray()) {
                if (c != 'O' && c != '.') {
                    System.err.println("Error: Invalid character detected");
                    System.exit(1);
                }
            }

            rows.add(cleaned);
        }

        if (rows.size() < 2) {
            System.out.println("Error: Grid must be at least 2x2");
            System.exit(1);
        }

        columns = rows.getFirst().length();

        if (columns < 2) {
            System.out.println("Error: Grid must be at least 2x2");
            System.exit(1);
        }

        for (String row : rows) {
            if (row.length() != columns) {
                System.out.println("\nError: Rows have inconsistent lengths.");
                System.exit(1);
            }
        }

        cell = new char[rows.size()][columns];

        for (int i = 0; i < rows.size(); i++) {
            cell[i] = rows.get(i).toCharArray();
        }

        System.out.println("\nGrid successfully loaded.");
    }

    public void printGrid() {
        for (int i = 0; i < rows.size(); i++) {
            System.out.println();
            for (int j = 0; j < columns; j++) {
                System.out.print(cell[i][j]);
                System.out.print("  ");
            }
        }
        System.out.println();
    }

    public void timeShiftRules() {

        char[][] copy = new char[rows.size()][columns];

        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < columns; j++) {
                copy[i][j] = cell[i][j];
            }
        }

        int count = 0;

        // For center cells

        for (int i = 1; i < rows.size() - 1; i++) {
            for (int j = 1; j < columns - 1; j++) {
                if (copy[i - 1][j - 1] == 'O') {
                    count = count + 1;
                }
                if (copy[i - 1][j] == 'O') {
                    count = count + 1;
                }
                if (copy[i - 1][j + 1] == 'O') {
                    count = count + 1;
                }
                if (copy[i][j - 1] == 'O') {
                    count = count + 1;
                }
                if (copy[i][j + 1] == 'O') {
                    count = count + 1;
                }
                if (copy[i + 1][j - 1] == 'O') {
                    count = count + 1;
                }
                if (copy[i + 1][j] == 'O') {
                    count = count + 1;
                }
                if (copy[i + 1][j + 1] == 'O') {
                    count = count + 1;
                }
                if (count == 3) {
                    cell[i][j] = 'O';
                }
                if (count < 2) {
                    cell[i][j] = '.';
                }
                if (count > 3) {
                    cell[i][j] = '.';
                }
                count = 0;
            }
        }

        // For the top line wrap around

        for (int i = 1; i < columns - 1; i++) {
            if (copy[0][i - 1] == 'O') {
                count = count + 1;
            }
            if (copy[1][i - 1] == 'O') {
                count = count + 1;
            }
            if (copy[1][i] == 'O') {
                count = count + 1;
            }
            if (copy[1][i + 1] == 'O') {
                count = count + 1;
            }
            if (copy[0][i + 1] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 1][i - 1] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 1][i] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 1][i + 1] == 'O') {
                count = count + 1;
            }
            if (count == 3) {
                cell[0][i] = 'O';
            }
            if (count < 2) {
                cell[0][i] = '.';
            }
            if (count > 3) {
                cell[0][i] = '.';
            }
            count = 0;
        }

        // For bottom line wrap around

        for (int i = 1; i < columns - 1; i++) {
            if (copy[0][i - 1] == 'O') {
                count = count + 1;
            }
            if (copy[0][i] == 'O') {
                count = count + 1;
            }
            if (copy[0][i + 1] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 1][i - 1] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 1][i + 1] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 2][i - 1] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 2][i] == 'O') {
                count = count + 1;
            }
            if (copy[rows.size() - 2][i + 1] == 'O') {
                count = count + 1;
            }
            if (count == 3) {
                cell[rows.size() - 1][i] = 'O';
            }
            if (count < 2) {
                cell[rows.size() - 1][i] = '.';
            }
            if (count > 3) {
                cell[rows.size() - 1][i] = '.';
            }
            count = 0;
        }

        // For left line wrap around

        for (int i = 1; i < rows.size() - 1; i++) {
            if (copy[i - 1][columns - 1] == 'O') {
                count = count + 1;
            }
            if (copy[i][columns - 1] == 'O') {
                count = count + 1;
            }
            if (copy[i + 1][columns - 1] == 'O') {
                count = count + 1;
            }
            if (copy[i - 1][0] == 'O') {
                count = count + 1;
            }
            if (copy[i + 1][0] == 'O') {
                count = count + 1;
            }
            if (copy[i - 1][1] == 'O') {
                count = count + 1;
            }
            if (copy[i][1] == 'O') {
                count = count + 1;
            }
            if (copy[i + 1][1] == 'O') {
                count = count + 1;
            }
            if (count == 3) {
                cell[i][0] = 'O';
            }
            if (count < 2) {
                cell[i][0] = '.';
            }
            if (count > 3) {
                cell[i][0] = '.';
            }
            count = 0;
        }

        // For Right line wrap around

        for (int i = 1; i < rows.size() - 1; i++) {
            if (copy[i - 1][columns - 1] == 'O') {
                count = count + 1;
            }
            if (copy[i + 1][columns - 1] == 'O') {
                count = count + 1;
            }
            if (copy[i - 1][0] == 'O') {
                count = count + 1;
            }
            if (copy[i][0] == 'O') {
                count = count + 1;
            }
            if (copy[i + 1][0] == 'O') {
                count = count + 1;
            }
            if (copy[i - 1][columns - 2] == 'O') {
                count = count + 1;
            }
            if (copy[i][columns - 2] == 'O') {
                count = count + 1;
            }
            if (copy[i + 1][columns - 2] == 'O') {
                count = count + 1;
            }
            if (count == 3) {
                cell[i][columns - 1] = 'O';
            }
            if (count < 2) {
                cell[i][columns - 1] = '.';
            }
            if (count > 3) {
                cell[i][columns - 1] = '.';
            }
            count = 0;
        }

        // For top-left corner wrap around

        if (copy[rows.size() - 1][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][0] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][1] == 'O') {
            count = count + 1;
        }
        if (copy[0][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[0][1] == 'O') {
            count = count + 1;
        }
        if (copy[1][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[1][0] == 'O') {
            count = count + 1;
        }
        if (copy[1][1] == 'O') {
            count = count + 1;
        }
        if (count == 3) {
            cell[0][0] = 'O';
        }
        if (count < 2) {
            cell[0][0] = '.';
        }
        if (count > 3) {
            cell[0][0] = '.';
        }
        count = 0;

        // For top-right corner wrap around

        if (copy[rows.size() - 1][columns - 2] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][0] == 'O') {
            count = count + 1;
        }
        if (copy[0][columns - 2] == 'O') {
            count = count + 1;
        }
        if (copy[0][0] == 'O') {
            count = count + 1;
        }
        if (copy[1][columns - 2] == 'O') {
            count = count + 1;
        }
        if (copy[1][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[1][0] == 'O') {
            count = count + 1;
        }
        if (count == 3) {
            cell[0][columns - 1] = 'O';
        }
        if (count < 2) {
            cell[0][columns - 1] = '.';
        }
        if (count > 3) {
            cell[0][columns - 1] = '.';
        }
        count = 0;

        // For bottom-left corner wrap around

        if (copy[rows.size() - 2][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 2][0] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 2][1] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][1] == 'O') {
            count = count + 1;
        }
        if (copy[0][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[0][0] == 'O') {
            count = count + 1;
        }
        if (copy[0][1] == 'O') {
            count = count + 1;
        }
        if (count == 3) {
            cell[rows.size() - 1][0] = 'O';
        }
        if (count < 2) {
            cell[rows.size() - 1][0] = '.';
        }
        if (count > 3) {
            cell[rows.size() - 1][0] = '.';
        }
        count = 0;

        // For bottom-right corner wrap around

        if (copy[rows.size() - 2][columns - 2] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 2][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 2][0] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][columns - 2] == 'O') {
            count = count + 1;
        }
        if (copy[rows.size() - 1][0] == 'O') {
            count = count + 1;
        }
        if (copy[0][columns - 2] == 'O') {
            count = count + 1;
        }
        if (copy[0][columns - 1] == 'O') {
            count = count + 1;
        }
        if (copy[0][0] == 'O') {
            count = count + 1;
        }
        if (count == 3) {
            cell[rows.size() - 1][columns - 1] = 'O';
        }
        if (count < 2) {
            cell[rows.size() - 1][columns - 1] = '.';
        }
        if (count > 3) {
            cell[rows.size() - 1][columns - 1] = '.';
        }
        count = 0;
    }

    public void timeShift() {

        Scanner sc = new Scanner(System.in);

        int input;

        System.out.println("\nEnter a positive integer to iterate or 0 to terminate: ");

        while (true) {

            String line = sc.nextLine();

            try {
                input = (Integer.parseInt(line));
                if (input >= 0) {
                    break;
                }
                else {
                    System.out.println("Number must be greater than 0: ");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Enter a positive integer: ");
            }
        }

        while (input != 0) {
            for (int i = 0; i < input; i++) {
                timeShiftRules();
            }
            printGrid();

            System.out.println("\nEnter a positive integer to jump generations or 0 to terminate: ");

            while (true) {

                String line = sc.nextLine();

                try {
                    input = (Integer.parseInt(line));
                    if (input >= 0) {
                        break;
                    }
                    else {
                        System.out.println("Number must be greater than 0: ");
                    }
                } catch (NumberFormatException e) {
                    System.out.print("\nInvalid input, Enter a positive integer: ");
                }
            }
        }
    }

    void main(String[] args) {
        File_or_Input();
        if (!standard) {
            importFile();
        }
        else {standardInput();}
        System.out.println("\nCurrent Cell Layout:");
        printGrid();
        timeShift();
    }
}