package rummikub;

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

    @JStep(when = "Player {int} draws {string}")
    public void player_draws(int pNum, String tile) {
        srv.players[pNum-1].drawTile(tile);
    }

    @JStep(then = "Player {int} hand contains {string}")
    public void player_hand_contains(int pNum, String expected) {

        String actual = srv.players[pNum-1].getHand();

        if (expected.equals("")) {
            assertEquals(expected,actual);
            return;
        }

        String[] expectedTiles = expected.substring(0,expected.length()-1).split(" ");
        String actualHand = actual.substring(0,actual.length()-1).replace("|", "");

        System.out.println("Expected: "+expected);
        System.out.println("Actual:   "+actualHand);
        assertEquals(expectedTiles.length, actualHand.split(" ").length);
        for (int i = 0; i < expectedTiles.length; ++i) {
            if (!expectedTiles[i].equals("?"))
                assertTrue(actualHand.contains(expectedTiles[i]));
        }
    }
}
