import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class Conways_Game {

    static void main(String[] args) throws FileNotFoundException {

        Scanner sc;

        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
            Universe.readFile(sc);
        }

        else {
            Universe.standardInput();
        }

        System.out.println("\nCurrent Cell Layout:");
        CellGeneration.timeShiftRules();
        Universe.printGrid();
    }
}
