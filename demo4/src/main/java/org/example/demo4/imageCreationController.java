package org.example.demo4;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.ResourceBundle;

public class imageCreationController {

    private static final Logger log = LoggerFactory.getLogger(imageCreationController.class);
    @FXML
    FlowPane flowPane;
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
}