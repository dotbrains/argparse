import argparse.ArgParse;
import argparse.NotAValidArgumentException;
import argparse.NotAValidFlagException;

import java.util.*;

public class ExampleCLI {
	// This map represents the argument and its expected number of parameters.
	// The key is the argument and the value is its expected number of parameters.
	private static final HashMap<String, Integer> ARGUMENT_MAPPINGS = new HashMap<>(Map.ofEntries(
			Map.entry("", 0)
	));

	private static final HashSet<String> KNOWN_FLAGS = new HashSet<>(List.of(
			""
	));

	private final ArgParse argParse;

	public ExampleCLI(String[] arguments) {
		ArrayList<String> args = new ArrayList<>(Arrays.asList(arguments));

		argParse = new ArgParse(args);

		argParse.setKnownArguments(new HashSet<>(ARGUMENT_MAPPINGS.keySet()));
		argParse.setKnownFlags(KNOWN_FLAGS);
		argParse.setMappings(ARGUMENT_MAPPINGS);

		try {
			argParse.map();
		} catch (NotAValidFlagException | NotAValidArgumentException e) {
			usage();

			return;
		}

		boolean isValid = validateArguments(args);

		if (isValid) {
			processArguments();
		}
	}

	private static void usage() {
		System.err.println(
				"Usage:\n" +
						"java -jar example.jar [COMMANDS]\n" +
						""
		);
	}

	private boolean validateArguments(ArrayList<String> args) {
		Set<String> argumentNames = argParse.getArgumentNames();
		String arguments = String.join("", argumentNames);

		if (arguments.length() == 0) {
			usage();

			return false;
		}

		Set<String> SUPPORTED_ARGUMENTS = new HashSet<>(ARGUMENT_MAPPINGS.keySet());
		SUPPORTED_ARGUMENTS.addAll(KNOWN_FLAGS);

		for (String argument : argumentNames) {
			if (!SUPPORTED_ARGUMENTS.contains(argument)) {
				usage();

				return false;
			}
		}

		// Additional argument checks.
		// e.g. make sure arguments have valid number of parameters.

		return true;
	}

	private boolean hasIncorrectNumberOfParameters(String argument) {
		String[] values = argParse.getArgumentParameters(argument);

		if (values == null) {
			values = new String[0];
		}
		int expectedNumberOfParameters = ARGUMENT_MAPPINGS.get(argument);

		return values.length != expectedNumberOfParameters;
	}

	private void processArguments() {

	}
}
