package de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.delegates;

import de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.PrzHelloConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZeigeAusgangDelegate implements JavaDelegate {
    public static final Logger log = LoggerFactory.getLogger(ZeigeAusgangDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        StringValue errorCode = delegateExecution.getVariableTyped(PrzHelloConstants.ERROR_CODE);
        StringValue errorMessage = delegateExecution.getVariableTyped(PrzHelloConstants.ERROR_MESSAGE);

        log.info("============================= Der Zutritt in den Kinosaal ist dir nicht gestattet: " + errorCode.getValue() + " - " + errorMessage.getValue());
    }
}
