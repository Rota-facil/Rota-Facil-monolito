package com.rota.facil.prefectures.exceptions;

public class PrefectureNotFoundExceptions extends RuntimeException {
    public PrefectureNotFoundExceptions() {
        super("Prefeitura não foi encontrada");
    }
}
