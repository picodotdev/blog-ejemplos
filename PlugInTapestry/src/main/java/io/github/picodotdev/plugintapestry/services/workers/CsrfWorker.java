package io.github.picodotdev.plugintapestry.services.workers;

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.ApplicationStateManager;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;

import io.github.picodotdev.plugintapestry.services.annotation.Csrf;
import io.github.picodotdev.plugintapestry.services.exceptions.CsrfException;
import io.github.picodotdev.plugintapestry.services.sso.Sid;

public class CsrfWorker implements ComponentClassTransformWorker2 {

	 private Request request;
	 private ApplicationStateManager manager;

	 public CsrfWorker(Request request, ApplicationStateManager manager) {
		  this.request = request;
		  this.manager = manager;
	 }

	 public void transform(PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model) {
		  MethodAdvice advice = new MethodAdvice() {
				public void advise(MethodInvocation invocation) {

					 String rsid = request.getParameter("t:sid");					 
					 Sid sid = manager.getIfExists(Sid.class);

					 if (sid != null && sid.isValid(rsid)) {
						  invocation.proceed();
					 } else {
						  invocation.setCheckedException(new CsrfException("El parámetro sid de la petición no se corresponde con el sid de la sesión. Esta petición no es válida (Posible ataque CSRF)."));
						  invocation.rethrow();
					 }
				}
		  };

		  for (PlasticMethod method : plasticClass.getMethodsWithAnnotation(Csrf.class)) {
				method.addAdvice(advice);
		  }
	 }
}