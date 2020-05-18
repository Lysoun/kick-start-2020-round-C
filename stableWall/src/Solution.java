import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int t = in.nextInt(); // Scanner has functions to read ints, longs, strings, chars, etc.
        for (int i = 1; i <= t; ++i) {
            int rowsNumber = in.nextInt();
            int columnsNumber = in.nextInt();
            char[][] wall = new char[rowsNumber][columnsNumber];
            for (int j = 0; j < rowsNumber; ++j) {
                wall[j] = in.next().toCharArray();
            }

            Map<Character, Polyomino> polyominos = new HashMap<>();
            for (int j = rowsNumber - 1; j >= 0; --j) {
                for (int k = 0; k < columnsNumber; ++k) {
                    final char letter = wall[j][k];
                    final Polyomino polyomino;
                    if (polyominos.containsKey(letter)) {
                        polyomino = polyominos.get(letter)
                                .addCoordinate(j, k);
                    } else {
                        polyominos.put(letter, polyomino = new Polyomino(j, k, letter));
                    }

                    if (j < rowsNumber - 1 && wall[j + 1][k] != letter) {
                        polyomino.addPillar(polyominos.get(wall[j + 1][k]));
                    }
                }
            }

            String result;

            try {
                result = buildWall(new ArrayList<>(polyominos.values()));
            } catch (CycleException cycleException) {
                result = "-1";
            }

            System.out.println("Case #" + i + ": " + result);
        }
    }

    private static String buildWall(List<Polyomino> polyominos) throws CycleException {
        Set<Character> builtPolyominos = new HashSet<>();

        int i = 0;
        StringBuilder result = new StringBuilder();
        while (i < polyominos.size() && builtPolyominos.size() < polyominos.size()) {
            result.append(buildPolyomino(polyominos.get(i), builtPolyominos, new HashSet<>()).toString());
            ++i;
        }

        return result.toString();
    }

    private static StringBuilder buildPolyomino(Polyomino polyomino, Set<Character> builtPolyominos, Set<Character> triedToBuildPolyominos) throws CycleException {
        if (builtPolyominos.contains(polyomino.getLetter())) {
            return new StringBuilder();
        }

        if (!triedToBuildPolyominos.add(polyomino.getLetter())) {
            throw new CycleException();
        }

        if (polyomino.canBuild(builtPolyominos)) {
            builtPolyominos.add(polyomino.getLetter());
            return new StringBuilder().append(polyomino.getLetter());
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (Polyomino pillar : polyomino.getPillars().values()) {
            stringBuilder.append(buildPolyomino(pillar, builtPolyominos, triedToBuildPolyominos));
        }

        builtPolyominos.add(polyomino.getLetter());
        return stringBuilder.append(polyomino.getLetter());
    }

    static class Coordinates {
        int row;
        int column;

        public Coordinates(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinates that = (Coordinates) o;
            return row == that.row &&
                    column == that.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }

    static class Polyomino {
        private final List<Coordinates> coordinates;
        private final Map<Character, Polyomino> pillars;
        private final char letter;

        public Polyomino(int row, int column, char letter) {
            this.coordinates = new LinkedList<>();
            this.pillars = new HashMap<>();
            this.letter = letter;

            this.addCoordinate(row, column);
        }

        public Polyomino addCoordinate(int row, int column) {
            coordinates.add(new Coordinates(row, column));
            return this;
        }

        public Polyomino addPillar(Polyomino polyomino) {
            pillars.put(polyomino.getLetter(), polyomino);
            return this;
        }

        public List<Coordinates> getCoordinates() {
            return coordinates;
        }

        public boolean canBuild(Set<Character> builtPolyominos) {
            return getPillars().size() == 0 || builtPolyominos.containsAll(getPillars().keySet());
        }

        public Map<Character, Polyomino> getPillars() {
            return pillars;
        }

        public char getLetter() {
            return letter;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Polyomino polyomino = (Polyomino) o;
            return getLetter() == polyomino.getLetter();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getLetter());
        }
    }

    static class CycleException extends Exception {

    }
}
