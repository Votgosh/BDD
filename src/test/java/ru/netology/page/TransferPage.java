package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.UserInfo;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static org.testng.Assert.assertEquals;

public class TransferPage {
    private SelenideElement amountInput = $x("//span[@data-test-id='amount']//input");
    private SelenideElement fromInput = $x("//span[@data-test-id='from']//input");
    private SelenideElement toInput = $x("//span[@data-test-id='to']//input");
    private SelenideElement transferButton = $x("//button[@data-test-id='action-transfer']");
    private SelenideElement cancelButton = $x("//button[@data-test-id='action-cancel']");
    private SelenideElement errorNotification = $x("//div[@data-test-id='error-notification']");
    private SelenideElement errorButton = $x("//div[@data-test-id='error-notification']/button");

    public TransferPage() {
        amountInput.should(visible);
        fromInput.should(visible);
        toInput.should(visible);
        transferButton.should(visible);
        cancelButton.should(visible);
        errorNotification.should(hidden);
        errorButton.should(hidden);
    }

    public CardBalancePage successTransfer(int amount, String cardFrom) {
        amountInput.val(String.valueOf(amount));
        fromInput.val(cardFrom);
        transferButton.click();
        errorNotification.should(hidden);
        return new CardBalancePage();
    }

    public CardBalancePage failedTransfer(int amount, String cardFrom) {
        amountInput.val(String.valueOf(amount));
        fromInput.val(cardFrom);
        transferButton.click();
        errorNotification.should(visible);
        errorButton.click();
        errorNotification.should(hidden);
        return new CardBalancePage();
    }

    public CardBalancePage cancelTransfer(int amount, String cardFrom) {
        amountInput.val(String.valueOf(amount));
        fromInput.val(cardFrom);
        cancelButton.click();
        errorNotification.should(hidden);
        return new CardBalancePage();
    }

    public CardBalancePage failedTransferWitEmptyAmount(String cardFrom) {
        fromInput.val(cardFrom);
        transferButton.click();
        errorNotification.should(visible);
        errorButton.click();
        errorNotification.should(hidden);
        cancelButton.click();
        return new CardBalancePage();
    }

    public CardBalancePage failedTransferWithEmptyCardFrom(int amount) {
        amountInput.val(String.valueOf(amount));
        transferButton.click();
        errorNotification.should(visible);
        errorButton.click();
        errorNotification.should(hidden);
        cancelButton.click();
        return new CardBalancePage();
    }
}