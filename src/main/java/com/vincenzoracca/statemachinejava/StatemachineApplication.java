package com.vincenzoracca.statemachinejava;

import com.vincenzoracca.statemachinejava.ar.ArDeliveryState;
import com.vincenzoracca.statemachinejava.ar.ArEvent;
import com.vincenzoracca.statemachinejava.ar.ArStateMachineBuilder;
import com.vincenzoracca.statemachinejava.ar.ArStateMachine;
import com.vincenzoracca.statemachinejava.model.PaperContext;
import com.vincenzoracca.statemachinejava.model.PaperProgressStatusEvent;
import com.vincenzoracca.statemachinejava.model.PnDeliveryRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StatemachineApplication {


	public static void main(String[] args) {
		SpringApplication.run(StatemachineApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ArStateMachineBuilder builder) {
		return args -> {
			// time 0
			PnDeliveryRequest entity = new PnDeliveryRequest("requestId", ArDeliveryState.SENT.name());
			PaperProgressStatusEvent paperRequest = new PaperProgressStatusEvent(ArEvent.CON996.name());
			PaperContext ctx = new PaperContext(entity, paperRequest);
			ArStateMachine fsm = builder.createStateMachine(ArDeliveryState.valueOf(entity.deliveryState()));
			fsm.fire(ArEvent.valueOf(ctx.paperRequest().event()), ctx);

			// time 1
			entity = new PnDeliveryRequest("requestId", ArDeliveryState.AR_PRESA_IN_CARICO.name());
			paperRequest = new PaperProgressStatusEvent(ArEvent.RECRN010.name());
			ctx = new PaperContext(entity, paperRequest);
			fsm = builder.createStateMachine(ArDeliveryState.valueOf(entity.deliveryState()));
			fsm.fire(ArEvent.valueOf(ctx.paperRequest().event()), ctx);

			// time 2
			entity = new PnDeliveryRequest("requestId", ArDeliveryState.AR_INESITO.name());
			paperRequest = new PaperProgressStatusEvent(ArEvent.RECRN006.name());
			ctx = new PaperContext(entity, paperRequest);
			fsm = builder.createStateMachine(ArDeliveryState.valueOf(entity.deliveryState()));
			fsm.fire(ArEvent.valueOf(ctx.paperRequest().event()), ctx);

			// time 3 stato inatteso
			entity = new PnDeliveryRequest("requestId", ArDeliveryState.AR_FURTO_SMARRIMENTO_DETERIORAMENTO.name());
			paperRequest = new PaperProgressStatusEvent(ArEvent.CON996.name());
			ctx = new PaperContext(entity, paperRequest);
			fsm = builder.createStateMachine(ArDeliveryState.valueOf(entity.deliveryState()));
			fsm.fire(ArEvent.valueOf(ctx.paperRequest().event()), ctx);
		};
	}

}
