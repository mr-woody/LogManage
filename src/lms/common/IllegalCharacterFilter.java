package lms.common;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

public class IllegalCharacterFilter extends StrutsPrepareAndExecuteFilter {
	
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) 
			throws IOException, ServletException {
		super.doFilter(req, res, chain);
	}
}
