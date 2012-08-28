package com.elgoooog.predictor;

import java.util.Date;
import java.util.List;

/**
 * @author Nicholas Hauschild
 *         Date: 8/27/12
 *         Time: 8:42 PM
 */
public class TrainData {
    private long postId;
    private Date postCreationDate;
    private long ownerUserId;
    private Date ownerCreationDate;
    private int reputationAtPostCreation;
    private int ownerUndeletedAnswerCountAtPostTime;
    private String title;
    private String bodyMarkdown;
    private List<String> tags;
    private Date postClosedDate;
    private OpenStatus openStatus;

    public TrainData(long postId, Date postCreationDate, long ownerUserId, Date ownerCreationDate,
                     int reputationAtPostCreation, int ownerUndeletedAnswerCountAtPostTime, String title,
                     String bodyMarkdown, List<String> tags, Date postClosedDate, OpenStatus openStatus)
    {
        this.postId = postId;
        this.postCreationDate = postCreationDate;
        this.ownerUserId = ownerUserId;
        this.ownerCreationDate = ownerCreationDate;
        this.reputationAtPostCreation = reputationAtPostCreation;
        this.ownerUndeletedAnswerCountAtPostTime = ownerUndeletedAnswerCountAtPostTime;
        this.title = title;
        this.bodyMarkdown = bodyMarkdown;
        this.tags = tags;
        this.postClosedDate = postClosedDate;
        this.openStatus = openStatus;
    }

    public long getPostId() {
        return postId;
    }

    public Date getPostCreationDate() {
        return postCreationDate;
    }

    public long getOwnerUserId() {
        return ownerUserId;
    }

    public Date getOwnerCreationDate() {
        return ownerCreationDate;
    }

    public int getReputationAtPostCreation() {
        return reputationAtPostCreation;
    }

    public int getOwnerUndeletedAnswerCountAtPostTime() {
        return ownerUndeletedAnswerCountAtPostTime;
    }

    public String getTitle() {
        return title;
    }

    public String getBodyMarkdown() {
        return bodyMarkdown;
    }

    public List<String> getTags() {
        return tags;
    }

    public Date getPostClosedDate() {
        return postClosedDate;
    }

    public OpenStatus getOpenStatus() {
        return openStatus;
    }
}
