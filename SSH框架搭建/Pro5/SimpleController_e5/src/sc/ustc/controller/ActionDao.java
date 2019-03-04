package sc.ustc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionDao {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
