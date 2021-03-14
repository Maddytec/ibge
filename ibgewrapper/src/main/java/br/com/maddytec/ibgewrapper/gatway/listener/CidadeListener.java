package br.com.maddytec.ibgewrapper.gatway.listener;

import br.com.maddytec.ibgewrapper.gatway.json.*;
import br.com.maddytec.ibgewrapper.service.CidadeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeListener {

    @Autowired
    private CidadeService cidadeService;

    @KafkaListener(topics = "${kafka.topic.request.cidade}")
    public Message<String> listarCidades(String ufJson, @Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                                         @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        EstadoRequestTopicJson estadoRequestTopicJson = objectMapper.readValue(ufJson, EstadoRequestTopicJson.class);

        List<CidadeJson> listaCidade = cidadeService.execute(estadoRequestTopicJson.getUf());
        String jsonReturn = objectMapper.writeValueAsString(CidadeList.builder().list(listaCidade).build());

        return MessageBuilder.withPayload(jsonReturn)
                .setHeader(KafkaHeaders.TOPIC, replyTo)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlation)
                .build();
    }
}
