package com.github.dicomflow.androiddicomflow.models.protocolo.xml.dicomobjects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by ricardobarbosa on 21/06/17.
 */

@Root
public class Serie {
    @Attribute public final String id;
    @Attribute public final String bodypart;
    @Attribute public final String description;
    @Attribute public final Integer instances;

    public Serie(String id, String bodypart, String description, Integer instances){
        this.id = id;

        this.bodypart = bodypart;
        this.description = description;
        this.instances = instances;
    }
}
