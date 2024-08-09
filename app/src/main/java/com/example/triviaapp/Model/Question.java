package com.example.triviaapp.Model;

public class Question {
    private String ans;
    private boolean ansTrue;

    public Question() {

    }

    public Question(String ans, boolean ansTrue) {
        this.ans = ans;
        this.ansTrue = ansTrue;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public boolean isAnsTrue() {
        return ansTrue;
    }

    public void setAnsTrue(boolean ansTrue) {
        this.ansTrue = ansTrue;
    }
}
