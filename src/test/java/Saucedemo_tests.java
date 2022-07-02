import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Saucedemo_tests {

    // tu powinny byc before each???

    // implicit wait - dodać do before all



    static void login() {
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));
        loginInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        submitButton.click();
    }

    // [tylko do użytku Testera]
    // login: "standard_user"
    // hasło: "secret_sauce"

    static WebDriver driver;

    @BeforeEach
    public void prepareBrowser() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
    }

    @AfterEach
    public void closeBrowser() {
        driver.close();
        driver.quit();

    }

//    Zad 1.
//    Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano loginu.

    @Test
    //@Disabled
    void shouldVerifyLogin1() {
        //przywołanie elementów strony z których zamierzam korzystać
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        //wprowadzenie danych logowania tj. hasło bez loginu
        loginInput.sendKeys("");
        passwordInput.sendKeys("secret_sauce");

        //kliknięcie przycisku "LOGIN"
        submitButton.click();

        //przywołanie elementu na stronie zalogowanego użytkownika i assercja przejścia do tej strony
        WebElement authenticationUserLogIn = driver.findElement(By.className("error-message-container"));

        // to jest nie potrzebne - czasem błędne Assertions.assertTrue(driver.findElement(By.cssSelector(".error-message-container")).isDisplayed());
        Assertions.assertTrue(driver.findElement(By.cssSelector(".error-message-container")).isDisplayed());
        Assertions.assertEquals("Epic sadface: Username is required", authenticationUserLogIn.getText());
        System.out.println(authenticationUserLogIn.getText());

    }

//    Zad 2.
//    Napisz test, który zweryfikuje działanie aplikacji, gdy przy próbie logowania nie podano hasła.

    @Test
    //@Disabled
    void shouldVerifyLogin2() {
        //przywołanie elementów strony z których zamierzam korzystać
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        //wprowadzenie danych logowania tj. login bez hasła
        loginInput.sendKeys("standard_user");
        passwordInput.sendKeys("");

        //kliknięcie przycisku "LOGIN"
        submitButton.click();

        //Dwie Assercje na wszelki wypadek czy jest okienko alertu i czy wyświetla odpowiedni komunikat
        WebElement authenticationUserLogIn = driver.findElement(By.className("error-message-container"));
        Assertions.assertTrue(driver.findElement(By.cssSelector(".error-message-container")).isDisplayed());
        Assertions.assertEquals("Epic sadface: Password is required", authenticationUserLogIn.getText());
        System.out.println(authenticationUserLogIn.getText());

    }

//    Zad 3.
//    Sprawdź, czy strona główna oraz strona logowania zawiera logo i pole wyszukiwania

    @Test
    //@Disabled
    void shouldVerifyLogoAndTitle() {
        //przywołanie elementów strony z których zamierzam korzystać
        String actualTitle = driver.getTitle();
        String ExpectedTitle = "Swag Labs";
        WebElement logoVisable = driver.findElement(By.className("login_logo"));

        //Assercje obecności logo, oraz tytułu strony na stronie logowania
        Assertions.assertTrue(logoVisable.isDisplayed());
        Assertions.assertEquals(ExpectedTitle, actualTitle);

        //metoda zalogowania się
        login();

        //Assercje obecności logo, oraz tytułu strony na stronie użytkownika zalogowanego
        String actualTitleLoggedIn = driver.getTitle();
        String ExpectedTitleLoggedIn = "Swag Labs";
        WebElement logoVisableLoggedIn = driver.findElement(By.className("app_logo"));
        Assertions.assertTrue(logoVisableLoggedIn.isDisplayed());
        Assertions.assertEquals(ExpectedTitleLoggedIn, actualTitleLoggedIn);

    }


//    Zad 4.
//    Napisz test sprawdzający przejście ze strony głównej do strony Kontakt
//    Zmiana z powodu korzystania ze strony "https://www.saucedemo.com/" na przejście z strony użytkownika zalogowanego
//    na stronę "about"

    @Test
    //@Disabled
    void shouldGoToAboutFromLoggedInUserPage() throws InterruptedException {
        //przejście do strony umożliwiającej wykonaniue testu - zalogowanie użytkownika
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        // przywołanie metody
        login();
//        loginInput.sendKeys("standard_user");
//        passwordInput.sendKeys("secret_sauce");
//        submitButton.click();

        //wykonanie kroków mających na celu przejście do oczekiwanej strony
        WebElement sideMenu = driver.findElement(By.id("react-burger-menu-btn"));
        sideMenu.click();
        Thread.sleep(2000);
        WebElement aboutSideMenu = driver.findElement(By.id("about_sidebar_link"));
        aboutSideMenu.click();

        //Assercje
        Assertions.assertEquals("https://saucelabs.com/", driver.getCurrentUrl());
        Assertions.assertNotEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());

    }


