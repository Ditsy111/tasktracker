run:
mvn spring-boot:run

test:
mvn clean test

lint:
mvn checkstyle:check

format:
mvn spotless:apply
