package com.example.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FilePollingRoute extends RouteBuilder {

    @Value("${rest.host}")
    String host;

    @Value("${rest.port}")
    String port;

    @Value("${input.path}")
    String inputPath;


    /**
     * 
     * @throws Exception
     */
    @Override
    public void configure() throws Exception {
        final String path = "file:" + inputPath
                        + "?move=backup/${date:now:yyyyMMddHMs}/${file:name}";

        from(path)
          // .process(new toMultipart())
          .setHeader(Exchange.HTTP_METHOD, constant("POST"))
          .setHeader(Exchange.CONTENT_TYPE, constant("multipart/form-data"))
          .setHeader("FILE-NAME", simple("${file:name}"))
          .to("http://" + host + ":" + port + "/file");

    }
}
