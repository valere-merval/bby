package de.congstar.selenium;

import lombok.experimental.UtilityClass;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.List;

@UtilityClass
public class ExpectedConditions {

    public static ExpectedCondition<Boolean> attributeToBe(WebElement elem, String attribute, String value) {
        return org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe(elem, attribute, value);
    }

    public static  ExpectedCondition<WebElement> hasStoppedMoving(SearchContext context, By selector) {
        return new ExpectedCondition<>() {
            private int positionEqualityCounter = 0;
            private Point lastPosition = null;

            @Override
            public WebElement apply(WebDriver ignore) {
                var animatedElement = isVisible(context, selector);
                if (animatedElement == null) {
                    return null;
                }
                Point currentPosition = animatedElement.getLocation();
                positionEqualityCounter = currentPosition.equals(lastPosition) ? positionEqualityCounter + 1 : 0;
                if (positionEqualityCounter >= 2) {
                    return animatedElement;
                }
                lastPosition = currentPosition;
                return null;
            }
        };
    }

    public static  List<WebElement> areVisible(SearchContext context, By selector) {
        var elems = context.findElements(selector);
        return !elems.isEmpty() && elems.stream().allMatch(WebElement::isDisplayed) ? elems : null;
    }

    public static  List<WebElement> arePresent(SearchContext context, By selector) {
        var elems = context.findElements(selector);
        return !elems.isEmpty() ? elems : null;
    }

    public static  WebElement isPresent(SearchContext context, By selector) {
        var elems = context.findElements(selector);
        return !elems.isEmpty() ? elems.get(0) : null;
    }

    public static  WebElement isVisible(SearchContext context, By selector) {
        var elem = context.findElement(selector);
        return elem.isDisplayed() ? elem : null;
    }

    public static  WebElement isEnabled(SearchContext context, By selector) {
        var elem = context.findElement(selector);
        return elem.isEnabled() ? elem : null;
    }

    public static  WebElement isClickable(SearchContext context, By selector) {
        var elem = context.findElement(selector);
        return elem.isDisplayed() && elem.isEnabled() ? elem : null;
    }
}
