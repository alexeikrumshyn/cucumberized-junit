package rummikub;

public class JStepDefs {

    LocalTestServer srv;

    @JGiven(cond = "Test Server is started")
    public void test_server_is_started() {
        srv = new LocalTestServer();
    }

    @JGiven(cond = "Player {int} hand starts with {string}")
    public void player_hand_starts_with(int pNum, String tiles) {
        String[] tilesStr = tiles.split(" ");
        for (String str: tilesStr) {
            srv.players[pNum-1].drawTile(str);
        }
    }
}
