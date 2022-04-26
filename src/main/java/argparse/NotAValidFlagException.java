package argparse;

public class NotAValidFlagException extends Exception {
    public NotAValidFlagException(String argument) {
        super("Flag " + argument + " cannot contain a parameter.");
    }
}
