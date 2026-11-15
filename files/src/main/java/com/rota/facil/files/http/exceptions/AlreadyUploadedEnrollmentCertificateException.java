package com.rota.facil.files.http.exceptions;

public class AlreadyUploadedEnrollmentCertificateException extends RuntimeException {
    public AlreadyUploadedEnrollmentCertificateException() {
        super("Só é possível cadastrar um comprovante de matrícula");
    }
}
