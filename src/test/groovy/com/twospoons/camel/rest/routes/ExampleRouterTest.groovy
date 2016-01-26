package com.twospoons.camel.rest.routes

import com.twospoons.camel.rest.Application
import groovy.util.logging.Slf4j
import org.apache.camel.CamelContext
import org.apache.camel.EndpointInject
import org.apache.camel.Produce
import org.apache.camel.ProducerTemplate
import org.apache.camel.builder.AdviceWithRouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.apache.camel.model.ModelCamelContext
import org.apache.camel.model.RouteDefinition
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@Slf4j
@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes = Application)
@WebIntegrationTest
class ExampleRouterTest{

    @Autowired
    CamelContext camelContext

    @EndpointInject(uri = 'mock:direct:end')
    MockEndpoint mockEndpoint

    @Produce(uri = 'direct:hello')
    ProducerTemplate producerHello

    @Produce(uri = 'direct:bye')
    ProducerTemplate producerBye

    @Before
    public void setup(){
        log.debug 'RouteDefinitions: {}', camelContext.getRouteDefinitions()

        camelContext.getRouteDefinitions().toArray().each{
            (it as RouteDefinition).adviceWith(camelContext as ModelCamelContext, new AdviceWithRouteBuilder() {
                @Override
                public void configure() throws Exception {
                    // mock sending to direct:foo and direct:bar and skip send to it
                    mockEndpointsAndSkip("direct:end")
                }
            })
        }
    }

    @DirtiesContext
    @Test
    public void testSendExchangeHelloRoute() throws Exception{
        mockEndpoint.expectedCount = 1
        producerHello.sendBody('test Hello')
        mockEndpoint.assertIsSatisfied()
    }

    @DirtiesContext
    @Test
    public void testSendExchangeByeRoute() throws Exception {
        mockEndpoint.expectedCount = 1
        producerBye.sendBody('test Goodbye')
        mockEndpoint.assertIsSatisfied()
    }



}
