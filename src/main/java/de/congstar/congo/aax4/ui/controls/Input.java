package de.congstar.congo.aax4.ui.controls;

import de.congstar.congo.aax4.ui.BasePageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static de.congstar.selenium.ExpectedConditions.*;


public class Input extends BasePageComponent {
    By widgetSelector;
    By inputSelector;

    public Input(WebDriver driver, SearchContext context, String inputName) {
        super(driver, context);
        widgetSelector = By.cssSelector(".v-input:has(input[name=%s])".formatted(inputName));
        inputSelector = By.cssSelector(".v-input:has(input[name=%s]) input[name=%s]".formatted(inputName, inputName));
        getWidget();
    }

    protected WebElement getInput() {
        return wait.until(ignore -> isPresent(context, inputSelector));
    }

    protected WebElement getWidget() {
        return wait.until(ignore -> isVisible(context, widgetSelector));
    }

    public void fill(String text) {
        var input = getInput();
        input.sendKeys(text);
        wait.until(attributeToBe(input, "value", text));
    }

    public void check() {
        var input = getInput();
        if (!input.isSelected()) {
            input.click();
        }
    }

}
