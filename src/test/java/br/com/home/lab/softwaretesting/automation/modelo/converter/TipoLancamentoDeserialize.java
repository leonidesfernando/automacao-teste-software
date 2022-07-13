package br.com.home.lab.softwaretesting.automation.modelo.converter;

import br.com.home.lab.softwaretesting.automation.util.StringUtil;
import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class TipoLancamentoDeserialize extends JsonDeserializer<TipoLancamento> {
    @Override
    public TipoLancamento deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {

        return TipoLancamento.valueOf(
                StringUtil.removeAccents(jsonParser.getText()).toUpperCase());
    }
}
