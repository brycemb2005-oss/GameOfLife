import java.util.ArrayList;

public class CellGeneration {

    final Universe passUniverse;

    public CellGeneration(Universe passUniverse) {
        this.passUniverse = passUniverse;
    }

    public void timeShiftRules(int[] birth, int[] survive, char cLive, char cDead) {

        char[][] copy = new char[passUniverse.rows.size()][passUniverse.columns];

        for (int i = 0; i < passUniverse.rows.size(); i++) {
            for (int j = 0; j < passUniverse.columns; j++) {
                copy[i][j] = passUniverse.cell[i][j];
            }
        }

        int count = 0;

        for (int i = 0; i < passUniverse.rows.size(); i++) {
            for (int j = 0; j < passUniverse.columns; j++) {

                int up = i - 1;
                int down = i + 1;
                int left = j - 1;
                int right = j + 1;

                if (up == -1) {
                    up = passUniverse.rows.size() - 1;
                }

                if (down == passUniverse.rows.size()) {
                    down = 0;
                }

                if (left == -1) {
                    left = passUniverse.columns - 1;
                }

                if (right == passUniverse.columns) {
                    right = 0;
                }

                if (copy[up][left] == cLive) {
                    count = count + 1;
                }
                if (copy[up][j] == cLive) {
                    count = count + 1;
                }
                if (copy[up][right] == cLive) {
                    count = count + 1;
                }
                if (copy[i][left] == cLive) {
                    count = count + 1;
                }
                if (copy[i][right] == cLive) {
                    count = count + 1;
                }
                if (copy[down][left] == cLive) {
                    count = count + 1;
                }
                if (copy[down][j] == cLive) {
                    count = count + 1;
                }
                if (copy[down][right] == cLive) {
                    count = count + 1;
                }

                for (int bnumber : birth) {
                    if (count == bnumber) {
                        passUniverse.cell[i][j] = cLive;
                        break;
                    }
                }

                ArrayList<Integer> values = new ArrayList<>();

                for (int amount = 0; amount < 9; amount++) {
                    values.add(amount);
                }

                for (int snumber : survive) {
                    values.remove(Integer.valueOf(snumber));
                }

                for (int val : values) {
                    if (count == val) {
                        passUniverse.cell[i][j] = cDead;
                        break;
                    }
                }
                count = 0;
            }
        }
    }
}


