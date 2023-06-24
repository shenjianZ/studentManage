package model;

public class Score {
    private int id;
    private String studentId;
    private String studentName;
    private float chineseScore;
    private float mathScore;
    private float englishScore;
    private float total;
    public Score(){}

    public Score(int id,String studentId,String name, float chineseScore, float mathScore, float englishScore,float total) {
        this.id=id;
        this.studentId=studentId;
        this.studentName = name;
        this.chineseScore = chineseScore;
        this.mathScore = mathScore;
        this.englishScore = englishScore;
        this.total=total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public float getChineseScore() {
        return chineseScore;
    }

    public void setChineseScore(float chineseScore) {
        this.chineseScore = chineseScore;
    }

    public float getMathScore() {
        return mathScore;
    }

    public void setMathScore(float mathScore) {
        this.mathScore = mathScore;
    }

    public float getEnglishScore() {
        return englishScore;
    }

    public void setEnglishScore(float englishScore) {
        this.englishScore = englishScore;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}