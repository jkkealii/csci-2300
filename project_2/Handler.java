import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

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
            System.out.println("Search performed in " + time + " seconds");
            System.out.println("Do you want to replace it? (Y/N)");

        } catch (Exception ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Sorry, fragment not found. Search performed in " + time + " seconds")
        }
	}

	public static void replaceWith (Document doc, String fragment, String alt) {
		String filepath = "/project_2/";
		XPathFactory xFactory = XPathFactory.newInstance();
        XPath xPath = xFactory.newXPath();
        XPathExpression exp = xPath.compile("/PLAY/ACT/SCENE/SPEECH/LINE[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " + fragment + ")]");

        NodeList nl = (NodeList)exp.evaluate(doc.getFirstChild(), XPathConstants.NODESET);
        for (int index = 0; index < nl.getLength(); index++) {
            Node node = nl.item(index);
            String original = node.getTextContent();

            String regex = fragment;
			String sourceString = node.getTextContent();

			Pattern pat = Pattern.compile(regex);
			Matcher match = pat.matcher(sourceString);
			StringBuffer sb = new StringBuffer();

			while (match.find()) {
			    String replacement = alt;
			    match.appendReplacement(sb, replacement);
			}
			match.appendTail(sb);
			sourceString = sb.toString();

			System.out.println("The sentence has been replaced as follows:");
			System.out.println(sourceString);
            System.out.println("Do you want to save the changes? (Y/N)");
            // if yes:
            System.out.println("Enter the name of the saved file (Press ENTER to overwrite)");
            // else don't make changes

            // if user makes new file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			replaceWith(result, fragment, alt);
            System.out.println("File saved successfully.");

            // if user wants to overwrite
            System.out.println("Do you want to overwrite the file hamlet.xml? (Y/N)");
            node.setTextContent(sourceString);
            System.out.println("File saved successfully.");
            // if not, no changes
        }
	}

	public static void replaceWith (File xml, String fragment, String alt) {
		File xmlFile = xml;
		Handler handler = new Handler();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document doc = documentBuilder.parse(xml);

		XPathFactory xFactory = XPathFactory.newInstance();
        XPath xPath = xFactory.newXPath();
        XPathExpression exp = xPath.compile("/PLAY/ACT/SCENE/SPEECH/LINE[contains(translate(text(), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), " + fragment + ")]");

        NodeList nl = (NodeList)exp.evaluate(doc.getFirstChild(), XPathConstants.NODESET);
        for (int index = 0; index < nl.getLength(); index++) {
            Node node = nl.item(index);
            String original = node.getTextContent();

            String regex = fragment;
			String sourceString = node.getTextContent();

			Pattern pat = Pattern.compile(regex);
			Matcher match = pat.matcher(sourceString);
			StringBuffer sb = new StringBuffer();

			while (match.find()) {
			    String replacement = alt;
			    match.appendReplacement(sb, replacement);
			}
			match.appendTail(sb);
			sourceString = sb.toString();

            node.setTextContent(sourceString);
        }
	}

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
