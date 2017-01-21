package com.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class RestRoute extends RouteBuilder {

    @Value("${rest.host}")
    String host;

    @Value("${rest.port}")
    String port;

    @Value("${output.path}")
    String outputPath;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty").host(host).port(port);

        rest("/file").description("Image Upload Service")
                     .consumes("multipart/form-data")
                     .post()
                     .description("Uploads image")
                     .to("file:" +  outputPath + "?fileName=${header.FILE-NAME}");

    }
}
