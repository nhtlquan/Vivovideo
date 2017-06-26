package com.smile.studio.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smile.studio.network.utils.Debug;

public class DuDoan implements Parcelable {

    public static final String MATCHID = "matchID";
    public static final String MATCH = "match";
    public static final String TIME = "time";

    @SerializedName("QuestionID")
    @Expose
    private Integer questionID;
    @SerializedName("Question")
    @Expose
    private String question;
    @SerializedName("TimeStart")
    @Expose
    private Integer timeStart;
    @SerializedName("TimeEnd")
    @Expose
    private Integer timeEnd;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("ModifyDate")
    @Expose
    private String modifyDate;
    @SerializedName("PhanLoai")
    @Expose
    private Integer phanLoai;
    @SerializedName("IsActive")
    @Expose
    private Integer isActive;
    @SerializedName("MatchID")
    @Expose
    private Integer matchID;
    @SerializedName("IsPlay")
    @Expose
    private Integer IsPlay;

    /**
     * @return The questionID
     */
    public Integer getQuestionID() {
        return questionID;
    }

    /**
     * @param questionID The QuestionID
     */
    public void setQuestionID(Integer questionID) {
        this.questionID = questionID;
    }

    /**
     * @return The question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question The Question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return The timeStart
     */
    public Integer getTimeStart() {
        return timeStart;
    }

    /**
     * @param timeStart The TimeStart
     */
    public void setTimeStart(Integer timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * @return The timeEnd
     */
    public Integer getTimeEnd() {
        return timeEnd;
    }

    /**
     * @param timeEnd The TimeEnd
     */
    public void setTimeEnd(Integer timeEnd) {
        this.timeEnd = timeEnd;
    }

    /**
     * @return The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status The Status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return The createDate
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate The CreateDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * @return The modifyDate
     */
    public String getModifyDate() {
        return modifyDate;
    }

    /**
     * @param modifyDate The ModifyDate
     */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    /**
     * @return The phanLoai
     */
    public Integer getPhanLoai() {
        return phanLoai;
    }

    /**
     * @param phanLoai The PhanLoai
     */
    public void setPhanLoai(Integer phanLoai) {
        this.phanLoai = phanLoai;
    }

    /**
     * @return The isActive
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * @param isActive The IsActive
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    /**
     * @return The matchID
     */
    public Integer getMatchID() {
        return matchID;
    }

    /**
     * @param matchID The MatchID
     */
    public void setMatchID(Integer matchID) {
        this.matchID = matchID;
    }

    public Integer getIsPlay() {
        return IsPlay;
    }

    public void setIsPlay(Integer isPlay) {
        IsPlay = isPlay;
    }

    public DuDoan(Parcel in) {
        questionID = in.readInt();
        question = in.readString();
        timeStart = in.readInt();
        timeEnd = in.readInt();
        status = in.readInt();
        createDate = in.readString();
        modifyDate = in.readString();
        phanLoai = in.readInt();
        isActive = in.readInt();
        matchID = in.readInt();
        IsPlay = in.readInt();
    }

    public static final Creator<DuDoan> CREATOR = new Creator<DuDoan>() {
        @Override
        public DuDoan[] newArray(int size) {
            return new DuDoan[size];
        }

        @Override
        public DuDoan createFromParcel(Parcel source) {
            return new DuDoan(source);
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(questionID);
        dest.writeString(question);
        dest.writeInt(timeStart);
        dest.writeInt(timeEnd);
        dest.writeInt(status);
        dest.writeString(createDate);
        dest.writeString(modifyDate);
        dest.writeInt(phanLoai);
        dest.writeInt(isActive);
        dest.writeInt(matchID);
        dest.writeInt(IsPlay);
    }

    public void trace() {
        Debug.e("questionID: " + questionID + "\nquestion: " + question + "\ntimeStart: " + timeStart + "\ntimeEnd: "
                + timeEnd + "\nstatus: " + status + "\ncreateDate: " + createDate + "\nmodifyDate: " + modifyDate
                + "\nphanLoai: " + phanLoai + "\nisActive: " + isActive + "\nmatchID: " + matchID + "\nIsPlay: "
                + IsPlay);
    }

}
