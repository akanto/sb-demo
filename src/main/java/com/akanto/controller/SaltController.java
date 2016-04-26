package com.akanto.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.akanto.api.SaltEndpoint;
import com.akanto.hello.Greeting;
import com.akanto.service.AkkaService;
import com.google.gson.reflect.TypeToken;
import com.suse.salt.netapi.AuthModule;
import com.suse.salt.netapi.calls.LocalAsyncResult;
import com.suse.salt.netapi.calls.LocalCall;
import com.suse.salt.netapi.calls.modules.Test;
import com.suse.salt.netapi.client.SaltClient;
import com.suse.salt.netapi.datatypes.target.Glob;
import com.suse.salt.netapi.datatypes.target.Target;
import com.suse.salt.netapi.results.ResultInfoSet;

// controller contains only spring annotation

@Controller
public class SaltController implements SaltEndpoint {

    private static final String SALT_API_URL = "http://192.168.99.100:8000";
    private static final String USER = "saltuser";
    private static final String PASSWORD = "saltpass";


    private Logger log = LoggerFactory.getLogger(SaltController.class);

    @Inject
    private AkkaService akkaService;

    @Override
    public Greeting greetingSalt() {

        try {
            // Init the client
            SaltClient client = new SaltClient(URI.create(SALT_API_URL));

            client.login(USER, PASSWORD, AuthModule.PAM);

            // Ping all minions using a glob matcher
            Target<String> globTarget = new Glob("*");
            Map<String, Boolean> results = Test.ping().callSync(
                    client, globTarget);

            System.out.println("--> Ping results:\n");
            results.forEach((minion, result) -> System.out.println(minion + " -> " + result));


            LocalCall<Object> DEBUG =
                    new LocalCall<>("state.apply", Optional.of(Arrays.asList("debug.all")), Optional.empty(),
                            new TypeToken<Object>() {
                            });

            LocalAsyncResult<Object> debugAsyncResult = DEBUG.callAsync(client, globTarget);

            log.info("Debug result: {}", debugAsyncResult);

            String jid = debugAsyncResult.getJid();


            ResultInfoSet jobResult = client.getJobResult(jid);
            //Object jobResult = Jobs.lookupJid(debugAsyncResult).callSync(client, USER, PASSWORD, AuthModule.PAM);


            log.info("Debug result: {}", jobResult);


            /*
            // Get the grains from a list of minions
            Target<List<String>> minionList = new MinionList("minion1", "minion2");
            Map<String, Map<String, Object>> grainResults = Grains.items(false).callSync(
                    client, minionList, USER, PASSWORD, AuthModule.AUTO);

            grainResults.forEach((minion, grains) -> {
                System.out.println("\n--> Listing grains for '" + minion + "':\n");
                grains.forEach((key, value) -> System.out.println(key + ": " + value));
            });

            // Call a wheel function: list accepted and pending minion keys
            WheelResult<Key.Names> keyResults = Key.listAll().callSync(
                    client, USER, PASSWORD, AuthModule.AUTO);
            Key.Names keys = keyResults.getData().getResult();

            System.out.println("\n--> Accepted minion keys:\n");
            keys.getMinions().forEach(minion -> System.out.println(minion));
            System.out.println("\n--> Pending minion keys:\n");
            keys.getUnacceptedMinions().forEach(minion -> System.out.println(minion));*/
        } catch (Exception e) {
            log.error("Failed", e);
        }


        return new Greeting(1, "Hello Salt");
    }


}