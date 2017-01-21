package com.example.processor;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class toMultipart implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        // Read the incoming message…
        final File file = exchange.getIn().getBody(File.class);
        final String name = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);

        final MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.addBinaryBody("file", file);
        multipartEntityBuilder.addTextBody("name", name);

        exchange.getOut().setBody(multipartEntityBuilder.build().getContent());
    }
}
