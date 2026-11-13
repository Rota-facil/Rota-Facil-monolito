package com.rota.facil.prefectures.http.exceptions;

public class PrefectureNotFoundExceptions extends RuntimeException {
    public PrefectureNotFoundExceptions() {
        super("Prefeitura não foi encontrada");
    }
}
