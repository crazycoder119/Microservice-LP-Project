package com.chandima.branchconnector.branchui;

import com.chandima.branchconnector.branchui.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BranchUiApplication {

	public static void main(String[] args) {
		CustomerUIServerImpl customerUIServer= new CustomerUIServerImpl();
		DeliveryUIServerImpl deliveryUIServer = new DeliveryUIServerImpl();
		OrderUIServerImpl orderUIServer = new OrderUIServerImpl();
		ProductUIServerImpl productUIServer = new ProductUIServerImpl();
		deliveryUIServer.setDelivery(orderUIServer);
		orderUIServer.setOrder(customerUIServer,productUIServer);

		SpringApplication.run(BranchUiApplication.class, args);
	}

}
