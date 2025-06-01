# Statemachine Demo

This repository contains a **Spring Boot** demo that models the life‑cycle of an *AR* using the **Squirrel‑foundation** state‑machine library.
It is a strongly‑typed finite‑state machine that is self‑documenting and automatically exportable to **DOT** and **SCXML**.

---

## ✨Features

* **Typed FSM** (`ArStateMachine`) with \~\~30 lines of fluent configuration
* Re‑usable, generic **actions** (`RetryableAction`, `MetaAction`, …)
* Runtime export to `ar-state-machine.dot` **and** `ar-state-machine.scxml`
* Headless diagram rendering in CI via GitHub Actions + Graphviz
* 100% open‑source stack (Spring Boot 3, Java 21, Squirrel)

---

## 🗂Project layout

```text
src/
 ├─ main/java/com/vincenzoracca/statemachine
 │   ├─ StatemachineApplication.java     # Spring Boot entry‑point
 │   ├─ ar/                              # FSM model
 │   │   ├─ ArDeliveryState.java         # enum of states
 │   │   ├─ ArEvent.java                 # enum of events
 │   │   ├─ ArStateMachine.java          # typed FSM interface
 │   │   └─ ArStateMachineBuilder.java   # builder with transitions
 │   ├─ action/                          # Transition actions
 │   │   ├─ MetaAction.java
 │   │   ├─ RetryableAction.java
 │   │   ├─ DematAction.java
 │   │   └─ FascicoloChiusoAction.java
 │   └─ model/
 │       └─ PaperContext.java                   # Domain context passed to actions (PaperProgressStatusEvent + PnDeliveryRequest)
 │       └─ PaperProgressStatusEvent.java       # External event 
 │       └─ PnDeliveryRequest.java              # Entity in DB
 └─ test/java/…
     └─ ExportArSmDiagram.java           # JUnit that emits DOT + SCXML
```

---

## 🏗Requirements

| Tool         | Version                       |
| ------------ | ----------------------------- |
| **JDK**      | 21+                           |
| **Graphviz** | 8.x+ (only for PNG generation) |

Install Graphviz locally (`brew install graphviz` / `apt install graphviz`) **or** let the GitHub Action do it.

---

## 🚀Quickstart

```bash
# Compile + run all tests (generates ar-state-machine.dot & .scxml)
./mvnw clean test

# Optional: render the PNG locally
mkdir -p diagram
dot -Tpng ar-state-machine.dot -o diagram/ar-state-machine.png

# Start the Spring Boot app
./mvnw spring-boot:run
```

---

## 🔄State‑machine overview

The FSM starts in **`SENT`** and reacts to status‑codes coming from the
analogic flow ecosystem:

```
SENT --CON996--> AR_PRESA_IN_CARICO --RECRN010--> AR_INESITO
      \                                         |
       \--RECRN006--> AR_FURTO_SMARRIMENTO_DETERIORAMENTO <--/
```

* **Retryable** errors (`RECRN006`, …) trigger `RetryableAction`.
* **Meta** events (`CON996`, `RECRN010`) trigger `MetaAction`.
* When the dossier is closed the FSM fires `FascicoloChiusoAction` and stops.

The full, up‑to‑date diagram is produced automatically at every build; see
`diagram/ar-state-machine.png` or the *Artifacts* tab of your last GH run.

---


## 🧪Testing

Unit tests live under `src/test/java`.
`ExportArSmDiagramTest` is both a test and a documentation generator: if the
FSM definition changes, the test fails only if the export throws, keeping
your diagram in sync with the code.

You can paste the code of `ar-state-machine.dot` to https://dreampuf.github.io/GraphvizOnline to visualize the diagram
without create a png file, or, you can use `DOT Language` plugin in IntelliJ.

---

## 🙋‍♀️Contributing

1. Fork the repo
2. Create a branch `feature/my-idea`
3. Commit + push and open a PR

Please add or update tests when you change the FSM transitions.

---

## 📜License

This project is licensed under the **Apache License 2.0** – see
[`LICENSE`](LICENSE) for details.
