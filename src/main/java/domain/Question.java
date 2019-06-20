package domain;

public class Question {
    private String Title;
    private String Content;

    private String Answer1;
    private boolean A1;
    private String Answer2;
    private boolean A2;
    private String Answer3;
    private boolean A3;
    private String Answer4;
    private boolean A4;

    public Question(String title, String content, String answer1, boolean a1, String answer2, boolean a2, String answer3, boolean a3, String answer4, boolean a4) {
        Title = title;
        Content = content;
        Answer1 = answer1;
        A1 = a1;
        Answer2 = answer2;
        A2 = a2;
        Answer3 = answer3;
        A3 = a3;
        Answer4 = answer4;
        A4 = a4;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public String getAnswer1() {
        return Answer1;
    }

    public String getAnswer2() {
        return Answer2;
    }

    public String getAnswer3() {
        return Answer3;
    }

    public String getAnswer4() {
        return Answer4;
    }

    public Boolean checkAnswer1(){
        if(A1)
            return true;
        else
            return false;
    }

    public Boolean checkAnswer2(){
        if(A2)
            return true;
        else
            return false;
    }

    public Boolean checkAnswer3(){
        if(A3)
            return true;
        else
            return false;
    }

    public Boolean checkAnswer4(){
        if(A4)
            return true;
        else
            return false;
    }
}
