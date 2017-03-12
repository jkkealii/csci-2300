
import java.io.File;
import java.io.IOException;

import javax.xml.transform.*;
import javax.xml.xpath.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.util.Scanner;

public class Driver {

	public static void main(String argv[]) throws XPathExpressionException, TransformerConfigurationException, SAXException, IOException, ParserConfigurationException {
		Scanner input = new Scanner(System.in);
        System.out.println("Default XML file is hamlet.xml, do you wish to use a different file? (Y/N)");
        String choice = input.nextLine();
        String filename;
        if (choice.equals("Y") || choice.equals("y")) {
            System.out.println("Enter the name of the XML file to be read: ");
            filename = input.nextLine();
        } else {
        	filename = "hamlet.xml";
        }
        File xmlFile = new File(filename);
		
        Handler handler = new Handler();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document document = documentBuilder.parse(xmlFile);
		Element rootElement = document.getDocumentElement();
		
		System.out.println("Type the corresponding letter for the desired function:");
		System.out.println("Calculate number of PERSONA: P");
		System.out.println("Calculate number of times SPEAKER acts: S");
		System.out.println("Search/Replace fragment: F");

		String function = input.nextLine();
		if (function.equals("P") || function.equals("p")) {
			System.out.println("Number of total PERSONA: " + handler.getPersonaTotal(rootElement));
		} else if (function.equals("S") || function.equals("s")) {
			System.out.println("Enter SPEAKER name, press ENTER for default (HAMLET)");
			String speaker = input.nextLine().toUpperCase();
			if (speaker.equals("")) {
				System.out.println("SPEAKER [HAMLET] appears: " + handler.getActNumberOf(rootElement, "HAMLET") + " times.");
			} else {
				System.out.println("SPEAKER [" + speaker + "] appears: " +handler.getActNumberOf(rootElement, speaker) + " times.");
			}
		} else if (function.equals("F") || function.equals("f")) {
			System.out.println("Enter fragment to search:");
			String fragment = input.nextLine();
			handler.searchFragment(document, fragment);
		} else {
			System.out.println("No function chosen. See ya.");
		}
	}
	
}
