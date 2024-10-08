# omi-municipality

# What's claims this project?

The municipalities may merge, or from one municipality two or more may appear, this project have an object collect the
information of all municipalities that are necessary. The information is the follow:

- Codice regione
- Codice provincia
- Codice Comune
- Sigla Comune
- Nome Comune
- Nome Regione
- Codice Catastale
- Tipologia Unita Territoriale
- Comune Capoluogo

The previous information you can find in the next
url [current municipalities](https://www.istat.it/storage/codici-unita-amministrative/Archivio-elenco-comuni-codici%20e-denominazioni_Anno_2024.zip)

To previous list, us add the next information that are retrieved from the next
url [Geocoding API](https://developers.google.com/maps/documentation/geocoding/overview?hl=it)

- Latitudine
- Longitudine
- Altitudine

The next information is the information for the municipalities that are suppressed.

- Codice Comune
- Nome Comune
- Codice Nuovo Comune
- Anno Sopressione

The previous information you can find in the next
url [suppressed municipalities](https://www.istat.it/wp-content/uploads/2024/09/Elenco-comuni-soppressi.zip)

# Other information

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/omi-municipality-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- Mutiny ([guide](https://quarkus.io/guides/mutiny-primer)): Write reactive applications with the modern Reactive
  Programming library Mutiny
- Eclipse Vert.x ([guide](https://quarkus.io/guides/vertx)): Write reactive applications with the Vert.x API
