package klient;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.*;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

import dane.Odpowiedz;
import dane.Test;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import serwer.JDBC;

public class Klient extends Application {
	ObservableList<Test> data = FXCollections.observableArrayList();
	Test wybrany = null;
	int i = 0;
	Color red = new Color(.71f,0,0,1);
	Color red2 = new Color(.29f,0,0,1);
	Color green = new Color(.03f,.68f,.0,1);
	Color green2 = new Color(.03f,.26f,0,1);
	ArrayList<Odpowiedz> odp = new ArrayList<Odpowiedz>();
	int odpPop = 0;
	String serwer = "";
	String user = "";
	
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Group ekranPowitalny = new Group();
		Group listaTestów = new Group();
		Group ekranTestu= new Group();
		Group ekranPytania= new Group();
		Group ekranPodsumowanie= new Group();
		
		listaTestów.setVisible(false);
		ekranTestu.setVisible(false);
		ekranPytania.setVisible(false);
		ekranPodsumowanie.setVisible(false);

		FadeTransition hide = new FadeTransition();
		hide.setFromValue(1f);
		hide.setToValue(0f);
		hide.setDuration(Duration.seconds(.5f));
		FadeTransition hideFast = new FadeTransition();
		hideFast.setFromValue(1f);
		hideFast.setToValue(0f);
		hideFast.setDuration(Duration.seconds(.001f));
		FadeTransition show = new FadeTransition();
    	show.setDelay(Duration.seconds(.2f));
		show.setFromValue(0f);
		show.setToValue(1f);
		show.setDuration(Duration.seconds(.5f));
		ScaleTransition procentAnimation1s = new ScaleTransition();
		procentAnimation1s.setFromX(1.0f);
		procentAnimation1s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimation1t = new TranslateTransition();
		procentAnimation1t.setFromX(0);
		ParallelTransition procentAnimation1 = new ParallelTransition(procentAnimation1s, procentAnimation1t);
		ScaleTransition procentAnimation2s = new ScaleTransition();
		procentAnimation2s.setFromX(1.0f);
		procentAnimation2s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimation2t = new TranslateTransition();
		procentAnimation2t.setFromX(0);
		ParallelTransition procentAnimation2 = new ParallelTransition(procentAnimation2s, procentAnimation2t);
		ScaleTransition procentAnimation3s = new ScaleTransition();
		procentAnimation3s.setFromX(1.0f);
		procentAnimation3s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimation3t = new TranslateTransition();
		procentAnimation3t.setFromX(0);
		ParallelTransition procentAnimation3 = new ParallelTransition(procentAnimation3s, procentAnimation3t);
		ScaleTransition procentAnimation4s = new ScaleTransition();
		procentAnimation4s.setFromX(1.0f);
		procentAnimation4s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimation4t = new TranslateTransition();
		procentAnimation4t.setFromX(0);
		ParallelTransition procentAnimation4 = new ParallelTransition(procentAnimation4s, procentAnimation4t);
		ScaleTransition procentAnimationPowrot1s = new ScaleTransition();
		procentAnimationPowrot1s.setDelay(Duration.seconds(1f));
		procentAnimationPowrot1s.setToX(1.0f);
		procentAnimationPowrot1s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimationPowrot1t = new TranslateTransition();
		procentAnimationPowrot1t.setDelay(Duration.seconds(1f));
		procentAnimationPowrot1t.setToX(0);
		ParallelTransition procentAnimationPowrot1 = new ParallelTransition(procentAnimationPowrot1s, procentAnimationPowrot1t);
		ScaleTransition procentAnimationPowrot2s = new ScaleTransition();
		procentAnimationPowrot2s.setDelay(Duration.seconds(1f));
		procentAnimationPowrot2s.setToX(1.0f);
		procentAnimationPowrot2s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimationPowrot2t = new TranslateTransition();
		procentAnimationPowrot2t.setDelay(Duration.seconds(1f));
		procentAnimationPowrot2t.setToX(0);
		ParallelTransition procentAnimationPowrot2 = new ParallelTransition(procentAnimationPowrot2s, procentAnimationPowrot2t);
		ScaleTransition procentAnimationPowrot3s = new ScaleTransition();
		procentAnimationPowrot3s.setDelay(Duration.seconds(1f));
		procentAnimationPowrot3s.setToX(1.0f);
		procentAnimationPowrot3s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimationPowrot3t = new TranslateTransition();
		procentAnimationPowrot3t.setDelay(Duration.seconds(1f));
		procentAnimationPowrot3t.setToX(0);
		ParallelTransition procentAnimationPowrot3 = new ParallelTransition(procentAnimationPowrot3s, procentAnimationPowrot3t);
		ScaleTransition procentAnimationPowrot4s = new ScaleTransition();
		procentAnimationPowrot4s.setDelay(Duration.seconds(1f));
		procentAnimationPowrot4s.setToX(1.0f);
		procentAnimationPowrot4s.setDuration(Duration.seconds(.5f));
		TranslateTransition procentAnimationPowrot4t = new TranslateTransition();
		procentAnimationPowrot4t.setDelay(Duration.seconds(1f));
		procentAnimationPowrot4t.setToX(0);
		ParallelTransition procentAnimationPowrot4 = new ParallelTransition(procentAnimationPowrot4s, procentAnimationPowrot4t);
		
		

