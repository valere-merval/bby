package de.congstar.congo.pages.aax4.container;

import de.congstar.congo.pages.aax4.BasePageComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static de.congstar.selenium.ExpectedConditions.isPresent;
import static de.congstar.selenium.ExpectedConditions.isVisible;

public abstract class Card extends BasePageComponent {
    By titleSelector;
    By bodySelector;

    public Card(WebDriver driver, SearchContext context, String id) {
        super(driver, context);
        titleSelector = By.cssSelector("#%s .v-card-item:has(.v-card-title)".formatted(id));
        bodySelector = By.cssSelector("#%s .v-card-item:not(:has(.v-card-title))".formatted(id));

        wait.until(ignore -> isVisible(context, titleSelector));
        wait.until(ignore -> isVisible(context, bodySelector));

        initialize();
    }

    protected WebElement getTitle() {
        return wait.until(ignore -> isPresent(context, titleSelector));
    }

    protected WebElement getBody() {
        return wait.until(ignore -> isPresent(context, bodySelector));
    }

    protected abstract void initialize();
}
