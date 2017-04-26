/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrl;

import java.awt.Color;
import javafx.scene.paint.Paint;
import java.util.*;
import java.lang.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.shape.*;
import javafx.scene.chart.*;
import javafx.collections.*;

/**
 *
 * @author lemonjello
 */
public class VRLController implements Initializable{
 
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML
    private Rectangle color_view;
    
    @FXML
    private TextField wavelength_value;
    private int wavelength;
    
    @FXML
    private ComboBox material_type;
    private String material;
    
    @FXML
    private TextField ref_index;
    private double refraction;
    
    @FXML
    private TextField acoustic_vel;
    private double velocity;
    
    @FXML
    private TextField q_factor;
    private double q;
    
    @FXML
    private TextField regime;
    private String regimeName;
    
    @FXML
    private TextField diff_efficiency;
    private double d_e;
    
    @FXML
    private TextField internal_radians;
    private double int_rad;
    
    @FXML
    private TextField internal_degrees;
    private double int_deg;
    
    @FXML
    private TextField external_radians;
    private double ext_rad;
    
    @FXML
    private TextField external_degrees;
    private double ext_deg;
    
    @FXML
    private TextField acoustic_length;
    private double length;
    
    @FXML
    private TextField acoustic_height;
    private double height;
    
    @FXML
    private TextField figure_merit;
    private double fig_merit;
    
    @FXML
    private LineChart RF_graph;
    
    @FXML
    private TextField voltage;
    private double volts;
    
    @FXML
    private TextField vrms;
    private double v_rms;
    
    @FXML
    private TextField frequency;
    private double freq;
    
    @FXML
    private TextField rf_power;
    private double watts;

