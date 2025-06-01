package com.vincenzoracca.statemachinejava.action;

import com.vincenzoracca.statemachinejava.model.PaperContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.squirrelframework.foundation.fsm.Action;
import org.squirrelframework.foundation.fsm.StateMachine;

/**
 * Action ri-utilizzabile in più FSM.
 *
 * @param <T> la classe della state-machine
 * @param <S> enum degli stati
 * @param <E> enum degli eventi
 */
public class DematAction<
        T extends StateMachine<T, S, E, PaperContext>,
        S,
        E>
        implements Action<T, S, E, PaperContext> {

    private static final Logger log = LoggerFactory.getLogger(DematAction.class);


    @Override
    public void execute(S from, S to, E event, PaperContext ctx, T stateMachine) {
        log.info("Transition from '{}' to '{}' on event '{}' – ctx: {}",
                from, to, event, ctx);
    }

    @Override public String  name()    { return "DEMAT_ACTION"; }
    @Override public int     weight()  { return NORMAL_WEIGHT; }
    @Override public boolean isAsync() { return false; }
    @Override public long    timeout() { return 0; }
}
