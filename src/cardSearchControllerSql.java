import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*----- VERSION 1.1.0-rc.1 --------UPDATED:1/12/2021 ---------*/
public class cardSearchControllerSql
{
    // -------------------------------- CONSTANTS -------------------------- //
    // ------ SQL ------//
    static final String DB_URL = "jdbc:mysql://localHost/DigimonCardDatabase";
    static final String USER = "jordan";
    static final String PASS = "AQuakiss12!";
    static final String QUERY = "SELECT cardNumber, rarity, cardType, color, " +
                                "cardName,playCost, form, attribute, type, dp," +
                                " dvReq1, dvReq2, dvCost1, dvCost2, dvColor1, " +
                                "dvColor2, effect1, effect2, inheritedEffect, " +
                                "securityEffect, cardsOwn, level, imageName " +
                                "FROM DigimonCards";

    // Black Background
    private static final Background BLACK = new Background(
            new BackgroundFill(Color.BLACK, null, null));

    // -------------------------------- FXML Items ------------------------ //
    @FXML
    // Display for all cards listed
            GridPane CardPane;

    @FXML
    // Text Search Window, Detailed Filter Window
    Pane textSearchPane, detailedSort, cardDetailPane;

    @FXML
    // Text Input Search Field
    TextField searchField;

    @FXML
    // Search for Text, Open Text Search, Detailed Sort Button, clear all
    // filters
    Button searchClose, filterButton, detailedSortSearch, trashFilters,
            reverseSorting, detailedSortTrash;
    @FXML
    ImageView cardImageDetailed, detailClose, redIcon, blueIcon, yellowIcon,
            redEffect, blueEffect, yellowEffect, redInherited, blueInherited,
            yellowInherited, ownUp, ownDown, saveOwned;

    @FXML
    Label nameDetail, cardTypeDetail, colorDetail, levelDetail,
            playCostDetail, dvCostDetail, formDetail, attributeDetailed, typeDetailed,
            rarityDetailed, ownCount, effectField, inheritedField, setNumber,
            inheritedSecurity;
    @FXML
    CheckBox // Top Menu Sort Buttons
            tamaCardBox, tamerCardBox, digimonCardBox, optionCardBox,
    // Attributes
    vaccineBox, virusBox, dataBox, freeBox,
    // Color
    redBox, blueBox, yellowBox, greenBox, blackBox, purpleBox,
            whiteBox,
    // DV Color
    dvRedBox, dvBlueBox, dvYellowBox, dvGreenBox, dvBlackBox,
            dvPurpleBox, dvWhiteBox,
    // Level / Form
    lvl2Box, lvl3Box, lvl4Box, lvl5Box,
            lvl6Box, lvl7Box,
    // Rarity
    commonBox, unCommonBox, rareBox, superRareBox,
            secretBox, promoBox,
    // Type
    bulbBox, babyDragonBox, amphibianBox,
            lesserBox, fireDragonBox, earthDragonBox, cyborgBox, birdkinBox,
            undeadBox, dragonkinBox, machineDragonBox, mammalBox, beastkinBox,
            puppetBox, darkAnimalBox, machineBox, fairyBox, archangelBox,
            magicWarriorBox, authorityBox, insectoidBox, carnivorousPlantBox,
            perfectBox, holyWarriorBox, fourGreatDragonsBox, rockBox,
            molluskBox, evilBox, sevenGreatDemonLordsBox, miniAngelBox,
            unidentifiedBox, miniDragonBox, reptileBox, birdBox, avianBox,
            giantBirdBox, dinosaurBox, seaBeastBox, beastBox, iceSnowBox,
            seaAnimalBox, ancientAnimalBox, holyBeastBox, angelBox, shamanBox,
            seraphBox, threeGreatAngelsBox, demonBox, vegetationBox,
            royalKnightBox, mythicalDragonBox, darkDragonBox, holyDragonBox,
            wizardBox, fallenAngelBox, compositeBox, demonLordBox,
            evilDragonBox;

    @FXML
    // Drop Down Menu for sorting
    SplitMenuButton sortingBox;

    // Boolean Checkers
    boolean // Top Menu Buttons
            tamaCheck = false, digiCheck = false, tamerCheck = false,
            optionCheck = false,
    // Sorting Direction
    reverse = false;

    // Element in XML search (Changes)
    String selection = "none";


