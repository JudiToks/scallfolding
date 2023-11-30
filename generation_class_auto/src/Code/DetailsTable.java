package Code;

public class DetailsTable
{
    String colonne;
    String type;

//    get & set
    public String getColonne() {
        return colonne;
    }
    public void setColonne(String colonne) {
        this.colonne = colonne;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        if (type.equals("serial")) {
            type = "int";
        }
        else if (type.equals("varchar")) {
            type = "String";
        }
        else if (type.equals("date")) {
            type = "Date";
        }
        else if (type.equals("float8")) {
            type = "double";
        }
        else if (type.equals("int4")) {
            type = "int";
        }
        this.type = type;
    }
}
