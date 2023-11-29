package Code;

import java.util.List;

public class Table
{
    String talbe_name;
    List<String> colonne;
    List<String> type;
    String pkg;

    //    get & set
    public String getTalbe_name() {
        return talbe_name;
    }
    public void setTalbe_name(String talbe_name) {
        this.talbe_name = talbe_name;
    }
    public String getPkg() {
        return pkg;
    }
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
    public List<String> getColonne() {
        return colonne;
    }
    public void setColonne(List<String> colonne) {
        this.colonne = colonne;
    }
    public List<String> getType() {
        return type;
    }
    public void setType(List<String> type) {
        this.type = type;
    }
}
