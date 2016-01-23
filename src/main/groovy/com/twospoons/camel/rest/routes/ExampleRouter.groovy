package com.twospoons.camel.rest.routes

import static org.apache.camel.LoggingLevel.INFO
import org.apache.camel.builder.RouteBuilder
import org.springframework.stereotype.Component

@Component
class ExampleRouter extends RouteBuilder{

    @Override
    void configure() throws Exception {
        from("direct:hello")
                .log(INFO, 'Hello Route Fired')
        from("direct:bye")
                .log(INFO, 'Bye Route Fired')
    }
}
