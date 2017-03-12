import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NameList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Handler {

	public Handler () {
		
	}

	public static int getPersonaTotal (Element root) {
		NodeList personaList = root.getElementsByTagName("PERSONA");
		return personaList.getLength();
	}

	public static int getActNumberOf (Element root, String speaker) {
		String speaker = speaker.toUpperCase();
		NodeList speakerList = root.getElementsByTagName("SPEAKER");
		int actNumber = 0;
		Element child;
		for (int i = 0; i < speakerList.getLength(); i++) {
			child = (Element) speakerList.item(i);
			if (getString("SPEAKER", child) == speaker) {
				actNumber++;
			}
		}
		return actNumber.getLength();
	}

	public static int getActNumberOf (Element root) { // default speaker is hamlet
		String speaker = "HAMLET";
		NodeList speakerList = root.getElementsByTagName(speaker);
		int actNumber = 0;
		Element child;
		for (int i = 0; i < speakerList.getLength(); i++) {
			child = (Element) speakerList.item(i);
			if (getString("SPEAKER", child) == speaker) {
				actNumber++;
			}
		}
		return actNumber.getLength();
	}

	public static String searchFragment (Document doc, String fragment) {
		double startTime,endTime,time;
        startTime = System.currentTimeMillis();
        try {
            XPathFactory xFactory = XPathFactory.newInstance();
            XPath xPath = xFactory.newXPath();
            XPathExpression exp = xPath.compile("/PLAY/ACT/SCENE/SPEECH/LINE[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " + fragment + ")]");

            NodeList nl = (NodeList)exp.evaluate(doc.getFirstChild(), XPathConstants.NODESET);
            for (int index = 0; index < nl.getLength(); index++) {
            	System.out.println("The fragment has been found in the following sentence:");
                Node node = nl.item(index);
                System.out.println(node.getTextContent());
            }
            endTime = System.currentTimeMillis();
            time = (endTime - startTime)/1000;
            System.out.println("Search performed in " + time + " seconds");
            System.out.println("Do you want to replace it? (Y/N)");

        } catch (Exception ex) {
            endTime = System.currentTimeMillis();
            time = (endTime - startTime)/1000;
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorry, fragment not found. Search performed in " + time + " seconds")
        }
	}

	// public static void replaceWith (String alt) {

	// }

	protected String getString (String tagName, Element root) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }

        return null;
    }

	public static void handleChannelTag(Document document) {
		System.out.println("<" + document.getDocumentElement().getNodeName() + ">");
		System.out.println("\t<name>" + document.getElementsByTagName("name").item(0).getTextContent() + "</name>");
		NodeList nodeList = document.getElementsByTagName("topic");
		handleTopicTag(nodeList, "\t");		
		System.out.println("</" + document.getDocumentElement().getNodeName() + ">");
	}
		
	private static void handleTopicTag(NodeList nodeList, String tab){
		for (int i=0; i< nodeList.getLength(); i++){
			System.out.println(tab+"<topic>");
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE){
				Element element = (Element) node;
				System.out.println(handleTextTag("name", element, "\t\t"));
				System.out.println(handleTextTag("tutorial", element, "\t\t"));
			}
			System.out.println(tab+"</topic>");
		
		}
	}
	
	private static String handleTextTag(String tagName, Element element, String tab){
		StringBuffer returnValue = new StringBuffer();
		for (int i = 0; i < element.getElementsByTagName(tagName).getLength(); i++){
			NodeList nodeList = element.getElementsByTagName(tagName).item(i).getChildNodes();
			Node node = (Node) nodeList.item(0);
			if (i == 0) returnValue.append(tab+"<" + tagName + ">" + node.getNodeValue() + "</" + tagName + ">");
			else returnValue.append("\n" +tab+"<" + tagName + ">" + node.getNodeValue() + "</" + tagName + ">");
		}
		
		return returnValue.toString();
	}
	
}
