package hello;

import java.io.IOException;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ofg.infrastructure.web.resttemplate.fluent.ServiceRestClient;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationServer.class)
@WebIntegrationTest
@Profile("prod")
public class ApplicationServerTest {

    TestingServer zkTestServer;

    private CuratorFramework cli;

    @Autowired
    private ServiceRestClient serviceRestClient;

    @Before
    public void startZookeeper() throws Exception {
        zkTestServer = new TestingServer(2181);
        cli = CuratorFrameworkFactory.newClient(zkTestServer.getConnectString(), new RetryOneTime(2000));
    }

    @After
    public void stopZookeeper() throws IOException {
        cli.close();
        zkTestServer.stop();
    }

    @Test
    public void shouldWork() throws Exception {
        String response = serviceRestClient.forService("greeting")
                .get()
                .onUrl("rest/greeting")
                .anObject()
                .ofType(String.class);

        System.out.println(response);
    }
}