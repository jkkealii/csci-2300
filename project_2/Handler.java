package xmlparsingtutorial;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NameList;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Handler {

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
