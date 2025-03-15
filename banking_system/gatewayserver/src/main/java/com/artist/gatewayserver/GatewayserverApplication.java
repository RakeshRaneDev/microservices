package com.artist.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator artistBankRouteConfig(RouteLocatorBuilder routeLocatorBuilder){
		return  routeLocatorBuilder.routes()
						.route(p -> p
								.path("/artist/accounts/**")
						       .filters( f -> f.rewritePath("/artist/accounts/(?<segment>.*)","/${segment}")
									   .addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.uri("lb://ACCOUNTS"))
						.route(p -> p
						 	.path("/artist/loans/**")
							.filters( f -> f.rewritePath("/artist/loans/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://LOANS"))
						.route(p -> p
							.path("/artist/cards/**")
							.filters( f -> f.rewritePath("/artist/cards/(?<segment>.*)","/${segment}")
									.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
							.uri("lb://CARDS")).build();
	}

}
