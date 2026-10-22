package org.example.demo4;

import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javafx.scene.paint.Color.color;

public class dockerprogreshower extends BuildImageResultCallback  {
    private static final Logger log = LoggerFactory.getLogger(dockerprogreshower.class);
    public FlowPane flowPane;
    dockerprogreshower(FlowPane FlowPane){
        flowPane = FlowPane;
    }
    @Override
    public void onNext(BuildResponseItem item){
        super.onNext(item);
        if(item.getErrorDetail() != null){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textadder(item.getErrorDetail().getMessage(),Color.RED);
                }
            });
        }
        if(item.getStream() != null){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    textadder(item.getStream(),Color.WHITE);
                }
            });
        }
    }

    @Override
    public void onError(Throwable throwable) {
        super.onError(throwable);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                textadder(String.valueOf(throwable), Color.RED);
            }
        });
    }

    public void textadder(String string , Color color){
        Label text = new Label(">" + string + "\n");
        text.setTextFill(color);
        flowPane.getChildren().add(text);
    }
}

