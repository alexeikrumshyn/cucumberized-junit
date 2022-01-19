package rummikub;

public class JStepDefs {

    LocalTestServer srv;

    @JGiven(cond = "Test Server is started")
    public void test_server_is_started() {
        srv = new LocalTestServer();
    }
}
