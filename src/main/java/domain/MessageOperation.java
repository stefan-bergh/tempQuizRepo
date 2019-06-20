package domain;

public enum MessageOperation {
    NEW_USER, //Get userdata for score management
    NEXT_QUESTION, // Get data for next question
    ANSWER_QUESTION, // Send answer to question
    UPDATE_PLAYERS // Update the playerlist
}
