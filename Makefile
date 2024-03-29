.PHONY: *

build:
	./gradlew build

test:
	./gradlew --info test

publish:
	./gradlew clean test
	./gradlew clean uploadArchives

java-docs:
	./gradlew clean javadocJar

docs: # See here for doxybook2 installation instructions https://github.com/matusnovak/doxybook2#Install
	doxygen

prism-start:
	@echo "Starting mock API..."
	docker run -t \
		--init \
		--detach \
		-p 4010:4010 \
		stoplight/prism:3.2.8 mock -d -h 0.0.0.0 \
		https://raw.githubusercontent.com/LucidtechAI/cradl-docs/master/reference/restapi/oas.json \
		> /tmp/prism.cid
