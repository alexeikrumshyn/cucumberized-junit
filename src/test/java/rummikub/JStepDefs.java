package rummikub;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JStepDefs {

    LocalTestServer srv;

    @JStep(given = "Test Server is started")
    public void test_server_is_started() {
        srv = new LocalTestServer();
    }

    @JStep(given = "Player {int} hand starts with {string}")
    public void player_hand_starts_with(int pNum, String tiles) {
        String[] tilesStr = tiles.split(" ");
        for (String str: tilesStr) {
            srv.players[pNum-1].drawTile(str);
        }
    }

    @JStep(given = "Player {int} has played initial points")
    public void player_has_played_initial_points(int pNum) {
        srv.players[pNum-1].hasInitialPoints = true;
    }

    @JStep(when = "Player {int} draws {string}")
    public void player_draws(int pNum, String tile) {
        srv.players[pNum-1].drawTile(tile);
    }

    @JStep(when = "Player {int} chooses to draw")
    public void player_chooses_to_draw(int pNum) {
        String expected = "Select an action: \n" +
                "(1) Play Meld on Table\n" +
                "(2) Draw Tile and End Turn\n" +
                "(3) End Turn\n";
        assertEquals(expected, srv.players[pNum-1].getOptions());

        ByteArrayInputStream in = new ByteArrayInputStream(("2\n").getBytes());
        System.setIn(in);
        srv.players[pNum-1].getAction();
        srv.game = srv.players[pNum-1].game;
    }

    @JStep(when = "Player {int} has to draw")
    public void player_has_to_draw(int pNum) {
        String expected = "Select an action: \n" +
                "(1) Play Meld on Table\n" +
                "(2) Draw Tile and End Turn\n";
        assertEquals(expected, srv.players[pNum-1].getOptions());

        ByteArrayInputStream in = new ByteArrayInputStream(("2\n").getBytes());
        System.setIn(in);
        srv.players[pNum-1].getAction();
        srv.game = srv.players[pNum-1].game;
    }

    @JStep(when = "Player {int} plays {string}")
    public void player_plays(int pNum, String tiles) {
        String[] meldsPlayed = tiles.split(",");
        String inString = "";
        for (String meld : meldsPlayed) {
            inString += "1\n" + meld + "\n";
        }
        inString += "3\n";
        ByteArrayInputStream in = new ByteArrayInputStream((inString).getBytes());
        System.setIn(in);
        srv.players[pNum-1].getAction();
        srv.game = srv.players[pNum-1].game;
    }

    @JStep(then = "table contains {string}")
    public void table_contains(String expected) {
        assertEquals(expected, srv.game.getTable().replace("|", "").replace("\n",""));
    }

    @JStep(then = "Player {int} hand contains {string}")
    public void player_hand_contains(int pNum, String expected) {

        String actual = srv.players[pNum-1].getHand();

        if (expected.equals("")) {
            assertEquals(expected,actual);
            return;
        }

        String[] expectedTiles = expected.split(" ");
        String actualHand = actual.replace("|", "");

        System.out.println("Expected: "+expected);
        System.out.println("Actual:   "+actualHand);
        assertEquals(expectedTiles.length, actualHand.split(" ").length);
        for (int i = 0; i < expectedTiles.length; ++i) {
            if (!expectedTiles[i].equals("?"))
                assertTrue(actualHand.contains(expectedTiles[i]));
        }
    }

    @JStep(then = "Player {int} wins")
    public void player_wins(int pNum) {
        assertEquals(srv.players[pNum-1].name, srv.game.getWinner());
    }

    @JStep(then = "Player {int} score is {int}")
    public void player_score_is(int pNum, int score) {
        assertEquals(score, srv.players[pNum-1].score);
    }

}
