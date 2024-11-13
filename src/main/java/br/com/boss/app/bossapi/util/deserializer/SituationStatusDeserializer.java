package br.com.boss.app.bossapi.util.deserializer;

import br.com.boss.app.bossapi.enums.SituationStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class SituationStatusDeserializer extends JsonDeserializer<SituationStatus> {
    @Override
    public SituationStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText().toUpperCase();
        try {
            return SituationStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new JsonProcessingException("Valor inválido para a situação: " + value) {};
        }
    }
}