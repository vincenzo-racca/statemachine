package com.vincenzoracca.statemachinejava;

import com.vincenzoracca.statemachinejava.ar.ArDeliveryState;
import com.vincenzoracca.statemachinejava.ar.ArStateMachineBuilder;
import org.junit.jupiter.api.Test;
import org.squirrelframework.foundation.component.SquirrelProvider;
import org.squirrelframework.foundation.fsm.DotVisitor;
import org.squirrelframework.foundation.fsm.SCXMLVisitor;

class ExportArSmDiagramTest {

    @Test
    void exportFsm() {
        ArStateMachineBuilder arStateMachineBuilder = new ArStateMachineBuilder();
        arStateMachineBuilder.init();
        var sm = arStateMachineBuilder.createStateMachine(ArDeliveryState.SENT);
        DotVisitor v =
                SquirrelProvider.getInstance().newInstance(DotVisitor.class);
        sm.accept(v);
        v.convertDotFile("ar-state-machine");



        SCXMLVisitor visitor = SquirrelProvider.getInstance().newInstance(SCXMLVisitor.class);
        sm.accept(visitor);
        visitor.convertSCXMLFile("ar-state-machine", true);




    }

}
