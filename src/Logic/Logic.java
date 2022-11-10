package Logic;

public class Logic {
    Client client;

    public Logic() {
        this.client = new Client();
    }

    public Logic(Client client) {
        this.client = client;
    }

    public boolean authenticate(String testUsername, String testPassword) {
        return client.authenticate(testUsername, testPassword);
    }
}
