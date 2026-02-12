package com.tk.coap.controller;

import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.OptionSet;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadingsControllerTest {

    private ReadingsController controller;

    @Mock
    private CoapExchange exchange;

    @Mock
    private OptionSet optionSet;

    @BeforeEach
    void setup() {
        controller = new ReadingsController();
    }


    @Test
    void handlePOST_withValidPayload_returnsContent() {

        byte[] payload = "{\"temp\":24.5}".getBytes();
        when(exchange.getRequestPayload()).thenReturn(payload);

        when(exchange.getRequestOptions()).thenReturn(optionSet);
        when(optionSet.getContentFormat()).thenReturn(50);

        controller.handlePOST(exchange);

        verify(exchange).accept();

        verify(exchange).respond(
                CoAP.ResponseCode.CONTENT,
                "{\"status\":\"ok\"}"
        );
    }



    @Test
    void handlePOST_withNullPayload_returnsBadRequest() {

        when(exchange.getRequestPayload()).thenReturn(null);

        controller.handlePOST(exchange);

        verify(exchange).accept();
        verify(exchange).respond(CoAP.ResponseCode.BAD_REQUEST);
    }


    @Test
    void handlePOST_withEmptyPayload_returnsBadRequest() {

        when(exchange.getRequestPayload()).thenReturn(new byte[0]);

        controller.handlePOST(exchange);

        verify(exchange).accept();
        verify(exchange).respond(CoAP.ResponseCode.BAD_REQUEST);
    }

    @Test
    void handlePOST_readsContentFormat() {

        when(exchange.getRequestPayload())
                .thenReturn("data".getBytes());

        when(exchange.getRequestOptions()).thenReturn(optionSet);
        when(optionSet.getContentFormat()).thenReturn(50);

        controller.handlePOST(exchange);

        verify(exchange.getRequestOptions()).getContentFormat();
        verify(exchange)
                .respond(CoAP.ResponseCode.CONTENT, "{\"status\":\"ok\"}");
    }


    @Test
    void constructor_setsNameAndTitle() {

        ReadingsController rc = new ReadingsController();

        assertEquals("readings", rc.getName());
        assertEquals("Sensor Readings", rc.getAttributes().getTitle());
    }
}
