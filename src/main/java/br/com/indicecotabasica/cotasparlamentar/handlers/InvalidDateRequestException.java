package br.com.indicecotabasica.cotasparlamentar.handlers;

public class InvalidDateRequestException extends RuntimeException{

    public InvalidDateRequestException(String message){
        super(message);
    }
}
