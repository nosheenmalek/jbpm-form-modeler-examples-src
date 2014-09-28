package org.jbpm.formModeler.examples.functions;

import org.jbpm.formModeler.api.client.FormRenderContext;
import org.jbpm.formModeler.api.client.FormRenderContextManager;
import org.jbpm.formModeler.core.processing.formProcessing.replacers.FormulaReplacementContext;
import org.jbpm.formModeler.examples.providers.ListValue;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;

@Dependent
public class MyFunctions {
    @Inject
    FormRenderContextManager contextManager;

    /**
     * This method will calculate the description to show
     * @param ctx The Context of the formula
     * @param elementId The value of the field that holds the id of the element
     * @return
     */
    public String getDescription(FormulaReplacementContext ctx, String elementId) {
        FormRenderContext context = contextManager.getRootContext(ctx.getNamespace());

        if (context != null) {
            // Obtain my fakeProperty value and get the element with the given id
            List<ListValue> list = (List<ListValue>) context.getInputData().get("myFakeProperty");
            for (ListValue value : list) {
                if (value.getId().equals(elementId)) return value.getDescription();
            }
        }
        return "";
    }
}
