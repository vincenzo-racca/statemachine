package com.vincenzoracca.statemachinejava.ar;


public enum ArDeliveryState {
    SENT, // stato iniziale dell'entità, invio di SENT a ext-channel
    AR_PRESA_IN_CARICO, // stato di presa in carico dal recapitista

    // eventi preesito
    AR_INESITO,
    AR_IRREPERIBILITA_ASSOLUTA_PREESITO,

    // eventi demat
    AR_IRREPERIBILITA_ASSOLUTA_DEMAT,
    // eventi finali
    AR_FURTO_SMARRIMENTO_DETERIORAMENTO,
    AR_IRREPERIBILITA_ASSOLUTA_FASCICOLO_CHIUSO,
}
