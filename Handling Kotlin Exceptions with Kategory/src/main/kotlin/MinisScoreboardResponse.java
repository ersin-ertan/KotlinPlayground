import com.squareup.moshi.Json;

public class MinisScoreboardResponse {

    @Json(name = "subject")
    private String subject;
    @Json(name = "copyright")
    private String copyright;
    @Json(name = "data")
    private Data data;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}