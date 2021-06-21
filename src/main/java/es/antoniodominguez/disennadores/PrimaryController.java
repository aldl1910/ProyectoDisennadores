/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.antoniodominguez.disennadores;

import es.antoniodominguez.entidades.Disennador;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author anton
 */
public class PrimaryController implements Initializable {
    
    private Disennador disennadorSeleccionado;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldApelidos;
    @FXML
    private TableView<Disennador> tableViewDisennadores;
    @FXML
    private TableColumn<Disennador, String> columnNombre;
    @FXML
    private TableColumn<Disennador, String> columnApellidos;
    @FXML
    private TableColumn<Disennador, String> columnEmail;
    @FXML
    private TableColumn<Disennador, String> columnGrupo;
    @FXML
    private BorderPane rootPrimary;
    @FXML
    private Button ButtonBuscarPrimary;
    @FXML
    private CheckBox checkBoxCoincide;
    @FXML
    private TextField textFieldBuscar;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        columnGrupo.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getGrupo() != null) {
                        String estuGrupo = cellData.getValue().getGrupo().getEstudio();
                        property.setValue(estuGrupo);
                    }
                    return property;
                });
        tableViewDisennadores.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    disennadorSeleccionado = newValue;
                    if (disennadorSeleccionado != null) {
                        textFieldNombre.setText(disennadorSeleccionado.getNombre());
                        textFieldApelidos.setText(disennadorSeleccionado.getApellidos());
                    } else {
                        textFieldNombre.setText("");
                        textFieldApelidos.setText("");
                    }
                });
        cargarTodosDisennadores();
    }    
    private void cargarTodosDisennadores(){
        Query queryDisennadorFindAll = App.em.createNamedQuery("Disennador.findAll");
        List<Disennador> listDisennador = queryDisennadorFindAll.getResultList();
        tableViewDisennadores.setItems(FXCollections.observableArrayList(listDisennador));
    }
    @FXML 
    private void onActtionButtonNuevo(ActionEvent event) {
        try {
            App.setRoot("secondary");
            SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
            disennadorSeleccionado = new Disennador();
            secondaryController.setDisennador(disennadorSeleccionado);
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } 
    }

    @FXML
    private void onActtionButtonEditar(ActionEvent event) {
        if (disennadorSeleccionado != null) {
            try {
                App.setRoot("secondary");
                SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
                secondaryController.setDisennador(disennadorSeleccionado);
            } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atención");
            alert.setHeaderText("Debe seleccionar un registro");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActtionButtonSuprimir(ActionEvent event) {
        if(disennadorSeleccionado != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar");
            alert.setHeaderText("¿Desea suprimir el siguiente registro?");
            alert.setContentText(disennadorSeleccionado.getNombre() + " "
                    + disennadorSeleccionado.getApellidos());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                App.em.getTransaction().begin();
                App.em.remove(disennadorSeleccionado);
                App.em.getTransaction().commit();
                tableViewDisennadores.getItems().remove(disennadorSeleccionado);
                tableViewDisennadores.getFocusModel().focus(null);
                tableViewDisennadores.requestFocus();
            } else {
                int numFilaSeleccionada = tableViewDisennadores.getSelectionModel().getSelectedIndex();
                tableViewDisennadores.getItems().set(numFilaSeleccionada, disennadorSeleccionado);
                TablePosition pos = new TablePosition(tableViewDisennadores, numFilaSeleccionada, null);
                tableViewDisennadores.getFocusModel().focus(pos);
                tableViewDisennadores.requestFocus();
                } 
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atención");
                alert.setHeaderText("Debe seleccionar un registro");
                alert.showAndWait();
            }
    }

    @FXML
    private void onActtionButtonGuardar(ActionEvent event) {
        if (disennadorSeleccionado != null) {
            disennadorSeleccionado.setNombre(textFieldNombre.getText());
            disennadorSeleccionado.setApellidos(textFieldApelidos.getText());
            App.em.getTransaction().begin();
            App.em.merge(disennadorSeleccionado);
            App.em.getTransaction().commit();
            
            int numFilaSeleccionada = tableViewDisennadores.getSelectionModel().getSelectedIndex();
            tableViewDisennadores.getItems().set(numFilaSeleccionada, disennadorSeleccionado);
            TablePosition pos = new TablePosition(tableViewDisennadores, numFilaSeleccionada, null);
            tableViewDisennadores.getFocusModel().focus(pos);
            tableViewDisennadores.requestFocus();
            
        }
    }

    @FXML
    private void onActtionButtonBuscar(ActionEvent event) {
        if (!textFieldBuscar.getText().isEmpty()) {
            if (checkBoxCoincide.isSelected()) {
                Query queryDisennadorFindByNombre = App.em.createNamedQuery("Disennador.findByNombre");
                queryDisennadorFindByNombre.setParameter("nombre", textFieldBuscar.getText());
                List<Disennador> listDisennador = queryDisennadorFindByNombre.getResultList();
                tableViewDisennadores.setItems(FXCollections.observableArrayList(listDisennador));
            } else {
                String strQuery = "SELECT * FROM Disennador WHERE LOWER(nombre) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryDisennadorFindLikeNombre = App.em.createNativeQuery(strQuery, Disennador.class);
                
                List<Disennador> listDisennador = queryDisennadorFindLikeNombre.getResultList();
                tableViewDisennadores.setItems(FXCollections.observableArrayList(listDisennador));
            }
        } else {
            cargarTodosDisennadores();
        }
    }
    
}
