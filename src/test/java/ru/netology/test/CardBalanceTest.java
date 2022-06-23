package ru.netology.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.netology.data.UserInfo;
import ru.netology.page.CardBalancePage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Math.abs;
import static org.testng.AssertJUnit.assertEquals;

public class CardBalanceTest {
    UserInfo user = new UserInfo();
    CardBalancePage dashboard;

    @BeforeMethod
    public void setup() {
        open("http://localhost:9999/");
        var loginPage = new LoginPage();
        var verifyPage = loginPage.login(user);
        dashboard = verifyPage.verify(user);
    }

    @Test
    public void transferAbsolutValue() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = -1;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.successTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        dashboard = revertTransferPage.successTransfer(amount, user.getCard(indexCardTo));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notTransferNullAmount() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = 0;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.failedTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void transferBoundaryValueOne() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = 1;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.successTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        dashboard = revertTransferPage.successTransfer(amount, user.getCard(indexCardTo));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void transferBoundaryValueTwo() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom - 1;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.successTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        dashboard = revertTransferPage.successTransfer(amount, user.getCard(indexCardTo));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void transferBoundaryValueThree() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.successTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        dashboard = revertTransferPage.successTransfer(amount, user.getCard(indexCardTo));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notTransferBoundaryValueFour() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom + 1;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.successTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo + abs(amount), dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom - abs(amount), dashboard.getBalance(indexCardFrom));

        var revertTransferPage = dashboard.transferClick(indexCardFrom);
        dashboard = revertTransferPage.failedTransfer(amount, user.getCard(indexCardTo));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notTransferSingleCard() {
        int indexCardTo = 0;
        int indexCardFrom = 0;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom / 2;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.failedTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void cancelTransfer() {
        int indexCardTo = 0;
        int indexCardFrom = 1;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom / 2;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.cancelTransfer(amount, user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notTransferEmptyAmount() {
        int indexCardTo = 0;
        int indexCardFrom = 0;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.failedTransferWitEmptyAmount(user.getCard(indexCardFrom));
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }

    @Test
    public void notTransferEmptyCardFrom() {
        int indexCardTo = 0;
        int indexCardFrom = 0;
        int cardBalanceTo = dashboard.getBalance(indexCardTo);
        int cardBalanceFrom = dashboard.getBalance(indexCardFrom);
        int amount = cardBalanceFrom / 2;

        var transferPage = dashboard.transferClick(indexCardTo);
        dashboard = transferPage.failedTransferWithEmptyCardFrom(amount);
        dashboard.reloadBalance();
        assertEquals(cardBalanceTo, dashboard.getBalance(indexCardTo));
        assertEquals(cardBalanceFrom, dashboard.getBalance(indexCardFrom));
    }
}
