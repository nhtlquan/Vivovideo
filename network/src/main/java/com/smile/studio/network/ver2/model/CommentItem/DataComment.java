
package com.smile.studio.network.ver2.model.CommentItem;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataComment {

    @SerializedName("totalComment")
    @Expose
    private Integer totalComment;
    @SerializedName("comments")
    @Expose
    private List<Comment> comments = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DataComment() {
    }

    /**
     * 
     * @param totalComment
     * @param comments
     */
    public DataComment(Integer totalComment, List<Comment> comments) {
        super();
        this.totalComment = totalComment;
        this.comments = comments;
    }

    public Integer getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(Integer totalComment) {
        this.totalComment = totalComment;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
