package de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello;

import java.util.Optional;
import java.util.stream.Stream;

public final class PrzHelloConstants {
    private PrzHelloConstants() {
    }

    public static final String PROCESS_KEY = "HELLO.CAMUNDA.EMBEDDED";
    public static final String ERROR_CODE = "errorCode";
    public static final String ERROR_MESSAGE = "errorMessage";

    public static final String VORNAME_VALUE = "vornameValue";
    public static final String ALTER_VALUE = "alterValue";
    public static final String SAAL_VALUE = "saalValue";

    public enum Film {
        KINO_1("Der König der Löwen", 6),
        KINO_2("Der Herr der Ringe", 12),
        KINO_3("Das Kettensägenmassaker", 18);

        private final String titel;
        private final int fsk;

        Film(String titel, int fsk) {
            this.titel = titel;
            this.fsk = fsk;
        }

        public String getTitel() {
            return titel;
        }

        public int getFsk() {
            return fsk;
        }

        public static Optional<Film> find(String name) {
            return Stream.of(values()).filter(film -> film.name().equals(name)).findAny();
        }
    }
}
