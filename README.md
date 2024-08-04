# Spring
# Spring-7
/*
1. Переделать строки RoleName в сущность Role:
   1.1 Создать отдельную таблицу Role(id, name)
   1.2 Связать User <-> Role отношением ManyToMany
2. После п.1 подправить формирование ролей в MyCustomUserDetailsService
   В SecurityFilterChain настроить:
   3.1 Стандартная форма логина
   3.2 Страницы с проектами доступы пользователям с ролью admin
   3.2 Страницы с таймшитами доступы пользователям с ролью user
   3.3 REST-ресурсы доступны пользователям с ролью rest
3. 
   **** Для rest-ресурсов НЕ показывать форму логина.
   Т.е. если пользователь не авторизован, то его НЕ редиректит на форму логина, а сразу показывается 401.
   Для авторизации нужно отдельно получить JSESSIONID и подставить в запрос.
   */