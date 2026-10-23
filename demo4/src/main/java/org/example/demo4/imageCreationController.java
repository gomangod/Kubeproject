package org.example.demo4;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;

public class imageCreationController {

    private static final Logger log = LoggerFactory.getLogger(imageCreationController.class);
    @FXML
    FlowPane flowPane;
    @FXML
    Button backbutton;
    public DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();
    DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(config.getDockerHost())
            .sslConfig(config.getSSLConfig())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build();

    DockerClient dockerClient = DockerClientImpl.getInstance(config, httpClient);

    public void initialize(){
        Thread thread = new Thread(this::CreateImage);
        thread.start();
    }
    public void CreateImage() {
        Path dockerFilePath = Path.of("dockerfile");
        File dockerfile = new File(dockerFilePath.toUri());
        if(Files.exists(dockerFilePath)){
            BuildImageCmd buildImageCmd = dockerClient.buildImageCmd(dockerfile)
                    .withTags(Collections.singleton("my-image:latest"))
                    .withNoCache(true);
            try {
                buildImageCmd.exec(new dockerprogreshower(flowPane)).awaitCompletion();
            }
            catch (Exception e){
                log.error("e: ", e);
            }
        }
    }

    protected void senceloader(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dockerFileFrom.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
    }


    public void onBackButton(javafx.event.ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        senceloader(stage);
    }
}