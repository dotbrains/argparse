package argparse;

import java.util.*;

public class ArgParse {
	private final ArrayList<String> arguments;
	private final HashMap<String, List<String>> map = new HashMap<>();
	private final Set<String> flags = new HashSet<>();

	private HashMap<String, Integer> argumentMappings;
	private HashSet<String> knownArguments;
	private HashSet<String> knownFlags;

	public ArgParse(ArrayList<String> arguments) {
		this.arguments = arguments;
	}

	// Return argument names
	public Set<String> getArgumentNames() {
		Set<String> argumentNames = new HashSet<>();
		argumentNames.addAll(flags);
		argumentNames.addAll(map.keySet());

		return argumentNames;
	}

	// Check if flag is given.
	public boolean hasFlag(String flagName)
	{
		return flags.contains(flagName);
	}

	public boolean hasArgument(String argumentName) {
		return map.containsKey(argumentName);
	}

	public String[] getArgumentParameters(String argumentName) {
		if (map.containsKey(argumentName))
			return map.get(argumentName).toArray(new String[0]);
		else
			return null;
	}

	public void map() throws NotAValidFlagException, NotAValidArgumentException {
		for (int i = 0; i < arguments.size(); i++) {
			String argument = arguments.get(i);

			if (isArgument(argument) || isFlag(argument)) {
				if (isFlag(argument)) {
					int j = i + 1;

					if (j < arguments.size()) {
						String arg = arguments.get(j);

						if (!isArgument(arg) && !isFlag(arg)) {
							throw new NotAValidFlagException(argument);
						}
					}

					flags.add(argument);
				} else {
					// List of values (can be multiple).
					List<String> argumentValues = new ArrayList<>();

					// Set of all previously seen arguments.
					// Used for handling duplicates.
					HashSet<String> seenArguments = new HashSet<>();

					for (int j = 0; j < arguments.size(); j++) {
						String arg = arguments.get(j);

						if (isArgument(arg) && arg.equals(argument)) {
							if (seenArguments.contains(arg)) {
								argumentValues.clear();
							} else {
								seenArguments.add(arg);
							}

							int expectedNumberOfParameters = argumentMappings.get(arg);

							for (int k = 1; k <= expectedNumberOfParameters; k++) {
								int l = j + k;

								if (l < arguments.size()) {
									String value = arguments.get(l);

									if (value.length() == 0) {
										throw new NotAValidArgumentException(argument);
									}

									argumentValues.add(value);
								}
							}
						}
					}

					map.put(argument, argumentValues);
				}
			}
		}
	}

	private boolean isArgument(String argument) {
		return (argument.startsWith("--") || argument.startsWith("-")) && knownArguments.contains(argument);
	}

	private boolean isFlag(String argument) {
		return (argument.startsWith("--") || argument.startsWith("-")) && knownFlags.contains(argument);
	}

	public void setMappings(Map<String, Integer> mappings) {
		argumentMappings = new HashMap<>(mappings);
	}

	public void setKnownFlags(HashSet<String> knownFlags) {
		this.knownFlags = knownFlags;
	}

	public void setKnownArguments(HashSet<String> knownArguments) {
		this.knownArguments = knownArguments;
	}
}


