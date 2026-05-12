import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import sun.misc.Signal;

public class Conways_Game {

    static void main(String[] args) throws FileNotFoundException {

        Parser myParser = new Parser();

        Universe myUniverse = new Universe(myParser.getcLive(), myParser.getcDead());

        Scanner sc;

        // Fetch the parsed file name from the Parser
        String fileName = myParser.getFile();

        // Input source selection (file vs stdin)
        if (fileName != null) {
            File file = new File(fileName);
            sc = new Scanner(file);
            myUniverse.readFile(sc);
        } else {
            myUniverse.standardInput();
        }
        
        System.out.println("\nCurrent Cell Layout:");
        myUniverse.printGrid();

        CellGeneration generation = new CellGeneration(myUniverse);

        if (myParser.getInfinity()) {

            System.out.println("\nRunning in Infinite Mode (Press Ctrl+C to stop)");

            final boolean[] running = {true};

            Signal.handle(new Signal("INT"), signal -> {
                running[0] = false;
            });

            while (running[0]) {

                generation.timeShiftRules(
                        myParser.getBirth(),
                        myParser.getSurvive(),
                        myParser.getcLive(),
                        myParser.getcDead()
                );

                myUniverse.printGrid();

                System.out.println();
                for (int k = 0; k < myUniverse.columns; k++) {
                    System.out.print("#");
                }
                System.out.println();

                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    System.out.println("Simulation interrupted.");
                    break;
                }
            }
        } else {
            System.out.println("\nSingle Generation");

            generation.timeShiftRules(
                    myParser.getBirth(),
                    myParser.getSurvive(),
                    myParser.getcLive(),
                    myParser.getcDead()
            );

            myUniverse.printGrid();
        }
    }
}
