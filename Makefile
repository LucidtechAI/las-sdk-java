.PHONY: *

build:
	./gradlew build

test:
	./gradlew test

prism-start:
	@echo "Starting mock API..."
	docker run -t \
		--init \
		--detach \
		-p 4010:4010 \
		stoplight/prism:3.2.8 mock -d -h 0.0.0.0 \
		https://raw.githubusercontent.com/LucidtechAI/las-docs/master/apis/dev/oas.json > /tmp/prism.cid
