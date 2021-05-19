package de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.delegates;

import de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.PrzHelloConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZeigeFilmDelegate implements JavaDelegate {
    public static final Logger log = LoggerFactory.getLogger(ZeigeFilmDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        StringValue saal = delegateExecution.getVariableTyped(PrzHelloConstants.SAAL_VALUE);
        StringValue vorname = delegateExecution.getVariableTyped(PrzHelloConstants.VORNAME_VALUE);

        PrzHelloConstants.Film.find(saal.getValue())
                .ifPresent(film -> {
                    log.info("============================= Hallo " + vorname.getValue() + ", viel Spaß beim Film!");

                    if (vorname.getValue().equals("Fred")) {
                        throw new RuntimeException("!!!!!!!!!!!!!!!!!!!!!!!!!!! Stromausfall. Bitte verlassen Sie den Saal.");
                    }

                    if (Math.random()>0.5) {
                        throw new RuntimeException("!!!!!!!!!!!!!!!!!!!!!!!!!!! Der Film ist gerissen. Wir bitten um Geduld.");
                    }

                    log.info("============================= CIMEMA " + saal.getValue() + " PROUDLY PRESENTS: '" + film.getTitel() + "'...");
                });
    }
}
