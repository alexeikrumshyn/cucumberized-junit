package rummikub;

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
}
