package fr.projet.declarationfrais.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.webflow.core.collection.AttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.definition.FlowDefinition;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.definition.TransitionDefinition;
import org.springframework.webflow.execution.*;

public class Fel implements FlowExecutionListener {
    static Logger logger = LoggerFactory.getLogger(Fel.class);

@Override
public void requestSubmitted(RequestContext context) {
    FlowExecutionListener.super.requestSubmitted(context);
    logger.debug("requestSubmitted");  // Utilisez l'instance de logger
}

}