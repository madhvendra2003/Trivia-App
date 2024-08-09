package com.example.triviaapp.data;

import com.example.triviaapp.Model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void ProcessFinished(ArrayList<Question> questionArrayList);
}
