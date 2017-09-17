import com.squareup.moshi.Json;

public class Game {

    @Json(name = "home_team_runs")
    private String homeTeamRuns;
    @Json(name = "preview_link")
    private String previewLink;
    @Json(name = "game_type")
    private String gameType;
    @Json(name = "double_header_sw")
    private String doubleHeaderSw;
    @Json(name = "location")
    private String location;
    @Json(name = "away_time")
    private String awayTime;
    @Json(name = "away_preview_link")
    private String awayPreviewLink;
    @Json(name = "away_audio_link")
    private String awayAudioLink;
    @Json(name = "time")
    private String time;
    @Json(name = "top_inning")
    private String topInning;
    @Json(name = "home_time")
    private String homeTime;
    @Json(name = "home_team_sb")
    private String homeTeamSb;
    @Json(name = "home_team_name")
    private String homeTeamName;
    @Json(name = "away_team_hr")
    private String awayTeamHr;
    @Json(name = "ind")
    private String ind;
    @Json(name = "original_date")
    private String originalDate;
    @Json(name = "home_team_city")
    private String homeTeamCity;
    @Json(name = "venue_id")
    private String venueId;
    @Json(name = "home_team_so")
    private String homeTeamSo;
    @Json(name = "gameday_sw")
    private String gamedaySw;
    @Json(name = "away_win")
    private String awayWin;
    @Json(name = "home_games_back_wildcard")
    private String homeGamesBackWildcard;
    @Json(name = "away_team_id")
    private String awayTeamId;
    @Json(name = "tz_hm_lg_gen")
    private String tzHmLgGen;
    @Json(name = "status")
    private String status;
    @Json(name = "home_loss")
    private String homeLoss;
    @Json(name = "home_games_back")
    private String homeGamesBack;
    @Json(name = "home_code")
    private String homeCode;
    @Json(name = "away_sport_code")
    private String awaySportCode;
    @Json(name = "home_team_hr")
    private String homeTeamHr;
    @Json(name = "home_win")
    private String homeWin;
    @Json(name = "time_hm_lg")
    private String timeHmLg;
    @Json(name = "away_name_abbrev")
    private String awayNameAbbrev;
    @Json(name = "league")
    private String league;
    @Json(name = "time_zone_aw_lg")
    private String timeZoneAwLg;
    @Json(name = "away_games_back")
    private String awayGamesBack;
    @Json(name = "home_file_code")
    private String homeFileCode;
    @Json(name = "game_data_directory")
    private String gameDataDirectory;
    @Json(name = "time_zone")
    private String timeZone;
    @Json(name = "away_league_id")
    private String awayLeagueId;
    @Json(name = "home_team_id")
    private String homeTeamId;
    @Json(name = "time_aw_lg")
    private String timeAwLg;
    @Json(name = "away_team_city")
    private String awayTeamCity;
    @Json(name = "day")
    private String day;
    @Json(name = "tbd_flag")
    private String tbdFlag;
    @Json(name = "tz_aw_lg_gen")
    private String tzAwLgGen;
    @Json(name = "away_code")
    private String awayCode;
    @Json(name = "game_media")
    private GameMedia gameMedia;
    @Json(name = "game_nbr")
    private String gameNbr;
    @Json(name = "time_date_aw_lg")
    private String timeDateAwLg;
    @Json(name = "away_team_sb")
    private String awayTeamSb;
    @Json(name = "away_games_back_wildcard")
    private String awayGamesBackWildcard;
    @Json(name = "scheduled_innings")
    private String scheduledInnings;
    @Json(name = "venue_w_chan_loc")
    private String venueWChanLoc;
    @Json(name = "away_team_so")
    private String awayTeamSo;
    @Json(name = "away_team_name")
    private String awayTeamName;
    @Json(name = "gameday_link")
    private String gamedayLink;
    @Json(name = "outs")
    private String outs;
    @Json(name = "time_date_hm_lg")
    private String timeDateHmLg;
    @Json(name = "id")
    private String id;
    @Json(name = "home_name_abbrev")
    private String homeNameAbbrev;
    @Json(name = "wrapup_link")
    private String wrapupLink;
    @Json(name = "tiebreaker_sw")
    private String tiebreakerSw;
    @Json(name = "ampm")
    private String ampm;
    @Json(name = "home_division")
    private String homeDivision;
    @Json(name = "home_time_zone")
    private String homeTimeZone;
    @Json(name = "tv_station")
    private String tvStation;
    @Json(name = "away_team_errors")
    private String awayTeamErrors;
    @Json(name = "away_team_runs")
    private String awayTeamRuns;
    @Json(name = "mlbtv_link")
    private String mlbtvLink;
    @Json(name = "away_time_zone")
    private String awayTimeZone;
    @Json(name = "home_audio_link")
    private String homeAudioLink;
    @Json(name = "hm_lg_ampm")
    private String hmLgAmpm;
    @Json(name = "home_sport_code")
    private String homeSportCode;
    @Json(name = "time_date")
    private String timeDate;
    @Json(name = "inning")
    private String inning;
    @Json(name = "home_team_hits")
    private String homeTeamHits;
    @Json(name = "home_ampm")
    private String homeAmpm;
    @Json(name = "game_pk")
    private String gamePk;
    @Json(name = "venue")
    private String venue;
    @Json(name = "home_league_id")
    private String homeLeagueId;
    @Json(name = "away_team_hits")
    private String awayTeamHits;
    @Json(name = "away_loss")
    private String awayLoss;
    @Json(name = "home_team_errors")
    private String homeTeamErrors;
    @Json(name = "away_file_code")
    private String awayFileCode;
    @Json(name = "aw_lg_ampm")
    private String awLgAmpm;
    @Json(name = "home_preview_link")
    private String homePreviewLink;
    @Json(name = "time_zone_hm_lg")
    private String timeZoneHmLg;
    @Json(name = "away_ampm")
    private String awayAmpm;
    @Json(name = "away_division")
    private String awayDivision;

