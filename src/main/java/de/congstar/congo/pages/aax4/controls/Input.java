package de.congstar.congo.pages.aax4.controls;

import de.congstar.congo.pages.aax4.BasePageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static de.congstar.selenium.ExpectedConditions.*;


public abstract class Input extends BasePageComponent {
    By widgetSelector;
    By inputSelector;
    By alertSelector;

    public Input(WebDriver driver, SearchContext context, String inputName) {
        super(driver, context);
        widgetSelector = By.cssSelector(".v-input:has(input[name=%s])".formatted(inputName));
        inputSelector = By.cssSelector(".v-input:has(input[name=%1$s]) input[name=%1$s]".formatted(inputName));
        alertSelector = By.cssSelector(".v-input:has(input[name=%s]) [role=alert]".formatted(inputName));
        getWidget();
    }

    WebElement getInput() {
        return wait.until(ignore -> isPresent(context, inputSelector));
    }

    WebElement getWidget() {
        return wait.until(ignore -> isVisible(context, widgetSelector));
    }

    WebElement getAlert() {
        return wait.until(ignore -> isPresent(context, alertSelector));
    }


}
