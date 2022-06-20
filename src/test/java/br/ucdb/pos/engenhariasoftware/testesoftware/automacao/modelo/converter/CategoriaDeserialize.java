package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.converter;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.Categoria;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.util.StringUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CategoriaDeserialize extends JsonDeserializer<Categoria> {
    @Override
    public Categoria deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String text = StringUtil.removeAccents(jsonParser.getText()).toUpperCase()
                .replaceAll("\\u00a0", "")
                .replaceAll("\\s*", "")
                .replace("&","_");

        return Categoria.valueOf(text);
    }
}
