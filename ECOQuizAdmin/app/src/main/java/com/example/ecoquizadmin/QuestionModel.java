package com.example.ecoquizadmin;

public class QuestionModel {

    private String quesID;
    private String question;
    private String optionA;
    private String optionB;
    private int correctAns;

    public QuestionModel(String quesID, String question, String optionA, String optionB, int correctAns) {
        this.quesID = quesID;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.correctAns = correctAns;
    }

    public String getQuesID() {
        return quesID;
    }

    public void setQuesID(String quesID) {
        this.quesID = quesID;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }
}
