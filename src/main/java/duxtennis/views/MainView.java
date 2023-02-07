package duxtennis.views;

import javax.swing.JFrame;

/*
Class that outlines the fundamental view methods, functioning as an abstract class.
*/
public abstract class MainView extends JFrame {

//Abstract protected methods
/*
Initiates the view and serves as an abstract class that outlines the fundamental view methods.
*/
protected abstract void initializeInterface();

/*
Places the buttons onto the appropriate panel.
*/
protected abstract void addButtons();
}