    public void initialize()
    {
        sortingBox.setText("Sort: Number");
        selection = "cardNumber";
        sortLoad();
    }
    public void clearGrid()
    {
        CardPane.getChildren().clear();
    }
    public void trashFilters()
    {
        tamaCheck = false;
        digiCheck = false;
        tamerCheck = false;
        optionCheck = false;
        reverse = false;

        // Top Menu Sort Buttons
        tamaCardBox.setSelected(false);
        tamerCardBox.setSelected(false);
        digimonCardBox.setSelected(false);
        optionCardBox.setSelected(false);
        // Attributes
        vaccineBox.setSelected(false);
        virusBox.setSelected(false);
        dataBox.setSelected(false);
        freeBox.setSelected(false);
        // Color
        redBox.setSelected(false);
        blueBox.setSelected(false);
        yellowBox.setSelected(false);
        greenBox.setSelected(false);
        blackBox.setSelected(false);
        purpleBox.setSelected(false);
        whiteBox.setSelected(false);
        // DV Color
        dvRedBox.setSelected(false);
        dvBlueBox.setSelected(false);
        dvYellowBox.setSelected(false);
        dvGreenBox.setSelected(false);
        dvBlackBox.setSelected(false);
        dvPurpleBox.setSelected(false);
        dvWhiteBox.setSelected(false);
        // Level / Form
        lvl2Box.setSelected(false);
        lvl3Box.setSelected(false);
        lvl4Box.setSelected(false);
        lvl5Box.setSelected(false);
        lvl6Box.setSelected(false);
        lvl7Box.setSelected(false);
        // Rarity
        commonBox.setSelected(false);
        unCommonBox.setSelected(false);
        rareBox.setSelected(false);
        superRareBox.setSelected(false);
        secretBox.setSelected(false);
        promoBox.setSelected(false);
        // Type
        bulbBox.setSelected(false);
        babyDragonBox.setSelected(false);
        amphibianBox.setSelected(false);
        lesserBox.setSelected(false);
        fireDragonBox.setSelected(false);
        earthDragonBox.setSelected(false);
        cyborgBox.setSelected(false);
        birdkinBox.setSelected(false);
        undeadBox.setSelected(false);
        dragonkinBox.setSelected(false);
        machineDragonBox.setSelected(false);
        mammalBox.setSelected(false);
        beastkinBox.setSelected(false);
        puppetBox.setSelected(false);
        darkAnimalBox.setSelected(false);
        machineBox.setSelected(false);
        fairyBox.setSelected(false);
        archangelBox.setSelected(false);
        magicWarriorBox.setSelected(false);
        authorityBox.setSelected(false);
        insectoidBox.setSelected(false);
        carnivorousPlantBox.setSelected(false);
        perfectBox.setSelected(false);
        holyWarriorBox.setSelected(false);
        fourGreatDragonsBox.setSelected(false);
        rockBox.setSelected(false);
        molluskBox.setSelected(false);
        evilBox.setSelected(false);
        sevenGreatDemonLordsBox.setSelected(false);
        miniAngelBox.setSelected(false);
        unidentifiedBox.setSelected(false);
        miniDragonBox.setSelected(false);
        reptileBox.setSelected(false);
        birdBox.setSelected(false);
        avianBox.setSelected(false);
        giantBirdBox.setSelected(false);
        dinosaurBox.setSelected(false);
        seaBeastBox.setSelected(false);
        beastBox.setSelected(false);
        iceSnowBox.setSelected(false);
        seaAnimalBox.setSelected(false);
        ancientAnimalBox.setSelected(false);
        holyBeastBox.setSelected(false);
        angelBox.setSelected(false);
        shamanBox.setSelected(false);
        seraphBox.setSelected(false);
        threeGreatAngelsBox.setSelected(false);
        demonBox.setSelected(false);
        vegetationBox.setSelected(false);
        royalKnightBox.setSelected(false);
        mythicalDragonBox.setSelected(false);
        darkDragonBox.setSelected(false);
        holyDragonBox.setSelected(false);
        wizardBox.setSelected(false);
        fallenAngelBox.setSelected(false);
        compositeBox.setSelected(false);
        demonLordBox.setSelected(false);
        evilDragonBox.setSelected(false);
        sortLoad();
    }

    // -------------------------- Image Loading and sorting ------------//

