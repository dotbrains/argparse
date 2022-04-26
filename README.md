# ArgParse ☕

*Argument parsing done right based on Java.*

Argument parsing is difficult and some times hard to implement. The inspiration for this project came from a graduate school project that required the implementation of a system that utilizes a CLI program that act as the front-end for the system.

## Requirements

Maven and `make` are required to build the delivery service CLI.

## Make

To build the CLI application, run the following command:

```bash
make jar
```

## Running

To run the CLI simply run the following command:

```bash
java -jar target/example.jar # [commands here]
```

## Usage

```text
java -jar example.jar [COMMANDS]
```
