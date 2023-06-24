package model;
public class SchoolClass {

    private int id; // 编号
    private String className; // 班级名称
    private String calssDesc; // 备注
    public SchoolClass() {
        super();
        // TODO Auto-generated constructor stub
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getCalssDesc() {
        return calssDesc;
    }

    public void setCalssDesc(String calsDesc) {
        this.calssDesc = calssDesc;
    }

    @Override
    public String toString() {
        return className;
    }
}

