package br.com.maddytec.ibgewrapper.gatway.listener;

import br.com.maddytec.ibgewrapper.gatway.json.EstadoJson;
import br.com.maddytec.ibgewrapper.gatway.json.EstadoList;
import br.com.maddytec.ibgewrapper.service.EstadoService;
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
public class EstadoListener {

    @Autowired
    private EstadoService estadoService;

    @KafkaListener(topics = "${kafka.topic.request.estado}")
    public Message<String> listarEstados(@Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                                         @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        List<EstadoJson> listEstado = estadoService.execute();
        String jsonReturn = objectMapper.writeValueAsString(EstadoList.builder().list(listEstado).build());

        return MessageBuilder.withPayload(jsonReturn)
                .setHeader(KafkaHeaders.TOPIC, replyTo)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlation)
                .build();
    }
}
