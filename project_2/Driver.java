
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.util.Scanner;

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
		Element rootElement = document.getDocumentElement().normalize();
		
		System.out.println("Type the corresponding letter for the desired function:");
		System.out.println("Calculate number of PERSONA: P");
		System.out.println("Calculate number of times SPEAKER acts: S");
		System.out.println("Search/Replace fragement: F");

		String function = input.nextLine();
		if (function == 'P' || function == 'p') {
			System.out.println("Number of total PERSONA: " + handler.getPersonaTotal(rootElement));
		} else if (function == 'S' || function == 's') {
			System.out.println("Enter SPEAKER name, press ENTER for default (HAMLET)");
			String speaker = input.nextLine();
			if (speaker == "") {
				System.out.println("SPEAKER [HAMLET] appears: " + handler.getActNumberOf(rootElement) + " times.");
			} else {
				System.out.println("SPEAKER [" + speaker + "] appears: " +handler.getActNumberOf(rootElement, speaker) + " times.");
			}
		} else if (function == 'F' || function == 'f') {
			System.out.println("Enter fragment to search:");
			String fragment = input.nextLine();
			handler.searchFragment(document, fragment);
		}

	}
	
}
