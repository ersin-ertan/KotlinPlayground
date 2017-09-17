import com.squareup.moshi.Json;

public class Medium {

    @Json(name = "free")
    private String free;
    @Json(name = "title")
    private String title;
    @Json(name = "thumbnail")
    private String thumbnail;
    @Json(name = "media_state")
    private String mediaState;
    @Json(name = "start")
    private String start;
    @Json(name = "has_mlbtv")
    private String hasMlbtv;
    @Json(name = "calendar_event_id")
    private String calendarEventId;
    @Json(name = "enhanced")
    private String enhanced;
    @Json(name = "type")
    private String type;
    @Json(name = "headline")
    private String headline;
    @Json(name = "content_id")
    private String contentId;
    @Json(name = "topic_id")
    private String topicId;

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getMediaState() {
        return mediaState;
    }

    public void setMediaState(String mediaState) {
        this.mediaState = mediaState;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getHasMlbtv() {
        return hasMlbtv;
    }

    public void setHasMlbtv(String hasMlbtv) {
        this.hasMlbtv = hasMlbtv;
    }

    public String getCalendarEventId() {
        return calendarEventId;
    }

    public void setCalendarEventId(String calendarEventId) {
        this.calendarEventId = calendarEventId;
    }

    public String getEnhanced() {
        return enhanced;
    }

    public void setEnhanced(String enhanced) {
        this.enhanced = enhanced;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

}