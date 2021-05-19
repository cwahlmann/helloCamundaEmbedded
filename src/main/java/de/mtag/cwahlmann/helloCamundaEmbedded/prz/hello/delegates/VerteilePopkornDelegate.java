package de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.delegates;

import de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.PrzHelloConstants;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.value.StringValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VerteilePopkornDelegate implements JavaDelegate {
    public static final Logger log = LoggerFactory.getLogger(VerteilePopkornDelegate.class);

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        StringValue vorname = delegateExecution.getVariableTyped(PrzHelloConstants.VORNAME_VALUE);

        log.info("============================= Popkorn, frische Popkorn - " + vorname.getValue() + ", möchtest du eine Tüte Popkorn?");
    }
}
