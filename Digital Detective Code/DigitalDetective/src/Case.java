import java.util.ArrayList;

public class Case {

    String caseName;

    ArrayList<Clue> clues;

    public Case(String caseName) {

        this.caseName = caseName;

        clues = new ArrayList<>();

        createClues();
    }

    private void createClues() {

        clues.add(new Clue(
                "Broken Glass",
                250,
                180,
                20
        ));

        clues.add(new Clue(
                "Fingerprint",
                700,
                200,
                30
        ));

        clues.add(new Clue(
                "Red Thread",
                500,
                400,
                25
        ));

        clues.add(new Clue(
                "Footprint",
                200,
                500,
                20
        ));

        clues.add(new Clue(
                "Hidden Letter",
                800,
                500,
                50
        ));
    }

    public int getFoundClues() {

        int count = 0;

        for (Clue clue : clues) {

            if (clue.found) {
                count++;
            }
        }

        return count;
    }

    public int getTotalClues() {
        return clues.size();
    }

    public boolean isComplete() {
        return getFoundClues() == getTotalClues();
    }

    public void reset() {

        for (Clue clue : clues) {
            clue.found = false;
        }
    }
}