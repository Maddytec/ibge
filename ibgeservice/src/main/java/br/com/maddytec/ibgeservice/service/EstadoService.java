package br.com.maddytec.ibgeservice.service;

import br.com.maddytec.ibgeservice.gateway.json.EstadoList;
import br.com.maddytec.ibgeservice.gateway.json.EstadoRequestTopicJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class EstadoService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    @Value("${kafka.topic.request.estado}")
    private String requestTopic;

    @Value("${kafka.topic.request.reply.estado}")
    private String requestReplayTopic;

    @Cacheable(value = "estado-principal")
    public EstadoList execute() throws JsonProcessingException, ExecutionException, InterruptedException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(EstadoRequestTopicJson.builder().build());

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(requestTopic, jsonString);
        producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplayTopic.getBytes()));

        RequestReplyFuture<String, String, String> requestReplyFuture = kafkaTemplate.sendAndReceive(producerRecord);

        SendResult<String, String> sendResult = requestReplyFuture.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ":" +header.value().toString()));

        ConsumerRecord<String, String> consumerRecord = requestReplyFuture.get();

        EstadoList estadoList = objectMapper.readValue(consumerRecord.value(), EstadoList.class);

        return estadoList;
    }

}
