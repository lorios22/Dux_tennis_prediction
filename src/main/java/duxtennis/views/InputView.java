package duxtennis.views;

import duxtennis.Main;
import duxtennis.controllers.InputController;
import duxtennis.models.Views;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InvalidNameException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

/*
Input view class.
*/
public class InputView extends MainView {

//Private constants
private static final String FRAME_TITLE = "Ingreso de datos";
private static final String GROWX = "growx";
private static final String GROWX_SPAN = "growx, span";
private static final String[] OPTIONS_COMBOBOX = {
    "Mejor de 3",
    "Mejor de 5"
  };

//Public constants
public static final int SLIDER_INI = 50;
public static final int SLIDER_MIN = 0;
public static final int SLIDER_MAX = 100;
public static final int SLIDER_SPACING_MAJOR = 25;
public static final int SLIDER_SPACING_MINOR = 5;

//Private fields
private JButton continueButton;
private JComboBox<String> comboBox;
private JPanel panel;
private List<JSlider> sliders;
private List<JTextField> textFields;

//Constructor
/*
Creates the input view.
*/
public InputView() {
    initializeInterface();
  }

//Public methods
//Getters
/*
--return: The 'continue' button.
*/
  public JButton getContinueButton() {
    return continueButton;
  }

/*
--return: The combobox.
*/
public JComboBox<String> getComboBox() {
    return comboBox;
  }

/*
--return: list containing the view's sliders.
*/
public List<JSlider> getSliders() {
    return sliders;
  }

/*
--return: list containing the view's text fields.
*/
public List<JTextField> getTextFields() {
    return textFields;
  }

//Protected methods

/*
Initializes the view and makes it visible.
*/
@Override
protected void initializeInterface() {
    panel = new JPanel(new MigLayout("wrap 2"));

    addTextFields();
    addSliders();
    addComboBox();
    addButtons();
    add(panel);
    setResizable(false);
    setTitle(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setIconImage(Main.ICON
                     .getImage());
    pack();
    setLocationRelativeTo(null);
  }

/*
Adds the buttons to the panel.
*/
  @Override
  protected void addButtons() {
    JButton backButton = new JButton("Atrás");

    continueButton = new JButton("Continuar");
    continueButton.setEnabled(false);

    backButton.addActionListener(e ->
        ((InputController) Main.getController(Views.INPUT)).backButtonEvent()
    );

    continueButton.addActionListener(e ->
        ((InputController) Main.getController(Views.INPUT)).continueButtonEvent()
    );

    panel.add(continueButton, GROWX_SPAN);
    panel.add(backButton, GROWX_SPAN);
  }
  
//Private methods
/*
Generates and inserts the input text fields for parameters into the panel.
*/
private void addTextFields() {
  textFields = new ArrayList<>();

  var wrapperIndex = new Object() {
    private int index;
  };

  for (wrapperIndex.index = 0; wrapperIndex.index < 3; wrapperIndex.index++) {
    JLabel label = new JLabel("Nombre del " + (wrapperIndex.index == 0
                                               ? "torneo"
                                               : "jugador #" + wrapperIndex.index));

    JTextField tf = new JTextField(Main.MAX_NAME_LEN);

    textFields.add(tf);

    tf.addActionListener(e -> {
      try {
        ((InputController) Main.getController(Views.INPUT))
        .textFieldEvent(tf.getText(), textFields.indexOf(tf));
      } catch (IllegalArgumentException stringEx) {
        Main.showErrorMessage("El nombre debe estar formado sólo por letras");

        tf.setText("");

        return;
      } catch (InvalidNameException nameEx) {
        Main.showErrorMessage("El nombre no puede estar vacío, "
                              + "tener más de " + Main.MAX_NAME_LEN
                              + " caracteres, o estar repetido");

        tf.setText("");

        return;
      } finally {
        continueButton.setEnabled(namesSetted());
      }
    });

    panel.add(label);
    panel.add(tf, GROWX);
  }
}
/*
Generates and positions the sliders used to input parameters on the panel.
*/
private void addSliders() {
  List<JLabel> skillLabels = new ArrayList<>();
  sliders = new ArrayList<>();

  for (int i = 0; i < 2; i++) {
    JSlider slider = new JSlider(SwingConstants.HORIZONTAL, InputView.SLIDER_MIN,
    InputView.SLIDER_MAX, InputView.SLIDER_INI);

    slider.setMajorTickSpacing(InputView.SLIDER_SPACING_MAJOR);
    slider.setMinorTickSpacing(InputView.SLIDER_SPACING_MINOR);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

    JLabel label = new JLabel("Prob. victoria jugador #" + (i + 1) + ":");
    skillLabels.add(new JLabel(slider.getValue() + "%"));

    panel.add(new JSeparator(), GROWX_SPAN);
    panel.add(label);
    panel.add(skillLabels.get(i));
    panel.add(slider, "align center, growx, span");

    sliders.add(slider);
  }

  for (int i = 0; i < 2; i++) {
    final int j = i;
    sliders.get(i).addChangeListener(e -> {
      sliders.get(j == 0 ? 1 : 0).setValue(InputView.SLIDER_MAX - sliders.get(j).getValue());
      skillLabels.get(j).setText(sliders.get(j).getValue() + "%");
    });}
  }

/*
Creates and places the game sets amount combobox to the panel.
*/
private void addComboBox() {
    comboBox = new JComboBox<>(OPTIONS_COMBOBOX);

    comboBox.setSelectedIndex(0);

    JLabel setsAmountLabel = new JLabel("Cantidad de sets:");

    panel.add(new JSeparator(), GROWX_SPAN);
    panel.add(setsAmountLabel);
    panel.add(comboBox, GROWX);
  }

/*
--return: Whether the tournament and the players names are setted.
*/
private boolean namesSetted() {
    return !Main.getMatch()
                .getTournamentName()
                .equals("")
           && Main.getMatch()
                  .getPlayers()
                  .stream()
                  .noneMatch(p -> p.getName()
                                   .equals(""));
  }
}