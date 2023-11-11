// SOURCES A COMPLETER...

package ihm;

import circuits.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author carre
 */
public class Testeur extends JFrame {

    private static final int HTEXT=10; // dimensions des zones de textes
    private static final int WTEXT=30;

    // le circuit a interfacer'
    private Circuit circuit = null;
    private String fileName; // auquel doivent correspondre .png et .bin
    // table d'identification des interrupteurs : pour leur identification a la selection
    private TreeMap<String, Interrupteur> ins = new TreeMap<String, Interrupteur>();
    //GUIDE: ajouter la liste des vannes...
    private TreeMap<String, Vanne> outs = new TreeMap<String, Vanne>();
    // les composants de l'interface
    private ImageCanvas vue = new ImageCanvas(); // pour affichage du .png
    private JTextArea config; // description() du circuit
    private JComboBox selectIn; // liste de selection sur les interrupteurs
    private JRadioButton onOff; // sur l'interrupteur selectionne
    private JTextArea insStates; // trace des etats des interrupteurs
    //GUIDE: trace des vannes...
    private JTextArea outsStates;
    //interrupteur selectionne dans la combo box pour lui appliquer on/off
    private Interrupteur selectedSwitch;

    // Construction et configuration de l'application
    private Testeur(String fileName) throws IOException, ClassNotFoundException {
        // chargement du circuit
        this.fileName = fileName;
        circuit = new Circuit();
        circuit.load(fileName + ".bin");

        // banniere de la Frame
        setTitle("Test de " + circuit.getName());

        // remplissage de l'image .png
        vue.setSize(200,200);
        vue.setImage(getToolkit().createImage(fileName + ".png"));

        //instanciation et configuration des composants d'interface
        // description du circuit
        config = new JTextArea(HTEXT, WTEXT);
        config.setEditable(false);
        config.setBorder(BorderFactory.createLineBorder(Color.black));
        config.setText(circuit.getDescription());

        // interrupteurs: liste de selection
        selectIn = new JComboBox();
        for (Interrupteur in : circuit.getIns()) {
            ins.put(in.getId(), in);
            selectIn.addItem(in.getId());
        }
        // selection intiale par defaut
        selectIn.setSelectedIndex(0);
        selectedSwitch = circuit.getIns().get(0);
        //ecouteur de selection
        selectIn.addActionListener(new InListener());
        // bouton on/off sur la selection
        onOff = new JRadioButton("on/off");
        //GUIDE: brancher ici un ecouteur du bouton (classe OnOffListener)
        onOff.addActionListener(new OnOffListener());
        // zones de texte pour les traces d'etats
        // etats des interrupteurs
        insStates = new JTextArea(HTEXT, WTEXT);
        insStates.setEditable(false);
        insStates.setBorder(BorderFactory.createLineBorder(Color.black));
        //GUIDE: faire de meme pour les vannes...
        for (Vanne out : circuit.getOuts()) {
            outs.put(out.getId(), out);
        }
        outsStates = new JTextArea(HTEXT, WTEXT);
        outsStates.setEditable(false);
        outsStates.setBorder(BorderFactory.createLineBorder(Color.black));
        // etat initial
        update();

        // ajout et positionnement des composants d'interface
        Container cp = getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.Y_AXIS));
        cp.add(vue);
        cp.add(config);
        cp.add(selectIn);
        cp.add(onOff);
        cp.add(insStates);
        //GUIDE: ajouter la zone de texte des vannes
        cp.add(outsStates);

        //ecouteur sur la JFrame
        this.addWindowListener(new TesteurListener());
    }

    // ECOUTEURS 
    // ecouteur sur la selection: memorise l'interrupteur selectionne dans selectedSwitch
    class InListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String inId = (String) selectIn.getSelectedItem();
            selectedSwitch = ins.get(inId);
        }
    }

    //GUIDE: ecouteur du bouton onOFF pour switcher l'interrupteur selectionne
    //GUIDE: programmer la classe d'ActionListener OnOffListener
    //GUIDE: avec sa methode actionPerformed
    //GUIDE: si le bouton onOff est selectionne (cf. javadoc)
    //GUIDE: on sur l'interrupteur selectionne sinon off
    //GUIDE: terminer par update() pour mettre a jour les etats

    class OnOffListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (onOff.isSelected()){
                selectedSwitch.on();
                update();
            }else {
                selectedSwitch.off();
                update();
            }
        }
    }
    // zones d'etats
    void update() {
        insStates.setText(""); // reinitialisation
        for (Interrupteur in : ins.values()) {
            insStates.append(in.traceEtat());
            insStates.append("\n");
        }
        //GUIDE: completer pour afficher l'etat des vannes
        outsStates.setText("");
        for (Vanne out : outs.values()) {
            outsStates.append(out.traceEtat());
            outsStates.append("\n");
        }
    }

    // on quit => sauvegarder l'etat du circuit
    class TesteurListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            try {
                circuit.save(fileName+ ".bin");
            } catch (IOException ex) {
                System.err.println("Erreur save sur " + fileName);
            }
            System.exit(0);
        }
    }

    // Utilitaire: afficheur d'image
    class ImageCanvas extends Canvas {

        java.awt.Image image;

        public void setImage(java.awt.Image img) {
            image = img;
            repaint(0);
        }

        @Override
        public synchronized void paint(Graphics g) {
            if (image != null) {
                g.drawImage(image, 0, 0, this); //this = ImageObserver pour repaint
            }
        }
    }

    // lancement de l'application
    public static void main(String argv[]) throws IOException, ClassNotFoundException {
        if (argv.length != 1) {
            System.err.println("usage: java ihm.Testeur fichier de circuit (sans .bin)>");
        } else {

            Testeur test = new Testeur(argv[0]);
            test.pack();
            test.setVisible(true);
        }
    }
}
