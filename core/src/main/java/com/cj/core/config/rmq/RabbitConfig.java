package com.cj.core.config.rmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    //=========================================广播模式、轮询消费==================

//    // 创建一个立即消费队列
//    @Bean
//    public Queue qUrl1() {
//        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
////        return new Queue("qUrl.1",false,false,true);
//
//        //AnonymousQueue这个类是代表一个匿名的、不耐用的、独占的、自动删除的队列
//        return new AnonymousQueue();
//    }
//
//    /**
//     * Fanout 就是我们熟悉的广播模式或者订阅模式，给Fanout交换机发送消息，绑定了这个交换机的所有队列都收到这个消息。
//     *
//     * @return
//     */
//    @Bean
//    FanoutExchange exUrl() {
//        return new FanoutExchange("ExUrl");
//    }
//
//
//    @Bean
//    Binding bindingExchangeA() {
//        return BindingBuilder.bind(qUrl1()).to(exUrl());
//    }

    //========================================Direct模式=====================================

    @Bean
    public Queue queueLog() {
        return new Queue("queueLog2");
    }

    @Bean
    public DirectExchange exLog() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange("ExLog", false, true);
    }

    //通过routingKey和exchange决定的那个唯一的queue可以接收消息
    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(queueLog()).to(exLog()).with("key-log");
    }

    //================================================Topic模式==========================


    //  a  创建queue
    public Queue power() {
        return new Queue("topic.power");  //  topic.message  是rounting-key,匹配规则
    }

//    @Bean(name = "notes")
//    public Queue queueMessages(){
//        return new Queue("topic.notes");
//    }

    //  b. 创建交换机TopicExchange
    @Bean
    public TopicExchange ExPower() {
        return new TopicExchange("ExPower");
    }

    /*
      c. 根据绑定规则将队列绑定到相应的交换机上（bindingKey）--Exchange Queues

     */
    @Bean
    public Binding bindingExchangeC() {
        return BindingBuilder.bind(power()).to(ExPower()).with("key-power");
    }

//    @Bean
//    /**
//     * 将队列"messages" 绑定到交换机上，绑定规则是 topic.messages
//     *
//     */
//    public  Binding bindingExchangeMessages(@Qualifier("notes")Queue queueMessages,TopicExchange exchange){
//        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
//    }


    //==============================================配置==============================

    @Bean
    public RabbitAdmin rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new ContentTypeDelegatingMessageConverter(new Jackson2JsonMessageConverter());
    }

}

