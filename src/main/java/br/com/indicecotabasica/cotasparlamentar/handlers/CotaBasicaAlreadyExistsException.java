package br.com.indicecotabasica.cotasparlamentar.handlers;

public class CotaBasicaAlreadyExistsException extends  RuntimeException{

    public CotaBasicaAlreadyExistsException(String mensagem) {
        super(mensagem);
    }
}