    @FXML
    private TextField irms;
    private double i_rms;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rss) {
        ObservableList<String> materials = 
        FXCollections.observableArrayList(
            "Ge",
            "Doped Glass",
            "Ge33AS12SE55",
            "As2S3",
            "PbMoO4",
            "TeO2 (Linear)",
            "TeO2 (Linear-Circular)",
            "SiO2 (Linear)",
            "SiO2 (Unpolarized)"
        );
        System.out.println(this.getClass().getSimpleName() + ".initialize");
        material_type.getItems().removeAll(material_type.getItems());
        material_type.getItems().addAll(materials);
        material_type.getSelectionModel().select("As2S3");
        v_rms = 12.0;
    }
    
    @FXML
    void materialChosen (ActionEvent event) {
        
        if (material_type.getValue().equals("Ge")) {
            ref_index.setText("4");
            refraction = 4.0;
            acoustic_vel.setText("5500");
            velocity = 5500;
            figure_merit.setText("180");
            fig_merit = 180;
        } else if (material_type.getValue().equals("Doped Glass")) {
            ref_index.setText("2.09");
            refraction = 2.09;
            acoustic_vel.setText("3400");
            velocity = 3400;
            figure_merit.setText("24");
            fig_merit = 24;
        } else if (material_type.getValue().equals("Ge33AS12SE55")) {
            ref_index.setText("2.59");
            refraction = 2.59;
            acoustic_vel.setText("2520");
            velocity = 2520;
            figure_merit.setText("248");
            fig_merit = 248;
        } else if (material_type.getValue().equals("As2S3")) {
            ref_index.setText("2.46");
            refraction = 2.46;
            acoustic_vel.setText("2600");
            velocity = 2600;
            figure_merit.setText("433");
            fig_merit = 433;
        } else if (material_type.getValue().equals("PbMoO4")) {
            ref_index.setText("2.26/2.38");
            refraction = 2.38;
            acoustic_vel.setText("3630");
            velocity = 3630;
            figure_merit.setText("36");
            fig_merit = 36;
        } else if (material_type.getValue().equals("TeO2 (Linear)")) {
            ref_index.setText("2.26");
            refraction = 2.26;
            acoustic_vel.setText("4200");
            velocity = 4200;
            figure_merit.setText("34");
            fig_merit = 34;
        } else if (material_type.getValue().equals("TeO2 (Linear-Circular)")) {
            ref_index.setText("2.26");
            refraction = 2.26;
            acoustic_vel.setText("620");
            velocity = 620;
            figure_merit.setText("1200");
            fig_merit = 1200;
        } else if (material_type.getValue().equals("SiO2 (Linear)")) {
            ref_index.setText("1.46");
            refraction = 1.46;
            acoustic_vel.setText("5960");
            figure_merit.setText("1.5");
            fig_merit = 1.5;
        } else if (material_type.getValue().equals("SiO2 (Unpolarized)")) {
            ref_index.setText("1.46");
            refraction = 1.46;
            acoustic_vel.setText("3760");
            velocity = 3760;
            figure_merit.setText("0.5");
            fig_merit = 0.5;
        }
    }
    
    @FXML
    void wavelengthChosen (ActionEvent event) {
        wavelength = Integer.parseInt(wavelength_value.getText());
        if (wavelength < 200 || wavelength > 11000) {
            wavelength = 700;
        } else {
            if (wavelength > 380 && wavelength <= 450) {
                color_view.setFill(Paint.valueOf("magenta"));
            } else if (wavelength > 450 && wavelength <= 495) {
                color_view.setFill(Paint.valueOf("blue"));
            } else if (wavelength > 495 && wavelength <= 570) {
                color_view.setFill(Paint.valueOf("green"));
            } else if (wavelength > 570 && wavelength <= 590) {
                color_view.setFill(Paint.valueOf("yellow"));
            } else if (wavelength > 590 && wavelength <= 620) {
                color_view.setFill(Paint.valueOf("orange"));
            } else if (wavelength > 620 && wavelength <= 750) {
                color_view.setFill(Paint.valueOf("red"));
            }
        }
    }
    
    @FXML
    void lengthHeightChosen (ActionEvent event) {
        double input = Double.parseDouble(acoustic_length.getText());
        if (input > 0.0 && input < 10.0) {
            length = input;
        } else {
            acoustic_length.setText("5.0");
        }
        
        double h = Double.parseDouble(acoustic_height.getText());
        if (h > 0.0 && h < 10.0) {
            height = h;
        } else {
            acoustic_height.setText("5.0");
        }
    }
    
    @FXML
    void voltFreqPower (ActionEvent event) {
        voltageCalc(event);
        frequencyChosen(event);
        powerChosen(event);
    }
    
    @FXML
    void voltageCalc (ActionEvent event) {
        double input = Double.parseDouble(voltage.getText());
        if (input > 0.0 && input < 24.0) {
            volts = input;
            v_rms = volts / Math.sqrt(2);
            vrms.setText(String.valueOf(v_rms));
        } else {
            vrms.setText("12.0");
        }
    }
    
    @FXML
    void frequencyChosen (ActionEvent event) {
        double input = Double.parseDouble(frequency.getText());
        if (input > 1.0 && input < 1000.0) {
            freq = input;
        } else {
            frequency.setText("1.0");
        }
    }
    
    @FXML
    void powerChosen (ActionEvent event) {
        double input = Double.parseDouble(rf_power.getText());
        if (input > 1.0 && input < 2.0) {
            watts = input;
            i_rms = watts / v_rms;
            irms.setText(String.valueOf(i_rms));
        } else {
            rf_power.setText("1.1");
        }
    }
    
    @FXML
    void qFactor (ActionEvent event) {
        q = (2 * Math.PI * wavelength * length * freq * freq) / (refraction * velocity);
        q_factor.setText(String.valueOf(q));
        double mult = Math.sin(Math.sqrt((Math.PI * Math.PI) / 2 * wavelength * wavelength) * fig_merit * (length / height) * watts);
        d_e = mult * mult;
        diff_efficiency.setText(String.valueOf(d_e));
        if (q > 1.0) {
            int_rad = (wavelength * freq) / (2 * refraction * velocity);
            int_deg = int_rad * (180/Math.PI);
            ext_rad = refraction * int_rad;
            ext_deg = ext_rad * (180/Math.PI);
            regime.setText("Bragg");
            internal_radians.setText(String.valueOf(int_rad));
            internal_degrees.setText(String.valueOf(int_deg));
            external_radians.setText(String.valueOf(ext_rad));
            external_degrees.setText(String.valueOf(ext_deg));
        } else {
            regime.setText("Raman-Nath");
        }
    }
}