    // Set Sorting
    public void sortLoad()
    {
        if(selection.equals("cardName"))
        {
            String sort = " ORDER BY cardName asc";
            if(reverse)
            {
                sort = " ORDER BY cardName DESC";
            }
            checkBoxes(sort);
        }
        if(selection.equals("cardNumber"))
        {
            String sort = " ORDER BY cardNumber asc";
            if (reverse)
            {
                sort = " ORDER BY cardNumber DESC";
            }
            checkBoxes(sort);
        }
        if(selection.equals("type"))
        {
            String sort = " ORDER BY type asc";
            if (reverse)
            {
                sort = " ORDER BY type DESC";
            }
            checkBoxes(sort);
        }
        if(selection.equals("attribute"))
        {
            String sort = " ORDER BY attribute asc";
            if (reverse)
            {
                sort = " ORDER BY attribute DESC";
            }
            checkBoxes(sort);
        }
        if (selection.equals("rarity"))
        {
            String sort = " ORDER BY case WHEN rarity = 'C' THEN 1 WHEN rarity =" +
                    " 'U' THEN 2 WHEN rarity = 'R' THEN 3 WHEN rarity = " +
                    "'SR' THEN 4 WHEN rarity = 'SEC' THEN 5 WHEN rarity" +
                    " = 'PROMO' THEN 6 ELSE 7 end asc";
            if (reverse)
            {
                sort = " ORDER BY case WHEN rarity = 'PROMO' THEN 1 WHEN rarity " +
                        "= 'SEC' THEN 2 WHEN rarity = 'SR' THEN 3 WHEN rarity ="
                        + " 'R' THEN 4 WHEN rarity = 'U' THEN 5 WHEN rarity =" +
                        " 'C' THEN 6 ELSE 7 end asc";
            }
            checkBoxes(sort);
        }
        if(selection.equals("level"))
        {
            String sort = " WHERE NOT cardType LIKE 'Option' AND NOT " +
                    "cardType LIKE 'Tamer' ORDER " +
                    "BY level asc";
            if (reverse)
            {
                sort = " WHERE NOT cardType LIKE 'Option' AND NOT " +
                        "cardType LIKE 'Tamer' ORDER " +
                        "BY level DESC";
            }
            checkBoxes(sort);
        }
        if(selection.equals("dp"))
        {
            String sort = " WHERE NOT cardType LIKE 'Option' AND NOT cardType " +
                    "LIKE 'Tamer' AND NOT cardType LIKE 'Tama' ORDER BY dp asc";
            if(reverse)
            {
                sort = " WHERE NOT cardType LIKE 'Option' AND NOT cardType " +
                        "LIKE 'Tamer' AND NOT cardType LIKE 'Tama' ORDER BY " +
                        "dp DESC";
            }
            checkBoxes(sort);
        }
    }
    // Set Checked Boxes
    public void checkBoxes(String sort)
    {
        int j = 0, i = 0;
        clearGrid();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY + sort))
        {
            while(rs.next())
            {
                String cardNumber = rs.getString("cardNumber");
                String cardName = rs.getString("cardName");
                String type = rs.getString("type");
                String cardType = rs.getString("cardType");
                int cardsOwn = rs.getInt("cardsOwn");
                String cardImage = rs.getString("imageName");
                String color = rs.getString("color");
                int playCost = rs.getInt("playCost");
                int level = rs.getInt("level");
                int dvCost1 = rs.getInt("dvCost1");
                int dvCost2 = rs.getInt("dvCost2");
                String rarity = rs.getString("rarity");
                String effect1 = rs.getString("effect1");
                String effect2 = rs.getString("effect2");
                String inheritedEffect = rs.getString("inheritedEffect");
                String attribute = rs.getString("attribute");
                String form = rs.getString("form");
                String dvColor1 = rs.getString("dvColor1");
                String dvColor2 = rs.getString("dvColor2");

                boolean addCard = false;
                // TopMenu - CardType
                if(levelCheck() && levels().contains("," + level + ","))
                {
                    if(topMenuCheck() && topMenu().contains(cardType) ||
                            cardTypeBoxes() && cardTypes().contains(cardType))
                    {
                        // Type
                        if(typeBoxes() && types().contains(type))
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!typeBoxes())
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(!topMenuCheck() && !cardTypeBoxes())
                    {
                        // Type
                        if(typeBoxes() && types().contains(type))
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!typeBoxes())
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if(!levelCheck())
                {
                    if(topMenuCheck() && topMenu().contains(cardType) ||
                            cardTypeBoxes() && cardTypes().contains(cardType))
                    {
                        // Type
                        if(typeBoxes() && types().contains(type))
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!typeBoxes())
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(!topMenuCheck() && !cardTypeBoxes())
                    {
                        // Type
                        if(typeBoxes() && types().contains(type))
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                        if(!typeBoxes())
                        {
                            if(attributeBoxes() && attributes().contains(attribute))
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                            if(!attributeBoxes())
                            {
                                if(colorBoxes() && colors().contains(color))
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                                if(!colorBoxes())
                                {
                                    if(dvColorBoxes() && dvColors().contains(dvColor1)
                                            || dvColorBoxes() && dvColors().
                                            contains(dvColor2))
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                    if(!dvColorBoxes())
                                    {
                                        if(rarityCheck() && rarity().contains("," + rarity + ","))
                                        {
                                            addCard = true;
                                        }
                                        if(!rarityCheck())
                                        {
                                            addCard = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (addCard)
                {
                    loadImages(cardImage,  cardName, cardType, cardNumber,
                            color, type, form, attribute, rarity, effect1,
                            inheritedEffect, effect2, dvColor1, dvColor2, level,
                            playCost,dvCost1, dvCost2, cardsOwn, i, j);
                    i++;
                    if (i == 5)
                    {
                        j++;
                        i = 0;
                        CardPane.setMaxHeight(CardPane.getHeight() + 150);
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        detailedSort.setVisible(false);
    }
    // Load the cards
    public void loadImages(String cardImage, String cardName, String cardType,
                           String cardNumber, String color, String type,
                           String form, String attribute,
                           String rarity, String effect1, String inheritedEffect,
                           String effect2, String dvColor1, String dvColor2, int
                           level, int playCost, int dvCost1, int dvCost2, int
                           cardsOwn, int i, int j)
    {
        ImageView imageView = new ImageView
                (new Image(cardImage));
        imageView.addEventHandler(MouseEvent.
                MOUSE_CLICKED, event ->
        {
            cardDetailPane.setVisible(true);
            if (cardType.equals("Option"))
            {
                inheritedSecurity.setText("Security Effect");
            }
            else
            {
                inheritedSecurity.setText("Inherited Effect");
            }
            cardImageDetailed.setImage(new Image(cardImage));
            nameDetail.setText(cardName);
            cardTypeDetail.setText(": " + cardType);
            setNumber.setText(cardNumber);
            if (color.equals("Red")) {
                colorDetail.setText(": Red");
                redIcon.setVisible(true);
                yellowIcon.setVisible(false);
                blueIcon.setVisible(false);
                redEffect.setVisible(true);
                yellowEffect.setVisible(false);
                blueEffect.setVisible(false);
                redInherited.setVisible(true);
                yellowInherited.setVisible(false);
                blueInherited.setVisible(false);
            }
            if (color.equals("Blue")) {
                colorDetail.setText(": Blue");
                redIcon.setVisible(false);
                yellowIcon.setVisible(false);
                blueIcon.setVisible(true);
                redEffect.setVisible(false);
                yellowEffect.setVisible(false);
                blueEffect.setVisible(true);
                redInherited.setVisible(false);
                yellowInherited.setVisible(false);
                blueInherited.setVisible(true);
            }
            if (color.equals("Yellow")) {
                colorDetail.setText(": Yellow");
                redIcon.setVisible(false);
                yellowIcon.setVisible(true);
                blueIcon.setVisible(false);
                redEffect.setVisible(false);
                yellowEffect.setVisible(true);
                blueEffect.setVisible(false);
                redInherited.setVisible(false);
                yellowInherited.setVisible(true);
                blueInherited.setVisible(false);
            }
            if (level == -1) {
                levelDetail.setText("");
            } else {
                levelDetail.setText(": " + level);
            }
            if (type.contains("none")) {
                typeDetailed.setText("");
            } else {
                typeDetailed.setText(": " + type);
            }
            if (playCost == -1) {
                playCostDetail.setText("");
            } else {
                playCostDetail.setText(": " + playCost);
            }
            if (dvCost1 == -1) {
                dvCostDetail.setText("");
            } else {
                dvCostDetail.setText(": " + dvCost1);
            }
            if (form.contains("none")) {
                formDetail.setText("");
            } else {
                formDetail.setText(": " + form);
            }
            if (attribute.contains("none")) {
                attributeDetailed.setText("");
            } else {
                attributeDetailed.setText(": " + attribute);
            }
            if (type.contains("none")) {
                typeDetailed.setText("");
            } else {
                typeDetailed.setText(": " + type);
            }
            switch (rarity) {
                case "C":
                    rarityDetailed.setText(": " + "Common");
                    break;
                case "U":
                    rarityDetailed.setText(": " + "Uncommon");
                    break;
                case "R":
                    rarityDetailed.setText(": " + "Rare");
                    break;
                case "SR":
                    rarityDetailed.setText(": " + "Super Rare");
                    break;
                case "SEC":
                    rarityDetailed.setText(": " + "Secret");
                    break;
            }
            if (effect1.contains("none"))
            {
                effectField.setText("");
            } else {
                effectField.setText(effect1 + ", " + effect2);
            }
            if (inheritedEffect.contains("none"))
            {
                inheritedField.setText("");
            }
            else
            {
                inheritedField.setText(inheritedEffect);
            }
            ownCount.setText(": " + cardsOwn);
            event.consume();
        });
        imageView.setFitHeight(150);
        imageView.setFitWidth(110);
        CardPane.add(imageView, i, j);

        // Add the area that will display owned cards.
        Pane ownText = new Pane();
        ownText.setMaxSize(10, 10);
        ownText.setTranslateX(90);
        ownText.setTranslateY(60);
        ownText.setBackground(BLACK);
        Label own = new Label(String.valueOf(cardsOwn));
        own.setTextFill(Color.WHITE);
        own.setTranslateX(3);
        own.setTranslateY(-3);
        ownText.getChildren().add(own);
        ownText.setBackground(BLACK);
        CardPane.add(ownText, i, j);
    }

    // --------------------------------- Text Searching ----------------//

    // Text Searching
    public void textSearch()
    {
        clearGrid();
        try
        {
            String text = searchField.getText().toLowerCase();
            String sort = " WHERE LOWER(cardNumber) LIKE '%" + text + "%' " +
                    "OR LOWER(cardName) LIKE '%" + text + "%' " +
                    " OR LOWER(rarity) LIKE '%" + text + "%' " +
                    " OR LOWER(cardType) LIKE '%" + text + "%' " +
                    " OR LOWER(color) LIKE '%" + text + "%' " +
                    " OR LOWER(cardName) LIKE '%" + text + "%' " +
                    " OR LOWER(form) LIKE '%" + text + "%' " +
                    " OR LOWER(attribute) LIKE '%" + text + "%' " +
                    " OR LOWER(type) LIKE '%" + text + "%' " +
                    " OR LOWER(dvColor1) LIKE '%" + text + "%' " +
                    " OR LOWER(dvColor2) LIKE '%" + text + "%' " +
                    " OR LOWER(effect1) LIKE '%" + text + "%' " +
                    " OR LOWER(effect2) LIKE '%" + text + "%' " +
                    " OR LOWER(inheritedEffect) LIKE '%" + text + "%' " +
                    " OR LOWER(securityEffect) LIKE '%" + text + "%' ";
            checkBoxes(sort);
        }
        catch (Exception ex)
        {
            Logger.getLogger(cardSearchController.class.getName())
                    .log(Level.SEVERE, null,ex);
        }
    }
    public void searchCloseText()
    {
        searchClose.setText("Search");
        if (searchField.getText().isEmpty())
        {
            searchClose.setText("Close");
        }
    }
    public void changeSearchCloseText()
    {
        if (searchField.getText().isEmpty())
        {
            textSearchPane.setVisible(false);
        }
        else
            textSearch();
        textSearchPane.setVisible(false);
    }
    // -------------------------- Actions ----------------------------- //
    // Changes the detailed search button
    // to search if one is selected, or return
    // to close if none.
    public void changeDetailedButton()
    {
        if (typeBoxes() || colorBoxes() || dvColorBoxes()
                || cardTypeBoxes() || attributeBoxes() || levelCheck() ||
                rarityCheck())
        {
            detailedSortSearch.setText("Search");
        }
        else
            detailedSortSearch.setText("Close");
    }
    // increase or decrease own count.
    public void increase()
    {
        int count = Integer.parseInt(ownCount.getText().replace(": ","")) + 1;
        ownCount.setText(": " + count);
    }
    public void decrease()
    {
        int count = Integer.parseInt(ownCount.getText().replace(": ","")) - 1;
        ownCount.setText(": " + count);
    }
    // increase or decrease button image
    // size for when clicked and released.
    public void small(MouseEvent mouseEvent)
    {
        ImageView thisImage = (ImageView) mouseEvent.getTarget();
        thisImage.setFitHeight(thisImage.getFitHeight() - 3);
        thisImage.setFitWidth(thisImage.getFitWidth() - 3);
    }
    public void normal(MouseEvent mouseEvent)
    {
        ImageView thisImage = (ImageView) mouseEvent.getTarget();
        thisImage.setFitHeight(thisImage.getFitHeight() + 3);
        thisImage.setFitWidth(thisImage.getFitWidth() + 3);
    }
    // update XML
    public void upDateOwn()
    {
        // Get the owned number in the full Detail window
        int count = Integer.parseInt(ownCount.getText().replace(": ",""));
        // get the specified card, then modify the owned number.
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement())
        {
            String statement = " UPDATE DigimonCards SET cardsOwn = '" + count +
                    "' WHERE cardNumber LIKE '" + setNumber.getText() + "'";
            stmt.executeUpdate(statement);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        cardDetailPane.setVisible(false);
        sortLoad();
    }
    // Reverses the sort direction
    public void reverseSort()
    {
        reverse = !reverse;

        if(reverseSorting.getText().equals("Filter ⮟"))
        {
            reverseSorting.setText("Filter ⮝");
        }
        else if(reverseSorting.getText().equals("Filter ⮝"))
        {
            reverseSorting.setText("Filter ⮟");
        }
        sortLoad();
    }

    // ------------------------------ Visible Changing -----------------//

    public void detailedFilter()
    {
        detailedSort.setVisible(true);
    }
    public void detailedFilterCancel()
    {
        detailedSort.setVisible(false);
    }
    public void showTextSearch()
    {
        textSearchPane.setVisible(true);
    }
    public void closeDetailPane()
    {
        cardDetailPane.setVisible(false);
    }

    // ------------------------- Change Selection ----------------------//
    // cardEffect

    // Set sorting method.
    public void toType()
    {
        sortingBox.setText("Sort: Type");
        selection = "type";
        sortLoad();
    }
    public void toName()
    {
        sortingBox.setText("Sort: Name");
        selection = "cardName";
        sortLoad();
    }
    public void toLvl()
    {
        sortingBox.setText("Sort: Level");
        selection = "level";
        sortLoad();
    }
    public void toDP()
    {
        sortingBox.setText("Sort: DP");
        selection = "dp";
        sortLoad();
    }
    public void toRarity()
    {
        sortingBox.setText("Sort: Rarity");
        selection = "rarity";
        sortLoad();
    }
    public void toNumber()
    {
        sortingBox.setText("Sort: Number");
        selection = "cardNumber";
        sortLoad();
    }
    public void toAttribute()
    {
        sortingBox.setText("Sort: Attribute");
        selection = "attribute";
        sortLoad();
    }

    // ------------------------------ Boolean Changing -----------------//

    // Top Menu Button checking.
    public void tamaFilter()
    {
        tamaCheck = !tamaCheck;
        sortLoad();
    }
    public void digimonFilter()
    {
        digiCheck = !digiCheck;
        sortLoad();
    }
    public void optionFilter()
    {
        optionCheck = !optionCheck;
        sortLoad();
    }
    public void tamerFilter()
    {
        tamerCheck = !tamerCheck;
        sortLoad();
    }
    public boolean topMenuCheck()
    {
        return tamaCheck || digiCheck || tamerCheck || optionCheck;
    }
    public String topMenu()
    {
        String top = ",";
        if (tamaCheck)
        {
            top = top + "Tama,";
        }
        if (digiCheck)
        {
            top = top + "Digimon,";
        }
        if (tamerCheck)
        {
            top = top + "Tamer,";
        }
        if (optionCheck)
        {
            top = top + "Option,";
        }

        return top;
    }

    // --------------- Check Boxes
    // ** Card Type
    public boolean cardTypeBoxes()
    {

        return tamaCardBox.isSelected() || tamerCardBox.isSelected() ||
                digimonCardBox.isSelected() || optionCardBox.isSelected();
    }
    public String cardTypes()
    {
        String cardType = ",";
        if(tamaCardBox.isSelected())
        {
            cardType += "Tama,";
        }
        if(digimonCardBox.isSelected())
        {
            cardType += "Digimon,";
        }
        if(tamerCardBox.isSelected())
        {
            cardType += "Tamer,";
        }
        if(optionCardBox.isSelected())
        {
            cardType += "Option,";
        }
        return cardType;
    }
    // ** Attribute
    public boolean attributeBoxes()
    {
        return vaccineBox.isSelected() || virusBox.isSelected() ||
                dataBox.isSelected() || freeBox.isSelected();
    }
    public String attributes()
    {
        String attribute = ",";
        if(freeBox.isSelected())
        {
            attribute += "Free,";
        }
        if(vaccineBox.isSelected())
        {
            attribute += "Vaccine,";
        }
        if(dataBox.isSelected())
        {
            attribute += "Data,";
        }
        if(virusBox.isSelected())
        {
            attribute += "Virus,";
        }

        return attribute;
    }
    // ** Digimon Type
    public boolean typeBoxes()
    {
        boolean boxes = false;

        if(bulbBox.isSelected())
        {
            boxes = true;
        }
        else if(babyDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(amphibianBox.isSelected())
        {
            boxes = true;
        }
        else if(lesserBox.isSelected())
        {
            boxes = true;
        }
        else if(fireDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(earthDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(cyborgBox.isSelected())
        {
            boxes = true;
        }
        else if(birdkinBox.isSelected())
        {
            boxes = true;
        }
        else if(undeadBox.isSelected())
        {
            boxes = true;
        }
        else if(dragonkinBox.isSelected())
        {
            boxes = true;
        }
        else if(machineDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(mammalBox.isSelected())
        {
            boxes = true;
        }
        else if(archangelBox.isSelected())
        {
            boxes = true;
        }
        else if(magicWarriorBox.isSelected())
        {
            boxes = true;
        }
        else if(authorityBox.isSelected())
        {
            boxes = true;
        }
        else if(insectoidBox.isSelected())
        {
            boxes = true;
        }
        else if(carnivorousPlantBox.isSelected())
        {
            boxes = true;
        }
        else if(perfectBox.isSelected())
        {
            boxes = true;
        }
        else if(holyWarriorBox.isSelected())
        {
            boxes = true;
        }
        else if(fourGreatDragonsBox.isSelected())
        {
            boxes = true;
        }
        else if(rockBox.isSelected())
        {
            boxes = true;
        }
        else if(molluskBox.isSelected())
        {
            boxes = true;
        }
        else if(evilBox.isSelected())
        {
            boxes = true;
        }
        else if(sevenGreatDemonLordsBox.isSelected())
        {
            boxes = true;
        }
        else  if(miniAngelBox.isSelected())
        {
            boxes = true;
        }
        else if(unidentifiedBox.isSelected())
        {
            boxes = true;
        }
        else if(miniDragonBox.isSelected())
        {
            boxes = true;
        }
        else  if(reptileBox.isSelected())
        {
            boxes = true;
        }
        else if(birdBox.isSelected())
        {
            boxes = true;
        }
        else if(avianBox.isSelected())
        {
            boxes = true;
        }
        else if (giantBirdBox.isSelected())
        {
            boxes = true;
        }
        else if(dinosaurBox.isSelected())
        {
            boxes = true;
        }
        else if(seaBeastBox.isSelected())
        {
            boxes = true;
        }
        else if(beastBox.isSelected())
        {
            boxes = true;
        }
        else if(iceSnowBox.isSelected())
        {
            boxes = true;
        }
        else if(seaAnimalBox.isSelected())
        {
            boxes = true;
        }
        else if(ancientAnimalBox.isSelected())
        {
            boxes = true;
        }
        else if(holyBeastBox.isSelected())
        {
            boxes = true;
        }
        else if(angelBox.isSelected())
        {
            boxes = true;
        }
        else if(shamanBox.isSelected())
        {
            boxes = true;
        }
        else if(seraphBox.isSelected())
        {
            boxes = true;
        }
        else if(threeGreatAngelsBox.isSelected())
        {
            boxes = true;
        }
        else if(demonBox.isSelected())
        {
            boxes = true;
        }
        else if(vegetationBox.isSelected())
        {
            boxes = true;
        }
        else if(royalKnightBox.isSelected())
        {
            boxes = true;
        }
        else if(mythicalDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(darkDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(holyDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(wizardBox.isSelected())
        {
            boxes = true;
        }
        else if(fallenAngelBox.isSelected())
        {
            boxes = true;
        }
        else if(compositeBox.isSelected())
        {
            boxes = true;
        }
        else if(demonLordBox.isSelected())
        {
            boxes = true;
        }
        else if(evilDragonBox.isSelected())
        {
            boxes = true;
        }
        else if(beastkinBox.isSelected())
        {
            boxes = true;
        }
        else if(puppetBox.isSelected())
        {
            boxes = true;
        }
        else if(darkAnimalBox.isSelected())
        {
            boxes = true;
        }
        else if(machineBox.isSelected())
        {
            boxes = true;
        }
        else if(fairyBox.isSelected())
        {
            boxes = true;
        }
        return boxes;
    }
    public String types()
    {
        String typeText = ",";
        if(bulbBox.isSelected())
        {
            typeText = typeText+"Bulb,";
        }
        if(babyDragonBox.isSelected())
        {
            typeText = typeText+"Baby Dragon,";
        }
        if(amphibianBox.isSelected())
        {
            typeText = typeText+"Amphibian,";
        }
        if(lesserBox.isSelected())
        {
            typeText = typeText+"Lesser,";
        }
        if(fireDragonBox.isSelected())
        {
            typeText = typeText+"Fire Dragon,";
        }
        if(earthDragonBox.isSelected())
        {
            typeText = typeText+"Earth Dragon,";
        }
        if(cyborgBox.isSelected())
        {
            typeText = typeText+"Cyborg,";
        }
        if(birdkinBox.isSelected())
        {
            typeText = typeText+"Birdkin,";
        }
        if(undeadBox.isSelected())
        {
            typeText = typeText+"Undead,";
        }
        if(dragonkinBox.isSelected())
        {
            typeText = typeText+"Dragonkin,";
        }
        if(machineDragonBox.isSelected())
        {
            typeText = typeText+"Machine Dragon,";
        }
        if(mammalBox.isSelected())
        {
            typeText = typeText+"Mammal,";
        }
        if(archangelBox.isSelected())
        {
            typeText = typeText+"Archangel,";
        }
        if(magicWarriorBox.isSelected())
        {
            typeText = typeText+"Magic Warrior,";
        }
        if(authorityBox.isSelected())
        {
            typeText = typeText+"Authority,";
        }
        if(insectoidBox.isSelected())
        {
            typeText = typeText+"Insectoid,";
        }
        if(carnivorousPlantBox.isSelected())
        {
            typeText = typeText+"Carnivorous Plant,";
        }
        if(perfectBox.isSelected())
        {
            typeText = typeText+"Perfect,";
        }
        if(holyWarriorBox.isSelected())
        {
            typeText = typeText+"Holy Warrior,";
        }
        if(fourGreatDragonsBox.isSelected())
        {
            typeText = typeText+"Four Great Dragon,";
        }
        if(rockBox.isSelected())
        {
            typeText = typeText+"Rock,";
        }
        if(molluskBox.isSelected())
        {
            typeText = typeText+"Mollusk,";
        }
        if(evilBox.isSelected())
        {
            typeText = typeText+"Evil,";
        }
        if(sevenGreatDemonLordsBox.isSelected())
        {
            typeText = typeText+"Seven Great Demon Lords,";
        }
        if(miniAngelBox.isSelected())
        {
            typeText = typeText+"Mini Angel,";
        }
        if(unidentifiedBox.isSelected())
        {
            typeText = typeText+"Unidentified,";
        }
        if(miniDragonBox.isSelected())
        {
            typeText = typeText+"Mini Dragon,";
        }
        if(reptileBox.isSelected())
        {
            typeText = typeText+"Reptile,";
        }
        if(birdBox.isSelected())
        {
            typeText = typeText+"Bird,";
        }
        if(avianBox.isSelected())
        {
            typeText = typeText+"Avian,";
        }
        if(giantBirdBox.isSelected())
        {
            typeText = typeText+"Giant Bird,";
        }
        if(dinosaurBox.isSelected())
        {
            typeText = typeText+"Dinosaur,";
        }
        if(seaBeastBox.isSelected())
        {
            typeText = typeText+"Sea Beast,";
        }
        if(beastBox.isSelected())
        {
            typeText = typeText+"Beast,";
        }
        if(iceSnowBox.isSelected())
        {
            typeText = typeText+"Ice-Snow,";
        }
        if(seaAnimalBox.isSelected())
        {
            typeText = typeText+"Sea Animal,";
        }
        if(ancientAnimalBox.isSelected())
        {
            typeText = typeText+"Ancient Animal,";
        }
        if(holyBeastBox.isSelected())
        {
            typeText = typeText+"Holy Beast,";
        }
        if(angelBox.isSelected())
        {
            typeText = typeText+"Angel,";
        }
        if(shamanBox.isSelected())
        {
            typeText = typeText+"Shaman,";
        }
        if(seraphBox.isSelected())
        {
            typeText = typeText+"Seraph,";
        }
        if(threeGreatAngelsBox.isSelected())
        {
            typeText = typeText+"Three Great Angels,";
        }
        if(demonBox.isSelected())
        {
            typeText = typeText+"Demon,";
        }
        if(vegetationBox.isSelected())
        {
            typeText = typeText+"Vegetation,";
        }
        if(royalKnightBox.isSelected())
        {
            typeText = typeText+"Royal Knight,";
        }
        if(mythicalDragonBox.isSelected())
        {
            typeText = typeText+"Mythical Dragon,";
        }
        if(darkDragonBox.isSelected())
        {
            typeText = typeText+"Dark Dragon,";
        }
        if(holyDragonBox.isSelected())
        {
            typeText = typeText+"Holy Dragon,";
        }
        if(wizardBox.isSelected())
        {
            typeText = typeText+"Wizard,";
        }
        if(fallenAngelBox.isSelected())
        {
            typeText = typeText+"Fallen Angel,";
        }
        if(compositeBox.isSelected())
        {
            typeText = typeText+"Composite,";
        }
        if(demonLordBox.isSelected())
        {
            typeText = typeText+"Demon Lord,";
        }
        if(evilDragonBox.isSelected())
        {
            typeText = typeText+"Evil Dragon,";
        }
        if(beastkinBox.isSelected())
        {
            typeText = typeText+"Beastkin,";
        }
        if(puppetBox.isSelected())
        {
            typeText = typeText+"Puppet,";
        }
        if(darkAnimalBox.isSelected())
        {
            typeText = typeText+"Dark Animal,";
        }
        if(machineBox.isSelected())
        {
            typeText = typeText+"Machine,";
        }
        if(fairyBox.isSelected())
        {
            typeText = typeText+"Fairy,";
        }
        return typeText;
    }
    // ** Digimon Level
    public boolean levelCheck()
    {

        return lvl2Box.isSelected() || lvl3Box.isSelected() || lvl4Box.isSelected()
                || lvl5Box.isSelected() || lvl6Box.isSelected() ||
                lvl7Box.isSelected();
    }
    public String levels()
    {
        String lvl = ",";
        if(lvl2Box.isSelected())
        {
            lvl += "2,";
        }
        if(lvl3Box.isSelected())
        {
            lvl += "3,";
        }
        if(lvl4Box.isSelected())
        {
            lvl += "4,";
        }
        if(lvl5Box.isSelected())
        {
            lvl += "5,";
        }
        if(lvl6Box.isSelected())
        {
            lvl += "6,";
        }
        if(lvl7Box.isSelected())
        {
            lvl += "7,";
        }
        return lvl;
    }
    // ** Card Rarity
    public boolean rarityCheck()
    {
        return commonBox.isSelected() || unCommonBox.isSelected() ||
                rareBox.isSelected() || superRareBox.isSelected() ||
                secretBox.isSelected() || promoBox.isSelected();
    }
    public String rarity()
    {
        String rare = ",";
        if (commonBox.isSelected())
        {
            rare  = rare +"C,";
        }
        if (unCommonBox.isSelected())
        {
            rare  = rare + "U,";
        }
        if (rareBox.isSelected())
        {
            rare  = rare +"R,";
        }
        if (superRareBox.isSelected())
        {
            rare = rare +"SR,";
        }
        if (secretBox.isSelected())
        {
            rare = rare + "SEC,";
        }
        return rare;
    }
    // ** Card Color
    public boolean colorBoxes()
    {
        return redBox.isSelected() || blueBox.isSelected() ||
                yellowBox.isSelected() || greenBox.isSelected() ||
                purpleBox.isSelected() || blackBox.isSelected() ||
                whiteBox.isSelected();
    }
    public String colors()
    {
        String colors = ",";
        if (redBox.isSelected())
        {
            colors += "Red,";
        }
        if (blueBox.isSelected())
        {
            colors += "Blue,";
        }
        if (yellowBox.isSelected())
        {
            colors += "Yellow,";
        }
        if (greenBox.isSelected())
        {
            colors += "Green,";
        }
        if (blackBox.isSelected())
        {
            colors += "Black,";
        }
        if (purpleBox.isSelected())
        {
            colors += "Purple,";
        }
        if (whiteBox.isSelected())
        {
            colors += "White,";
        }

        return colors;
    }
    // ** Digimon DV Colors
    public boolean dvColorBoxes()
    {
        return dvRedBox.isSelected() || dvBlueBox.isSelected() ||
                dvYellowBox.isSelected() || dvGreenBox.isSelected() ||
                dvPurpleBox.isSelected() || dvBlackBox.isSelected() ||
                dvWhiteBox.isSelected();
    }
    public String dvColors()
    {
        String dvColors = ",";
        if (dvRedBox.isSelected())
        {
            dvColors += "Red,";
        }
        if (dvBlueBox.isSelected())
        {
            dvColors += "Blue,";
        }
        if (dvYellowBox.isSelected())
        {
            dvColors += "Yellow,";
        }
        if (dvGreenBox.isSelected())
        {
            dvColors += "Green,";
        }
        if (dvBlackBox.isSelected())
        {
            dvColors += "Black,";
        }
        if (dvPurpleBox.isSelected())
        {
            dvColors += "Purple,";
        }
        if (dvWhiteBox.isSelected())
        {
            dvColors += "White,";
        }

        return dvColors;
    }
}
