import com.squareup.moshi.Json;

import java.util.List;

public class GameMedia {

    @Json(name = "media")
    private List<Medium> media = null;

    public List<Medium> getMedia() {
        return media;
    }

    public void setMedia(List<Medium> media) {
        this.media = media;
    }

}
