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
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
public class cardSearchController
{
    // -------------------------------- CONSTANTS -------------------------- //

    // Black Background
    private static final Background BLACK = new Background(
            new BackgroundFill(Color.BLACK, null, null));
    // XML File to load
    private final File file = new File("src/cards.xml");

    // Document Builder
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

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
            rarityDetailed, ownCount, effectField, inheritedField, setNumber;
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

    // To Hold for sort items
    ArrayList<String> currentCards;
    ArrayList<Integer> currentCards2;

    // -------------------------- Function Methods ---------------------//

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
    public void sortLoad()
    {
        clearGrid();
        try
        {
            int j = 0, i = 0;

            // Parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // Gets all elements with the tag card
            NodeList nodeList = doc.getElementsByTagName("card");
            doc.getDocumentElement().normalize();
            currentCards = new ArrayList<>();

            // Start the sorting out here.
            // DP and Level
            if (selection.equals("dp") || selection.equals("level"))
            {
                currentCards2 = new ArrayList<>();
                try
                {
                    // Adds the card names to an arraylist.
                    for (int itr = 0; itr < nodeList.getLength(); itr++)
                    {
                        Node node = nodeList.item(itr);
                        if (node.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element eElement = (Element) node;

                            String element = eElement.getElementsByTagName
                                    (selection).item(0).getTextContent();

                            if (!element.contains("none"))
                            {
                                int numEle = Integer.parseInt((element));
                                if (!currentCards2.contains(numEle))
                                {
                                    currentCards2.add(numEle);
                                }
                            }
                        }
                    }

                    Collections.sort(currentCards2);

                    if (reverse)
                    {
                        Collections.reverse(currentCards2);
                    }

                    // Display the cards
                    for (Integer integer : currentCards2)
                    {
                        // nodeList is not iterable, so we are using for loop
                        for (int itr = 0; itr < nodeList.getLength(); itr++)
                        {
                            Node node = nodeList.item(itr);
                            if (node.getNodeType() == Node.ELEMENT_NODE)
                            {
                                Element eElement = (Element) node;
                                int numEle = integer;
                                String element = eElement.getElementsByTagName
                                        (selection).item(0).getTextContent();
                                if (tamaCheck || digiCheck || tamerCheck || optionCheck)
                                {
                                    if(digiCheck && eElement.getElementsByTagName
                                            ("cardType").item(0).getTextContent().contains("Digimon"))
                                    {
                                        if (checkTypeBoxes())
                                        {
                                            String typeText = typeBoxes();
                                            // This Currently is for types only
                                            if (typeText.contains("," + eElement.
                                                    getElementsByTagName("type").item(0).
                                                    getTextContent() + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }

                                                if ((Integer.parseInt(element) == numEle))
                                                {
                                                    loadImages(eElement, i, j);
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
                                        else
                                        {
                                            if (element.contains("none"))
                                            {
                                                continue;
                                            }
                                            if ((Integer.parseInt(element) == numEle))
                                            {
                                                loadImages(eElement, i, j);
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
                                }
                                else
                                {
                                    if (element.contains("none"))
                                    {
                                        continue;
                                    }
                                    if ((Integer.parseInt(element) == numEle))
                                    {
                                        loadImages(eElement, i, j);
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
                        }
                    }
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
            // Rarity
            if (selection.equals("rarity"))
            {
                currentCards = new ArrayList<>();
                try
                {
                    if(!reverse)
                    {
                        currentCards.add("C");
                        currentCards.add("U");
                        currentCards.add("R");
                        currentCards.add("SR");
                        currentCards.add("SEC");
                    }

                    if (reverse)
                    {
                        currentCards.add("SEC");
                        currentCards.add("SR");
                        currentCards.add("R");
                        currentCards.add("U");
                        currentCards.add("C");
                    }

                    // Display the cards
                    checkBoxes(nodeList);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
            // Card name, attribute, type, number
            if (selection.equals("cardNumber") || selection.equals("cardName")
                    || selection.equals("type") || selection.equals("attribute"))
            {
                currentCards = new ArrayList<>();
                try
                {
                    // Adds the card names to an arraylist.
                    for (int itr = 0; itr < nodeList.getLength(); itr++)
                    {
                        Node node = nodeList.item(itr);
                        if (node.getNodeType() == Node.ELEMENT_NODE)
                        {
                            Element eElement = (Element) node;

                            String element = eElement.getElementsByTagName
                                    (selection).item(0).getTextContent();

                            if (!element.contains("none"))
                            {
                                if (!currentCards.contains(element))
                                {
                                    currentCards.add(element);
                                }
                            }
                        }
                    }

                    Collections.sort(currentCards);

                    if (reverse)
                    {
                        Collections.reverse(currentCards);
                    }

                    // Display the cards
                    checkBoxes(nodeList);
                }
                catch (NumberFormatException e)
                {
                    e.printStackTrace();
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        detailedSort.setVisible(false);
    }

    public void loadImages(Element eElement, int i, int j)
    {
        ImageView imageView = new ImageView(new Image(eElement.
                getElementsByTagName("imageName").item(0).getTextContent()));

        imageView.addEventHandler(MouseEvent.
                MOUSE_CLICKED, event ->
        {
            cardDetailPane.setVisible(true);
            cardImageDetailed.setImage(new Image(eElement.getElementsByTagName("imageName").item(0).getTextContent()));
            nameDetail.setText(eElement.getElementsByTagName("cardName").item(0).getTextContent());
            cardTypeDetail.setText( ": " + eElement.getElementsByTagName("cardType").item(0).getTextContent());
            setNumber.setText(eElement.getElementsByTagName("cardNumber").item(0).getTextContent());
            if( eElement.getElementsByTagName("color").item(0).getTextContent().contains("Red"))
            {
                colorDetail.setText( ": Red");
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
            if( eElement.getElementsByTagName("color").item(0).getTextContent().contains("Blue"))
            {
                colorDetail.setText( ": Blue");
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
            if( eElement.getElementsByTagName("color").item(0).getTextContent().contains("Yellow"))
            {
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

            if (eElement.getElementsByTagName("level").item(0).getTextContent().contains("none"))
            {
                levelDetail.setText("");
            }
            else
            {
                levelDetail.setText( ": " + eElement.getElementsByTagName("level").item(0).getTextContent());
            }
            if (eElement.getElementsByTagName("type").item(0).getTextContent().contains("none"))
            {
                typeDetailed.setText("");
            }
            else
            {
                typeDetailed.setText( ": " + eElement.getElementsByTagName("type").item(0).getTextContent());
            }

            if (eElement.getElementsByTagName("playCost").item(0).getTextContent().contains("none"))
            {
                playCostDetail.setText("");

            }
            else
            {
                playCostDetail.setText( ": " + eElement.getElementsByTagName
                        ("playCost").item(0).getTextContent());
            }
            if (eElement.getElementsByTagName("dvCost1").item(0).getTextContent().contains("none"))
            {
                dvCostDetail.setText("");

            }
            else
            {
                dvCostDetail.setText( ": " + eElement.getElementsByTagName
                        ("dvCost1").item(0).getTextContent());
            }
            if (eElement.getElementsByTagName("form").item(0).getTextContent().contains("none"))
            {
                formDetail.setText("");
            }
            else
            {
                formDetail.setText( ": " + eElement.getElementsByTagName("form").item(0).getTextContent());
            }
            if (eElement.getElementsByTagName("attribute").item(0).getTextContent().contains("none"))
            {
                attributeDetailed.setText( "");
            }
            else
            {
                attributeDetailed.setText( ": "
                        + eElement.getElementsByTagName("attribute").item(0).getTextContent());
            }
            if (eElement.getElementsByTagName("type").item(0).getTextContent().contains("none"))
            {
                typeDetailed.setText("");
            }
            else
            {
                typeDetailed.setText( ": " + eElement.getElementsByTagName("type").item(0).getTextContent());
            }

            if(eElement.getElementsByTagName("rarity").item(0).getTextContent().equals("C"))
            {
                rarityDetailed.setText( ": " + "Common" );
            }
            else if(eElement.getElementsByTagName("rarity").item(0).getTextContent().equals("U"))
            {
                rarityDetailed.setText( ": " + "Uncommon" );
            }
            else if(eElement.getElementsByTagName("rarity").item(0).getTextContent().equals("R"))
            {
                rarityDetailed.setText( ": " + "Rare" );
            }
            else
            {
                rarityDetailed.setText( ": " + "Super Rare" );
            }
            if(eElement.getElementsByTagName("effect1").item(0).getTextContent().contains("none"))
            {
                effectField.setText("");
            }
            else
            {
                effectField.setText(eElement.getElementsByTagName("effect1").item(0).getTextContent());
            }
            if(eElement.getElementsByTagName("inheritedEffect").item(0).getTextContent().contains("none"))
            {
                inheritedField.setText("");
            }
            else
            {
                inheritedField.setText((eElement.getElementsByTagName("inheritedEffect").item(0).getTextContent()));
            }

            ownCount.setText( ": " + eElement.getElementsByTagName("cardsOwn").item(0).getTextContent());
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
        Label own = new Label(eElement.getElementsByTagName
                ("cardsOwn").item(0).getTextContent());
        own.setTextFill(Color.WHITE);
        own.setTranslateX(3);
        own.setTranslateY(-3);
        ownText.getChildren().add(own);
        ownText.setBackground(BLACK);
        CardPane.add(ownText, i, j);
    }

    public void checkBoxes(NodeList nodeList)
    {
        int j = 0, i = 0;
        for (String currentCard : currentCards)
        {
            // nodeList is not iterable, so we are using for loop
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;
                    String element = eElement.getElementsByTagName
                            (selection).item(0).getTextContent();

                    // -Get Attributes
                    String attribute = eElement.getElementsByTagName
                            ("attribute").item(0).getTextContent();
                    // -Get DV Colors
                    String dvColor1 = eElement.getElementsByTagName("dvColor1")
                            .item(0).getTextContent();
                    String dvColor2 = eElement.getElementsByTagName("dvColor2")
                            .item(0).getTextContent();
                    // -Get Type
                    String typeText = eElement.getElementsByTagName("type")
                            .item(0).getTextContent();
                    // -Get Level
                    String level = eElement.getElementsByTagName("level").item
                            (0).getTextContent();
                    // -Get Rarity
                    String rarity = eElement.getElementsByTagName("rarity").item
                            (0).getTextContent();
                    // -Get cardType
                    String cardType = eElement.getElementsByTagName
                            ("cardType").item(0).getTextContent();
                    if(topMenuCheck())
                    {
                        if(topMenu().contains("," + cardType + ","))
                        {
                            if (cardTypeBoxes())
                            {

                                if (cardTypes().contains("," + cardType + ","))
                                {
                                    if (colorBoxes())
                                    {
                                        // -Get Colors
                                        String color1 = eElement.getElementsByTagName("color1").item
                                                (0).getTextContent();
                                        String color2 = eElement.getElementsByTagName("color2").item
                                                (0).getTextContent();
                                        if (colors().contains("," + color1 + ",") ||
                                                colors().contains("," + color2 + ","))
                                        {
                                            if (element.contains("none"))
                                            {
                                                continue;
                                            }
                                            if (attributeBoxes())
                                            {
                                                if (attributes().contains("," + attribute + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }
                                                    if (dvColorBoxes())
                                                    {
                                                        if (dvColors().contains("," + dvColor1 + ",") ||
                                                                dvColors().contains("," + dvColor2 + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (checkTypeBoxes())
                                                            {
                                                                // This Currently is for types only
                                                                if (typeText.contains("," + typeText + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }

                                                                    if (levelCheck())
                                                                    {
                                                                        if (level().contains("," + level + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (rarityCheck())
                                                                            {
                                                                                if (rarity().contains("," + rarity + ","))
                                                                                {
                                                                                    if (element.contains("none"))
                                                                                    {
                                                                                        continue;
                                                                                    }
                                                                                    if (element.equals(currentCard))
                                                                                    {
                                                                                        loadImages(eElement, i, j);
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
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (levelCheck())
                                                                {
                                                                    if (level().contains("," + level + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                    else
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (checkTypeBoxes())
                                                        {
                                                            // This Currently is for types only
                                                            if (typeText.contains("," + typeText + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }

                                                                if (levelCheck())
                                                                {
                                                                    if (level().contains("," + level + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.contains("none"))
                                                                                {
                                                                                    continue;
                                                                                }
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                                else
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (dvColorBoxes())
                                                {
                                                    if (dvColors().contains("," + dvColor1 + ",") ||
                                                            dvColors().contains("," + dvColor2 + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (checkTypeBoxes())
                                                        {
                                                            // This Currently is for types only
                                                            if (typeText.contains("," + typeText + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }

                                                                if (levelCheck())
                                                                {
                                                                    if (level().contains("," + level + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.contains("none"))
                                                                                {
                                                                                    continue;
                                                                                }
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                                else
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (attributeBoxes())
                                        {
                                            if (attributes().contains("," + attribute + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }
                                                if (dvColorBoxes())
                                                {
                                                    if (dvColors().contains("," + dvColor1 + ",") ||
                                                            dvColors().contains("," + dvColor2 + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (checkTypeBoxes())
                                                        {
                                                            // This Currently is for types only
                                                            if (typeText.contains("," + typeText + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }

                                                                if (levelCheck())
                                                                {
                                                                    if (level().contains("," + level + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.contains("none"))
                                                                                {
                                                                                    continue;
                                                                                }
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                                else
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (dvColorBoxes())
                                            {
                                                if (dvColors().contains("," + dvColor1 + ",") ||
                                                        dvColors().contains("," + dvColor2 + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (colorBoxes())
                                {
                                    // -Get Colors
                                    String color1 = eElement.getElementsByTagName("color").item
                                            (0).getTextContent();
                                    if (colors().contains("," + color1 + ","))
                                    {
                                        if (element.contains("none"))
                                        {
                                            continue;
                                        }
                                        if (attributeBoxes())
                                        {
                                            if (attributes().contains("," + attribute + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }
                                                if (dvColorBoxes())
                                                {
                                                    if (dvColors().contains("," + dvColor1 + ",") ||
                                                            dvColors().contains("," + dvColor2 + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (checkTypeBoxes())
                                                        {
                                                            // This Currently is for types only
                                                            if (typeText.contains("," + typeText + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }

                                                                if (levelCheck())
                                                                {
                                                                    if (level().contains("," + level + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.contains("none"))
                                                                                {
                                                                                    continue;
                                                                                }
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                                else
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (dvColorBoxes())
                                            {
                                                if (dvColors().contains("," + dvColor1 + ",") ||
                                                        dvColors().contains("," + dvColor2 + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (attributeBoxes())
                                    {
                                        if (attributes().contains("," + attribute + ","))
                                        {
                                            if (element.contains("none"))
                                            {
                                                continue;
                                            }
                                            if (dvColorBoxes())
                                            {
                                                if (dvColors().contains("," + dvColor1 + ",") ||
                                                        dvColors().contains("," + dvColor2 + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (dvColorBoxes())
                                        {
                                            if (dvColors().contains("," + dvColor1 + ",") ||
                                                    dvColors().contains("," + dvColor2 + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }

                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (checkTypeBoxes())
                                            {
                                                // This Currently is for types only
                                                if (typeText.contains("," + typeText + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (levelCheck())
                                                {
                                                    if (level().contains("," + level + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                                else
                                                {
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                    else
                                                    {
                                                        if (element.equals(currentCard))
                                                        {
                                                            loadImages(eElement, i, j);
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
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else
                    {
                        if (cardTypeBoxes())
                        {
                            if (cardTypes().contains("," + cardType + ","))
                            {
                                if (colorBoxes())
                                {
                                    // -Get Colors
                                    String color1 = eElement.getElementsByTagName("color1").item
                                            (0).getTextContent();
                                    String color2 = eElement.getElementsByTagName("color2").item
                                            (0).getTextContent();
                                    if (colors().contains("," + color1 + ",") ||
                                            colors().contains("," + color2 + ","))
                                    {
                                        if (element.contains("none"))
                                        {
                                            continue;
                                        }
                                        if (attributeBoxes())
                                        {
                                            if (attributes().contains("," + attribute + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }
                                                if (dvColorBoxes())
                                                {
                                                    if (dvColors().contains("," + dvColor1 + ",") ||
                                                            dvColors().contains("," + dvColor2 + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (checkTypeBoxes())
                                                        {
                                                            // This Currently is for types only
                                                            if (typeText.contains("," + typeText + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }

                                                                if (levelCheck())
                                                                {
                                                                    if (level().contains("," + level + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (rarityCheck())
                                                                        {
                                                                            if (rarity().contains("," + rarity + ","))
                                                                            {
                                                                                if (element.contains("none"))
                                                                                {
                                                                                    continue;
                                                                                }
                                                                                if (element.equals(currentCard))
                                                                                {
                                                                                    loadImages(eElement, i, j);
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
                                                                    }
                                                                }
                                                                else
                                                                {
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                                else
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (dvColorBoxes())
                                            {
                                                if (dvColors().contains("," + dvColor1 + ",") ||
                                                        dvColors().contains("," + dvColor2 + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (attributeBoxes())
                                    {
                                        if (attributes().contains("," + attribute + ","))
                                        {
                                            if (element.contains("none"))
                                            {
                                                continue;
                                            }
                                            if (dvColorBoxes())
                                            {
                                                if (dvColors().contains("," + dvColor1 + ",") ||
                                                        dvColors().contains("," + dvColor2 + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (dvColorBoxes())
                                        {
                                            if (dvColors().contains("," + dvColor1 + ",") ||
                                                    dvColors().contains("," + dvColor2 + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }

                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (checkTypeBoxes())
                                            {
                                                // This Currently is for types only
                                                if (typeText.contains("," + typeText + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (levelCheck())
                                                {
                                                    if (level().contains("," + level + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                                else
                                                {
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                    else
                                                    {
                                                        if (element.equals(currentCard))
                                                        {
                                                            loadImages(eElement, i, j);
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
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else
                        {
                            if (colorBoxes())
                            {
                                // -Get Colors
                                String color1 = eElement.getElementsByTagName("color").item
                                        (0).getTextContent();
                                if (colors().contains("," + color1 + ","))
                                {
                                    if (element.contains("none"))
                                    {
                                        continue;
                                    }
                                    if (attributeBoxes())
                                    {
                                        if (attributes().contains("," + attribute + ","))
                                        {
                                            if (element.contains("none"))
                                            {
                                                continue;
                                            }
                                            if (dvColorBoxes())
                                            {
                                                if (dvColors().contains("," + dvColor1 + ",") ||
                                                        dvColors().contains("," + dvColor2 + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (checkTypeBoxes())
                                                    {
                                                        // This Currently is for types only
                                                        if (typeText.contains("," + typeText + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }

                                                            if (levelCheck())
                                                            {
                                                                if (level().contains("," + level + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (rarityCheck())
                                                                    {
                                                                        if (rarity().contains("," + rarity + ","))
                                                                        {
                                                                            if (element.contains("none"))
                                                                            {
                                                                                continue;
                                                                            }
                                                                            if (element.equals(currentCard))
                                                                            {
                                                                                loadImages(eElement, i, j);
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
                                                                }
                                                            }
                                                            else
                                                            {
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                            else
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (dvColorBoxes())
                                        {
                                            if (dvColors().contains("," + dvColor1 + ",") ||
                                                    dvColors().contains("," + dvColor2 + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }

                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (checkTypeBoxes())
                                            {
                                                // This Currently is for types only
                                                if (typeText.contains("," + typeText + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (levelCheck())
                                                {
                                                    if (level().contains("," + level + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                                else
                                                {
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                    else
                                                    {
                                                        if (element.equals(currentCard))
                                                        {
                                                            loadImages(eElement, i, j);
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
                                            }
                                        }
                                    }
                                }
                            }
                            else
                            {
                                if (attributeBoxes())
                                {
                                    if (attributes().contains("," + attribute + ","))
                                    {
                                        if (element.contains("none"))
                                        {
                                            continue;
                                        }
                                        if (dvColorBoxes())
                                        {
                                            if (dvColors().contains("," + dvColor1 + ",") ||
                                                    dvColors().contains("," + dvColor2 + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }

                                                if (checkTypeBoxes())
                                                {
                                                    // This Currently is for types only
                                                    if (typeText.contains("," + typeText + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }

                                                        if (levelCheck())
                                                        {
                                                            if (level().contains("," + level + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (rarityCheck())
                                                                {
                                                                    if (rarity().contains("," + rarity + ","))
                                                                    {
                                                                        if (element.contains("none"))
                                                                        {
                                                                            continue;
                                                                        }
                                                                        if (element.equals(currentCard))
                                                                        {
                                                                            loadImages(eElement, i, j);
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
                                                            }
                                                        }
                                                        else
                                                        {
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                }
                                                else
                                                {
                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                        else
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (checkTypeBoxes())
                                            {
                                                // This Currently is for types only
                                                if (typeText.contains("," + typeText + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (levelCheck())
                                                {
                                                    if (level().contains("," + level + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                                else
                                                {
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                    else
                                                    {
                                                        if (element.equals(currentCard))
                                                        {
                                                            loadImages(eElement, i, j);
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
                                            }
                                        }
                                    }
                                }
                                else
                                {
                                    if (dvColorBoxes())
                                    {
                                        if (dvColors().contains("," + dvColor1 + ",") ||
                                                dvColors().contains("," + dvColor2 + ","))
                                        {
                                            if (element.contains("none"))
                                            {
                                                continue;
                                            }

                                            if (checkTypeBoxes())
                                            {
                                                // This Currently is for types only
                                                if (typeText.contains("," + typeText + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }

                                                    if (levelCheck())
                                                    {
                                                        if (level().contains("," + level + ","))
                                                        {
                                                            if (element.contains("none"))
                                                            {
                                                                continue;
                                                            }
                                                            if (rarityCheck())
                                                            {
                                                                if (rarity().contains("," + rarity + ","))
                                                                {
                                                                    if (element.contains("none"))
                                                                    {
                                                                        continue;
                                                                    }
                                                                    if (element.equals(currentCard))
                                                                    {
                                                                        loadImages(eElement, i, j);
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
                                                        }
                                                    }
                                                    else
                                                    {
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                            }
                                            else
                                            {
                                                if (levelCheck())
                                                {
                                                    if (level().contains("," + level + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                                else
                                                {
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                    else
                                                    {
                                                        if (element.equals(currentCard))
                                                        {
                                                            loadImages(eElement, i, j);
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
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (checkTypeBoxes())
                                        {
                                            // This Currently is for types only
                                            if (typeText.contains("," + typeText + ","))
                                            {
                                                if (element.contains("none"))
                                                {
                                                    continue;
                                                }

                                                if (levelCheck())
                                                {
                                                    if (level().contains("," + level + ","))
                                                    {
                                                        if (element.contains("none"))
                                                        {
                                                            continue;
                                                        }
                                                        if (rarityCheck())
                                                        {
                                                            if (rarity().contains("," + rarity + ","))
                                                            {
                                                                if (element.contains("none"))
                                                                {
                                                                    continue;
                                                                }
                                                                if (element.equals(currentCard))
                                                                {
                                                                    loadImages(eElement, i, j);
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
                                                    }
                                                }
                                                else
                                                {
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                        }
                                        else
                                        {
                                            if (levelCheck())
                                            {
                                                if (level().contains("," + level + ","))
                                                {
                                                    if (element.contains("none"))
                                                    {
                                                        continue;
                                                    }
                                                    if (rarityCheck())
                                                    {
                                                        if (rarity().contains("," + rarity + ","))
                                                        {
                                                            if (element.equals(currentCard))
                                                            {
                                                                loadImages(eElement, i, j);
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
                                                }
                                            }
                                            else
                                            {
                                                if (rarityCheck())
                                                {
                                                    if (rarity().contains("," + rarity + ","))
                                                    {
                                                        if (element.equals(currentCard))
                                                        {
                                                            loadImages(eElement, i, j);
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
                                                else
                                                {
                                                    if (element.equals(currentCard))
                                                    {
                                                        loadImages(eElement, i, j);
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
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // --------------------------------- Text Searching ----------------//

    // Text Searching
    public void textSearch()
    {
        clearGrid();
        try
        {
            // Grid location variables
            int j = 0, i = 0;

            // Parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            // Element tags to go over
            NodeList nodeList = doc.getElementsByTagName("card");
            doc.getDocumentElement().normalize();

            XPathFactory xFactory = XPathFactory.newInstance();
            XPath xPath = xFactory.newXPath();
            String searchVar = searchField.getText();

            XPathExpression exp = xPath.compile("/class/card/cardName[contains"
                    + "(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', " +
                    "'abcdefghijklmnopqrstuvwxyz'), 'yokomon')]");

            NodeList nl = (NodeList)exp.evaluate(doc.getFirstChild(),
                    XPathConstants.NODESET);
            for (int index = 0; index < nl.getLength(); index++)
            {
                // nodeList is not iterable, so we are using for loop
                for (int itr = 0; itr < nodeList.getLength(); itr++)
                {
                    Node node = nodeList.item(itr);         // "Object" in XML

                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) node;  // Element in the XML

                        // Load the currently selected image into the view
                        String element = eElement.getElementsByTagName
                                ("cardName").item(0).getTextContent();
                        String element2 = eElement.getElementsByTagName
                                ("cardNumber").item(0).getTextContent();
                        String element3 = eElement.getElementsByTagName
                                ("color").item(0).getTextContent();
                        String element4 = eElement.getElementsByTagName
                                ("form").item(0).getTextContent();
                        String element5 = eElement.getElementsByTagName
                                ("type").item(0).getTextContent();
                        String element6 = eElement.getElementsByTagName
                                ("rarity").item(0).getTextContent();
                        String element7 = eElement.getElementsByTagName
                                ("inheritedEffect").item(0).getTextContent();
                        String element8 = eElement.getElementsByTagName
                                ("level").item(0).getTextContent();

                        if (element.toLowerCase()
                                .contains(searchVar.toLowerCase()) || element2.
                                toLowerCase().contains(searchVar.toLowerCase())
                                || element3.toLowerCase().contains(searchVar.
                                toLowerCase()) || element4.toLowerCase().
                                contains(searchVar.toLowerCase()) || element5.
                                toLowerCase().contains(searchVar.toLowerCase())
                                || element6.toLowerCase().contains(searchVar.
                                toLowerCase()) || element7.toLowerCase().
                                contains(searchVar.toLowerCase()) || element8.
                                toLowerCase().contains(searchVar.toLowerCase()))
                        {
                            loadImages(eElement, i, j);
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
            }
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

    // --------------------------------- Text Changing -----------------//

    // These will change the split menu
    // sorting method.
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

    // -------------------------- Actions ------------------------------//
    // Changes the detailed search button
    // to search if one is selected, or return
    // to close if none.
    public void changeDetailedButton()
    {
        if (!Objects.equals(typeBoxes(), ",") || colorBoxes() || dvColorBoxes()
                || cardTypeBoxes() || attributeBoxes() || levelCheck() ||
                rarityCheck())
        {
            detailedSortSearch.setText("Search");
        }
        else
            detailedSortSearch.setText("Close");
    }
    // increase or decrease the count for
    // owned cards.
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
        try
        {
            // Parse the specified xml file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("card");

            // Loop over the entire file
            // Compare the setNumber to cardNumber
            for (int itr = 0; itr < nodeList.getLength(); itr++)
            {
                Node node = nodeList.item(itr);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element eElement = (Element) node;

                    if (eElement.getElementsByTagName("cardNumber").item(0).getTextContent().contains(setNumber.getText()))
                    {
                        eElement.getElementsByTagName("cardsOwn").item(0).setTextContent(String.valueOf(count));
                    }
                }
            }

            // write the content to the xml file
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource src = new DOMSource(doc);
            StreamResult res = new StreamResult(new File(String.valueOf(file)));
            transformer.transform(src, res);
        }
        catch (ParserConfigurationException | IOException | SAXException | TransformerException e)
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
    public boolean checkTypeBoxes()
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
    public String typeBoxes()
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
    public String level()
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
    // cardEffect
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