import java.util.ArrayList;
import java.util.Scanner;

    class Universe {

        boolean standard = true;

        public static char[][] cell;

        static int columns;

        static ArrayList<String> rows = new ArrayList<>();

        public static void readFile(Scanner sc) {

            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                System.out.println(line);

                String cleaned = line.replace(" ", "");

                for (char c : cleaned.toCharArray()) {
                    if (c != 'O' && c != '.') {
                        System.err.println("Error: Invalid character detected");
                        System.exit(1);
                    }
                }

                rows.add(cleaned);

            }

            columns = rows.getFirst().length();

            errorCheck();

            cell = new char[rows.size()][columns];

            for (int i = 0; i < rows.size(); i++) {
                cell[i] = rows.get(i).toCharArray();
            }

            System.out.println("\nGrid successfully loaded.");

            sc.close();
        }

        public static void standardInput() {

            System.out.println("\nType the configuration you would like for the cells line by line with zero or one space in\nbetween each cell,dead cells = '.' and live cells = 'O' when you are finished type the word 'done'\n");

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

            columns = rows.getFirst().length();

            errorCheck();

            cell = new char[rows.size()][columns];

            for (int i = 0; i < rows.size(); i++) {
                cell[i] = rows.get(i).toCharArray();
            }

            System.out.println("\nGrid successfully loaded.");
        }

        public static void errorCheck() {

            if (rows.size() < 2) {
                System.out.println("Error: Grid must be at least 2x2");
                System.exit(1);
            }

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
        }

        public static void printGrid() {
            for (int i = 0; i < rows.size(); i++) {
                System.out.println();
                for (int j = 0; j < columns; j++) {
                    System.out.print(cell[i][j]);
                    System.out.print("");
                }
            }
            System.out.println();
        }
    }