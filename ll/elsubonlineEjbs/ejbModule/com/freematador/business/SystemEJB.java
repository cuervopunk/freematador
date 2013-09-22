package com.freematador.business;

import java.util.List;

import javax.ejb.Local;

import com.freematador.domain.Parameter;

@Local
public interface SystemEJB {

	List<Parameter> getParameters();

	void saveParameters(List<Parameter> parameters);

	Parameter getParameter(String parameterName);

}
