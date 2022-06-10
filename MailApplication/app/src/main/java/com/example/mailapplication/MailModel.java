package com.example.mailapplication;

public class MailModel {
    private String senderMail;
    private String title;
    private String content;
    private String time;
    private Boolean isInterested;

    public MailModel(String senderMail, String title, String content, String time, Boolean isInterested) {
        this.senderMail = senderMail;
        this.title = title;
        this.content = content;
        this.time = time;
        this.isInterested = isInterested;
    }

    public Boolean getInterested() {
        return isInterested;
    }

    public void setInterested(Boolean interested) {
        isInterested = interested;
    }

    public String getSenderMail() {
        return senderMail;
    }

    public void setSenderMail(String senderMail) {
        this.senderMail = senderMail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
