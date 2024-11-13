package br.com.boss.app.bossapi.util.deserializer;

import br.com.boss.app.bossapi.enums.DeliveryStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class DeliveryStatusDeserializer extends JsonDeserializer<DeliveryStatus> {
    @Override
    public DeliveryStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText().toUpperCase();
        try {
            return DeliveryStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new JsonProcessingException("Estado inválido da entrega: " + value) {};
        }
    }
}