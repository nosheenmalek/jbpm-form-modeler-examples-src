package org.jbpm.formModeler.examples.providers;

import org.jbpm.formModeler.api.client.FormRenderContext;
import org.jbpm.formModeler.api.client.FormRenderContextManager;
import org.jbpm.formModeler.api.model.RangeProvider;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FakeTaskVariablesProvider implements RangeProvider {
    @Inject
    FormRenderContextManager formRenderContextManager;

    ArrayList<ListValue> valuesList = new ArrayList<ListValue>();

    @PostConstruct
    protected void init() {
        valuesList.add(new ListValue("1", "value 1", "this is the description for value 1"));
        valuesList.add(new ListValue("2", "value 2", "this is the description for value 2"));
        valuesList.add(new ListValue("3", "value 3", "this is the description for value 3"));
        valuesList.add(new ListValue("4", "value 4", "this is the description for value 4"));
    }
    @Override
    public String getType() {
        return "{my_fake_list}";
    }

    @Override
    public Map<String, String> getRangesMap(String namespace) {
        FormRenderContext context = formRenderContextManager.getRootContext(namespace);

        /*
            I'm going to add the valueList to the properties map to simulate that the context has a List variable
            like your example, please don't do this on real environment, this is a EXAMPLE.
        */
        context.getInputData().put("myFakeProperty", valuesList);

        Map<String, String> result = new HashMap<String, String>();

        for (ListValue value : valuesList) {
            result.put(value.getId(), value.getText());
        }

        return result;
    }
}
