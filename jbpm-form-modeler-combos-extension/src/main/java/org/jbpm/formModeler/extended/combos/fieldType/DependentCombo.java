package org.jbpm.formModeler.extended.combos.fieldType;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Template;
import org.jbpm.formModeler.api.client.FormRenderContextManager;
import org.jbpm.formModeler.core.fieldTypes.CustomFieldType;
import org.jbpm.formModeler.core.processing.FormProcessor;
import org.jbpm.formModeler.core.processing.FormStatusData;
import org.jbpm.formModeler.extended.combos.DependentComboValuesProvider;
import org.jbpm.formModeler.extended.combos.DependentComboValuesProviderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DependentCombo implements CustomFieldType {
    private Logger log = LoggerFactory.getLogger( DependentCombo.class );

    @Inject
    protected DependentComboValuesProviderManager comboValuesProviderManager;

    @Inject
    protected FormRenderContextManager formRenderContextManager;

    @Inject
    protected FormProcessor formProcessor;

    @Override
    public String getDescription( Locale locale ) {
        return "Dependent Combo";
    }

    @Override
    public String getShowHTML( Object value, String fieldName, String namespace, boolean required, boolean readonly, String... params ) {
        String result = "<div>";

        if (value != null) result += value;

        result += "</div>";

        return result;
    }

    @Override
    public String getInputHTML( Object value, String fieldName, String namespace, boolean required, boolean readonly, String... params ) {
        String rootField = params[0];
        DependentComboValuesProvider provider = comboValuesProviderManager.getProvider( params[ 1 ] );

        String theValue = ( String ) value;

        String str = "";
        try {
            FormStatusData status = formProcessor.read(formRenderContextManager.getRootContext( namespace ).getForm(), namespace);

            String rootValue = ( String ) status.getCurrentValue( rootField );

            Map<String, String> elements = null;

            if (provider != null) elements = provider.getValues( rootValue );
            if ( elements == null ) elements = new HashMap<String, String>(  );

            Map<String, Object> context = new HashMap<String, Object>();

            context.put( "readonly", readonly );
            context.put( "elements", elements );
            context.put( "fieldName", fieldName );
            context.put( "value", theValue);

            InputStream src = this.getClass().getResourceAsStream("input.ftl");
            freemarker.template.Configuration cfg = new freemarker.template.Configuration();
            BeansWrapper defaultInstance = new BeansWrapper();
            defaultInstance.setSimpleMapWrapper(true);
            cfg.setObjectWrapper(defaultInstance);
            cfg.setTemplateUpdateDelay(0);
            Template temp = new Template(fieldName, new InputStreamReader(src), cfg);
            StringWriter out = new StringWriter();
            temp.process(context, out);
            out.flush();
            str = out.getBuffer().toString();
        } catch (Exception e) {
            log.warn("Failed to process template for field '{}'", fieldName, e);
        }
        return str;
    }

    @Override
    public Object getValue( Map requestParameters, Map requestFiles, String fieldName, String namespace, Object previousValue, boolean required, boolean readonly, String... params ) {
        String[] paramValue = (String[]) requestParameters.get(fieldName);
        if (paramValue == null || paramValue.length == 0) return null;

        return paramValue[0];
    }
}
