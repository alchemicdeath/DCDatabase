import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class databaseConnect
{
    static final String DB_URL = "jdbc:mysql://localhost/DigimonCardDatabase";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main(String[] args)
    {
        /** Open a connection
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();)
        {
            String sql = "Create DATABASE DigimonCardDatabase";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        } */
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();)
        {
            String sql = "CREATE TABLE `DigimonCards` (" +
                    "`cardNumber` VARCHAR(8)," +
                    "`rarity` VARCHAR(10)," +
                    "`cardType` VARCHAR(8)," +
                    "`color` VARCHAR(12)," +
                    "`cardName` VARCHAR(255)," +
                    "`playCost` INT," +
                    "`form` VARCHAR(36)," +
                    "`attribute` VARCHAR(36)," +
                    "`type` VARCHAR(36)," +
                    "`dp` INT(6)," +
                    "`dvReq1` INT(2)," +
                    "`dvReq2` INT(2)," +
                    "`dvCost1` INT(2)," +
                    "`dvCost2` INT(2)," +
                    "`dvColor1` VARCHAR(36)," +
                    "`dvColor2` VARCHAR(36)," +
                    "`effect1` VARCHAR(255)," +
                    "`effect2` VARCHAR(255)," +
                    "`inheritedEffect` VARCHAR(255)," +
                    "`securityEffect` VARCHAR(255)," +
                    "`cardsOwn` INT(2), level INT(2)," +
                    "`imageName` VARCHAR(255), " +
                    "PRIMARY KEY (cardNumber));";
            stmt.executeUpdate(sql);
            System.out.println("Table created successfully...");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // XML File to load
        File file = new File("src/cards.xml");
        // Document Builder
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();)
        {

            // Parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // Gets all elements with the tag card
            NodeList nodeList = doc.getElementsByTagName("card");
            doc.getDocumentElement().normalize();

            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {

                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    System.out.println("HERE");
                    Element eElement = (Element) node;
                    String cardNumber = eElement.getElementsByTagName
                                ("cardNumber").item(0).getTextContent(),
                            rarity = eElement.getElementsByTagName
                                    ("rarity").item(0).getTextContent(),
                            cardType = eElement.getElementsByTagName
                                    ("cardType").item(0).getTextContent(),
                            color = eElement.getElementsByTagName
                                    ("color").item(0).getTextContent(),
                            cardName = eElement.getElementsByTagName
                                    ("cardName").item(0).getTextContent(),
                            form = eElement.getElementsByTagName
                                    ("form").item(0).getTextContent(),
                            attribute = eElement.getElementsByTagName
                                    ("attribute").item(0).getTextContent(),
                            type = eElement.getElementsByTagName
                                    ("type").item(0).getTextContent(),
                            dvColor1 = eElement.getElementsByTagName
                                    ("dvColor1").item(0).getTextContent(),
                            dvColor2 = eElement.getElementsByTagName
                                    ("dvColor2").item(0).getTextContent(),
                            effect1 = eElement.getElementsByTagName
                                    ("effect1").item(0).getTextContent(),
                            effect2 = eElement.getElementsByTagName
                                    ("effect2").item(0).getTextContent(),
                            inheritedEffect = eElement.getElementsByTagName
                                    ("inheritedEffect").item(0).getTextContent(),
                            securityEffect = eElement.getElementsByTagName
                                    ("securityEffect").item(0).getTextContent(),
                            imageName = eElement.getElementsByTagName
                                         ("imageName").item(0).getTextContent();

                            int playCost = Integer.parseInt(eElement.getElementsByTagName
                                    ("playCost").item(0).getTextContent()),
                            dp = Integer.parseInt(eElement.getElementsByTagName
                                    ("dp").item(0).getTextContent()),
                            dvReq1 = Integer.parseInt(eElement.getElementsByTagName
                                    ("dvReq1").item(0).getTextContent()),
                            dvReq2 = Integer.parseInt(eElement.getElementsByTagName
                                    ("dvReq2").item(0).getTextContent()),
                            dvCost1 = Integer.parseInt(eElement.getElementsByTagName
                                    ("dvCost1").item(0).getTextContent()),
                            dvCost2 = Integer.parseInt(eElement.getElementsByTagName
                                    ("dvCost2").item(0).getTextContent()),
                            level = Integer.parseInt(eElement.getElementsByTagName
                                     ("level").item(0).getTextContent()),
                            cardsOwn = Integer.parseInt(eElement.getElementsByTagName
                                    ("cardsOwn").item(0).getTextContent());

                    String sql = "INSERT IGNORE INTO DigimonCards " +
                            "(cardNumber,rarity, cardType, color," +
                            "cardName,playCost, form, attribute, " +
                            "type, dp, dvReq1, dvReq2, dvCost1," +
                            "dvCost2, dvColor1, dvColor2, effect1," +
                            "effect2, inheritedEffect, securityEffect, " +
                            "cardsOwn, level, imageName) VALUES ('"+
                            cardNumber +"','"+
                            rarity +"','"+
                            cardType +"','"+
                            color  +"','"+
                            cardName +"','"+
                            playCost +"','"+
                            form +"','"+
                            attribute +"','"+
                            type +"','"+
                            dp +"','"+
                            dvReq1 +"','"+
                            dvReq2 +"','"+
                            dvCost1 +"','"+
                            dvCost2 +"','"+
                            dvColor1 +"','"+
                            dvColor2 +"','"+
                            effect1 +"','"+
                            effect2 +"','"+
                            inheritedEffect +"','"+
                            securityEffect +"','"+
                            cardsOwn +"','"+
                            level +"','"+
                            imageName +"');";

                    stmt.executeUpdate(sql);
                    System.out.println(eElement.getElementsByTagName("cardName")
                     .item(0).getTextContent() + "created successfully...");
                }
            }
        }
        catch (SQLException | ParserConfigurationException | IOException | SAXException e)
        {
            e.printStackTrace();
        }
    }
}
