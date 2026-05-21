package io.github.picodotdev.blogbitix.lanterna;

import java.util.Arrays;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.BorderLayout;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {

    public static void main(String[] args) throws Exception {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        Panel titlePanel = new Panel();
        titlePanel.addComponent(new Label(" "));
        titlePanel.addComponent(new Label(" Arch Linux Installation").addStyle(SGR.BOLD));

        Panel buttonsPanel = new Panel(new GridLayout(2).setHorizontalSpacing(1));
        buttonsPanel.addComponent(new Button("Continue", () -> System.exit(0)));
        buttonsPanel.addComponent(new Button("Exit", () -> System.exit(0)));
        buttonsPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER, false, false));

        Panel contentPanel = new Panel(new GridLayout(1).setLeftMarginSize(1).setRightMarginSize(1));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        contentPanel.addComponent(new Label("Welcome to Arch Linux Install Script.\n\nThis automated, unnatended and configurable script\nwill install Arch Linux on your system."));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        contentPanel.addComponent(new Label("Warning!\n\nThis script deletes all partitions of the persistent\nstorage and continuing all your data in it will be lost.").setForegroundColor(TextColor.ANSI.RED));
        contentPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
        buttonsPanel.setLayoutData(GridLayout.createLayoutData(GridLayout.Alignment.END, GridLayout.Alignment.CENTER,false,false)).addTo(contentPanel);

        Panel mainPanel = new Panel(new BorderLayout());
        mainPanel.addComponent(titlePanel, BorderLayout.Location.TOP);

        BasicWindow mainWindow = new BasicWindow();
        mainWindow.setHints(Arrays.asList(Window.Hint.FULL_SCREEN, Window.Hint.NO_DECORATIONS));
        mainWindow.setTheme(new SimpleTheme(TextColor.ANSI.WHITE, TextColor.ANSI.BLUE));
        mainWindow.setComponent(mainPanel);

        BasicWindow window = new BasicWindow("Arch Linux Installation");
        window.setHints(Arrays.asList(Window.Hint.CENTERED));
        window.setComponent(contentPanel);

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindow(mainWindow);
        gui.addWindow(window);
        gui.waitForWindowToClose(mainWindow);
        gui.setActiveWindow(window);
    }
}