        TableView<Test> table = new TableView<>();
		//ekran Powitalny
		Label tytulPowitalny = new Label("Testy");
		tytulPowitalny.getStyleClass().add("labelTytul");
		tytulPowitalny.setLayoutX(0);
		tytulPowitalny.setLayoutY(200);
		ekranPowitalny.getChildren().add(tytulPowitalny);
		TextField tfserwer = new TextField();
		TextField tfuser = new TextField();
		tfserwer.setText("127.0.0.1");
		tfserwer.setLayoutY(420);
		tfserwer.setLayoutX(210);
		ekranPowitalny.getChildren().add(tfserwer);
		tfuser.setText("user");
		tfuser.setLayoutY(420);
		tfuser.setLayoutX(410);
		ekranPowitalny.getChildren().add(tfuser);
		Button btnStart = new Button();
		btnStart.setText("Start");
		btnStart.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				serwer = tfserwer.getText();
				user = tfuser.getText();
		    	Task<Integer> task = new Task<Integer>() {
		            @Override protected Integer call() throws InterruptedException {
		                updateMessage("Pobieram testy");
				    	data.addAll(getTesty());
		                updateMessage("Ukończono");
		                return 1;
		              }
		    	};
		        new Thread(task).start();
				hide.setNode(ekranPowitalny);
                hide.play();
                hide.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	hideFast.setNode(listaTestów);
        		    	hideFast.play();
        		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
                		    @Override
                		    public void handle(ActionEvent event) {
                        		listaTestów.setVisible(true);
                		    }
                		});
        		    	show.setNode(listaTestów);
        		    	show.play();
        		    	ekranPowitalny.setVisible(false);
        		    }
        		});
            }
        });
		btnStart.setLayoutX(250);
		btnStart.setLayoutY(320);
		ekranPowitalny.getChildren().add(btnStart);
		hideFast.setNode(btnStart);
		hideFast.play();
		FadeTransition tytulAnimationFade = new FadeTransition();
		tytulAnimationFade.setFromValue(0f);
		tytulAnimationFade.setToValue(1f);
		tytulAnimationFade.setDuration(Duration.seconds(2));
		tytulAnimationFade.setNode(tytulPowitalny);
		tytulAnimationFade.play();
		TranslateTransition tytulAnimation = new TranslateTransition();
		tytulAnimation.setToY(-100);
		tytulAnimation.setDelay(Duration.seconds(.5f));
		tytulAnimation.setDuration(Duration.seconds(2f));
		tytulAnimation.setNode(tytulPowitalny);
		tytulAnimationFade.setOnFinished(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent event) {
		    	tytulAnimation.play();
		    	show.setNode(btnStart);
		    	show.play();
		    }
		});
		//koniec = ekran powitalny
		
		//ekran podsumowania
		Label tytulPodsumowanie = new Label("Pytanie 1");
		tytulPodsumowanie.getStyleClass().add("labelTytul");
		tytulPodsumowanie.setLayoutX(0);
		tytulPodsumowanie.setLayoutY(0);
		tytulPodsumowanie.setWrapText(true);
		ekranPodsumowanie.getChildren().add(tytulPodsumowanie);
		Label wynikPodsumowanie = new Label("Pytanie 1");
		wynikPodsumowanie.getStyleClass().add("labelTytul");
		wynikPodsumowanie.setLayoutX(0);
		wynikPodsumowanie.setLayoutY(200);
		wynikPodsumowanie.setWrapText(true);
		ekranPodsumowanie.getChildren().add(wynikPodsumowanie);
		Button btnPowrot = new Button();
		btnPowrot.setText("Powrót");
		btnPowrot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				hide.setNode(ekranPodsumowanie);
                hide.play();
                hide.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	show.setNode(listaTestów);
        		    	show.play();
        		    	ekranPodsumowanie.setVisible(false);
        		    	listaTestów.setVisible(true);
        		    	i=0;
        		    	table.getSelectionModel().clearSelection();
        		    }
        		});
            }
        });
		btnPowrot.setLayoutX(250);
		btnPowrot.setLayoutY(450);
		ekranPodsumowanie.getChildren().add(btnPowrot);
		//koniec = ekran podsumowania
		
		//ekran pytania
		Label numerPytania = new Label("Pytanie 1");
		numerPytania.getStyleClass().add("labelTytul");
		numerPytania.setLayoutX(0);
		numerPytania.setLayoutY(0);
		numerPytania.setWrapText(true);
		ekranPytania.getChildren().add(numerPytania);
		Label tekstPytania = new Label("Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis ");
		tekstPytania.setLayoutX(50);
		tekstPytania.setLayoutY(200);
		tekstPytania.setWrapText(true);
		tekstPytania.getStyleClass().add("labelMultiline");
		ekranPytania.getChildren().add(tekstPytania);
		Rectangle r1 = new Rectangle(1, 80, red2);
		Rectangle r2 = new Rectangle(1, 80, red2);
		Rectangle r3 = new Rectangle(1, 80, red2);
		Rectangle r4 = new Rectangle(1, 80, red2);
		r1.setLayoutX(95);
		r1.setLayoutY(420);
		r2.setLayoutX(405);
		r2.setLayoutY(420);
		r3.setLayoutX(95);
		r3.setLayoutY(510);
		r4.setLayoutX(405);
		r4.setLayoutY(510);
		ekranPytania.getChildren().add(r1);
		ekranPytania.getChildren().add(r2);
		ekranPytania.getChildren().add(r3);
		ekranPytania.getChildren().add(r4);
		Button btnOdp1 = new Button();
		Button btnOdp2 = new Button();
		Button btnOdp3 = new Button();
		Button btnOdp4 = new Button();
		btnOdp1.setText("A");
		btnOdp1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				wybrany.getPytania().get(i).getOdpowiedzi().get(0).dodajLicznik();
				odp.add(wybrany.getPytania().get(i).getOdpowiedzi().get(0));
				btnOdp1.getStyleClass().add("buttonActive");
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(0).isPrawidlowa())
		    	{
		    		r1.setFill(green);
		    		odpPop++;
		    	}
		    	else
		    		r1.setFill(red);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(1).isPrawidlowa())
		    		r2.setFill(green2);
		    	else
		    		r2.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(2).isPrawidlowa())
		    		r3.setFill(green2);
		    	else
		    		r3.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(3).isPrawidlowa())
		    		r4.setFill(green2);
		    	else
		    		r4.setFill(red2);
		    	procentAnimation1.setNode(r1);
		    	procentAnimation1s.setToX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimationPowrot1s.setFromX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimation1t.setToX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimationPowrot1t.setFromX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimation1.play();
		    	procentAnimation2.setNode(r2);
		    	procentAnimation2s.setToX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimationPowrot2s.setFromX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimation2t.setToX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimationPowrot2t.setFromX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimation2.play();
		    	procentAnimation3.setNode(r3);
		    	procentAnimation3s.setToX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimationPowrot3s.setFromX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimation3t.setToX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimationPowrot3t.setFromX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimation3.play();
		    	procentAnimation4.setNode(r4);
		    	procentAnimation4s.setToX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimationPowrot4s.setFromX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimation4t.setToX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimationPowrot4t.setFromX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimation4.play();
				procentAnimation4.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	procentAnimationPowrot1.setNode(r1);
        		    	procentAnimationPowrot2.setNode(r2);
        		    	procentAnimationPowrot3.setNode(r3);
        		    	procentAnimationPowrot4.setNode(r4);
        		    	procentAnimationPowrot1.play();
        		    	procentAnimationPowrot2.play();
        		    	procentAnimationPowrot3.play();
        		    	procentAnimationPowrot4.play();
        		    	hide.setDelay(Duration.seconds(1f));
        		    	hide.setNode(ekranPytania);
        		    	hide.play();
        		    	hide.setOnFinished(new EventHandler<ActionEvent>() {
                		    @Override
                		    public void handle(ActionEvent event) {
                		    	hide.setDelay(Duration.seconds(0));
                		    	i++;
                		    	if(wybrany.getPytania().size() > i)
                		    	{
	                		    	numerPytania.setText("Pytanie "+(i+1));
	                		    	tekstPytania.setText(wybrany.getPytania().get(i).getTekst());
	                		    	btnOdp1.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(0).getTekst());
	                		    	btnOdp2.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(1).getTekst());
	                		    	btnOdp3.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(2).getTekst());
	                		    	btnOdp4.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(3).getTekst());
	                				btnOdp1.getStyleClass().clear();
	                				btnOdp1.getStyleClass().add("button");
		            		    	show.setNode(ekranPytania);
		            		    	show.play();
                		    	}
                		    	else
                		    	{
                					btnOdp1.getStyleClass().clear();
                			    	btnOdp1.getStyleClass().add("button");
                					btnOdp2.getStyleClass().clear();
                			    	btnOdp2.getStyleClass().add("button");
                					btnOdp3.getStyleClass().clear();
                			    	btnOdp3.getStyleClass().add("button");
                					btnOdp4.getStyleClass().clear();
                			    	btnOdp4.getStyleClass().add("button");
                		    		hideFast.setNode(ekranPodsumowanie);
                    		    	hideFast.play();
                    		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
                            		    @Override
                            		    public void handle(ActionEvent event) {
                            		    	ekranPodsumowanie.setVisible(true);
                            		    	ekranPytania.setVisible(false);
                            		    	show.setNode(ekranPodsumowanie);
                            		    	tytulPodsumowanie.setText(wybrany.getTytul());
                            		    	wynikPodsumowanie.setText("Wynik: "+odpPop+"/"+wybrany.getPytania().size());
                            		    	updateOdpowiedzi();
                            		    	show.play();
                            		    }
                            		});
                		    	}
                		    }
                		});
        		    }
        		});
            }
        });
		btnOdp1.setLayoutX(95);
		btnOdp1.setLayoutY(420);
		ekranPytania.getChildren().add(btnOdp1);
		btnOdp2.setText("B");
		btnOdp2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				wybrany.getPytania().get(i).getOdpowiedzi().get(1).dodajLicznik();
				odp.add(wybrany.getPytania().get(i).getOdpowiedzi().get(1));
				btnOdp2.getStyleClass().add("buttonActive");
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(1).isPrawidlowa())
		    	{
		    		r2.setFill(green);
		    		odpPop++;
		    	}
		    	else
		    		r2.setFill(red);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(0).isPrawidlowa())
		    		r1.setFill(green2);
		    	else
		    		r1.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(2).isPrawidlowa())
		    		r3.setFill(green2);
		    	else
		    		r3.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(3).isPrawidlowa())
		    		r4.setFill(green2);
		    	else
		    		r4.setFill(red2);
		    	procentAnimation1.setNode(r1);
		    	procentAnimation1s.setToX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimationPowrot1s.setFromX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimation1t.setToX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimationPowrot1t.setFromX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimation1.play();
		    	procentAnimation2.setNode(r2);
		    	procentAnimation2s.setToX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimationPowrot2s.setFromX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimation2t.setToX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimationPowrot2t.setFromX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimation2.play();
		    	procentAnimation3.setNode(r3);
		    	procentAnimation3s.setToX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimationPowrot3s.setFromX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimation3t.setToX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimationPowrot3t.setFromX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimation3.play();
		    	procentAnimation4.setNode(r4);
		    	procentAnimation4s.setToX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimationPowrot4s.setFromX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimation4t.setToX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimationPowrot4t.setFromX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimation4.play();
				procentAnimation4.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	procentAnimationPowrot1.setNode(r1);
        		    	procentAnimationPowrot2.setNode(r2);
        		    	procentAnimationPowrot3.setNode(r3);
        		    	procentAnimationPowrot4.setNode(r4);
        		    	procentAnimationPowrot1.play();
        		    	procentAnimationPowrot2.play();
        		    	procentAnimationPowrot3.play();
        		    	procentAnimationPowrot4.play();
        		    	hide.setDelay(Duration.seconds(1f));
        		    	hide.setNode(ekranPytania);
        		    	hide.play();
        		    	hide.setOnFinished(new EventHandler<ActionEvent>() {
                		    @Override
                		    public void handle(ActionEvent event) {
                		    	hide.setDelay(Duration.seconds(0));
                		    	i++;
                		    	if(wybrany.getPytania().size() > i)
                		    	{
	                		    	numerPytania.setText("Pytanie "+(i+1));
	                		    	tekstPytania.setText(wybrany.getPytania().get(i).getTekst());
	                		    	btnOdp1.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(0).getTekst());
	                		    	btnOdp2.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(1).getTekst());
	                		    	btnOdp3.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(2).getTekst());
	                		    	btnOdp4.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(3).getTekst());
	                				btnOdp2.getStyleClass().clear();
	                		    	btnOdp2.getStyleClass().add("button");
		            		    	show.setNode(ekranPytania);
		            		    	show.play();
                		    	}
                		    	else
                		    	{
                					btnOdp1.getStyleClass().clear();
                			    	btnOdp1.getStyleClass().add("button");
                					btnOdp2.getStyleClass().clear();
                			    	btnOdp2.getStyleClass().add("button");
                					btnOdp3.getStyleClass().clear();
                			    	btnOdp3.getStyleClass().add("button");
                					btnOdp4.getStyleClass().clear();
                			    	btnOdp4.getStyleClass().add("button");
                		    		hideFast.setNode(ekranPodsumowanie);
                    		    	hideFast.play();
                    		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
                            		    @Override
                            		    public void handle(ActionEvent event) {
                            		    	ekranPodsumowanie.setVisible(true);
                            		    	ekranPytania.setVisible(false);
                            		    	show.setNode(ekranPodsumowanie);
                            		    	tytulPodsumowanie.setText(wybrany.getTytul());
                            		    	wynikPodsumowanie.setText("Wynik: "+odpPop+"/"+wybrany.getPytania().size());
                            		    	updateOdpowiedzi();
                            		    	show.play();
                            		    }
                            		});
                		    	}
                		    }
                		});
        		    }
        		});
            }
        });
		btnOdp2.setLayoutX(405);
		btnOdp2.setLayoutY(420);
		ekranPytania.getChildren().add(btnOdp2);
		btnOdp3.setText("C");
		btnOdp3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				wybrany.getPytania().get(i).getOdpowiedzi().get(2).dodajLicznik();
				odp.add(wybrany.getPytania().get(i).getOdpowiedzi().get(2));
				btnOdp3.getStyleClass().add("buttonActive");
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(2).isPrawidlowa())
		    	{
		    		r3.setFill(green);
		    		odpPop++;
		    	}
		    	else
		    		r3.setFill(red);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(0).isPrawidlowa())
		    		r1.setFill(green2);
		    	else
		    		r1.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(1).isPrawidlowa())
		    		r2.setFill(green2);
		    	else
		    		r2.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(3).isPrawidlowa())
		    		r4.setFill(green2);
		    	else
		    		r4.setFill(red2);
		    	procentAnimation1.setNode(r1);
		    	procentAnimation1s.setToX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimationPowrot1s.setFromX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimation1t.setToX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimationPowrot1t.setFromX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimation1.play();
		    	procentAnimation2.setNode(r2);
		    	procentAnimation2s.setToX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimationPowrot2s.setFromX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimation2t.setToX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimationPowrot2t.setFromX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimation2.play();
		    	procentAnimation3.setNode(r3);
		    	procentAnimation3s.setToX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimationPowrot3s.setFromX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimation3t.setToX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimationPowrot3t.setFromX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimation3.play();
		    	procentAnimation4.setNode(r4);
		    	procentAnimation4s.setToX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimationPowrot4s.setFromX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimation4t.setToX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimationPowrot4t.setFromX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimation4.play();
				procentAnimation4.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	procentAnimationPowrot1.setNode(r1);
        		    	procentAnimationPowrot2.setNode(r2);
        		    	procentAnimationPowrot3.setNode(r3);
        		    	procentAnimationPowrot4.setNode(r4);
        		    	procentAnimationPowrot1.play();
        		    	procentAnimationPowrot2.play();
        		    	procentAnimationPowrot3.play();
        		    	procentAnimationPowrot4.play();
        		    	hide.setDelay(Duration.seconds(1f));
        		    	hide.setNode(ekranPytania);
        		    	hide.play();
        		    	hide.setOnFinished(new EventHandler<ActionEvent>() {
                		    @Override
                		    public void handle(ActionEvent event) {
                		    	hide.setDelay(Duration.seconds(0));
                		    	i++;
                		    	if(wybrany.getPytania().size() > i)
                		    	{
	                		    	numerPytania.setText("Pytanie "+(i+1));
	                		    	tekstPytania.setText(wybrany.getPytania().get(i).getTekst());
	                		    	btnOdp1.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(0).getTekst());
	                		    	btnOdp2.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(1).getTekst());
	                		    	btnOdp3.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(2).getTekst());
	                		    	btnOdp4.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(3).getTekst());
	                				btnOdp3.getStyleClass().clear();
	                		    	btnOdp3.getStyleClass().add("button");
		            		    	show.setNode(ekranPytania);
		            		    	show.play();
                		    	}
                		    	else
                		    	{
                					btnOdp1.getStyleClass().clear();
                			    	btnOdp1.getStyleClass().add("button");
                					btnOdp2.getStyleClass().clear();
                			    	btnOdp2.getStyleClass().add("button");
                					btnOdp3.getStyleClass().clear();
                			    	btnOdp3.getStyleClass().add("button");
                					btnOdp4.getStyleClass().clear();
                			    	btnOdp4.getStyleClass().add("button");
                		    		hideFast.setNode(ekranPodsumowanie);
                    		    	hideFast.play();
                    		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
                            		    @Override
                            		    public void handle(ActionEvent event) {
                            		    	ekranPodsumowanie.setVisible(true);
                            		    	ekranPytania.setVisible(false);
                            		    	show.setNode(ekranPodsumowanie);
                            		    	tytulPodsumowanie.setText(wybrany.getTytul());
                            		    	wynikPodsumowanie.setText("Wynik: "+odpPop+"/"+wybrany.getPytania().size());
                            		    	updateOdpowiedzi();
                            		    	updateOdpowiedzi();
                            		    	show.play();
                            		    }
                            		});
                		    	}
                		    }
                		});
        		    }
        		});
            }
        });
		btnOdp3.setLayoutX(95);
		btnOdp3.setLayoutY(510);
		ekranPytania.getChildren().add(btnOdp3);
		btnOdp4.setText("D");
		btnOdp4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				wybrany.getPytania().get(i).getOdpowiedzi().get(3).dodajLicznik();
				odp.add(wybrany.getPytania().get(i).getOdpowiedzi().get(3));
				btnOdp4.getStyleClass().add("buttonActive");
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(3).isPrawidlowa())
		    	{
		    		r4.setFill(green);
		    		odpPop++;
		    	}
		    	else
		    		r4.setFill(red);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(0).isPrawidlowa())
		    		r1.setFill(green2);
		    	else
		    		r1.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(2).isPrawidlowa())
		    		r3.setFill(green2);
		    	else
		    		r3.setFill(red2);
		    	if(wybrany.getPytania().get(i).getOdpowiedzi().get(1).isPrawidlowa())
		    		r2.setFill(green2);
		    	else
		    		r2.setFill(red2);
		    	procentAnimation1.setNode(r1);
		    	procentAnimation1s.setToX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimationPowrot1s.setFromX(300*wybrany.getPytania().get(i).procent(0));
		    	procentAnimation1t.setToX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimationPowrot1t.setFromX(300*wybrany.getPytania().get(i).procent(0)/2);
		    	procentAnimation1.play();
		    	procentAnimation2.setNode(r2);
		    	procentAnimation2s.setToX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimationPowrot2s.setFromX(300*wybrany.getPytania().get(i).procent(1));
		    	procentAnimation2t.setToX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimationPowrot2t.setFromX(300*wybrany.getPytania().get(i).procent(1)/2);
		    	procentAnimation2.play();
		    	procentAnimation3.setNode(r3);
		    	procentAnimation3s.setToX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimationPowrot3s.setFromX(300*wybrany.getPytania().get(i).procent(2));
		    	procentAnimation3t.setToX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimationPowrot3t.setFromX(300*wybrany.getPytania().get(i).procent(2)/2);
		    	procentAnimation3.play();
		    	procentAnimation4.setNode(r4);
		    	procentAnimation4s.setToX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimationPowrot4s.setFromX(300*wybrany.getPytania().get(i).procent(3));
		    	procentAnimation4t.setToX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimationPowrot4t.setFromX(300*wybrany.getPytania().get(i).procent(3)/2);
		    	procentAnimation4.play();
				procentAnimation4.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	procentAnimationPowrot1.setNode(r1);
        		    	procentAnimationPowrot2.setNode(r2);
        		    	procentAnimationPowrot3.setNode(r3);
        		    	procentAnimationPowrot4.setNode(r4);
        		    	procentAnimationPowrot1.play();
        		    	procentAnimationPowrot2.play();
        		    	procentAnimationPowrot3.play();
        		    	procentAnimationPowrot4.play();
        		    	hide.setDelay(Duration.seconds(1f));
        		    	hide.setNode(ekranPytania);
        		    	hide.play();
        		    	hide.setOnFinished(new EventHandler<ActionEvent>() {
                		    @Override
                		    public void handle(ActionEvent event) {
                		    	hide.setDelay(Duration.seconds(0));
                		    	i++;
                		    	if(wybrany.getPytania().size() > i)
                		    	{
	                		    	numerPytania.setText("Pytanie "+(i+1));
	                		    	tekstPytania.setText(wybrany.getPytania().get(i).getTekst());
	                		    	btnOdp1.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(0).getTekst());
	                		    	btnOdp2.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(1).getTekst());
	                		    	btnOdp3.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(2).getTekst());
	                		    	btnOdp4.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(3).getTekst());
	                				btnOdp4.getStyleClass().clear();
	                		    	btnOdp4.getStyleClass().add("button");
		            		    	show.setNode(ekranPytania);
		            		    	show.play();
                		    	}
                		    	else
                		    	{
                					btnOdp1.getStyleClass().clear();
                			    	btnOdp1.getStyleClass().add("button");
                					btnOdp2.getStyleClass().clear();
                			    	btnOdp2.getStyleClass().add("button");
                					btnOdp3.getStyleClass().clear();
                			    	btnOdp3.getStyleClass().add("button");
                					btnOdp4.getStyleClass().clear();
                			    	btnOdp4.getStyleClass().add("button");
                		    		hideFast.setNode(ekranPodsumowanie);
                    		    	hideFast.play();
                    		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
                            		    @Override
                            		    public void handle(ActionEvent event) {
                            		    	ekranPodsumowanie.setVisible(true);
                            		    	ekranPytania.setVisible(false);
                            		    	show.setNode(ekranPodsumowanie);
                            		    	tytulPodsumowanie.setText(wybrany.getTytul());
                            		    	wynikPodsumowanie.setText("Wynik: "+odpPop+"/"+wybrany.getPytania().size());
                            		    	updateOdpowiedzi();
                            		    	show.play();
                            		    }
                            		});
                		    	}
                		    }
                		});
        		    }
        		});
            }
        });
		btnOdp4.setLayoutX(405);
		btnOdp4.setLayoutY(510);
		ekranPytania.getChildren().add(btnOdp4);
		//koniec = ekran pytania
        
		//ekran testu
		Label tytulEkranTestu = new Label("Tytuł");
		tytulEkranTestu.getStyleClass().add("labelTytul");
		tytulEkranTestu.setLayoutX(0);
		tytulEkranTestu.setLayoutY(0);
		tytulEkranTestu.setWrapText(true);
		ekranTestu.getChildren().add(tytulEkranTestu);
		Label opisEkranTestu = new Label("Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis Opis ");
		opisEkranTestu.setLayoutX(50);
		opisEkranTestu.setLayoutY(200);
		opisEkranTestu.setWrapText(true);
		opisEkranTestu.getStyleClass().add("labelMultiline");
		ekranTestu.getChildren().add(opisEkranTestu);
		Button btnEkranTestuRozpocznij = new Button();
		btnEkranTestuRozpocznij.setText("Rozpocznij");
		btnEkranTestuRozpocznij.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				hide.setNode(ekranTestu);
                hide.play();
                hide.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
        		    	numerPytania.setText("Pytanie "+(i+1));
        		    	tekstPytania.setText(wybrany.getPytania().get(i).getTekst());
        		    	btnOdp1.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(0).getTekst());
        		    	btnOdp2.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(1).getTekst());
        		    	btnOdp3.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(2).getTekst());
        		    	btnOdp4.setText(wybrany.getPytania().get(i).getOdpowiedzi().get(3).getTekst());
        		    	hideFast.setNode(ekranPytania);
        		    	hideFast.play();
        		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
                		    @Override
                		    public void handle(ActionEvent event) {
                		    	ekranPytania.setVisible(true);
                		    	odpPop = 0;
                		    }
                		});
        		    	show.setNode(ekranPytania);
        		    	show.play();
        		    	ekranTestu.setVisible(false);
        		    }
        		});
            }
        });
		btnEkranTestuRozpocznij.setLayoutX(95);
		btnEkranTestuRozpocznij.setLayoutY(510);
		ekranTestu.getChildren().add(btnEkranTestuRozpocznij);
		Button btnEkranTestuPowrot = new Button();
		btnEkranTestuPowrot.setText("Powrót");
		btnEkranTestuPowrot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				hide.setNode(ekranTestu);
                hide.play();
                hide.setOnFinished(new EventHandler<ActionEvent>() {
        		    @Override
        		    public void handle(ActionEvent event) {
                		listaTestów.setVisible(true);
        		    	show.setNode(listaTestów);
        		    	show.play();
        		    	ekranTestu.setVisible(false);
        		    }
        		});
            }
        });
		btnEkranTestuPowrot.setLayoutX(405);
		btnEkranTestuPowrot.setLayoutY(510);
		ekranTestu.getChildren().add(btnEkranTestuPowrot);
		//koniec = ekran testu
		
		//lista testów
		//
    	data.clear();
    	//
		hideFast.setNode(listaTestów);
		hideFast.play();
        table.setEditable(true);
        TableColumn tytułCol = new TableColumn("Tytuł");
        tytułCol.setMinWidth(400);
        tytułCol.setPrefWidth(400);
        tytułCol.setMaxWidth(400);
        tytułCol.setCellValueFactory(
                new PropertyValueFactory<>("tytul"));
        TableColumn autorCol = new TableColumn("Autor");
        autorCol.setMinWidth(400);
        autorCol.setPrefWidth(400);
        autorCol.setMaxWidth(400);
        autorCol.setCellValueFactory(
                new PropertyValueFactory<>("autor"));
        table.setItems(data);
        table.getColumns().addAll(tytułCol, autorCol);
        table.setMinHeight(700);
        table.setPrefHeight(700);
        table.setMaxHeight(700);
        table.setLayoutX(0);
        table.setLayoutY(60);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        	if(newSelection != null)
        	{
	        	wybrany = newSelection;
	        	tytulEkranTestu.setText(wybrany.getTytul());
	        	opisEkranTestu.setText(wybrany.getOpis());
	        	hide.setNode(listaTestów);
	            hide.play();
	            hide.setOnFinished(new EventHandler<ActionEvent>() {
	    		    @Override
	    		    public void handle(ActionEvent event) {
	    		    	hideFast.setNode(ekranTestu);
	    		    	hideFast.play();
	    		    	hideFast.setOnFinished(new EventHandler<ActionEvent>() {
	            		    @Override
	            		    public void handle(ActionEvent event) {
	            		    	ekranTestu.setVisible(true);
	            		    }
	            		});
	    		    	show.setNode(ekranTestu);
	    		    	show.play();
	    		    	listaTestów.setVisible(false);
	    		    }
	    		});
        	}
        });
        listaTestów.getChildren().add(table);
		//koniec = lista testów
		
		
		Group root = new Group();
		root.getChildren().add(new Rectangle(800, 600, new Color(.2f, .2f, .2f, 1f)));
		Button btnClose = new Button();
		btnClose.setText("X");
		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });
		btnClose.setLayoutX(745);
		btnClose.setLayoutY(5);
		btnClose.getStyleClass().add("buttonKolo");

		root.getChildren().add(ekranPowitalny);
		root.getChildren().add(listaTestów);
		root.getChildren().add(ekranTestu);
		root.getChildren().add(ekranPytania);
		root.getChildren().add(ekranPodsumowanie);

		root.getChildren().add(btnClose);
		primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(root, 800, 600);
        File f = new File("res/style2.css");
        scene.getStylesheets().clear();
        //scene.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        scene.getStylesheets().add(getClass().getResource("style2.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public ArrayList<Test> getTesty(){
		try {
			int port = 753;
			
			Socket socket = new Socket(serwer, port);
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String str = "getTesty";
			
			socket.setTcpNoDelay(true);
			out.println(str);
			out.flush();
			
			System.out.println("rozpoczynam odbiór");
			InputStream inputStream = socket.getInputStream();
			ObjectInputStream objInputStream = null;
			objInputStream = new ObjectInputStream(inputStream);
            ArrayList<Test> p = (ArrayList<Test>) objInputStream.readObject();
			System.out.println("kończę odbiór");
			socket.close();
			return p;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}
	public void updateOdpowiedzi(){
		try {
			int port = 754;
			
			Socket socket = new Socket(serwer, port);
			
			socket.setTcpNoDelay(true);
			OutputStream outputStream = socket.getOutputStream();
	        ObjectOutputStream objOutputStream = new ObjectOutputStream(outputStream);
	        objOutputStream.writeObject(user);
            objOutputStream.flush();
	        objOutputStream.writeObject(odp);
            objOutputStream.flush();
			socket.close();
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}