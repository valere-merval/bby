package de.congstar.selenium;

import lombok.SneakyThrows;
import org.openqa.selenium.*;

import java.util.List;

public class ByJs extends By implements By.Remotable {

    private final String js;

    public ByJs(String js) {
        this.js = js.replaceAll("\n", "").trim();
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public List<WebElement> findElements(SearchContext context) {
        WebDriver driver = getWebDriver(context);
        if (!(driver instanceof JavascriptExecutor javascriptExecutor)) {
            throw new IllegalArgumentException("WebDriver is not a Javascript executor");
        }
        String script = "return " + js + ";";
        try {
            return (List<WebElement>) javascriptExecutor.executeScript(script);
        } catch (JavascriptException e) {
            if (e.getMessage().contains("$x is not defined")
                    || e.getMessage().contains("$ is not defined")
                    || e.getMessage().contains("$$ is not defined")) {
                try (var is = getClass().getClassLoader().getResourceAsStream("basic-devtools.js")) {
                    assert is != null;
                    javascriptExecutor.executeScript(new String(is.readAllBytes()));
                    return (List<WebElement>) javascriptExecutor.executeScript(script);
                }
            } else {
                throw e;
            }
        }
    }

    @Override
    public Parameters getRemoteParameters() {
        return new Parameters("js", js);
    }
}
