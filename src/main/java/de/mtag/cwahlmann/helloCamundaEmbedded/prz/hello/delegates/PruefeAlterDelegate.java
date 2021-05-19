package de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.delegates;

import de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.PrzHelloConstants;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.LongValue;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PruefeAlterDelegate implements JavaDelegate {
    public static final Logger log = LoggerFactory.getLogger(PruefeAlterDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        LongValue alter = delegateExecution.getVariableTyped(PrzHelloConstants.ALTER_VALUE);
        StringValue vorname = delegateExecution.getVariableTyped(PrzHelloConstants.VORNAME_VALUE);
        StringValue saal = delegateExecution.getVariableTyped(PrzHelloConstants.SAAL_VALUE);

        PrzHelloConstants.Film film = PrzHelloConstants.Film.find(saal.getValue())
                .orElseThrow(() -> new RuntimeException("==================== In Saal " + saal.getValue() + " wird derzeit kein Film gezeigt."));

        if (alter.getValue() < film.getFsk()) {
            throw new BpmnError("FSK" + film.getFsk(),
                    String.format("%s, mit deinen %d Jahren bist du zu jung für den Film '%s' in Saal '%s'.",
                            vorname.getValue(), alter.getValue(), film.getTitel(), saal.getValue()));
        }
    }
}
