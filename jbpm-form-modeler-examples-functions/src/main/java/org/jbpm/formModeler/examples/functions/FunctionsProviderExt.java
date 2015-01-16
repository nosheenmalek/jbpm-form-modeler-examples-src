package org.jbpm.formModeler.examples.functions;

import bsh.EvalError;
import bsh.Interpreter;
import org.jbpm.formModeler.core.processing.formProcessing.FunctionsProvider;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

@Specializes
@Dependent
public class FunctionsProviderExt extends FunctionsProvider {
    @Inject
    MyFunctions myFunctions;

    @Override
    public void populate(Interpreter interpreter) throws EvalError {
        super.populate(interpreter);
        //Declaring a new Functions class
        interpreter.set("MyFunctions", myFunctions);
    }
}
