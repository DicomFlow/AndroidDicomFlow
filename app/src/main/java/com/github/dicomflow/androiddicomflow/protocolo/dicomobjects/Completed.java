package com.github.dicomflow.androiddicomflow.protocolo.dicomobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ricardobarbosa on 22/06/17.
 */
@Root
public class Completed {
    public enum Status {
        SUCCESS, ERROR
    }

    @Attribute public final String status;
    @Element public final String completedMessage;

    public Completed(@Attribute(name = "status") String status,
                     @Element(name = "completedMessage") String completedMessage) {
        this.status = status;
        this.completedMessage = completedMessage;
    }
}
