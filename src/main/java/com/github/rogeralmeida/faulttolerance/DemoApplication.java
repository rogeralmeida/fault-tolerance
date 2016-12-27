package com.github.rogeralmeida.faulttolerance;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.github.rogeralmeida.faulttolerance.foursquare.services.FourSquareService;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

import lombok.extern.java.Log;

@SpringBootApplication
@Configuration
@Log
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public ServletRegistrationBean registerHystrixMetrics() {
		return new ServletRegistrationBean(new HystrixMetricsStreamServlet(), "/hystrix.stream");
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		ClientHttpRequestInterceptor loggerInterceptor = new LoggerInterceptor();
		interceptors.add(loggerInterceptor);
		restTemplate.setInterceptors(interceptors);
		StringHttpMessageConverter element = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		element.setWriteAcceptCharset(false);
		restTemplate.getMessageConverters().add(0, element);
		return restTemplate;
	}

	@Bean
	public FourSquareService fourSquareService() {
		return new FourSquareService();
	}

	private class LoggerInterceptor implements ClientHttpRequestInterceptor {
		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			log.info("Request: {" + request.getMethod() + " " + request.getURI() + " headers: {" + request.getHeaders() + "} " + new String(body, "UTF-8") + "}");
			ClientHttpResponse response = execution.execute(request, body);
			log.info("Response: {" + response.getStatusText() + " " + response.getBody() + "}");
			return response;
		}
	}
}
