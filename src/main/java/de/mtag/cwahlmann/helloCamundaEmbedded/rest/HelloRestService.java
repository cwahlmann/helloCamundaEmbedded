package de.mtag.cwahlmann.helloCamundaEmbedded.rest;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Path("/prozesse")
public class HelloRestService {
    public static final Logger log = LoggerFactory.getLogger(HelloRestService.class);

    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/start/{pid}")
    public String greeting(@PathVariable(value = "pid") String pid) {
        try {
            ProcessInstance instance = runtimeService.startProcessInstanceByKey(pid);
            return "Prozess '" + pid + "' erfolgreich gestartet mit InstanceId '" + instance.getProcessInstanceId() + "'.";
        } catch (Exception e) {
            log.error("Fehler beim Starten des Prozesses '" + pid + "'", e);
            throw (e);
        }
    }
}
