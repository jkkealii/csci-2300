package application;
	
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;


public class Main extends Application{
	@Override
	public void start(Stage primaryStage) throws XPathExpressionException, TransformerConfigurationException, SAXException, IOException, ParserConfigurationException{
		try {
			Pane root = new Pane();
			Label fileLabel = new Label("Filename:");
			fileLabel.setLayoutX(0);
			fileLabel.setLayoutY(0);
			TextField textField = new TextField();
			textField.setLayoutX(75);
			textField.setLayoutY(0);
			Button genHandle = new Button("Submit filename");
			genHandle.setLayoutX(275);
			genHandle.setLayoutY(0);
			Button search = new Button("Search for Fragment");
			search.setLayoutX(200);
			search.setLayoutY(50);
			Button persona = new Button("Get total number of Persona");
			persona.setLayoutX(200);
			persona.setLayoutY(150);
			Button act = new Button("Get number of Acts");
			act.setLayoutX(200);
			act.setLayoutY(250);
			Button node = new Button("Get node value");
			node.setLayoutX(200);
			node.setLayoutY(350);
			Button replace = new Button("Replace fragment?");
			replace.setLayoutX(200);
			replace.setLayoutY(450);
			
			root.getChildren().addAll(search, persona, act, node, replace, fileLabel, textField, genHandle);
			
			
			search.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					System.out.println("Search button pressed.");
				}
				
			});
			persona.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					System.out.println("Persona button pressed.");
				}
				
			});
			act.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					System.out.println("Act button pressed.");
				}
				
			});
			node.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					System.out.println("Node button pressed.");
				}
				
			});
			replace.setOnAction( new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					System.out.println("Replace button pressed.");
				}
				
			});
			genHandle.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					File xmlFile = new File(textField.getText());
					Handler handler = new Handler(textField.getText());
					DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
					try{
						DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
						Document document = documentBuilder.parse(xmlFile);
						Element rootElement = document.getDocumentElement();
					} catch (SAXException | IOException | ParserConfigurationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Generated handler and xmlFile.");
				}
				
			});
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
