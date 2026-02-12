package com.tk.coap;

import com.tk.coap.controller.ReadingsController;
import jakarta.annotation.PostConstruct;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.config.CoapConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoAPServerConfig {
    private static final Logger logger = LoggerFactory.getLogger(CoAPServerConfig.class);

    private static final Integer coapPort = 5683;

    static {
        CoapConfig.register();
    }

    @PostConstruct
    public void start() {

        CoapConfig.register();

        org.eclipse.californium.elements.config.Configuration config = org.eclipse.californium.elements.config.Configuration.getStandard();

        config.set(CoapConfig.MAX_MESSAGE_SIZE, 2048);
        config.set(CoapConfig.PREFERRED_BLOCK_SIZE, 512);
        config.set(CoapConfig.COAP_PORT, coapPort);

        CoapServer server = new CoapServer(config); // default CoAP port

        server.add(new ReadingsController());
        server.start();
        logger.info("CoAP server started on port: {}" , coapPort);
    }
}