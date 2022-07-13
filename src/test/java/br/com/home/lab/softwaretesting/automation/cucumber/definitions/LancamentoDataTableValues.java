package br.com.home.lab.softwaretesting.automation.cucumber.definitions;

import br.com.home.lab.softwaretesting.automation.modelo.converter.MoneyToStringConverter;
import br.com.home.lab.softwaretesting.automation.util.DataGen;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@AllArgsConstructor
public enum LancamentoDataTableValues {

    ANY_DATE("@AnyDate"){
        @Override
        public String value() {
            return DataGen.strDate();
        }
    },
    DATE_CURRENT_MONTH("@DateCurrentMonth"){
        @Override
        public String value() {
            return DataGen.strDateCurrentMonth();
        }
    },
    MONEY_VALUE("@MoneyValue"){
        @Override
        public String value() {
            MoneyToStringConverter converter = new MoneyToStringConverter();
            return converter.convert(BigDecimal.valueOf(DataGen.moneyValue())
                    .setScale(2, RoundingMode.HALF_UP));
        }
    };

    public static LancamentoDataTableValues from(String value){

        var values = LancamentoDataTableValues.values();
        for(var val : values){
            if(val.dataType.equals(value)){
                return val;
            }
        }
        throw new IllegalArgumentException();
    }

    abstract public String value();

    private String dataType;
}