    public String getHomeTeamRuns() {
        return homeTeamRuns;
    }

    public void setHomeTeamRuns(String homeTeamRuns) {
        this.homeTeamRuns = homeTeamRuns;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getDoubleHeaderSw() {
        return doubleHeaderSw;
    }

    public void setDoubleHeaderSw(String doubleHeaderSw) {
        this.doubleHeaderSw = doubleHeaderSw;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAwayTime() {
        return awayTime;
    }

    public void setAwayTime(String awayTime) {
        this.awayTime = awayTime;
    }

    public String getAwayPreviewLink() {
        return awayPreviewLink;
    }

    public void setAwayPreviewLink(String awayPreviewLink) {
        this.awayPreviewLink = awayPreviewLink;
    }

    public String getAwayAudioLink() {
        return awayAudioLink;
    }

    public void setAwayAudioLink(String awayAudioLink) {
        this.awayAudioLink = awayAudioLink;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTopInning() {
        return topInning;
    }

    public void setTopInning(String topInning) {
        this.topInning = topInning;
    }

    public String getHomeTime() {
        return homeTime;
    }

    public void setHomeTime(String homeTime) {
        this.homeTime = homeTime;
    }

    public String getHomeTeamSb() {
        return homeTeamSb;
    }

    public void setHomeTeamSb(String homeTeamSb) {
        this.homeTeamSb = homeTeamSb;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamHr() {
        return awayTeamHr;
    }

    public void setAwayTeamHr(String awayTeamHr) {
        this.awayTeamHr = awayTeamHr;
    }

    public String getInd() {
        return ind;
    }

    public void setInd(String ind) {
        this.ind = ind;
    }

    public String getOriginalDate() {
        return originalDate;
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
    }

    public String getHomeTeamCity() {
        return homeTeamCity;
    }

    public void setHomeTeamCity(String homeTeamCity) {
        this.homeTeamCity = homeTeamCity;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getHomeTeamSo() {
        return homeTeamSo;
    }

    public void setHomeTeamSo(String homeTeamSo) {
        this.homeTeamSo = homeTeamSo;
    }

    public String getGamedaySw() {
        return gamedaySw;
    }

    public void setGamedaySw(String gamedaySw) {
        this.gamedaySw = gamedaySw;
    }

    public String getAwayWin() {
        return awayWin;
    }

    public void setAwayWin(String awayWin) {
        this.awayWin = awayWin;
    }

    public String getHomeGamesBackWildcard() {
        return homeGamesBackWildcard;
    }

    public void setHomeGamesBackWildcard(String homeGamesBackWildcard) {
        this.homeGamesBackWildcard = homeGamesBackWildcard;
    }

    public String getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(String awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getTzHmLgGen() {
        return tzHmLgGen;
    }

    public void setTzHmLgGen(String tzHmLgGen) {
        this.tzHmLgGen = tzHmLgGen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHomeLoss() {
        return homeLoss;
    }

    public void setHomeLoss(String homeLoss) {
        this.homeLoss = homeLoss;
    }

    public String getHomeGamesBack() {
        return homeGamesBack;
    }

    public void setHomeGamesBack(String homeGamesBack) {
        this.homeGamesBack = homeGamesBack;
    }

    public String getHomeCode() {
        return homeCode;
    }

    public void setHomeCode(String homeCode) {
        this.homeCode = homeCode;
    }

    public String getAwaySportCode() {
        return awaySportCode;
    }

    public void setAwaySportCode(String awaySportCode) {
        this.awaySportCode = awaySportCode;
    }

    public String getHomeTeamHr() {
        return homeTeamHr;
    }

    public void setHomeTeamHr(String homeTeamHr) {
        this.homeTeamHr = homeTeamHr;
    }

    public String getHomeWin() {
        return homeWin;
    }

    public void setHomeWin(String homeWin) {
        this.homeWin = homeWin;
    }

    public String getTimeHmLg() {
        return timeHmLg;
    }

    public void setTimeHmLg(String timeHmLg) {
        this.timeHmLg = timeHmLg;
    }

    public String getAwayNameAbbrev() {
        return awayNameAbbrev;
    }

    public void setAwayNameAbbrev(String awayNameAbbrev) {
        this.awayNameAbbrev = awayNameAbbrev;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getTimeZoneAwLg() {
        return timeZoneAwLg;
    }

    public void setTimeZoneAwLg(String timeZoneAwLg) {
        this.timeZoneAwLg = timeZoneAwLg;
    }

    public String getAwayGamesBack() {
        return awayGamesBack;
    }

    public void setAwayGamesBack(String awayGamesBack) {
        this.awayGamesBack = awayGamesBack;
    }

    public String getHomeFileCode() {
        return homeFileCode;
    }

    public void setHomeFileCode(String homeFileCode) {
        this.homeFileCode = homeFileCode;
    }

    public String getGameDataDirectory() {
        return gameDataDirectory;
    }

    public void setGameDataDirectory(String gameDataDirectory) {
        this.gameDataDirectory = gameDataDirectory;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAwayLeagueId() {
        return awayLeagueId;
    }

    public void setAwayLeagueId(String awayLeagueId) {
        this.awayLeagueId = awayLeagueId;
    }

    public String getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(String homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getTimeAwLg() {
        return timeAwLg;
    }

    public void setTimeAwLg(String timeAwLg) {
        this.timeAwLg = timeAwLg;
    }

    public String getAwayTeamCity() {
        return awayTeamCity;
    }

    public void setAwayTeamCity(String awayTeamCity) {
        this.awayTeamCity = awayTeamCity;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTbdFlag() {
        return tbdFlag;
    }

    public void setTbdFlag(String tbdFlag) {
        this.tbdFlag = tbdFlag;
    }

    public String getTzAwLgGen() {
        return tzAwLgGen;
    }

    public void setTzAwLgGen(String tzAwLgGen) {
        this.tzAwLgGen = tzAwLgGen;
    }

    public String getAwayCode() {
        return awayCode;
    }

    public void setAwayCode(String awayCode) {
        this.awayCode = awayCode;
    }

    public GameMedia getGameMedia() {
        return gameMedia;
    }

    public void setGameMedia(GameMedia gameMedia) {
        this.gameMedia = gameMedia;
    }

    public String getGameNbr() {
        return gameNbr;
    }

    public void setGameNbr(String gameNbr) {
        this.gameNbr = gameNbr;
    }

    public String getTimeDateAwLg() {
        return timeDateAwLg;
    }

    public void setTimeDateAwLg(String timeDateAwLg) {
        this.timeDateAwLg = timeDateAwLg;
    }

    public String getAwayTeamSb() {
        return awayTeamSb;
    }

    public void setAwayTeamSb(String awayTeamSb) {
        this.awayTeamSb = awayTeamSb;
    }

    public String getAwayGamesBackWildcard() {
        return awayGamesBackWildcard;
    }

    public void setAwayGamesBackWildcard(String awayGamesBackWildcard) {
        this.awayGamesBackWildcard = awayGamesBackWildcard;
    }

    public String getScheduledInnings() {
        return scheduledInnings;
    }

    public void setScheduledInnings(String scheduledInnings) {
        this.scheduledInnings = scheduledInnings;
    }

    public String getVenueWChanLoc() {
        return venueWChanLoc;
    }

    public void setVenueWChanLoc(String venueWChanLoc) {
        this.venueWChanLoc = venueWChanLoc;
    }

    public String getAwayTeamSo() {
        return awayTeamSo;
    }

    public void setAwayTeamSo(String awayTeamSo) {
        this.awayTeamSo = awayTeamSo;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public String getGamedayLink() {
        return gamedayLink;
    }

    public void setGamedayLink(String gamedayLink) {
        this.gamedayLink = gamedayLink;
    }

    public String getOuts() {
        return outs;
    }

    public void setOuts(String outs) {
        this.outs = outs;
    }

    public String getTimeDateHmLg() {
        return timeDateHmLg;
    }

    public void setTimeDateHmLg(String timeDateHmLg) {
        this.timeDateHmLg = timeDateHmLg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeNameAbbrev() {
        return homeNameAbbrev;
    }

    public void setHomeNameAbbrev(String homeNameAbbrev) {
        this.homeNameAbbrev = homeNameAbbrev;
    }

    public String getWrapupLink() {
        return wrapupLink;
    }

    public void setWrapupLink(String wrapupLink) {
        this.wrapupLink = wrapupLink;
    }

    public String getTiebreakerSw() {
        return tiebreakerSw;
    }

    public void setTiebreakerSw(String tiebreakerSw) {
        this.tiebreakerSw = tiebreakerSw;
    }

    public String getAmpm() {
        return ampm;
    }

    public void setAmpm(String ampm) {
        this.ampm = ampm;
    }

    public String getHomeDivision() {
        return homeDivision;
    }

    public void setHomeDivision(String homeDivision) {
        this.homeDivision = homeDivision;
    }

    public String getHomeTimeZone() {
        return homeTimeZone;
    }

    public void setHomeTimeZone(String homeTimeZone) {
        this.homeTimeZone = homeTimeZone;
    }

    public String getTvStation() {
        return tvStation;
    }

    public void setTvStation(String tvStation) {
        this.tvStation = tvStation;
    }

    public String getAwayTeamErrors() {
        return awayTeamErrors;
    }

    public void setAwayTeamErrors(String awayTeamErrors) {
        this.awayTeamErrors = awayTeamErrors;
    }

    public String getAwayTeamRuns() {
        return awayTeamRuns;
    }

    public void setAwayTeamRuns(String awayTeamRuns) {
        this.awayTeamRuns = awayTeamRuns;
    }

    public String getMlbtvLink() {
        return mlbtvLink;
    }

    public void setMlbtvLink(String mlbtvLink) {
        this.mlbtvLink = mlbtvLink;
    }

    public String getAwayTimeZone() {
        return awayTimeZone;
    }

    public void setAwayTimeZone(String awayTimeZone) {
        this.awayTimeZone = awayTimeZone;
    }

    public String getHomeAudioLink() {
        return homeAudioLink;
    }

    public void setHomeAudioLink(String homeAudioLink) {
        this.homeAudioLink = homeAudioLink;
    }

    public String getHmLgAmpm() {
        return hmLgAmpm;
    }

    public void setHmLgAmpm(String hmLgAmpm) {
        this.hmLgAmpm = hmLgAmpm;
    }

    public String getHomeSportCode() {
        return homeSportCode;
    }

    public void setHomeSportCode(String homeSportCode) {
        this.homeSportCode = homeSportCode;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getInning() {
        return inning;
    }

    public void setInning(String inning) {
        this.inning = inning;
    }

    public String getHomeTeamHits() {
        return homeTeamHits;
    }

    public void setHomeTeamHits(String homeTeamHits) {
        this.homeTeamHits = homeTeamHits;
    }

    public String getHomeAmpm() {
        return homeAmpm;
    }

    public void setHomeAmpm(String homeAmpm) {
        this.homeAmpm = homeAmpm;
    }

    public String getGamePk() {
        return gamePk;
    }

    public void setGamePk(String gamePk) {
        this.gamePk = gamePk;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getHomeLeagueId() {
        return homeLeagueId;
    }

    public void setHomeLeagueId(String homeLeagueId) {
        this.homeLeagueId = homeLeagueId;
    }

    public String getAwayTeamHits() {
        return awayTeamHits;
    }

    public void setAwayTeamHits(String awayTeamHits) {
        this.awayTeamHits = awayTeamHits;
    }

    public String getAwayLoss() {
        return awayLoss;
    }

    public void setAwayLoss(String awayLoss) {
        this.awayLoss = awayLoss;
    }

    public String getHomeTeamErrors() {
        return homeTeamErrors;
    }

    public void setHomeTeamErrors(String homeTeamErrors) {
        this.homeTeamErrors = homeTeamErrors;
    }

    public String getAwayFileCode() {
        return awayFileCode;
    }

    public void setAwayFileCode(String awayFileCode) {
        this.awayFileCode = awayFileCode;
    }

    public String getAwLgAmpm() {
        return awLgAmpm;
    }

    public void setAwLgAmpm(String awLgAmpm) {
        this.awLgAmpm = awLgAmpm;
    }

    public String getHomePreviewLink() {
        return homePreviewLink;
    }

    public void setHomePreviewLink(String homePreviewLink) {
        this.homePreviewLink = homePreviewLink;
    }

    public String getTimeZoneHmLg() {
        return timeZoneHmLg;
    }

    public void setTimeZoneHmLg(String timeZoneHmLg) {
        this.timeZoneHmLg = timeZoneHmLg;
    }

    public String getAwayAmpm() {
        return awayAmpm;
    }

    public void setAwayAmpm(String awayAmpm) {
        this.awayAmpm = awayAmpm;
    }

    public String getAwayDivision() {
        return awayDivision;
    }

    public void setAwayDivision(String awayDivision) {
        this.awayDivision = awayDivision;
    }

}
