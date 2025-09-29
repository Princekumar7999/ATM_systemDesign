package com.scaler.atm.hardware;

public class SimpleReceiptPrinter implements ReceiptPrinter {
    @Override
    public void print(String content) {
        System.out.println("[Receipt]\n" + content + "\n------");
    }
}
