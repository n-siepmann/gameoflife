/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nicksiepmann.gameoflife;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Nick.Siepmann
 */
@Getter
public class GameFrame {

    private final LifeController controller;
    private VBox parent;
    private Button startStop;
    @Setter
    private GridPane grid;
//    private VBox pane;
//    private HBox hbox;

    public GameFrame() {
        this.controller = new LifeController();
        this.parent = new VBox(10);
        this.parent.setAlignment(Pos.CENTER);
        this.parent.setPadding(new Insets(5, 5, 5, 5));
        Pane pane = gamePane();
        HBox controls = controlsBox();
        this.parent.getChildren().add(pane);
        this.parent.getChildren().add(controls);
    }

    public GridPane buildGrid(int size) {
        GridPane grid = new GridPane();
        this.controller.newGame(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CheckBox checkbox = new CheckBox();
                checkbox.setSelected(this.controller.getBoard().getCell(i, j));
                grid.add(checkbox, i, j);
            }
        }
        return grid;
    }

    private void refreshGrid() {
        this.grid.getChildren().stream().forEach(s -> {
            ((CheckBox) s).setSelected(this.controller.getBoard().getCell(GridPane.getRowIndex(s), GridPane.getColumnIndex(s)));
        });
    }

    private VBox gamePane() {
        VBox pane = new VBox(5);
        pane.setAlignment(Pos.CENTER);
        HBox hbox = new HBox(5);
        hbox.setAlignment(Pos.CENTER);
        Label sizeTitle = new Label("Size:");
        Label sizeVal = new Label("20");
        Slider sizeSlider = new Slider();
        sizeSlider.setMin(20);
        sizeSlider.setMax(55);
        sizeSlider.valueProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        sizeVal.setText(String.valueOf((int) sizeSlider.getValue()));
                        controller.setSize((int) sizeSlider.getValue());
                    }
                }
                );
        sizeSlider.setValue(20);

        pane.getChildren().add(hbox);

        GridPane gameGrid = buildGrid(20);
        pane.getChildren().add(gameGrid);
        this.grid = gameGrid;

        Button create = new Button("Create board");
        create.setOnAction((event) -> {
            this.controller.clearGame();
            startStop.setText("Start");
            pane.getChildren().remove(pane.getChildren().size() - 1);
            GridPane newGrid = buildGrid((int) sizeSlider.getValue());
            pane.getChildren().add(newGrid);
            this.grid = newGrid;
            this.grid.getScene().getWindow().sizeToScene();
            this.grid.getScene().getWindow().centerOnScreen();
        }
        );

        hbox.getChildren().addAll(Arrays.asList(sizeTitle, sizeSlider, sizeVal, create));
        return pane;
    }

    private HBox controlsBox() {
        HBox controlsbox = new HBox(5);
        controlsbox.setAlignment(Pos.CENTER);
        this.startStop = new Button("Start");
        startStop.setOnAction((event) -> {
            gameStart();
        });

        Label intervalTitle = new Label("Interval:");
        Label intervalVal = new Label("100ms");
        Slider intervalSlider = new Slider();
        intervalSlider.setMin(100);
        intervalSlider.setMax(999);
        intervalSlider.valueProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        intervalVal.setText(String.valueOf((int) intervalSlider.getValue()) + "ms");
                        controller.setInterval((int) intervalSlider.getValue());
                    }
                }
                );
        intervalSlider.setValue(100);

        controlsbox.getChildren().addAll(Arrays.asList(startStop, intervalTitle, intervalSlider, intervalVal));
        return controlsbox;
    }

    private void gameStart() {
        if (!this.controller.isRunning()) {
            startStop.setText("Stop");
            this.grid.getChildren().stream().forEach(s -> {
                this.controller.getBoard().setCell(GridPane.getRowIndex(s), GridPane.getColumnIndex(s), ((CheckBox) s).isSelected());
            });
            this.controller.setRunning(true);
            Thread updater = new Thread(() -> {
                while (this.controller.isRunning()) {
                    boolean alive = this.controller.getBoard().run();
                    refreshGrid();
                    if (!alive) {
                        this.controller.stop();
                        Platform.runLater(() -> {
                            this.startStop.setText("Start");
                        });
                        return;
                    }
                    try {
                        Thread.sleep(this.controller.getInterval());

                    } catch (InterruptedException ex) {
                        Logger.getLogger(LifeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            updater.start();
            return;
        }
        this.controller.stop();
        startStop.setText("Start");
    }
}
