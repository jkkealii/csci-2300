
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Driver {

	public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {
		File xmlFile = new File("sample.xml"); // needs to prompt user
		Handler handler = new Handler();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		Element rootElement = document.getDocumentElement().normalize();
		
		handler.handleChannelTag(document);
	}
	
}
