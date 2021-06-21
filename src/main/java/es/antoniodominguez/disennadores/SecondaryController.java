package es.antoniodominguez.disennadores;

import es.antoniodominguez.disennadores.App;
import es.antoniodominguez.disennadores.App;
import es.antoniodominguez.entidades.Disennador;
import es.antoniodominguez.entidades.Grupodisenno;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javax.persistence.Query;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.RollbackException;


public class SecondaryController implements Initializable {
    
    private Disennador disennador;
    private boolean nuevoDisennador;
    
    private static final String CARPETA_FOTOS = "Fotos";

    
    @FXML
    private Button ButtonGuardarSecondary;
    @FXML
    private Button ButtonCancelarSecondary;
    @FXML
    private Button ButtonExaminarSecondary;
    @FXML
    private Button ButtonSuprimirSecondary;
    @FXML
    private TextField textFieldNombreSecondary;
    @FXML
    private TextField textFieldApellidosSecondary;
    @FXML
    private TextField textFieldDNISecondary;
    @FXML
    private TextField textFieldTelefonoSecondary;
    @FXML
    private TextField textFieldEmailSecondary;
    @FXML
    private DatePicker DatePicker;
    @FXML
    private TextField textFieldProyectosSecondary;
    @FXML
    private ComboBox<Grupodisenno> ComboBox;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private BorderPane rootSecondary;
    @FXML
    private CheckBox CheckBox;
    @FXML
    private Label CheckBoxLider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void setDisennador(Disennador disennador) {
        App.em.getTransaction().begin();
        this.disennador = disennador;
        mostrarDatos();
    }
    
    private void  mostrarDatos(){
        textFieldNombreSecondary.setText(disennador.getNombre());
        textFieldApellidosSecondary.setText(disennador.getApellidos());
        textFieldDNISecondary.setText(disennador.getDni());
        textFieldTelefonoSecondary.setText(disennador.getTelefono());
        textFieldEmailSecondary.setText(disennador.getEmail());
        
        if (disennador.getProyectos() != null){
            textFieldProyectosSecondary.setText(String.valueOf(disennador.getProyectos()));
        }
        
        if (disennador.getLider() != null) {
            CheckBox.setSelected(disennador.getLider());
        }
        
        if (disennador.getFechaNac() != null) {
            Date date = disennador.getFechaNac();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            DatePicker.setValue(localDate);
        }
        Query queryGrupodisennoFindAll = App.em.createNamedQuery("Grupodisenno.findAll");
        List<Grupodisenno> listGrupodisenno = queryGrupodisennoFindAll.getResultList();
        
        ComboBox.setItems(FXCollections.observableList(listGrupodisenno));
        if (disennador.getGrupo() != null) {
            ComboBox.setValue(disennador.getGrupo());
        }
        ComboBox.setCellFactory((ListView<Grupodisenno> l) -> new ListCell<Grupodisenno>() {
            @Override
            protected void updateItem(Grupodisenno grupo, boolean empty) {
                super.updateItem(grupo, empty);
                if (grupo == null || empty){
                    setText("");
                } else {
                    setText(grupo.getId() + "-" + grupo.getEstudio());
                }
            }
        });
        
        ComboBox.setConverter(new StringConverter<Grupodisenno>(){
            @Override
            public String toString(Grupodisenno grupo) {
                if (grupo == null) {
                    return null;
                } else {
                    return grupo.getId() + "-" + grupo.getEstudio();
                }
            }
            @Override
            public Grupodisenno fromString(String userId) {
                return null;
            }
        });
        
        if (disennador.getFoto() != null) {
            String imageFileName = disennador.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()) {
                Image image = new Image (file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void onActtionButtonGuardar(ActionEvent event) {
        boolean errorFormato = false;
        
        disennador.setNombre(textFieldNombreSecondary.getText());
        disennador.setApellidos(textFieldApellidosSecondary.getText());
        disennador.setTelefono(textFieldTelefonoSecondary.getText());
        disennador.setEmail(textFieldEmailSecondary.getText());
        disennador.setDni(textFieldDNISecondary.getText());
        disennador.setLider(CheckBox.isSelected());
        disennador.setGrupo(ComboBox.getValue());

        if (!textFieldProyectosSecondary.getText().isEmpty()) {
            try {
                disennador.setProyectos(Short.valueOf(textFieldProyectosSecondary.getText()));
            } catch (NumberFormatException ex) {
                errorFormato = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Número de proyecto no válido");
                alert.showAndWait();
                textFieldProyectosSecondary.requestFocus();
            }
        }
        
        if (DatePicker.getValue() != null) {
            LocalDate LocalDate = DatePicker.getValue();
            ZonedDateTime zonedDateTime =  LocalDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            disennador.setFechaNac(date);
        } else {
            disennador.setFechaNac(null);
        }
        
        if (!errorFormato) {
            try {
                if (disennador.getId() == null) {
                    System.out.println("Guardando nuevo diseñador en BD");
                    App.em.persist(disennador);
                } else {
                    System.out.println("Actualizando diseñador en BD");
                    App.em.merge(disennador);
                }
                App.em.getTransaction().commit();
                
                App.setRoot("primary");
            } catch (RollbackException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. " + 
                        "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    @FXML
    private void onActtionButtonCancelar(ActionEvent event) {
        App.em.getTransaction().rollback();
        
        try {
            App.setRoot("primary");
        } catch  (IOException ex){
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActtionButtonExaminar(ActionEvent event) {
        File carpetaFotos = new File(CARPETA_FOTOS);
        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdir();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes (jpg ,png)", "*.jpg", "*.png"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );
        File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/" + file.getName()).toPath());
                disennador.setFoto(file.getName());
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } catch (FileAlreadyExistsException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre de archivo duplicado");
                alert.showAndWait();
            } catch (IOException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No se ha podido guardar la imagen");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void onActtionButtonSuprimir(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar supresión de imagen");
        alert.setHeaderText("¿Desea SUPRIMIR el archivo asociado a al imagen, \n" 
            + "quitar la foto pero MANTENER el archivo, \no CANCELAR la operación");
        alert.setContentText("Elija la opción deseada:");
        
        ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
        ButtonType buttonTypeMantener = new ButtonType("Mantener");
        ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        
        alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeEliminar) {
            String imageFileName = disennador.getFoto();
            File file = new File(CARPETA_FOTOS + "/" + imageFileName);
            if (file.exists()){
                file.delete();
            }
            disennador.setFoto(null);
            imageViewFoto.setImage(null);
        } else if (result.get() == buttonTypeMantener) {
            disennador.setFoto(null);
            imageViewFoto.setImage(null);
        }
    }
        
}