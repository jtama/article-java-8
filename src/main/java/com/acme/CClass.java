package com.acme;

import java.util.Collection;

public class CClass {
    private String uri;

    public CClass(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }

    public Model getModel() {
        return null;
    }

    public Collection<CCObject> getClassInstances() {
        return null;
    }
}
