
package com.smile.studio.network.ver2.model.QuestionByID;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("start_at")
    @Expose
    private String startAt;
    @SerializedName("end_at")
    @Expose
    private Object endAt;
    @SerializedName("visible")
    @Expose
    private Integer visible;
    @SerializedName("visible_after")
    @Expose
    private Object visibleAfter;
    @SerializedName("team_a_score")
    @Expose
    private Integer teamAScore;
    @SerializedName("team_b_score")
    @Expose
    private Integer teamBScore;
    @SerializedName("allow_bet")
    @Expose
    private Integer allowBet;
    @SerializedName("winner_id")
    @Expose
    private Object winnerId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("tournament_id")
    @Expose
    private Integer tournamentId;
    @SerializedName("club_a")
    @Expose
    private ClubA clubA;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;
    @SerializedName("club_b")
    @Expose
    private ClubB clubB;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public Object getEndAt() {
        return endAt;
    }

    public void setEndAt(Object endAt) {
        this.endAt = endAt;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Object getVisibleAfter() {
        return visibleAfter;
    }

    public void setVisibleAfter(Object visibleAfter) {
        this.visibleAfter = visibleAfter;
    }

    public Integer getTeamAScore() {
        return teamAScore;
    }

    public void setTeamAScore(Integer teamAScore) {
        this.teamAScore = teamAScore;
    }

    public Integer getTeamBScore() {
        return teamBScore;
    }

    public void setTeamBScore(Integer teamBScore) {
        this.teamBScore = teamBScore;
    }

    public Integer getAllowBet() {
        return allowBet;
    }

    public void setAllowBet(Integer allowBet) {
        this.allowBet = allowBet;
    }

    public Object getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Object winnerId) {
        this.winnerId = winnerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public ClubA getClubA() {
        return clubA;
    }

    public void setClubA(ClubA clubA) {
        this.clubA = clubA;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public ClubB getClubB() {
        return clubB;
    }

    public void setClubB(ClubB clubB) {
        this.clubB = clubB;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

}
