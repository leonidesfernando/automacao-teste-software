package br.com.home.lab.softwaretesting.automation.selenium.webdriver.model;

import br.com.home.lab.softwaretesting.automation.modelo.TipoLancamento;
import lombok.NonNull;

public record Entry(@NonNull String description, @NonNull String entryDate, @NonNull TipoLancamento type) {
}
