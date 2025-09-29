package com.scaler.atm.hardware;

import java.util.Scanner;

public class ScreenAndKeypad implements InputDevice {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readInput() {
        return scanner.nextLine();
    }
}
