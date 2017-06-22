package com.github.dicomflow.androiddicomflow.models.protocolo.services.request;

import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Url;

import org.simpleframework.xml.Element;

/**
 * Created by netolucena on 22/06/2017.
 */

public class RequestPut extends Request {

    @Element public final String requestType;

    @Element public final Url url;

    public RequestPut(String requestType, Url url) {
        this.requestType = requestType;
        this.url = url;
    }


    @Override
    public String getAction() {

        return "PUT";
    }

}
