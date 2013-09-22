package com.freematador.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.freematador.domain.Parameter;
import com.freematador.persistence.ParameterDAO;

/**
 * Session Bean implementation class SystemEJBBean
 */
@Stateless
@LocalBean
public class SystemEJBBean implements SystemEJB {
	@EJB
	private ParameterDAO parameterDAO;
	
    /**
     * Default constructor. 
     */
    public SystemEJBBean() {
    }
    
    @Override
    public List<Parameter> getParameters() {
    	List<Parameter> parameters = new ArrayList<Parameter>();
    	parameters = parameterDAO.findAll();
    	return parameters;
    }

	@Override
	public void saveParameters(List<Parameter> parameters) {
		for(Parameter p : parameters) {
			Parameter refreshedParameter = parameterDAO.findById(p.getId());
			refreshedParameter.setValue(p.getValue());
			parameterDAO.update(refreshedParameter);
		}
	}
	
	@Override
	public Parameter getParameter(String parameterName) {
		Parameter parameter = parameterDAO.findByName(parameterName);
		return parameter;
	}

}
