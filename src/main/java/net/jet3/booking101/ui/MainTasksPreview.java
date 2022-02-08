package net.jet3.booking101.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.jet3.booking101.ui.edit.InsertNewUI;
import net.jet3.booking101.undoHandler.UndoHandler;
import net.jet3.booking101.undoHandler.Undoable;

public class MainTasksPreview
{
    private StackPane root;

    int rows = 30;
    int columns = 12;
    double width = 1200;
    double height = 650;
    boolean showHoverCursor = true;

    public static Grid publicGrid;

    private ContextMenu contextMenu;
    private MenuItem insertNew;
    private MenuItem deleteCurrent;

    public MainTasksPreview() {
        root = new StackPane();

        contextMenu = new ContextMenu();
        insertNew = new MenuItem("Insert new");
        deleteCurrent = new MenuItem("Delete current");
        contextMenu.getItems().addAll(insertNew, deleteCurrent);
    }

    public void init(Stage stage, VBox root, Scene scene) {
        Grid grid = new Grid(columns, rows, width, height);
        publicGrid = grid;

        MouseGestures mg = new MouseGestures();
        grid.setTranslateY(50);
        grid.setTranslateX(200);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Cell cell = new Cell(column, row);
                mg.makePaintable(cell);
                grid.add(cell, column, row);
            }
        }

        root.getChildren().add(grid);
        scene.getStylesheets().add("jfxstyle/grid.css");
        stage.setScene(scene);
    }

    private class Grid extends Pane {

        int rows;
        int columns;

        double width;
        double height;

        public Cell[][] cells;

        public Grid( int columns, int rows, double width, double height) {

            this.columns = columns;
            this.rows = rows;
            this.width = width;
            this.height = height;

            cells = new Cell[rows][columns];

        }

        /**
         * Add cell to array and to the UI.
         */
        public void add(Cell cell, int column, int row) {

            cells[row][column] = cell;

            double w = width / columns;
            double h = height / rows;
            double x = w * column;
            double y = h * row;

            cell.setLayoutX(x);
            cell.setLayoutY(y);
            cell.setPrefWidth(w);
            cell.setPrefHeight(h);

            getChildren().add(cell);

        }

        public Cell getCell(int column, int row) {
            return cells[row][column];
        }

        /**
         * Unhighlight all cells
         */
        public void unhighlight() {
            for( int row=0; row < rows; row++) {
                for( int col=0; col < columns; col++) {
                    cells[row][col].unhighlight();
                }
            }
        }
    }

    private class Cell extends StackPane {

        int column;
        int row;

        public Cell(int column, int row) {

            this.column = column;
            this.row = row;

            getStyleClass().add("cell");

//          Label label = new Label(this.toString());
//
//          getChildren().add(label);

            setOpacity(0.9);
        }

        public void highlight() {
            // ensure the style is only once in the style list
            getStyleClass().remove("cell-highlight");

            // add style
            getStyleClass().add("cell-highlight");

            UndoHandler.Companion.push(new Undoable(this::unhighlight, this::highlight));
        }

        public void unhighlight() {
            getStyleClass().remove("cell-highlight");

            UndoHandler.Companion.push(new Undoable(this::highlight, this::unhighlight));
        }

        public void hoverHighlight() {
            // ensure the style is only once in the style list
            getStyleClass().remove("cell-hover-highlight");

            // add style
            getStyleClass().add("cell-hover-highlight");
        }

        public void hoverUnhighlight() {
            getStyleClass().remove("cell-hover-highlight");
        }

        public String toString() {
            return this.column + "/" + this.row;
        }
    }

    public class MouseGestures {

        public void makePaintable( Node node) {


            // that's all there is needed for hovering, the other code is just for painting
            if(showHoverCursor) {
                node.hoverProperty().addListener(new ChangeListener<Boolean>(){

                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                        System.out.println( observable + ": " + newValue);

                        if( newValue) {
                            ((Cell) node).hoverHighlight();
                        } else {
                            ((Cell) node).hoverUnhighlight();
                        }

                        for( String s: node.getStyleClass())
                            System.out.println( node + ": " + s);
                    }

                });
            }

            node.setOnMousePressed( onMousePressedEventHandler);
            node.setOnDragDetected( onDragDetectedEventHandler);
            node.setOnMouseDragEntered(onMouseDragEnteredEventHandler);
            node.setCursor(Cursor.HAND);

            node.setOnMouseClicked(e -> {
                if (e.getButton().equals(MouseButton.PRIMARY)) {
                    insertNew.setDisable(node.getStyleClass().contains("hasProperty"));
                    deleteCurrent.setDisable(!node.getStyleClass().contains("hasProperty"));

                    insertNew.setOnAction(dwa -> {
                        InsertNewUI insertNewUI = new InsertNewUI(((Cell) node).column, ((Cell) node).row);
                        insertNewUI.start();
                    });

                    contextMenu.show(node, e.getScreenX(), e.getScreenY());
                }
            });

        }

        EventHandler<MouseEvent> onMousePressedEventHandler = event -> {

            Cell cell = (Cell) event.getSource();

            if( event.isPrimaryButtonDown()) {
                cell.highlight();
            } else if( event.isSecondaryButtonDown()) {
                cell.unhighlight();
            }
        };

        EventHandler<MouseEvent> onMouseDraggedEventHandler = event -> {

            PickResult pickResult = event.getPickResult();
            Node node = pickResult.getIntersectedNode();

            if( node instanceof Cell) {

                Cell cell = (Cell) node;

                if( event.isPrimaryButtonDown()) {
                    cell.highlight();
                } else if( event.isSecondaryButtonDown()) {
                    cell.unhighlight();
                }

            }

        };

        EventHandler<MouseEvent> onMouseReleasedEventHandler = event -> {
        };

        EventHandler<MouseEvent> onDragDetectedEventHandler = event -> {

            Cell cell = (Cell) event.getSource();
            cell.startFullDrag();

        };

        EventHandler<MouseEvent> onMouseDragEnteredEventHandler = event -> {

            Cell cell = (Cell) event.getSource();

            if( event.isPrimaryButtonDown()) {
                cell.highlight();
            } else if( event.isSecondaryButtonDown()) {
                cell.unhighlight();
            }

        };

    }
}
