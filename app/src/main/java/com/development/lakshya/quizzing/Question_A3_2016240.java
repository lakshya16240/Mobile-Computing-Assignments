package com.development.lakshya.quizzing;

class Question_A3_2016240 {

    private String question;
    private String correctAnswer, selectedAnswer;

    public Question_A3_2016240(String question, String correctAnswer, String selectedAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = selectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }
}
