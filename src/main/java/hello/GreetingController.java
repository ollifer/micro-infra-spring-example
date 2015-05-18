package hello;

import com.google.common.util.concurrent.ListenableFuture;
import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("rest")
public class GreetingController {

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    @Autowired
    AsyncRetryExecutor executor;

    @Autowired
    ServiceRestClient serviceRestClient;

    @RequestMapping("/greeting")
    @ResponseBody
    public Response greeting(@RequestParam(value = "name", defaultValue = "World") String name) throws ExecutionException, InterruptedException {

        Greeting g = new Greeting(counter.incrementAndGet(),
                String.format(template, name));


        ListenableFuture<String> future = serviceRestClient.forService("greeting")
                .retryUsing(
                        executor.dontRetry()).
                        get()
                .onUrl("rest/greeting")
                .anObject()
                .ofTypeAsync(String.class);

        future.addListener(() -> {

            try {
                String r = future.get();
                System.out.println("Response: " + r);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }, Executors.newSingleThreadExecutor());

        return Response.ok().entity(g).build();
    }
}
