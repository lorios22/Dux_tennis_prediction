package duxtennis.controllers;

import duxtennis.Main;
import duxtennis.models.Views;
import duxtennis.views.MenuView;

//class that controls the menu
public class MenuController extends MainController {

//Constructor
/*
Create the main menu view controller.
-parameter: mainMenuView (View to control).
*/
public MenuController(MenuView mainMenuView) {
  super(mainMenuView);
  }

//Public methods
/*
Makes the controlled view visible.
*/
@Override
  public void showView() {
    getView().setVisible(true);
  }

/*
The event handler for the 'Start' button, 
which hides the controlled view and displays the view 
for inputting names.
*/
public void startButtonEvent() {
  hideView();

  Main.getController(Views.INPUT)
      .showView();
  }

//Protected methods
/*
Resets the controlled view to its default values.
*/
@Override
protected void resetView(){
  }
}