package org.example.demo4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private TextField Application_type;

    @FXML
    private TextField Application_name;

    @FXML
    private TextField From_input;

    @FXML
    private TextField Expose_input;

    @FXML
    private TextField Version_input;

    @FXML
    private TextField Command_input;

    @FXML
    private TextField CMD_input;

    @FXML
    private TextField Tool_input;

    @FXML
    private TextField Artifact_input;

    @FXML
    private TextField Project_source;

    @FXML
    private Button Gen_Doc_file_button;

    @FXML
    private Button Restet_button;

    @FXML
    private Button ZIP_upload_button;

    @FXML
    private Button Add_Env_var_button;

    @FXML
    private ScrollPane Env_var_scroll_pane;

    @FXML
    private FlowPane Env_var_flow_pane;

    Map<String,String> EnvVar = new LinkedHashMap<>();
    int EnvVarChildCount = 0;
    private ArrayList<TextField> TextFieldArray;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TextFieldArray = new ArrayList<>(List.of(Application_type,Application_name,Project_source,From_input,Expose_input,CMD_input,Version_input,Tool_input,Artifact_input));
        Env_var_scroll_pane.setFitToWidth(true);
    }

    @FXML
    protected void onGen_Doc_file_buttonClick() {
        int filledFeilds = 0;
        for(int i = 0; i < TextFieldArray.size(); i++ ) {
            if (Objects.equals(TextFieldArray.get(i).getText(), "")) {
                TextFieldArray.get(i).setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            } else {
                if (!Expose_input.getText().matches("\\d+")) {
                    Expose_input.clear();
                    Expose_input.setPromptText("number's only");
                    Expose_input.setStyle("-fx-border-color: red;");
                    return;
                }
                TextFieldArray.get(i).setStyle("-fx-border-color: green; -fx-border-width: 1px;");
                filledFeilds++;
            }
        }
        if (filledFeilds >= TextFieldArray.size() ) {
            stringMapper();
        }
    }

    @FXML
    protected void onAdd_Env_var_buttonClick(){
        Env_var_flow_pane.getChildren().add(CreateRow());
    }

    protected HBox CreateRow(){
        EnvVarChildCount++;
        TextField left = new TextField();
        left.setId(""+EnvVarChildCount);
        left.setPrefWidth(120);
        Label eq_smb = new Label("=");
        TextField right = new TextField();
        right.setId(""+EnvVarChildCount);
        right.setPrefWidth(120);
        Button delete = new Button();
        ImageView icon = new ImageView(new Image(String.valueOf(getClass().getResource("icon-wrap.png"))));
        icon.setFitWidth(16);
        icon.setFitHeight(16);
        delete.setGraphic(icon);
        delete.setId(""+EnvVarChildCount);
        delete.setStyle("-fx-background-color: white;");
        HBox row = new HBox(5,left,eq_smb,right,delete);
        row.setId(""+EnvVarChildCount);
        row.setStyle("-fx-padding: 2;");
        delete.setOnAction(e-> deleterow(row));
        return row;
    }

    protected void deleterow(HBox row){
        Env_var_flow_pane.getChildren().remove(row);
        EnvVarChildCount--;
    }

    @FXML
    protected void onZIP_upload_buttonClick(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select zip project");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("ZIP files","*.zip"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File selectedfile = fileChooser.showOpenDialog(stage);
        if(selectedfile != null){
            Project_source.setText(selectedfile.getAbsolutePath());
            System.out.println(selectedfile.getAbsolutePath());
        }
        else {
            System.out.println("no file selected");
        }
    }

    @FXML
    protected void onRestet_buttonClick(){
        for(int i = 0; i < TextFieldArray.size(); i++ ) {
            TextFieldArray.get(i).clear();
        }
        Command_input.clear();
        Env_var_flow_pane.getChildren().clear();
    }

    protected String getEnvVar(){
        String EnvVarString = "";
        for(Node node: Env_var_flow_pane.getChildren()){
            if(node instanceof HBox row){
                List<TextField> fields = row.getChildren().stream().filter(n -> n instanceof TextField).map(n -> (TextField) n).toList();
                if(fields.size() >= 2){
                    EnvVar.put(fields.get(0).getText(),fields.get(1).getText());
                    EnvVarString = EnvVarString + "ENV " + fields.get(0).getText()+"="+fields.get(1).getText() + "\n" ;
                }
            }
        }
        return EnvVarString;
    }
    protected void stringMapper(){
        String From = "FROM "+ From_input.getText() + ":" + Version_input.getText();
        System.out.println(From);
        System.out.println("WORKDIR /app");
        if(!Objects.equals(Command_input.getText(), "")){
            String Run = "RUN " + Command_input.getText();
        }
        String Copy = "COPY target/" + Artifact_input.getText() + " app.jar";
        System.out.println(Copy);
        System.out.println(getEnvVar());
        String Expose = "EXPOSE " + Expose_input.getText();
        System.out.println(Expose);
        System.out.println(getCMDInput());
    }

    protected String getCMDInput(){
        String CMD ="";
        String presplit = CMD_input.getText();
        String[] postsplit = presplit.split(" ");
        if(postsplit.length == 1){
            CMD = "CMD [" + "\"" + postsplit[0] + "\"]" ;
        }
        else {
            for (int i = 0; i < postsplit.length; i++) {
                if (i == 0) {
                    CMD = "CMD [" + "\"" + postsplit[i] + "\"";
                } else if (i == postsplit.length - 1) {
                    CMD = CMD + ",\"" + postsplit[i] + "\"]";
                    break;
                } else {
                    CMD = CMD + ",\"" + postsplit[i] + "\"";
                }
            }
        }
        return CMD;
    }
}