//    Zad 5.
//    Napisz test sprawdzający przejście ze strony logowania do strony głównej
//    Zmiana z powodu korzystania ze strony "https://www.saucedemo.com/" na przejście z strony użytkownika zalogowanego
//    na stronę główną

    @Test
    //@Disabled
    void shouldGoToMainPageFromLoggedInUserPage() throws InterruptedException {
        //przejście do strony umożliwiającej wykonaniue testu - zalogowanie użytkownika
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        loginInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        submitButton.click();

        //wykonanie kroków mających na celu przejście do oczekiwanej strony
        WebElement sideMenu = driver.findElement(By.id("react-burger-menu-btn"));
        sideMenu.click();

        //zastosowanie metody imlicitlyWait wzamian za Thread.sleep
        //Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        WebElement logOutSideMenu = driver.findElement(By.id("logout_sidebar_link"));
        logOutSideMenu.click();

        //Assercje
        Assertions.assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
        Assertions.assertNotEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());

    }


//    Zad 6.
//    Napisz test, który dodaje produkt do koszyka . Zweryfikuj , czy dodanie powiodło się


    // webdriver wait dodać na początku testu w zadniu 6



    @Test
    //@Disabled
    void shouldAddProductToShoppingCart() throws InterruptedException {
        //przejście do strony umożliwiającej wykonaniue testu - zalogowanie użytkownika
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        loginInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        submitButton.click();

        //wykonanie kroków mających na celu dodanie produktu do koszyka
        WebElement redShirtAddToCart = driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)"));
        redShirtAddToCart.click();

        //zastosowanie metody sugerowanej w zamian za imlicit Wait oraz za Thread.sleep czyli explicit wait.
        //Thread.sleep(2000);
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Wait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".shopping_cart_badge")));

        //zweryfikowanie pojawienia się jedynki przy koszyku shopping_cart_badge =1
        WebElement shoppingCartNumber = driver.findElement(By.cssSelector(".shopping_cart_badge"));
        Assertions.assertEquals("1", shoppingCartNumber.getText());


        //przejście do koszyka
        WebElement shoppingCart = driver.findElement(By.cssSelector(".shopping_cart_link"));
        shoppingCart.click();

        //weryfikacja obecności wybranego produktu w koszyku
//        WebElement shoppingCartProduct = driver.findElement(By.className("inventory_item_name"));
//        Assertions.assertEquals("Test.allTheThings() T-Shirt (Red)", shoppingCartProduct.getText());
        List<WebElement> shoppingCartElements = driver.findElements(By.className("cart_item"));
        Assertions.assertEquals(1, shoppingCartElements.size());

    }


//    Zad 7.
//    Napisz test, który dodaje produkt do koszyka , a następnie usuwa go. Zweryfikuj , czy usunięcie powiodło się

    // web element shoppingcartremove nic nie mówi - poprawić


    @Test
    //@Disabled
    void shouldAddProductToShoppingCartAndThenRemoveIt() throws InterruptedException {
        //przejście do strony umożliwiającej wykonaniue testu - zalogowanie użytkownika
        WebElement loginInput = driver.findElement(By.id("user-name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.id("login-button"));

        loginInput.sendKeys("standard_user");
        passwordInput.sendKeys("secret_sauce");
        submitButton.click();

        //wykonanie kroków mających na celu dodanie produktu do koszyka
        WebElement redShirtAddToCart = driver.findElement(By.id("add-to-cart-test.allthethings()-t-shirt-(red)"));
        redShirtAddToCart.click();
        Thread.sleep(2000);

        //zweryfikowanie pojawienia się jedynki przy koszyku shopping_cart_badge =1
        WebElement shoppingCartNumber = driver.findElement(By.cssSelector(".shopping_cart_badge"));
        Assertions.assertEquals("1", shoppingCartNumber.getText());

        //przejście do koszyka
        WebElement shoppingCart = driver.findElement(By.cssSelector(".shopping_cart_link"));
        shoppingCart.click();

        //weryfikacja obecności wybranego produktu w koszyku
        WebElement shoppingCartProduct = driver.findElement(By.className("inventory_item_name"));
        Assertions.assertEquals("Test.allTheThings() T-Shirt (Red)", shoppingCartProduct.getText());

        //usunięcie produktu z koszyka
        WebElement shoppingCartRemove = driver.findElement(By.id("remove-test.allthethings()-t-shirt-(red)"));
        shoppingCartRemove.click();


        //weryfikacja koszyka
        List<WebElement> shoppingCartElements = driver.findElements(By.className("cart_item"));
        Assertions.assertEquals(0, shoppingCartElements.size());


    }


//    Zad 8.* (zadanie dodatkowe)
//    Zrefaktoruj logowanie. Utwórz metodę pomocniczą login(), która przymuje dwa parametry : login i hasło.
//    Użyj jej w teście sprawdzającym logowanie

    // zastosowano w zadaniach poprzednich

}
