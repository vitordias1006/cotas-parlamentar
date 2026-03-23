package br.com.indicecotabasica.cotasparlamentar.mappers;

import br.com.indicecotabasica.cotasparlamentar.dtos.CotaBasicaResponse;
import br.com.indicecotabasica.cotasparlamentar.models.CotaBasica;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CotaMapper {

    public CotaBasica toCotaBasica(CotaBasicaResponse cotaBasicaResponse) {
        CotaBasica cotaBasica = new CotaBasica();
        BeanUtils.copyProperties(cotaBasicaResponse, cotaBasica);
        return cotaBasica;
    }

    public CotaBasicaResponse toCotaResponse(CotaBasica cotaBasica) {
        CotaBasicaResponse cotaBasicaResponse = new CotaBasicaResponse();
        BeanUtils.copyProperties(cotaBasica, cotaBasicaResponse);
        return cotaBasicaResponse;
    }
}
