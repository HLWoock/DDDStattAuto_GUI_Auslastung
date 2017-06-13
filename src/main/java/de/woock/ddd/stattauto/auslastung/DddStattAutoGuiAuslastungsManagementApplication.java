package de.woock.ddd.stattauto.auslastung;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;

import de.woock.ddd.stattauto.auslastung.service.AuslastungsService;
import de.woock.ddd.stattauto.auslastung.views.AuslastungsView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

@RefreshScope
@EnableDiscoveryClient
@SpringBootApplication
public class DddStattAutoGuiAuslastungsManagementApplication extends Application {

	@Value("${auslastung.refresh.period}")
	String period = "0";
	
	@Autowired AuslastungsView    auslastungsView;
	@Autowired AuslastungsService service;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@PostConstruct
	public void holeZeitraum() {
		System.out.println(service.holeZeitraum());
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new Group());
		primaryStage.setTitle("StattAuto AuslastungsManagement");
		primaryStage.setWidth(1480);
		primaryStage.setHeight(600);

        ((Group) scene.getRoot()).getChildren().addAll(auslastungsView.initPane());
 
        primaryStage.setScene(scene);
        primaryStage.show();
		
    }
	
	@Override
	public void init() throws Exception {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(DddStattAutoGuiAuslastungsManagementApplication.class).headless(false)
                                                                                                                                    .web(false)
                                                                                                                                    .run("");
		auslastungsView = context.getBean(AuslastungsView.class);
	}
}
