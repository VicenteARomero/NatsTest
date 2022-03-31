package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import io.nats.client.MessageHandler;
import io.nats.client.Nats;

@SpringBootApplication
public class NatsMessageApplication {

	public static void main(String[] args) {
		SpringApplication.run(NatsMessageApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			Connection connect = Nats.connect();
			Dispatcher dispatcher = connect.createDispatcher(new MessageHandler() {
				@Override
				public void onMessage(Message message) throws InterruptedException{
					String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss").format(new java.util.Date());
					String str = "Received Message "+ message + " "+ timeStamp;
					System.out.println(str);
					try {
						
					BufferedWriter writer = new BufferedWriter(new FileWriter("Log.txt",true));
				    writer.append(str);
				    writer.close();
					}
					catch(Exception e) {
						
					}
				}
			});
			//dispatcher.subscribe("sport");
			dispatcher.subscribe("execution");
		};
	}
	

}
