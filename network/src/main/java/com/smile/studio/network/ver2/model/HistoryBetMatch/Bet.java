
package com.smile.studio.network.ver2.model.HistoryBetMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bet {

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
    private Integer amount;
    @SerializedName("correct")
    @Expose
    private Object correct;
    @SerializedName("payment_status")
    @Expose
    private Object paymentStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted")
    @Expose
    private Integer deleted;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Object getCorrect() {
        return correct;
    }

    public void setCorrect(Object correct) {
        this.correct = correct;
    }

    public Object getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }

}
