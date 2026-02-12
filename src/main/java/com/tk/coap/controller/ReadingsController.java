package com.tk.coap.controller;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.coap.CoAP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


public class ReadingsController extends CoapResource {
    private static final Logger logger = LoggerFactory.getLogger(ReadingsController.class);

    public ReadingsController() {
        super("readings");   // coap://gateway/readings
        getAttributes().setTitle("Sensor Readings");
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        exchange.accept();

        byte[] payload = exchange.getRequestPayload();

        logger.info("Payload size: {}",
                exchange.getRequestPayload() == null ? "null" : exchange.getRequestPayload().length);
        logger.info("Content format: {}", exchange.getRequestOptions() == null ? "null" :exchange.getRequestOptions().getContentFormat());

        if (payload == null || payload.length == 0) {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            return;
        }

        String readings = new String(payload);

        logger.info("Received: {}" , readings);

        exchange.respond(CoAP.ResponseCode.CONTENT, "{\"status\":\"ok\"}");
    }

}
