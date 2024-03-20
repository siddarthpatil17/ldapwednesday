package sec.ldap.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
//import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
//import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
//import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
//import org.springframework.security.ldap.userdetails.PersonContextMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Configuration
	public class SecurityConfiguration {

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http.authorizeHttpRequests((authorize) -> authorize.anyRequest().fullyAuthenticated())
					.formLogin(Customizer.withDefaults());

			return http.build();
		}

		@Bean
		public LdapTemplate ldapTemplate() {
			return new LdapTemplate(contextSource());
		}

		@Bean
		public LdapContextSource contextSource() {
			LdapContextSource ldapContextSource = new LdapContextSource();
			ldapContextSource.setUrl("ldap://localhost:8389");
			ldapContextSource.setUserDn("uid=admin,ou=system");
			ldapContextSource.setPassword("secret");
			return ldapContextSource;
		}

		@Bean
		AuthenticationManager authManager(BaseLdapPathContextSource source) {
			LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(source);
			factory.setUserDnPatterns("cn={0},ou=users,ou=system");
			return factory.createAuthenticationManager();
		}
	}

//    @Bean
//    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.authorizeHttpRequests((authz) -> authz.anyRequest().authenticated()).httpBasic(withDefaults());
//		return http.build();
//	}
//
//	private Customizer<HttpBasicConfigurer<HttpSecurity>> withDefaults() {
//		// TODO Auto-generated method stub
//		return null;
//	}
// 
//    @Bean
//    EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
//		EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean = EmbeddedLdapServerContextSourceFactoryBean
//				.fromEmbeddedLdapServer();
//		contextSourceFactoryBean.setPort(0);
//		return contextSourceFactoryBean;
//	}
//
//	@Bean
//	AuthenticationManager ldapAuthenticationManager(BaseLdapPathContextSource contextSource) {
//		LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
//		factory.setUserDnPatterns("uid={0},ou=people");
//		factory.setUserDetailsContextMapper(new PersonContextMapper());
//		return factory.createAuthenticationManager();
//	}
//
//    @Bean
//    DefaultSpringSecurityContextSource contextSource() {
//		return new DefaultSpringSecurityContextSource(Arrays.asList("ldap://localhost:8389/"),
//				"dc=springframework,dc=org");
//	}
}
