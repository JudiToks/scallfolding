package Code;

import connect.ConnectToBdd;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Code
{
    public Table getClassExist(Connection conn, String table_name, ConfigBdd config)
    {
        Table list_table = new Table();
        List<String> all_colonne = new ArrayList<>();
        List<String> all_type = new ArrayList<>();
        try
        {
            if (conn == null)
            {
                ConnectToBdd connection = new ConnectToBdd();
                conn = connection.connectToPostgres(config);
            }
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, table_name.toLowerCase(), null);
            while (resultSet.next())
            {
                String colonne_name = resultSet.getString("COLUMN_NAME");
                String colonne_type = resultSet.getString("TYPE_NAME");
                all_colonne.add(colonne_name);
                all_type.add(colonne_type);
                Table temp_table = new Table();
                temp_table.setTalbe_name(table_name.substring(0, 1).toUpperCase() + table_name.substring(1));
                temp_table.setColonne(all_colonne);
                temp_table.setType(all_type);
                list_table = temp_table;
            }
        }
        catch (Exception e)
        {
            System.out.println("dans la fonction getClassExist!");
            e.printStackTrace();
            return null;
        }
        return list_table;
    }

    public ConfigBdd readXMLDataBaseConfig()
    {
        ConfigBdd valiny = new ConfigBdd();
        try
        {
            File fichierXML = new File("config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fichierXML);
            document.getDocumentElement().normalize();

            NodeList listeContacts = document.getElementsByTagName("databaseconfig");

            for (int temp = 0; temp < listeContacts.getLength(); temp++) {
                Node nNode = listeContacts.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String link = eElement.getElementsByTagName("databaselink").item(0).getTextContent();
                    String user = eElement.getElementsByTagName("user").item(0).getTextContent();
                    String pwd = eElement.getElementsByTagName("password").item(0).getTextContent();
                    valiny.setLink(link);
                    valiny.setUser(user);
                    valiny.setPwd(pwd);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("readXML base config problem");
            e.printStackTrace();
        }
        return valiny;
    }

    public ConfigClasse readXMLConfig()
    {
        ConfigClasse valiny = new ConfigClasse();
        try
        {
            File fichierXML = new File("config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(fichierXML);
            document.getDocumentElement().normalize();

            NodeList listeContacts = document.getElementsByTagName("config");

            for (int temp = 0; temp < listeContacts.getLength(); temp++) {
                Node nNode = listeContacts.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String classe = eElement.getElementsByTagName("table").item(0).getTextContent();
                    String pkg = eElement.getElementsByTagName("package").item(0).getTextContent();
                    String path = eElement.getElementsByTagName("path").item(0).getTextContent();
                    Table table = new Table();
                    table.setPkg(pkg);
                    table.setTalbe_name(classe.substring(0, 1).toUpperCase() + classe.substring(1));
                    valiny.setPath(path);
                    valiny.setTable(table);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("readXML classe config problem");
            e.printStackTrace();
        }
        return valiny;
    }

    public boolean checkIsOnBdd(Table classe) // ilay table azo avy amin'ny getClassExist
    {
        boolean isChecked = false;
        try
        {
            if (classe.getTalbe_name() != null)
            {
                isChecked = true;
            }
        }
        catch (Exception e)
        {
            System.out.println("CheckIsOnBdd problem");
            e.printStackTrace();
        }
        return isChecked;
    }

    public boolean createClass(ConfigClasse conf_classe, Table table)
    {
        boolean isCreate = false;
        try
        {
            boolean isChecked = checkIsOnBdd(table);
            if (isChecked)
            {
                File pkg = new File(conf_classe.getPath()+conf_classe.getTable().getPkg());
                if (!pkg.exists())
                {
                    pkg.mkdir();
                }
                File file = new File(conf_classe.getPath()+conf_classe.getTable().getPkg()+"/"+conf_classe.getTable().getTalbe_name()+".java");
                if (!file.exists())
                {
                    file.createNewFile();
                }
                FileWriter writer = new FileWriter(file);
                writer.write("public class "+conf_classe.getTable().getTalbe_name()+" {\n");
                writer.write("\n");
                writer.write("}\n");
                writer.close();
                isCreate = true;
            }
        }
        catch (Exception e)
        {
            System.out.println("createClass exception!");
            e.printStackTrace();
        }
        return isCreate;
    }

    public void executeFramework(Connection conn)
    {
        try
        {
            ConfigBdd bdd = new ConfigBdd();
            bdd = readXMLDataBaseConfig();
            ConfigClasse config_classe = new ConfigClasse();
            config_classe = readXMLConfig();
            Table bdd_table = new Table();
            bdd_table = getClassExist(conn, config_classe.getTable().getTalbe_name(), bdd);
            boolean isCreate = createClass(config_classe, bdd_table);
            if (isCreate)
            {
                System.out.println("Creation classe successed");
            }
            else
            {
                System.out.println("Creation classe failed");
            }
        }
        catch (Exception e)
        {
            System.out.println("Execute framework problem");
            e.printStackTrace();
        }
    }
}
