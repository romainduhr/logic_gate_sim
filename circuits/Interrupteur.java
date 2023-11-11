package circuits;

public class Interrupteur extends Composant {
        
        protected boolean etat;
        
        public void on(){
            etat = true;
        }
        
        public void off(){
            etat = false;
        }
        
        public boolean getEtat() throws NonConnecteException {
            return etat;
        }
}
