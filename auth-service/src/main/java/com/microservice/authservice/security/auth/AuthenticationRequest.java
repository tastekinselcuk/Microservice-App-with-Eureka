package com.microservice.authservice.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest { //AuthenticationController'da yazdığımız doğrulama kısmı için kullanılacak kullanıcı bilgisi nesnesi oluşturuldu.

	private String email;
	
	String password;
}