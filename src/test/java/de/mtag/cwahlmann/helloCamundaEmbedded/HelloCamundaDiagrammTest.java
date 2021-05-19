package de.mtag.cwahlmann.helloCamundaEmbedded;

import de.mtag.cwahlmann.helloCamundaEmbedded.prz.hello.PrzHelloConstants;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@ExtendWith(ProcessEngineExtension.class)
public class HelloCamundaDiagrammTest {
    public static final String PRUEFE_ALTER_DELEGATE = "pruefeAlterDelegate";
    public static final String ZEIGE_FILM_DELEGATE = "zeigeFilmDelegate";
    public static final String VERTEILE_POPKORN_DELEGATE = "verteilePopkornDelegate";
    public static final String ZEIGE_AUSGANG_DELEGATE = "zeigeAusgangDelegate";
    public static final String HELLO_CAMUNDA_EMBEDDED_BPMN = "META-INF/processes/helloCamundaEmbedded.bpmn";

    private static final JavaDelegate NOOP_DELEGATE = delegateExecution -> {
    };
    private static final JavaDelegate BPMN_ERROR_DELEGATE = delegateExecution -> {
        throw new BpmnError("TEST-CODE", "TEST-MESSAGE");
    };
    public static final String ERFASSE_DATEN_TASK = "ErfasseDatenTask";
    public static final String ZEIGE_FILM_TASK = "ZeigeFilmTask";
    public static final String VERTEILE_POPKORN_TASK = "VerteilePopkornTask";
    public static final String ZEIGE_AUSGANG_TASK = "ZeigeAusgangTask";

    public static final Map<String, Object> TASK_TEST_DATA = withVariables(
            "vorname", "TESTNAME",
            "alter", 15,
            "saal", "TESTSAAL");
    public ProcessEngine processEngine;

    @BeforeEach
    public void init() {
        AbstractAssertions.init(processEngine);

        Mocks.register(PRUEFE_ALTER_DELEGATE, NOOP_DELEGATE);
        Mocks.register(ZEIGE_FILM_DELEGATE, NOOP_DELEGATE);
        Mocks.register(VERTEILE_POPKORN_DELEGATE, NOOP_DELEGATE);
        Mocks.register(ZEIGE_AUSGANG_DELEGATE, NOOP_DELEGATE);
    }

    @Test
    @Deployment(resources = HELLO_CAMUNDA_EMBEDDED_BPMN)
    void testGood() {
        ProcessInstance instance = runtimeService().startProcessInstanceByKey(PrzHelloConstants.PROCESS_KEY);
        assertThat(instance).isNotEnded();

        execute(job(ERFASSE_DATEN_TASK));
        complete(task(ERFASSE_DATEN_TASK), TASK_TEST_DATA);

        assertThat(instance).isWaitingAt(ZEIGE_FILM_TASK, VERTEILE_POPKORN_TASK);
        execute(job(ZEIGE_FILM_TASK));
        execute(job(VERTEILE_POPKORN_TASK));

        assertThat(instance).isEnded();
    }

    @Test
    @Deployment(resources = HELLO_CAMUNDA_EMBEDDED_BPMN)
    void testZuJung() {
        Mocks.register(PRUEFE_ALTER_DELEGATE, BPMN_ERROR_DELEGATE);

        ProcessInstance instance = runtimeService().startProcessInstanceByKey(PrzHelloConstants.PROCESS_KEY);
        assertThat(instance).isNotEnded();

        execute(job(ERFASSE_DATEN_TASK));
        complete(task(ERFASSE_DATEN_TASK), TASK_TEST_DATA);

        assertThat(instance).isWaitingAt(ZEIGE_AUSGANG_TASK);
        execute(job(ZEIGE_AUSGANG_TASK));

        assertThat(instance).isEnded();
    }
}
