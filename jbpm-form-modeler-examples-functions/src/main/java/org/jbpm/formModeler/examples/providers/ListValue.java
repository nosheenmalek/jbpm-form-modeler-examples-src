package org.jbpm.formModeler.examples.providers;

public class ListValue {
    String id;
    String text;
    String description;

    public ListValue(String id, String text, String description) {
        this.id = id;
        this.text = text;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

