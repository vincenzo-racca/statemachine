package com.vincenzoracca.statemachinejava.ar;

import com.vincenzoracca.statemachinejava.action.DematAction;
import com.vincenzoracca.statemachinejava.action.FascicoloChiusoAction;
import com.vincenzoracca.statemachinejava.action.MetaAction;
import com.vincenzoracca.statemachinejava.action.RetryableAction;
import com.vincenzoracca.statemachinejava.model.PaperContext;
import org.springframework.stereotype.Component;
import org.squirrelframework.foundation.fsm.*;

import jakarta.annotation.PostConstruct;

@Component
public class ArStateMachineBuilder {

    private StateMachineBuilder<ArStateMachine, ArDeliveryState, ArEvent, PaperContext> typedBuilder;


    @PostConstruct
    public void init() {
        typedBuilder = StateMachineBuilderFactory.create(ArStateMachine.class, ArDeliveryState.class, ArEvent.class, PaperContext.class);

        typedBuilder.externalTransition()
                .from(ArDeliveryState.SENT)
                .to(ArDeliveryState.AR_PRESA_IN_CARICO)
                .on(ArEvent.CON996)
                .perform(new MetaAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_PRESA_IN_CARICO)
                .to(ArDeliveryState.AR_INESITO)
                .on(ArEvent.RECRN010)
                .perform(new MetaAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_INESITO)
                .to(ArDeliveryState.AR_FURTO_SMARRIMENTO_DETERIORAMENTO)
                .on(ArEvent.RECRN006)
                .perform(new RetryableAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_PRESA_IN_CARICO)
                .to(ArDeliveryState.AR_FURTO_SMARRIMENTO_DETERIORAMENTO)
                .on(ArEvent.RECRN006)
                .perform(new RetryableAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_PRESA_IN_CARICO)
                .to(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_PREESITO)
                .on(ArEvent.RECRN002D)
                .perform(new MetaAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_PREESITO)
                .to(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_DEMAT)
                .on(ArEvent.RECRN002E)
                .perform(new DematAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_DEMAT)
                .to(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_DEMAT)
                .on(ArEvent.RECRN002E)
                .perform(new DematAction<>());

        typedBuilder.externalTransition()
                .from(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_DEMAT)
                .to(ArDeliveryState.AR_IRREPERIBILITA_ASSOLUTA_FASCICOLO_CHIUSO)
                .on(ArEvent.RECRN002F)
                .perform(new FascicoloChiusoAction<>());
    }

    public ArStateMachine createStateMachine(ArDeliveryState deliveryState) {
        return typedBuilder.newStateMachine(deliveryState);
    }
}
