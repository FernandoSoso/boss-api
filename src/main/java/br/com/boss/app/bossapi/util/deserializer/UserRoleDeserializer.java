package br.com.boss.app.bossapi.util.deserializer;

import br.com.boss.app.bossapi.enums.UserRole;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class UserRoleDeserializer extends JsonDeserializer<UserRole> {

    @Override
    public UserRole deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getText().toUpperCase();

        try {
            return UserRole.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidFormatException(p, "Permissão inválida. Valores permitidos: ADMIN, USER", value, UserRole.class);
        }
    }
}
