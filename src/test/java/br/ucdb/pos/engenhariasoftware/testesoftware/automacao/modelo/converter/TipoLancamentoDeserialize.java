package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.converter;

import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.TipoLancamento;
import br.ucdb.pos.engenhariasoftware.testesoftware.automacao.util.StringUtil;
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
