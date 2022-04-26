ARTIFACT_NAME ?= example

.DEFAULT_GOAL := help

.PHONY: help

.PHONY: all
all: ## Cleans the project including maven and docker image and builds the container
	make clean
	make jar

.PHONY: jar
jar:  ## Builds the jar
	make clean-maven
	mvn package
	rm -rf target/"${ARTIFACT_NAME}.jar"
	mv "target/${ARTIFACT_NAME}-jar-with-dependencies.jar" target/"${ARTIFACT_NAME}.jar"

# Clean Commands

.PHONY: clean
clean: ## Clean the project including maven and the docker image
	make clean-maven

.PHONY: clean-maven
clean-maven:
	rm -rf target/

help: ## This help.
	@awk 'BEGIN {FS = ":.*?## "} /^[a-zA-Z_-]+:.*?## / {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}' $(MAKEFILE_LIST)
