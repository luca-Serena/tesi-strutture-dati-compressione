package wavelet;

public class CharacterNotFoundException extends Exception {

  
    public CharacterNotFoundException() {
        super ("Impossibile trovare il carattere richiesto all'interno della stringa");
    }

    /**
     * Constructs an instance of <code>CharacterNotFoundException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public CharacterNotFoundException(String msg) {
        super(msg);
    }
}
