package com.dragonfly95.demo.security;

import com.dragonfly95.demo.dto.ResultDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    ObjectMapper om = new ObjectMapper();
    response.setStatus(HttpServletResponse.SC_OK);
    response.getWriter().print(om.writeValueAsString(ResultDto.success()));
    response.getWriter().flush();
  }
}
