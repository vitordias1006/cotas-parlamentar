package br.com.indicecotabasica.cotasparlamentar.handlers;

public class UnavailableCotaRequestException extends RuntimeException {
    public UnavailableCotaRequestException(String message) {
        super(message);
    }
}
