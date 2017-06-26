
package com.smile.studio.network.ver2.model.UserBet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {

    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("prediction_question")
    @Expose
    private Integer predictionQuestion;
    @SerializedName("prediction_answer")
    @Expose
    private Integer predictionAnswer;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("deleted")
    @Expose
    private Boolean deleted;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPredictionQuestion() {
        return predictionQuestion;
    }

    public void setPredictionQuestion(Integer predictionQuestion) {
        this.predictionQuestion = predictionQuestion;
    }

    public Integer getPredictionAnswer() {
        return predictionAnswer;
    }

    public void setPredictionAnswer(Integer predictionAnswer) {
        this.predictionAnswer = predictionAnswer;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
