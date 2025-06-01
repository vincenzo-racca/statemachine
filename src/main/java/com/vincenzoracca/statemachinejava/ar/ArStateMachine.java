package com.vincenzoracca.statemachinejava.ar;

import com.vincenzoracca.statemachinejava.model.PaperContext;
import org.squirrelframework.foundation.fsm.StateMachine;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;

public class ArStateMachine extends AbstractStateMachine<ArStateMachine, ArDeliveryState, ArEvent, PaperContext>
    implements StateMachine<ArStateMachine, ArDeliveryState, ArEvent, PaperContext> {
}
