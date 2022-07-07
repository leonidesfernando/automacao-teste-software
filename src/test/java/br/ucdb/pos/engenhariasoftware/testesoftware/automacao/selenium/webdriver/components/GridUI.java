package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.components;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Objects;

public class GridUI extends GenericUI{

    private WebElement grid;
    private String id;

    public GridUI(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void isLoaded() throws Error {
        Objects.requireNonNull(grid);
        boolean loaded = false;
        try{
            loaded = grid.isDisplayed();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!loaded){
            throw new Error("GridUI is not loaded yet :/");
        }
    }

    public GridUI gridElement(WebElement grid){
        this.grid = grid;
        this.id = grid.getAttribute("id");
        return this;
    }

    public GridUI id(String id){
        this.grid = getWebDriver().findElement(By.id(id));
        this.id = id;
        return this;
    }

    public GridUI xpath(String xpath){
        this.grid = getWebDriver().findElement(By.xpath(xpath));
        this.id = grid.getAttribute("id");
        return this;
    }

    public List<WebElement> getElements(){
        if(!areThereElements()){
            throw new IllegalStateException("No elements present. The data table is empty.");
        }
        return getWebDriver().findElements(By.xpath(".//tbody/tr"));
    }

    public String getCellValueAt(int row, String col){
        return getColumnsAtLine(row).get(getColunIndex(col)).getText();
    }

    public List<WebElement> getButtonsAt(int row, int col){
        return getColumnsAtLine(row).get(col).findElements(By.tagName("a"));
    }

    public List<WebElement> getColumnsAtLine(int row){
        return getElements().get(row).findElements(By.tagName("td"));
    }

    private int getColunIndex(String column){
        List<WebElement> header = getHeader();
        for(int i = 0; i < header.size(); i++){
            String columnHeader = header.get(i).getText().trim();
            if(columnHeader.equalsIgnoreCase(column)){
                return i;
            }
        }
        throw new IllegalArgumentException(String.format("The column '%s' doesn't exists on the grid.", column));
    }

    private List<WebElement> getHeader(){
        return getWebDriver().findElements(By.xpath(".//thead/tr/th"));
    }

    public boolean areThereElements(){
        try{
            getWebDriver().findElement(By.className("ui-empty-table"));
            return false;
        }catch (NoSuchElementException e){
            return true;
        }
    }
}
