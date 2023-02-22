package com.octospoon.core.lifecycle;

import org.springframework.stereotype.Service;

public class State {

    private String name;
    private int priority;
    private String[] keywords;
    private String parent;
    private String child;

    public State(String name, int priority, String[] keywords, String parent, String child) {
        this.name = name;
        this.priority = priority;
        this.keywords = keywords;
        this.parent = parent;
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keywords) {
        this.keywords = keywords;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }
}
