package com.egg.biblioteca;

import com.egg.biblioteca.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter {

    @Autowired
    public UsuarioServicio usuarioServicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio)
                //Una vez que se encuentre el usuario se codifica la contraseña
                //COMO? se registra usuario. Se autentifica a traves de userDatailsService y pasamos el codificardor
                //la contraseña es un texto plano que es Spring Security va a encriptar a traves de este Encoder
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                //conf para loguearse. pertenece al form de login
                .and().formLogin()
                //URL del form de login
                .loginPage("/login")
                //URL con la que SpringSecurity va autentificar usuario. Debe coincidir con el action de logueo, que envie este dato
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                //si se genera un login correcto se dirige a esta vista. INICIO = indez con mas permisos por que el usuario se logueo
                .defaultSuccessUrl("/inicio")
                .permitAll()
                .and().logout()
                //para cerrar la sesion
                .logoutUrl("/logout")
                //si el logout es exitoso
                .logoutSuccessUrl("/login")
                .permitAll()
                .and().csrf()
                .disable();

    }
}
