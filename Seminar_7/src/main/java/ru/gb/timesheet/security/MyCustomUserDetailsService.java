package ru.gb.timesheet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.gb.timesheet.model.User;
import ru.gb.timesheet.repository.RoleRepository;
import ru.gb.timesheet.repository.UserRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {

  // {SpringSecurity}
  // ... @ UserDetailsService userDetailsService
  // User[admin] --login--> [UserDetails userDetailsService.loadUserByUsername(login)]
  // [UserDetails -> SecurityContext]

  // В нашем случае юзеры хранятся в БД в таблице UserRepository
  // Строго говоря, в этой реализации UserDetailsService можно загружать данные о пользователе из любого источника:
  // внешний auth-service, ldap-сервер, ...
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userRepository.findByLogin(username)
      .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    List<String> userRoles = roleRepository.findUserRolesByUserId(user.getId());
    return new org.springframework.security.core.userdetails.User(
            username,
            user.getPassword(),
            userRoles.stream().map(SimpleGrantedAuthority::new).toList()

    );
  }
}
