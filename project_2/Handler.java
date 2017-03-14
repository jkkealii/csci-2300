import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.regex.*;

import java.io.File;
import java.io.IOException;

import javax.xml.xpath.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NameList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.util.Scanner;

public class Handler {
	private static String filename;

    public Handler (String filename) {
        this.filename = filename;
    }

    public static int getPersonaTotal (Element root) {
        NodeList personaList = root.getElementsByTagName("PERSONA");
        return personaList.getLength();
    }

    public static int getActNumberOf (Node root, String character) {
        String speaker = character;
        NodeList speakerList = root.getChildNodes();
        int actNumber = 0;
        Node child;
        for (int i = 0; i < speakerList.getLength(); i++) {
            child = speakerList.item(i);
            if (child instanceof Element) {
                actNumber += getActNumberOf(child, speaker);
            }
            if (child.getNodeName().equals("SPEAKER") && getNodeValue(child).equals(speaker)) {
                actNumber++;
            }
        }
        return actNumber;
    }

    public static String getNodeValue (Node node) {
        StringBuffer buf = new StringBuffer();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node textChild = children.item(i);
            if (textChild.getNodeType() != Node.TEXT_NODE) {
                System.err.println("Mixed content! Skipping child element " + textChild.getNodeName());
                continue;
            }
            buf.append(textChild.getNodeValue());
        }
        return buf.toString();
    }

    public static void searchFragment (Document doc, String fragment) throws TransformerConfigurationException, XPathExpressionException {
        String lowercaseFragment = fragment.toLowerCase();
        Scanner input = new Scanner(System.in);
        double startTime,endTime,time;
        startTime = System.currentTimeMillis();
        String lineExpression = "/PLAY/ACT/SCENE/SPEECH/LINE[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '" + lowercaseFragment + "')]";
        try {
            XPathFactory xFactory = XPathFactory.newInstance();
            XPath xPath = xFactory.newXPath();
            XPathExpression exp = xPath.compile(lineExpression);

            NodeList nl = (NodeList)exp.evaluate(doc.getFirstChild(), XPathConstants.NODESET);
            if (nl.getLength() < 1) {
            	throw new Exception("FRAGMENT NOT FOUND");
            }

            endTime = System.currentTimeMillis();
            time = (endTime - startTime)/1000;
            System.out.println("Search performed in " + time + " seconds");
            for (int index = 0; index < nl.getLength(); index++) {
            	if (index == 0) {
            		System.out.println("FOUND: " + nl.getLength() + " occurrence(s) of this fragment");
                	System.out.println("The fragment has been found in the following sentence:");           		
            	} else {
                	System.out.println("Another fragment has been found in the following sentence:");
            	}
                Node node = nl.item(index);
                System.out.println(node.getTextContent());
            
                System.out.println("Do you want to replace it? (Y/N)");
                String answer = input.nextLine();
                if (answer.equals("Y") || answer.equals("y")) {
                    System.out.println("Enter replacement string:");
                    String alt = input.nextLine();
                    replaceWith(node, fragment, alt);

                    System.out.println("The sentence has been replaced as follows:");
                    System.out.println(node.getTextContent());

                    System.out.println("Do you want to save the changes? (Y/N)");
                    String save = input.nextLine();

                    if (save.equals("Y") || save.equals("y")) {
                        System.out.println("Enter the name of the saved file (Press ENTER to overwrite)");
                        String newName = input.nextLine();

                        if (newName.equals("")) {
                            System.out.println("Do you want to overwrite the file? (Y/N)");
                            String overwrite = input.nextLine();

                            if (overwrite.equals("Y") || overwrite.equals("y")) {
                            	TransformerFactory transformerFactory = TransformerFactory.newInstance();
	                            Transformer transformer = transformerFactory.newTransformer();
	                            DOMSource source = new DOMSource(doc);
	                            StreamResult result = new StreamResult(new File(filename));
	                            transformer.transform(source, result);

                                System.out.println("File saved successfully.");
                            }
                        } else {
                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                            Transformer transformer = transformerFactory.newTransformer();
                            DOMSource source = new DOMSource(doc);
                            StreamResult result = new StreamResult(new File(newName));
                            transformer.transform(source, result);

                            replaceWith(node, alt, fragment);

                            System.out.println("File saved successfully.");
                        }
                    }
                }
            }
            
        } catch (Exception ex) {
            endTime = System.currentTimeMillis();
            time = (endTime - startTime)/1000;
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorry, fragment not found. Search performed in " + time + " seconds");
        }
    }

    public static void replaceWith (Node node, String fragment, String alt) throws IOException, SAXException, XPathExpressionException, TransformerConfigurationException, TransformerException, ParserConfigurationException {
        String nodeText = node.getTextContent();

        Pattern p = Pattern.compile(fragment, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(nodeText);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String replacement = alt;
            m.appendReplacement(sb, replacement);
        }

        m.appendTail(sb);

        nodeText = sb.toString();
        node.setTextContent(nodeText);
    }
}
