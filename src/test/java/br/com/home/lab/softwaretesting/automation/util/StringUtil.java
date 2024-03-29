package br.com.home.lab.softwaretesting.automation.util;

import lombok.experimental.UtilityClass;

import java.text.Normalizer;

@UtilityClass
public final class StringUtil {

    public String removeAccents(String text){
        CharSequence cs = new StringBuilder(text);
        return Normalizer.normalize(cs, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
