import com.squareup.moshi.Json;

import java.util.List;

public class Games {

    @Json(name = "last_modified")
    private String lastModified;
    @Json(name = "game")
    private List<Game> game = null;
    @Json(name = "date")
    private String date;

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List<Game> getGame() {
        return game;
    }

    public void setGame(List<Game> game) {
        this.game = game;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
