
package com.smile.studio.network.ver2.model.HistoryBetMatch;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("match_id")
    @Expose
    private Integer matchId;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("min_bet")
    @Expose
    private Integer minBet;
    @SerializedName("max_bet")
    @Expose
    private Integer maxBet;
    @SerializedName("increase_bet")
    @Expose
    private Integer increaseBet;
    @SerializedName("allow_bet")
    @Expose
    private Integer allowBet;
    @SerializedName("visible")
    @Expose
    private Integer visible;
    @SerializedName("correct_answer")
    @Expose
    private Object correctAnswer;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = null;
    @SerializedName("bet")
    @Expose
    private Bet bet;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getMinBet() {
        return minBet;
    }

    public void setMinBet(Integer minBet) {
        this.minBet = minBet;
    }

    public Integer getMaxBet() {
        return maxBet;
    }

    public void setMaxBet(Integer maxBet) {
        this.maxBet = maxBet;
    }

    public Integer getIncreaseBet() {
        return increaseBet;
    }

    public void setIncreaseBet(Integer increaseBet) {
        this.increaseBet = increaseBet;
    }

    public Integer getAllowBet() {
        return allowBet;
    }

    public void setAllowBet(Integer allowBet) {
        this.allowBet = allowBet;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public Object getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Object correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

}
