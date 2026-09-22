package com.rota.facil.journey.exceptions;

public class InstitutionInActiveTripException extends RuntimeException {
    public InstitutionInActiveTripException() {
        super("Não é possível excluir a instituição porque ela pertence a uma viagem em andamento");
    }
}
