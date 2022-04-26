package argparse;

public class NotAValidArgumentException extends Exception {
    public NotAValidArgumentException(String argument) {
        super("Argument " + argument + " must not contain blank parameters.");
    }
}
