package com.github.dicomflow.androiddicomflow.models.protocolo.services.request;

import com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects.Result;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by netolucena on 22/06/2017.
 */
@Root(name = "service")
public class RequestResult extends Request{

    @ElementList(name = "results", inline = true) public final List<Result> results;

    public RequestResult(
            @ElementList(name = "results", inline = true) List<Result> results) {
        super("RESULT");
        this.results = results;
    }

    public RequestResult(@Attribute(name = "name") String name,
                         @Attribute(name = "action") String action,
                         @Attribute(name = "version") String version,
                         @Element(name = "timeout") String timeout,
                         @Element(name = "timestamp") String timestamp,
                         @Element(name = "messageID")String messageID,
                         @ElementList(name = "results", inline = true) List<Result> results) {
        super(name, action, version, timeout, timestamp, messageID);
        this.results = results;
    }



}
