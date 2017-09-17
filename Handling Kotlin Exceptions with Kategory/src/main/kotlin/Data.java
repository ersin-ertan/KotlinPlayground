import com.squareup.moshi.Json;

public class Data {

    @Json(name = "games")
    private Games games;

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

}
