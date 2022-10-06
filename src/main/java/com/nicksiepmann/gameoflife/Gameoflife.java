/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.nicksiepmann.gameoflife;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Nick.Siepmann
 */
public class Gameoflife extends Application {

    public static void main(String[] args) {
        launch(Gameoflife.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameFrame gameframe = new GameFrame();
        Scene view = new Scene(gameframe.getParent());
        view.getStylesheets().add("static/styles.css");
        stage.setScene(view);
        stage.setTitle("Conway's Game of Life");
        stage.getIcons().add(new Image("life.png"));

        stage.show();
    }

}
