package com.github.dicomflow.androiddicomflow.protocolo.services.certificate;

import com.github.dicomflow.androiddicomflow.protocolo.services.Service;
import com.github.dicomflow.androiddicomflow.protocolo.dicomobjects.Domain;
import com.github.dicomflow.androiddicomflow.protocolo.dicomobjects.Mail;
import com.github.dicomflow.androiddicomflow.protocolo.dicomobjects.Port;

import org.simpleframework.xml.Element;

/**
 * Created by ricardobarbosa on 15/06/17.
 */
public abstract class Certificate extends Service {

    @Element public final Domain domain;
    @Element public final Mail mail;
    @Element public final Port port;

    public Certificate(String action, String from, Domain domain, Mail mail, Port port) {
        super("CERTIFICATE", action, from);
        this.domain = domain;
        this.mail = mail;
        this.port = port;
    }

}
