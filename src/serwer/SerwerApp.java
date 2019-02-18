package serwer;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.prism.paint.Paint;

import dane.Odpowiedz;
import dane.Pytanie;
import dane.Test;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.ObjectExpression;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SerwerApp extends Application {

    ObservableList<Test> data = FXCollections.observableArrayList();
    ObservableList<Pytanie> dataPytania = FXCollections.observableArrayList();
    ObservableList<Odpowiedz> dataOdpowiedzi = FXCollections.observableArrayList();
    Test wybranyTest = null;
    Pytanie wybranePytanie = null;
    Odpowiedz wybranaOdpowiedź = null;

    boolean edytujTest = false;
    boolean edytujPytanie = false;
    boolean edytujOdpowiedź = false;
    

    boolean tno = false;
    boolean tao = false;
    boolean too = false;
    boolean taop = false;
    boolean toop = false;
    boolean taoeo = false;
    boolean ttoeo = false;
    boolean taodo = false;
    boolean ttodo = false;
	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
        primaryStage.setTitle("Czarodziejski Test - Serwer");
        Serwer serwer = new Serwer(753);
        Serwer serwer2 = new Serwer(754);

        Group ekranGłówny = new Group();
        Group listaTestów = new Group();
        Group listaPytań = new Group();
        Group listaOdpowiedzi = new Group();
    	Group oknoDodawania = new Group();
    	Group oknoDodawaniaPytania = new Group();
    	Group oknoDodawaniaOdpowiedzi = new Group();
    	Group oknoEdycjiOdpowiedzi = new Group();
    	Group oknoInfo = new Group();
    	Group oknoStart = new Group();
        listaTestów.setVisible(false);
        listaPytań.setVisible(false);
        listaOdpowiedzi.setVisible(false);
        oknoDodawania.setVisible(false);
        oknoDodawaniaPytania.setVisible(false);
        oknoDodawaniaOdpowiedzi.setVisible(false);
        oknoEdycjiOdpowiedzi.setVisible(false);
        oknoInfo.setVisible(false);
        oknoStart.setVisible(false);
        

        Label labelNazwa = new Label("Nazwa");
        TextField textNazwa = new TextField ();
        Label labelAutor = new Label("Autor");
        TextField textAutor = new TextField ();
        Label labelOpis = new Label("Opis");
        TextArea textOpis = new TextArea ();
        Label labelAutorPytanie = new Label("Autor");
        TextField textAutorPytanie = new TextField ();
        Label labelTekstPytanie = new Label("Treść");
        TextArea textTekstPytanie = new TextArea ();

        ///////////konsola
    	@SuppressWarnings("deprecation")
		TextArea ta = TextAreaBuilder.create()
    		    .prefWidth(400)
    		    .prefHeight(600)
    		    .wrapText(true)
    		    .build();
        ta.setLayoutX(400);
        ta.setLayoutY(0);
        
        Button btnToggle = new Button();
        btnToggle.setText("-");
        btnToggle.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                ta.setVisible(!ta.isVisible());
                if(btnToggle.getText().equals("-"))
                    btnToggle.setText("+");
                else
                    btnToggle.setText("-");
            }
        });
        btnToggle.setLayoutX(760);
        btnToggle.setLayoutY(0);
        btnToggle.getStyleClass().add("button1");
        ///////////koniec = konsola

	        ///////////okno start
	        oknoStart.getChildren().add(new Rectangle(400, 300, new Color(1f, 1f, 1f, .85f)));
	        Label labelUserOknoStart = new Label("User");
	        TextField textUserOknoStart = new TextField ();
	        textUserOknoStart.setText("root");
	        Label labelPassOknoStart = new Label("Pass");
	        TextField textPassOknoStart = new TextField ();
	        textPassOknoStart.setText("");
	        Label labelAdresOknoStart = new Label("Adres");
	        TextField textAdresOknoStart = new TextField ();
	        textAdresOknoStart.setText("localhost");
	        Label labelBazaOknoStart = new Label("Baza");
	        TextField textBazaOknoStart = new TextField ();
	        textBazaOknoStart.setText("czarodziejskitest");
	        Button btn000 = new Button();
	        btn000.setText("X");
	        btn000.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	oknoStart.setVisible(false);
	            }
	        });
	        btn000.setLayoutX(10);
	        btn000.setLayoutY(220);
	        btn000.getStyleClass().add("buttonKwadrat");
	        oknoStart.getChildren().add(btn000);
	        
	        Button btn001 = new Button();
	        btn001.setText(">");
	        btn001.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("Start serwera");
	                Platform.runLater(new Runnable() {
	                    public void run() {
	                		JDBC.init(textAdresOknoStart.getText(), textUserOknoStart.getText(), textPassOknoStart.getText(), textBazaOknoStart.getText());
	                		JDBC.initTest();
	                    }
	                });
	                serwer.start();
	                serwer2.start();
	            	oknoStart.setVisible(false);
	            }
	        });
	        btn001.setLayoutX(310);
	        btn001.setLayoutY(220);
	        btn001.getStyleClass().add("buttonKwadrat");
	        oknoStart.getChildren().add(btn001);
	        labelUserOknoStart.setLayoutX(10);
	        labelUserOknoStart.setLayoutY(10);
	        textUserOknoStart.setLayoutX(10);
	        textUserOknoStart.setLayoutY(40);
	        labelPassOknoStart.setLayoutX(200);
	        labelPassOknoStart.setLayoutY(10);
	        textPassOknoStart.setLayoutX(200);
	        textPassOknoStart.setLayoutY(40);
	        labelAdresOknoStart.setLayoutX(10);
	        labelAdresOknoStart.setLayoutY(80);
	        textAdresOknoStart.setLayoutX(10);
	        textAdresOknoStart.setLayoutY(110);
	        labelBazaOknoStart.setLayoutX(200);
	        labelBazaOknoStart.setLayoutY(80);
	        textBazaOknoStart.setLayoutX(200);
	        textBazaOknoStart.setLayoutY(110);
	        oknoStart.setLayoutX(0);
	        oknoStart.setLayoutY(190);
	        oknoStart.getChildren().add(labelUserOknoStart);
	        oknoStart.getChildren().add(textUserOknoStart);
	        oknoStart.getChildren().add(labelPassOknoStart);
	        oknoStart.getChildren().add(textPassOknoStart);
	        oknoStart.getChildren().add(labelAdresOknoStart);
	        oknoStart.getChildren().add(textAdresOknoStart);
	        oknoStart.getChildren().add(labelBazaOknoStart);
	        oknoStart.getChildren().add(textBazaOknoStart);
	        ///////////koniec = okno start
        ///////////ekran główny
        ekranGłówny.getChildren().add(new Rectangle(800, 600, new Color(.8f, .8f, .8f, 1f)));
        Button btn = new Button();
        
        btn.setText("Uruchom Serwer");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	oknoStart.setVisible(true);
            }
        });
        btn.setLayoutX(10);
        btn.setLayoutY(10);
        ekranGłówny.getChildren().add(btn);

        Button btn2 = new Button();
        btn2.setText("Wyjście");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                System.out.println("Kończę pracę");
                if(serwer.isAlive())
                	serwer.close();
                if(serwer2.isAlive())
                	serwer2.close();
                primaryStage.close();
            }
        });
        btn2.setLayoutX(10);
        btn2.setLayoutY(510);
        ekranGłówny.getChildren().add(btn2);
        
        Button btn3 = new Button();
        btn3.setText("Lista Testów");
        btn3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				ekranGłówny.setVisible(false);
				btnToggle.setText("+");
                ta.setVisible(false);
				listaTestów.setVisible(true);
            	data.clear();
            	data.addAll(JDBC.pobierzTesty());
            }
        });
        btn3.setLayoutX(10);
        btn3.setLayoutY(100);
        ekranGłówny.getChildren().add(btn3);
        ekranGłówny.getChildren().add(oknoStart);
    	///////////koniec = ekran główny
        
        

    	
        ///////////lista testów
	        ///////////okno info
			oknoInfo.getChildren().add(new Rectangle(600, 400, new Color(1f, 1f, 1f, .85f)));
	        Button btn110 = new Button();
			btn110.setText("X");
	        btn110.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	oknoInfo.getChildren().remove(oknoInfo.getChildren().size()-1);
	            	oknoInfo.setVisible(false);
	            }
	        });
	        btn110.setLayoutX(520);
	        btn110.setLayoutY(10);
	        btn110.getStyleClass().add("buttonKwadrat");
	        oknoInfo.getChildren().add(btn110);
	        
			oknoInfo.setLayoutX(100);
			oknoInfo.setLayoutY(100);
	        ///////////koniec = okno info
        TableView<Test> table = new TableView<>();
        table.setEditable(true);
        listaTestów.getChildren().add(new Rectangle(800, 600, new Color(.8f, .8f, .8f, 1f)));
        
        Button btn10 = new Button();
        btn10.setText("Powrót");
        btn10.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
				ekranGłówny.setVisible(true);
				btnToggle.setText("-");
                ta.setVisible(true);
				listaTestów.setVisible(false);
            }
        });
        btn10.setLayoutX(10);
        btn10.setLayoutY(510);
        listaTestów.getChildren().add(btn10);
        
        Button btn11 = new Button();
        btn11.setText("Dodaj Test");
        btn11.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	oknoDodawania.setVisible(true);
            }
        });
        btn11.setLayoutX(10);
        btn11.setLayoutY(10);
        listaTestów.getChildren().add(btn11);
        
        Button btn12 = new Button();
        btn12.setText("Edytuj");
        btn12.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujTest)
            	{
	            	listaPytań.setVisible(true);
					btnToggle.setText("+");
	                ta.setVisible(false);
	            	listaTestów.setVisible(false);
	            	dataPytania.clear();
	            	wybranyTest = table.getSelectionModel().getSelectedItem();
	            	dataPytania.addAll(JDBC.pobierzPytania(wybranyTest.getId()));
	                textNazwa.setText(wybranyTest.getTytul());
	                textAutor.setText(wybranyTest.getAutor());
	                textOpis.setText(wybranyTest.getOpis());
            	}
            }
        });
        btn12.setLayoutX(10);
        btn12.setLayoutY(100);
        btn12.getStyleClass().add("buttonLock");
        listaTestów.getChildren().add(btn12);
        
        Button btn13 = new Button();
        btn13.setText("Info");
        btn13.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujTest)
            	{
            		NumberAxis xAxis = new NumberAxis();
	    	        NumberAxis yAxis = new NumberAxis();
	    	        LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
	    	        lineChart.setTitle("Odpowiedzi");
	    	        //defining a series
	    	        XYChart.Series series = new XYChart.Series();
	    	        XYChart.Series series2 = new XYChart.Series();
	    	        series.setName("Poprawne");
	    	        series2.setName("Błędne");
	    	        //populating the series with data
	            	wybranyTest = table.getSelectionModel().getSelectedItem();
        	        for(int j = 0; j <wybranyTest.getPytania().size(); j++)
        	        {
        		        int suma = 0;
        		        int poprawna = 0;
        		        for(int i = 0; i < wybranyTest.getPytania().get(j).getOdpowiedzi().size(); i++)
        		        {
        		        	suma += wybranyTest.getPytania().get(j).getOdpowiedzi().get(i).getLicznik();
        		        	if(wybranyTest.getPytania().get(j).getOdpowiedzi().get(i).isPrawidlowa())
        			        	poprawna += wybranyTest.getPytania().get(j).getOdpowiedzi().get(i).getLicznik();
        		        }
        		        series.getData().add(new XYChart.Data(j+1, 1.0*poprawna/suma));
        		        series2.getData().add(new XYChart.Data(j+1, 1-1.0*poprawna/suma));
        	        }
	    	        lineChart.getData().add(series);
	    	        lineChart.getData().add(series2);
	    	        lineChart.setPrefWidth(580);
	    	        lineChart.setPrefHeight(300);
	    	        lineChart.setLayoutX(10);
	    	        lineChart.setLayoutY(90);
        	        oknoInfo.getChildren().add(lineChart);
        	        oknoInfo.setVisible(true);
            	}
            }
        });
        btn13.setLayoutX(10);
        btn13.setLayoutY(190);
        btn13.getStyleClass().add("buttonLock");
        listaTestów.getChildren().add(btn13);
        Button btn14 = new Button();
        btn14.setText("Usuń");
        btn14.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujTest)
            	{
	            	Test t = table.getSelectionModel().getSelectedItem();
	            	JDBC.usunTest(t.getId()); 
	            	data.clear();
	            	data.addAll(JDBC.pobierzTesty());
            	}
            }
        });
        btn14.setLayoutX(10);
        btn14.setLayoutY(280);
        btn14.getStyleClass().add("buttonLock");
        listaTestów.getChildren().add(btn14);
            
        TableColumn idCol = new TableColumn("Id");
        idCol.setMinWidth(50);
        idCol.setPrefWidth(50);
        idCol.setMaxWidth(50);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));
 
        TableColumn tytułCol = new TableColumn("Tytuł");
        tytułCol.setMinWidth(200);
        tytułCol.setPrefWidth(200);
        tytułCol.setMaxWidth(200);
        tytułCol.setCellValueFactory(
                new PropertyValueFactory<>("tytul"));
 
        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(150);
        autorCol.setPrefWidth(150);
        autorCol.setMaxWidth(150);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("autor"));
 
        table.setItems(data);
        table.getColumns().addAll(idCol, tytułCol, autorCol);
        table.setMinHeight(600);
        table.setPrefHeight(600);
        table.setMaxHeight(600);
        table.setLayoutX(400);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn12.getStyleClass().clear();
                btn13.getStyleClass().clear();
                btn14.getStyleClass().clear();
                btn12.getStyleClass().add("button");
                btn13.getStyleClass().add("button");
                btn14.getStyleClass().add("button");
                edytujTest = true;
            }
            else
            {
                btn12.getStyleClass().clear();
                btn13.getStyleClass().clear();
                btn14.getStyleClass().clear();
                btn12.getStyleClass().add("buttonLock");
                btn13.getStyleClass().add("buttonLock");
                btn14.getStyleClass().add("buttonLock");
                edytujTest = false;
            }
        });
        listaTestów.getChildren().add(table);
        	///////////okno dodawania
    		oknoDodawania.getChildren().add(new Rectangle(400, 300, new Color(1f, 1f, 1f, .85f)));
	        Label labelNazwaOkno = new Label("Nazwa");
	        TextField textNazwaOkno = new TextField ();
	        Label labelAutorOkno = new Label("Autor");
	        TextField textAutorOkno = new TextField ();
	        Label labelOpisOkno = new Label("Opis");
	        TextArea textOpisOkno = new TextArea ();
	        Button btn100 = new Button();
	        btn100.setText("X");
	        btn100.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	oknoDodawania.setVisible(false);
	            }
	        });
	        btn100.setLayoutX(10);
	        btn100.setLayoutY(220);
	        btn100.getStyleClass().add("buttonKwadrat");
	        oknoDodawania.getChildren().add(btn100);
	        
	        Button btn101 = new Button();
	        btn101.setText("+");
	        btn101.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
		            if(tno && tao && too)
		            {
		    	        JDBC.dodajTest(textNazwaOkno.getText(), textOpisOkno.getText(), textAutorOkno.getText());
		    	        textNazwaOkno.setText("");
		    	        textOpisOkno.setText("");
		    	        textAutorOkno.setText("");
		            	data.clear();
		            	data.addAll(JDBC.pobierzTesty());
		            	oknoDodawania.setVisible(false);
		            }
	            }
	        });
	        btn101.setLayoutX(310);
	        btn101.setLayoutY(220);
	        btn101.getStyleClass().add("buttonKwadratLock");
	        oknoDodawania.getChildren().add(btn101);
	        labelNazwaOkno.setLayoutX(10);
	        labelNazwaOkno.setLayoutY(10);
	        textNazwaOkno.setLayoutX(10);
	        textNazwaOkno.setLayoutY(40);
	        labelAutorOkno.setLayoutX(200);
	        labelAutorOkno.setLayoutY(10);
	        textAutorOkno.setLayoutX(200);
	        textAutorOkno.setLayoutY(40);
	        labelOpisOkno.setLayoutX(105);
	        labelOpisOkno.setLayoutY(80);
	        textOpisOkno.setLayoutX(10);
	        textOpisOkno.setLayoutY(110);
	        textOpisOkno.getStyleClass().add("text-area1");
	        textNazwaOkno.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	tno = true;
	            else
	            	tno = false;
	            if(tno && tao && too) {
	            	btn101.getStyleClass().clear();
	    	        btn101.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn101.getStyleClass().clear();
	    	        btn101.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        textAutorOkno.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	tao = true;
	            else
	            	tao = false;
	            if(tno && tao && too) {
	            	btn101.getStyleClass().clear();
	    	        btn101.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn101.getStyleClass().clear();
	    	        btn101.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        textOpisOkno.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	too = true;
	            else
	            	too = false;
	            if(tno && tao && too) {
	            	btn101.getStyleClass().clear();
	    	        btn101.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn101.getStyleClass().clear();
	    	        btn101.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        oknoDodawania.setLayoutX(200);
	        oknoDodawania.setLayoutY(150);
	        oknoDodawania.getChildren().add(labelNazwaOkno);
	        oknoDodawania.getChildren().add(textNazwaOkno);
	        oknoDodawania.getChildren().add(labelAutorOkno);
	        oknoDodawania.getChildren().add(textAutorOkno);
	        oknoDodawania.getChildren().add(labelOpisOkno);
	        oknoDodawania.getChildren().add(textOpisOkno);
        	///////////koniec = okno dodawania
	        listaTestów.getChildren().add(oknoDodawania);
	        listaTestów.getChildren().add(oknoInfo);
        ///////////koniec = lista testów
        

        ///////////lista pytań
        TableView<Pytanie> tablePytania = new TableView<>();
        tablePytania.setEditable(true);
        listaPytań.getChildren().add(new Rectangle(800, 600, new Color(.8f, .8f, .8f, 1f)));
        
        Button btn20 = new Button();
        btn20.setText("Powrót");
        btn20.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	data.clear();
            	data.addAll(JDBC.pobierzTesty());
				listaTestów.setVisible(true);
				btnToggle.setText("+");
                ta.setVisible(false);
				listaPytań.setVisible(false);
            }
        });
        btn20.setLayoutX(10);
        btn20.setLayoutY(510);
        listaPytań.getChildren().add(btn20);
        
        Button btn21 = new Button();
        btn21.setText("Dodaj Pytanie");
        btn21.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	oknoDodawaniaPytania.setVisible(true);
            }
        });
        btn21.setLayoutX(10);
        btn21.setLayoutY(10);
        listaPytań.getChildren().add(btn21);
        
        Button btn22 = new Button();
        btn22.setText("Edytuj");
        btn22.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujPytanie)
            	{
	            	listaOdpowiedzi.setVisible(true);
					btnToggle.setText("+");
	                ta.setVisible(false);
	            	listaPytań.setVisible(false);
	            	dataOdpowiedzi.clear();
	            	wybranePytanie = tablePytania.getSelectionModel().getSelectedItem();
	            	dataOdpowiedzi.addAll(JDBC.pobierzOdpowiedzi(wybranePytanie.getId()));
	            	textAutorPytanie.setText(wybranePytanie.getAutor());
	            	textTekstPytanie.setText(wybranePytanie.getTekst());
            	}
            }
        });
        btn22.setLayoutX(10);
        btn22.setLayoutY(100);
        btn22.getStyleClass().add("buttonLock");
        listaPytań.getChildren().add(btn22);
        
        Button btn23 = new Button();
        btn23.setText("Usuń");
        btn23.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujPytanie)
            	{
	            	Pytanie t = tablePytania.getSelectionModel().getSelectedItem();
	            	JDBC.usunPytanie(t.getId()); 
	            	dataPytania.clear();
	            	dataPytania.addAll(JDBC.pobierzPytania(wybranyTest.getId()));
            	}
            }
        });
        btn23.setLayoutX(10);
        btn23.setLayoutY(190);
        btn23.getStyleClass().add("buttonLock");
        listaPytań.getChildren().add(btn23);

        labelNazwa.setLayoutX(10);
        labelNazwa.setLayoutY(280);
        textNazwa.setLayoutX(10);
        textNazwa.setLayoutY(310);
        labelAutor.setLayoutX(200);
        labelAutor.setLayoutY(280);
        textAutor.setLayoutX(200);
        textAutor.setLayoutY(310);
        labelOpis.setLayoutX(105);
        labelOpis.setLayoutY(350);
        textOpis.setLayoutX(10);
        textOpis.setLayoutY(380);
        textOpis.getStyleClass().add("text-area1");
        listaPytań.getChildren().add(labelNazwa);
        listaPytań.getChildren().add(textNazwa);
        listaPytań.getChildren().add(labelAutor);
        listaPytań.getChildren().add(textAutor);
        listaPytań.getChildren().add(labelOpis);
        listaPytań.getChildren().add(textOpis);
        textNazwa.focusedProperty().addListener((observable, oldValue, newValue) -> {
        	if(oldValue)
        	{
        		wybranyTest.setTytul(textNazwa.getText());
        		JDBC.edytujTest(wybranyTest);
        	}
        });
        textAutor.focusedProperty().addListener((observable, oldValue, newValue) -> {
        	if(oldValue)
        	{
        		wybranyTest.setAutor(textAutor.getText());
        		JDBC.edytujTest(wybranyTest);
        	}
        });
        textOpis.focusedProperty().addListener((observable, oldValue, newValue) -> {
        	if(oldValue)
        	{
        		wybranyTest.setOpis(textOpis.getText());
        		JDBC.edytujTest(wybranyTest);
        	}
        });
       
        TableColumn idColPytanie = new TableColumn("Id");
        idColPytanie.setMinWidth(50);
        idColPytanie.setPrefWidth(50);
        idColPytanie.setMaxWidth(50);
        idColPytanie.setCellValueFactory(
                new PropertyValueFactory<>("id"));
 
        TableColumn tekstColPytanie = new TableColumn("Tekst");
        tekstColPytanie.setMinWidth(200);
        tekstColPytanie.setPrefWidth(200);
        tekstColPytanie.setMaxWidth(200);
        tekstColPytanie.setCellValueFactory(
                new PropertyValueFactory<>("tekst"));
 
        TableColumn autorColPytanie = new TableColumn("Autor");
        autorColPytanie.setMinWidth(150);
        autorColPytanie.setPrefWidth(150);
        autorColPytanie.setMaxWidth(150);
        autorColPytanie.setCellValueFactory(
                new PropertyValueFactory<>("autor"));
 
        tablePytania.setItems(dataPytania);
        tablePytania.getColumns().addAll(idColPytanie, tekstColPytanie, autorColPytanie);
        tablePytania.setMinHeight(600);
        tablePytania.setPrefHeight(600);
        tablePytania.setMaxHeight(600);
        tablePytania.setLayoutX(400);
        tablePytania.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn22.getStyleClass().clear();
                btn23.getStyleClass().clear();
                btn22.getStyleClass().add("button");
                btn23.getStyleClass().add("button");
                edytujPytanie = true;
            }
            else
            {
                btn22.getStyleClass().clear();
                btn23.getStyleClass().clear();
                btn22.getStyleClass().add("buttonLock");
                btn23.getStyleClass().add("buttonLock");
                edytujPytanie = false;
            }
        });
        listaPytań.getChildren().add(tablePytania);
	    	///////////okno dodawania Pytania
			oknoDodawaniaPytania.getChildren().add(new Rectangle(400, 300, new Color(1f, 1f, 1f, .85f)));
	        Label labelAutorOknoPytania = new Label("Autor");
	        TextField textAutorOknoPytania = new TextField ();
	        Label labelOpisOknoPytania = new Label("Tekst");
	        TextArea textOpisOknoPytania = new TextArea ();
	        Button btn200 = new Button();
	        btn200.setText("X");
	        btn200.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	oknoDodawaniaPytania.setVisible(false);
	            }
	        });
	        btn200.setLayoutX(10);
	        btn200.setLayoutY(220);
	        btn200.getStyleClass().add("buttonKwadrat");
	        oknoDodawaniaPytania.getChildren().add(btn200);
	        
	        Button btn202 = new Button();
	        btn202.setText("+");
	        btn202.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
		            if(taop && toop)
		            {
		    	        JDBC.dodajPytanie(wybranyTest.getId(), textOpisOknoPytania.getText(), textAutorOknoPytania.getText());
		    	        textOpisOknoPytania.setText("");
		    	        textAutorOknoPytania.setText("");
		            	dataPytania.clear();
		            	dataPytania.addAll(JDBC.pobierzPytania(wybranyTest.getId()));
		            	oknoDodawaniaPytania.setVisible(false);
		            }
	            }
	        });
	        btn202.setLayoutX(310);
	        btn202.setLayoutY(220);
	        btn202.getStyleClass().add("buttonKwadratLock");
	        oknoDodawaniaPytania.getChildren().add(btn202);
	        labelAutorOknoPytania.setLayoutX(110);
	        labelAutorOknoPytania.setLayoutY(10);
	        textAutorOknoPytania.setLayoutX(110);
	        textAutorOknoPytania.setLayoutY(40);
	        labelOpisOknoPytania.setLayoutX(105);
	        labelOpisOknoPytania.setLayoutY(80);
	        textOpisOknoPytania.setLayoutX(10);
	        textOpisOknoPytania.setLayoutY(110);
	        textOpisOknoPytania.getStyleClass().add("text-area1");
	        textAutorOknoPytania.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	taop = true;
	            else
	            	taop = false;
	            if(taop && toop) {
	            	btn202.getStyleClass().clear();
	    	        btn202.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn202.getStyleClass().clear();
	    	        btn202.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        textOpisOknoPytania.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	toop = true;
	            else
	            	toop = false;
	            if(taop && toop) {
	            	btn202.getStyleClass().clear();
	    	        btn202.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn202.getStyleClass().clear();
	    	        btn202.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        oknoDodawaniaPytania.setLayoutX(200);
	        oknoDodawaniaPytania.setLayoutY(150);
	        oknoDodawaniaPytania.getChildren().add(labelAutorOknoPytania);
	        oknoDodawaniaPytania.getChildren().add(textAutorOknoPytania);
	        oknoDodawaniaPytania.getChildren().add(labelOpisOknoPytania);
	        oknoDodawaniaPytania.getChildren().add(textOpisOknoPytania);
	    	///////////koniec = okno dodawania Pytania
        listaPytań.getChildren().add(oknoDodawaniaPytania);
        ///////////koniec = lista pytań
        

        ///////////lista odpowiedzi
        	//okno edycji
	        Label labelAutorOknoEdycjiOdpowiedzi = new Label("Autor");
	        TextField textAutorOknoEdycjiOdpowiedzi = new TextField ();
	        Label labelOpisOknoEdycjiOdpowiedzi = new Label("Tekst");
	        TextArea textOpisOknoEdycjiOdpowiedzi = new TextArea ();
	        CheckBox cbPoprawnaOknoEdycjiOdpowiedzi = new CheckBox("Poprawna");
	        cbPoprawnaOknoEdycjiOdpowiedzi.setIndeterminate(false);
        	//okno edycji
        TableView<Odpowiedz> tableOdpowiedzi = new TableView<>();
        tableOdpowiedzi.setEditable(true);
        listaOdpowiedzi.getChildren().add(new Rectangle(800, 600, new Color(.8f, .8f, .8f, 1f)));
        
        Button btn30 = new Button();
        btn30.setText("Powrót");
        btn30.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	dataPytania.clear();
            	dataPytania.addAll(JDBC.pobierzPytania(wybranyTest.getId()));
				listaPytań.setVisible(true);
				btnToggle.setText("+");
                ta.setVisible(false);
				listaOdpowiedzi.setVisible(false);
            }
        });
        btn30.setLayoutX(10);
        btn30.setLayoutY(510);
        listaOdpowiedzi.getChildren().add(btn30);
        
        Button btn31 = new Button();
        btn31.setText("Dodaj Odpowiedź");
        btn31.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	oknoDodawaniaOdpowiedzi.setVisible(true);
            }
        });
        btn31.setLayoutX(10);
        btn31.setLayoutY(10);
        listaOdpowiedzi.getChildren().add(btn31);
        
        Button btn32 = new Button();
        btn32.setText("Edytuj");
        btn32.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujOdpowiedź)
            	{
	            	wybranaOdpowiedź = tableOdpowiedzi.getSelectionModel().getSelectedItem();
	            	textAutorOknoEdycjiOdpowiedzi.setText(wybranaOdpowiedź.getAutor());
	            	textOpisOknoEdycjiOdpowiedzi.setText(wybranaOdpowiedź.getTekst());
	            	cbPoprawnaOknoEdycjiOdpowiedzi.setSelected(wybranaOdpowiedź.isPrawidlowa());
	            	oknoEdycjiOdpowiedzi.setVisible(true);
            	}
            }
        });
        btn32.setLayoutX(10);
        btn32.setLayoutY(100);
        btn32.getStyleClass().add("buttonLock");
        listaOdpowiedzi.getChildren().add(btn32);
        
        Button btn33 = new Button();
        btn33.setText("Usuń");
        btn33.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	if(edytujOdpowiedź)
            	{
	            	Odpowiedz t = tableOdpowiedzi.getSelectionModel().getSelectedItem();
	            	JDBC.usunOdpowiedz(t.getId()); 
	            	dataOdpowiedzi.clear();
	            	dataOdpowiedzi.addAll(JDBC.pobierzOdpowiedzi(wybranePytanie.getId()));
            	}
            }
        });
        btn33.setLayoutX(10);
        btn33.setLayoutY(190);
        btn33.getStyleClass().add("buttonLock");
        listaOdpowiedzi.getChildren().add(btn33);
        
        labelAutorPytanie.setLayoutX(110);
        labelAutorPytanie.setLayoutY(280);
        textAutorPytanie.setLayoutX(110);
        textAutorPytanie.setLayoutY(310);
        labelTekstPytanie.setLayoutX(105);
        labelTekstPytanie.setLayoutY(350);
        textTekstPytanie.setLayoutX(10);
        textTekstPytanie.setLayoutY(380);
        textTekstPytanie.getStyleClass().add("text-area1");
        listaOdpowiedzi.getChildren().add(labelAutorPytanie);
        listaOdpowiedzi.getChildren().add(textAutorPytanie);
        listaOdpowiedzi.getChildren().add(labelTekstPytanie);
        listaOdpowiedzi.getChildren().add(textTekstPytanie);
        textAutorPytanie.focusedProperty().addListener((observable, oldValue, newValue) -> {
        	if(oldValue)
        	{
        		wybranePytanie.setAutor(textAutorPytanie.getText());
        		JDBC.edytujPytanie(wybranePytanie);
        	}
        });
        textTekstPytanie.focusedProperty().addListener((observable, oldValue, newValue) -> {
        	if(oldValue)
        	{
        		wybranePytanie.setTekst(textTekstPytanie.getText());
        		JDBC.edytujPytanie(wybranePytanie);
        	}
        });

        TableColumn idColOdpowiedź = new TableColumn("Id");
        idColOdpowiedź.setMinWidth(30);
        idColOdpowiedź.setPrefWidth(40);
        idColOdpowiedź.setMaxWidth(100);
        idColOdpowiedź.setCellValueFactory(
                new PropertyValueFactory<>("id"));
 
        TableColumn tekstColOdpowiedź = new TableColumn("Tekst");
        tekstColOdpowiedź.setMinWidth(100);
        tekstColOdpowiedź.setPrefWidth(140);
        tekstColOdpowiedź.setMaxWidth(200);
        tekstColOdpowiedź.setCellValueFactory(
                new PropertyValueFactory<>("tekst"));
 
        TableColumn autorColOdpowiedź = new TableColumn("Autor");
        autorColOdpowiedź.setMinWidth(100);
        autorColOdpowiedź.setPrefWidth(140);
        autorColOdpowiedź.setMaxWidth(200);
        autorColOdpowiedź.setCellValueFactory(
                new PropertyValueFactory<>("autor"));
 
        TableColumn<Odpowiedz, String >  poprawnaColOdpowiedź = new TableColumn("✓");
        poprawnaColOdpowiedź.setMinWidth(30);
        poprawnaColOdpowiedź.setPrefWidth(40);
        poprawnaColOdpowiedź.setMaxWidth(100);
        poprawnaColOdpowiedź.setCellValueFactory(cellData -> {
            boolean poprawna = cellData.getValue().isPrawidlowa();
            String a;
            if(poprawna == true)
            {
                a = "✓";
            }
            else
            {
                a = "X";
            }

         return new ReadOnlyStringWrapper(a);
        });
        TableColumn licznikColOdpowiedź = new TableColumn("∑");
        licznikColOdpowiedź.setMinWidth(30);
        licznikColOdpowiedź.setPrefWidth(40);
        licznikColOdpowiedź.setMaxWidth(100);
        licznikColOdpowiedź.setCellValueFactory(
                new PropertyValueFactory<>("licznik"));
 
        tableOdpowiedzi.setItems(dataOdpowiedzi);
        tableOdpowiedzi.getColumns().addAll(idColOdpowiedź,poprawnaColOdpowiedź,licznikColOdpowiedź, tekstColOdpowiedź, autorColOdpowiedź);
        tableOdpowiedzi.setMinHeight(600);
        tableOdpowiedzi.setPrefHeight(600);
        tableOdpowiedzi.setMaxHeight(600);
        tableOdpowiedzi.setLayoutX(400);
        tableOdpowiedzi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btn32.getStyleClass().clear();
                btn33.getStyleClass().clear();
                btn32.getStyleClass().add("button");
                btn33.getStyleClass().add("button");
                edytujOdpowiedź = true;
            }
            else
            {
                btn32.getStyleClass().clear();
                btn33.getStyleClass().clear();
                btn32.getStyleClass().add("buttonLock");
                btn33.getStyleClass().add("buttonLock");
                edytujOdpowiedź = false;
            }
        });
        listaOdpowiedzi.getChildren().add(tableOdpowiedzi);
        	///////////okno dodawania odpowiedzi
			oknoDodawaniaOdpowiedzi.getChildren().add(new Rectangle(400, 300, new Color(1f, 1f, 1f, .85f)));
	        Label labelAutorOknoDodawaniaOdpowiedzi = new Label("Autor");
	        TextField textAutorOknoDodawaniaOdpowiedzi = new TextField ();
	        Label labelOpisOknoDodawaniaOdpowiedzi = new Label("Tekst");
	        TextArea textOpisOknoDodawaniaOdpowiedzi = new TextArea ();
	        CheckBox cbPoprawnaOknoDodawaniaOdpowiedzi = new CheckBox("Poprawna");
	        cbPoprawnaOknoDodawaniaOdpowiedzi.setIndeterminate(false);
	        Button btn300 = new Button();
	        btn300.setText("X");
	        btn300.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	oknoDodawaniaOdpowiedzi.setVisible(false);
	            }
	        });
	        btn300.setLayoutX(10);
	        btn300.setLayoutY(220);
	        btn300.getStyleClass().add("buttonKwadrat");
	        oknoDodawaniaOdpowiedzi.getChildren().add(btn300);
	        
	        Button btn301 = new Button();
	        btn301.setText("+");
	        btn301.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
		            if(taodo && ttodo)
		            {
		    	        JDBC.dodajOdpowiedz(wybranePytanie.getId(), textOpisOknoDodawaniaOdpowiedzi.getText(), textAutorOknoDodawaniaOdpowiedzi.getText(), cbPoprawnaOknoDodawaniaOdpowiedzi.isSelected());
		    	        textAutorOknoDodawaniaOdpowiedzi.setText("");
		    	        textOpisOknoDodawaniaOdpowiedzi.setText("");
		            	dataOdpowiedzi.clear();
		            	dataOdpowiedzi.addAll(JDBC.pobierzOdpowiedzi(wybranePytanie.getId()));
		            	oknoDodawaniaOdpowiedzi.setVisible(false);
		            }
	            }
	        });
	        btn301.setLayoutX(310);
	        btn301.setLayoutY(220);
	        btn301.getStyleClass().add("buttonKwadratLock");
	        oknoDodawaniaOdpowiedzi.getChildren().add(btn301);
	        
	        labelAutorOknoDodawaniaOdpowiedzi.setLayoutX(10);
	        labelAutorOknoDodawaniaOdpowiedzi.setLayoutY(10);
	        textAutorOknoDodawaniaOdpowiedzi.setLayoutX(10);
	        textAutorOknoDodawaniaOdpowiedzi.setLayoutY(40);
	        labelOpisOknoDodawaniaOdpowiedzi.setLayoutX(105);
	        labelOpisOknoDodawaniaOdpowiedzi.setLayoutY(80);
	        textOpisOknoDodawaniaOdpowiedzi.setLayoutX(10);
	        textOpisOknoDodawaniaOdpowiedzi.setLayoutY(110);
	        textOpisOknoDodawaniaOdpowiedzi.getStyleClass().add("text-area1");
	        cbPoprawnaOknoDodawaniaOdpowiedzi.setLayoutX(200);
	        cbPoprawnaOknoDodawaniaOdpowiedzi.setLayoutY(25);
	        textAutorOknoDodawaniaOdpowiedzi.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	taodo = true;
	            else
	            	taodo = false;
	            if(taodo && ttodo) {
	            	btn301.getStyleClass().clear();
	    	        btn301.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn301.getStyleClass().clear();
	    	        btn301.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        textOpisOknoDodawaniaOdpowiedzi.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	ttodo = true;
	            else
	            	ttodo = false;
	            if(taodo && ttodo) {
	            	btn301.getStyleClass().clear();
	    	        btn301.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn301.getStyleClass().clear();
	    	        btn301.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        oknoDodawaniaOdpowiedzi.setLayoutX(200);
	        oknoDodawaniaOdpowiedzi.setLayoutY(150);
	        oknoDodawaniaOdpowiedzi.getChildren().add(labelAutorOknoDodawaniaOdpowiedzi);
	        oknoDodawaniaOdpowiedzi.getChildren().add(textAutorOknoDodawaniaOdpowiedzi);
	        oknoDodawaniaOdpowiedzi.getChildren().add(labelOpisOknoDodawaniaOdpowiedzi);
	        oknoDodawaniaOdpowiedzi.getChildren().add(textOpisOknoDodawaniaOdpowiedzi);
	        oknoDodawaniaOdpowiedzi.getChildren().add(cbPoprawnaOknoDodawaniaOdpowiedzi);
	    	///////////koniec = okno dodawania odpowiedzi
      listaOdpowiedzi.getChildren().add(oknoDodawaniaOdpowiedzi);
      
      		///////////okno edycji odpowiedzi
			oknoEdycjiOdpowiedzi.getChildren().add(new Rectangle(400, 300, new Color(1f, 1f, 1f, .85f)));
	        Button btn310 = new Button();
	        btn310.setText("X");
	        btn310.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	oknoEdycjiOdpowiedzi.setVisible(false);
	            }
	        });
	        btn310.setLayoutX(10);
	        btn310.setLayoutY(220);
	        btn310.getStyleClass().add("buttonKwadrat");
	        oknoEdycjiOdpowiedzi.getChildren().add(btn310);
	        
	        Button btn311 = new Button();
	        btn311.setText("✓");
	        btn311.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
		            if(taoeo && ttoeo)
		            {
		            	wybranaOdpowiedź.setAutor(textAutorOknoEdycjiOdpowiedzi.getText());
		            	wybranaOdpowiedź.setTekst(textOpisOknoEdycjiOdpowiedzi.getText());
		            	wybranaOdpowiedź.setPrawidlowa(cbPoprawnaOknoEdycjiOdpowiedzi.isSelected());
		    	        JDBC.edytujOdpowiedz(wybranaOdpowiedź);
		    	        textAutorOknoEdycjiOdpowiedzi.setText("");
		    	        textOpisOknoEdycjiOdpowiedzi.setText("");
		            	dataOdpowiedzi.clear();
		            	dataOdpowiedzi.addAll(JDBC.pobierzOdpowiedzi(wybranePytanie.getId()));
		            	oknoEdycjiOdpowiedzi.setVisible(false);
		            }
	            }
	        });
	        btn311.setLayoutX(310);
	        btn311.setLayoutY(220);
	        btn311.getStyleClass().add("buttonKwadratLock");
	        oknoEdycjiOdpowiedzi.getChildren().add(btn311);
	        
	        labelAutorOknoEdycjiOdpowiedzi.setLayoutX(10);
	        labelAutorOknoEdycjiOdpowiedzi.setLayoutY(10);
	        textAutorOknoEdycjiOdpowiedzi.setLayoutX(10);
	        textAutorOknoEdycjiOdpowiedzi.setLayoutY(40);
	        labelOpisOknoEdycjiOdpowiedzi.setLayoutX(105);
	        labelOpisOknoEdycjiOdpowiedzi.setLayoutY(80);
	        textOpisOknoEdycjiOdpowiedzi.setLayoutX(10);
	        textOpisOknoEdycjiOdpowiedzi.setLayoutY(110);
	        textOpisOknoEdycjiOdpowiedzi.getStyleClass().add("text-area1");
	        cbPoprawnaOknoEdycjiOdpowiedzi.setLayoutX(200);
	        cbPoprawnaOknoEdycjiOdpowiedzi.setLayoutY(25);
	        textAutorOknoEdycjiOdpowiedzi.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	taoeo = true;
	            else
	            	taoeo = false;
	            if(taoeo && ttoeo) {
	            	btn311.getStyleClass().clear();
	    	        btn311.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn311.getStyleClass().clear();
	    	        btn311.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        textOpisOknoEdycjiOdpowiedzi.textProperty().addListener((observable, oldValue, newValue) -> {
	            if(newValue != null && !newValue.equals(""))
	            	ttoeo = true;
	            else
	            	ttoeo = false;
	            if(taoeo && ttoeo) {
	            	btn311.getStyleClass().clear();
	    	        btn311.getStyleClass().add("buttonKwadrat");
	            }
	            else {
	            	btn311.getStyleClass().clear();
	    	        btn311.getStyleClass().add("buttonKwadratLock");
	            }
	        });
	        oknoEdycjiOdpowiedzi.setLayoutX(200);
	        oknoEdycjiOdpowiedzi.setLayoutY(150);
	        oknoEdycjiOdpowiedzi.getChildren().add(labelAutorOknoEdycjiOdpowiedzi);
	        oknoEdycjiOdpowiedzi.getChildren().add(textAutorOknoEdycjiOdpowiedzi);
	        oknoEdycjiOdpowiedzi.getChildren().add(labelOpisOknoEdycjiOdpowiedzi);
	        oknoEdycjiOdpowiedzi.getChildren().add(textOpisOknoEdycjiOdpowiedzi);
	        oknoEdycjiOdpowiedzi.getChildren().add(cbPoprawnaOknoEdycjiOdpowiedzi);
	    	///////////koniec = okno edycji odpowiedzi
	    listaOdpowiedzi.getChildren().add(oknoEdycjiOdpowiedzi);
        ///////////koniec = lista odpowiedzi
        
        Group root = new Group();
        root.getChildren().add(ekranGłówny);
        root.getChildren().add(listaTestów);
        root.getChildren().add(listaPytań);
        root.getChildren().add(listaOdpowiedzi);
        
        root.getChildren().add(ta);
        root.getChildren().add(btnToggle);

        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 800, 600);
        File f = new File("res/style.css");
        scene.getStylesheets().clear();
        //scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
		Console console = new Console(ta);
		PrintStream ps;
		ps = new PrintStream(console, true);
		System.setOut(ps);
    }
    public class Console extends OutputStream
    {
        private TextArea output;

        public Console(TextArea ta)
        {
            this.output = ta;
        }

        @Override
        public void write(int i) throws IOException
        {
        	Platform.runLater(new Runnable() {
                public void run() {
                    output.appendText(String.valueOf((char) i));
                }
            });
        }

    }
}
