package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.modelo.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

public class MoneyDeserialize extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser,
                                  DeserializationContext deserializationContext) throws IOException {

        String valorString = jsonParser.getText();
        List<String> values = List.of("", "R$");
        if (Objects.isNull(valorString) || values.contains(valorString)) return null;

        valorString = valorString
                .replaceAll("\\u00a0", "")
                .replaceAll("\\s*", "")
                .replaceAll("Â", "")
                .replace(".", "")
                .replace(",", ".")
                .replace("R$", "")
                .trim();
        final BigDecimal valor = new BigDecimal(valorString);
        return valor.setScale(2, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        String val = "98.64";
        System.out.println(new BigDecimal(val));
    }
}
