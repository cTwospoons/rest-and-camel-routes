package com.twospoons.camel.rest.controllers

import org.apache.camel.ProducerTemplate
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import spock.lang.Specification


class ExampleControllerSpec extends Specification {

    def helloProducer = Mock(ProducerTemplate)
    def byeProducer = Mock(ProducerTemplate)

    def controller
    def mockMvc

    def setup(){
        controller = new ExampleController(producerHello: helloProducer, producerBye: byeProducer)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def "test Hello Producer called on GET request"(){
        when: 'REST endpoint /say/hello receives a GET request'
            def result = mockMvc.perform(get('/say/hello'))
        then: 'camel hello producer template should send the request to the camel route'
            1 * helloProducer.sendBody(_)
            result.andExpect(status().isOk())
    }

    def "test Bye Producer called on GET request"(){
        when: 'REST endpoint /say/bye receives a GET request'
            def result = mockMvc.perform(get('/say/bye'))
        then: 'camel by producer template should send the request to the camel route'
            1 * byeProducer.sendBody(_)
            result.andExpect(status().isOk())

    }

}