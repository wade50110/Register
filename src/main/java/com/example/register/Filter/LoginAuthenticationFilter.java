package com.example.register.Filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

//使用者登入後，會經過的 Filter
//因為改成 Restful 的形式，因此會先判斷 request 過來的形式要為POST方法，接著取得 request 的輸入流，
//這個就是我們的 body 內容，因此可以將它轉成 Map 格式
public class LoginAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	System.out.println("LoginAuthenticationFilter:");
    	if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		ObjectMapper mapper = new ObjectMapper();
		UsernamePasswordAuthenticationToken authRequest = null;
		try (InputStream stream = request.getInputStream()) {
			Map<String, String> body = mapper.readValue(stream, Map.class);
			authRequest = new UsernamePasswordAuthenticationToken(body.get("account"), body.get("password"));
		} catch (IOException e) {
			e.printStackTrace();
			authRequest = new UsernamePasswordAuthenticationToken("", "");
		} finally {
			setDetails(request, authRequest);
			return this.getAuthenticationManager().authenticate(authRequest);
		}
        
    }
}

