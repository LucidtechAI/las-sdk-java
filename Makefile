.PHONY: *

build:
	./gradlew build

test:
	./gradlew test

publish:
	./gradlew clean test
	./gradlew clean uploadArchives

java-docs:
	./gradlew clean javadocJar

docs: # See here for doxybook2 installation instructions https://github.com/matusnovak/doxybook2#Install
	doxygen
	moxygen xml

prism-start:
	@echo "Starting mock API..."
	docker run -t \
		--init \
		--detach \
		-p 4010:4010 \
		stoplight/prism:3.2.8 mock -d -h 0.0.0.0 \
		https://raw.githubusercontent.com/LucidtechAI/las-docs/master/apis/dev/oas.json > /tmp/prism.cid
