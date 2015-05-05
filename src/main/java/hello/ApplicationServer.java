package hello;

import com.ofg.infrastructure.discovery.EnableServiceDiscovery;
import com.ofg.infrastructure.web.resttemplate.fluent.EnableServiceRestClient;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableServiceDiscovery
@EnableServiceRestClient
@PropertySource("classpath:prop.properties")
public class ApplicationServer {

    public static void main(String[] args) {

        ApplicationContext ctx =  SpringApplication.run(ApplicationServer.class, args);
        ServiceRestClient serviceRestClient = (ServiceRestClient) ctx.getBean("serviceRestClient");

        String response =  serviceRestClient.forService("greeting")
                .get()
                .onUrl("greeting")
                .anObject()
                .ofType(String.class);

        System.out.println(response);

    }
}
