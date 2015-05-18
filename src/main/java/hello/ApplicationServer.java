package hello;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.ofg.infrastructure.discovery.EnableServiceDiscovery;
import com.ofg.infrastructure.web.resttemplate.fluent.EnableServiceRestClient;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableServiceDiscovery
@EnableServiceRestClient
public class ApplicationServer {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ApplicationContext ctx = SpringApplication.run(ApplicationServer.class, args);
    }
}
