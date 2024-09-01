package de.congstar.congo.aax4.ui;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

public class BasePageComponent extends BasePageObject {
    protected SearchContext context;

    public BasePageComponent(WebDriver driver, SearchContext context) {
        super(driver);
        this.context = context;
    }
}
