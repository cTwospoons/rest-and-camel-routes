package com.twospoons.camel.rest.controllers

import org.apache.camel.Produce
import org.apache.camel.ProducerTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController {

    @Produce(uri = 'direct:hello')
    ProducerTemplate producerHello

    @Produce(uri = 'direct:bye')
    ProducerTemplate producerBye

    @RequestMapping('/say/hello')
    String sayHello(){
        producerHello.sendBody('')
    }

    @RequestMapping('/say/bye')
    String sayBye(){
        producerBye.sendBody('')
    }
}
