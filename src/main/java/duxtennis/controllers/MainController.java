package duxtennis.controllers;

import duxtennis.views.MainView;
import java.awt.Toolkit;

/*
class that outlines the essential methods
for linking controllers and their corresponding views
*/
public abstract class MainController {

//Private fields
private MainView controlledView;

// Constructor
/*
Creates the view controller.
-parameter: controlledView (View to control).
*/
protected MainController(MainView controlledView) {
  setView(controlledView);
  }

//Public methods 
/*
Gets the controlled view.
--return: The controlled view.
*/
public MainView getView() {
  return controlledView;
  }

//Protected methods
/*
Updates the controlled view object.
-parameters: controlledView (The new controlled view).
*/
protected void setView(MainView controlledView) {
  this.controlledView = controlledView;
  }

/*
Visible controlled view.
*/
protected void showView() {
  controlledView.setVisible(true);
  }

/*
Invisible controlled view.
*/
protected void hideView() {
  controlledView.setVisible(false);

  centerView();
  }

//Private methods
/*
Centers the controlled view on the screen.
*/

private void centerView() {
    controlledView.setLocation((Toolkit.getDefaultToolkit()
                                       .getScreenSize()
                                       .width - controlledView.getSize()
                                                              .width) / 2,
                               (Toolkit.getDefaultToolkit()
                                       .getScreenSize()
                                       .height - controlledView.getSize()
                                                               .height) / 2);
    controlledView.setLocationRelativeTo(null);
  }

// Abstract protected methods 
/*
Makes the controlled view invisible and resets it to its default values.
*/
protected abstract void resetView();
}

