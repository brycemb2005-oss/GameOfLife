public class CellGeneration {

    public static void timeShiftRules() {

        char[][] copy = new char[Universe.rows.size()][Universe.columns];

        for (int i = 0; i < Universe.rows.size(); i++) {
            for (int j = 0; j < Universe.columns; j++) {
                copy[i][j] = Universe.cell[i][j];
            }
        }

        int count = 0;

        for (int i = 0; i < Universe.rows.size(); i++) {
            for (int j = 0; j < Universe.columns; j++) {

                int up = i - 1;
                int down = i + 1;
                int left = j - 1;
                int right = j + 1;

                if (up == -1) {
                    up = Universe.rows.size() - 1;
                }

                if (down == Universe.rows.size()) {
                    down = 0;
                }

                if (left == -1) {
                    left = Universe.columns - 1;
                }

                if (right == Universe.columns) {
                    right = 0;
                }

                if (copy[up][left] == 'O') {
                    count = count + 1;
                }
                if (copy[up][j] == 'O') {
                    count = count + 1;
                }
                if (copy[up][right] == 'O') {
                    count = count + 1;
                }
                if (copy[i][left] == 'O') {
                    count = count + 1;
                }
                if (copy[i][right] == 'O') {
                    count = count + 1;
                }
                if (copy[down][left] == 'O') {
                    count = count + 1;
                }
                if (copy[down][j] == 'O') {
                    count = count + 1;
                }
                if (copy[down][right] == 'O') {
                    count = count + 1;
                }
                if (count == 3) {
                    Universe.cell[i][j] = 'O';
                }
                if (count < 2) {
                    Universe.cell[i][j] = '.';
                }
                if (count > 3) {
                    Universe.cell[i][j] = '.';
                }
                count = 0;
            }
        }
    }
}
