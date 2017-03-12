
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.Scanner
public class Driver {

	public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {
		Scanner input = new Scanner(System.in);
        System.out.println("Default XML file is hamlet.xml, do you wish to use a different file? (Y/N)");
        String choice = input.nextLine();
        String filename = "sample.xml";
        if(choice == 'Y' || choice == 'y'){
            System.out.println("Enter the name of the XML file to be read: ");
            filename = input.nextLine();
        }
        File xmlFile = new File(filename);
		
        Handler handler = new Handler();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		document.getDocumentElement().normalize();
		handler.handleChannelTag(document);
	}
	
}
