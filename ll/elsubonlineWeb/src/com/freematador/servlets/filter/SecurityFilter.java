package com.freematador.servlets.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.freematador.presentation.LoginManagedBean;

/**
 * Servlet Filter implementation class SecurityFilter
 */
@WebFilter("/security")
public class SecurityFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SecurityFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpRequest.getSession();
        String url = httpRequest.getServletPath();
        
        if(url.contains("user") || url.contains("admin")) {
        	System.out.println("Private URL detected: "+url);
        	if(httpSession!=null) {
                LoginManagedBean login = (LoginManagedBean) httpSession.getAttribute("loginManagedBean");
		        if (null == login || !login.isLoggedIn()) {
		        	httpResponse.sendRedirect(request.getServletContext().getContextPath()+"/index.xhtml");
		        }else if(url.contains("admin") && !login.isAdmin()) {
		        	httpResponse.sendRedirect(request.getServletContext().getContextPath()+"/index.xhtml");
		        }
        	}else{
        		httpResponse.sendRedirect(request.getServletContext().getContextPath()+"/index.xhtml");
        	}
        }		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
