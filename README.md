## Tworzenie i budowanie aplikacji Spring Boot (Tymofii Salashnik 75626)

### Generowanie szablonu aplikacji przez Spring Initializr

1. Przejdź na stronę https://start.spring.io/
2. W sekcji Project wybierz Maven
3. Language - Java  
4. Spring Boot - wybieramy najnowszą wersję (np. 4.0.6)
5. Project Metadata/Group - pl.vistula.webintro (można wpisać dowolną nazwę)
6. Project Metadata/artifact - demo - nazwa aplikacji
7. Dependencies - dodajemy Spring Web
8. Generate

### Rozbudowa aplikacji

1. Utwórzamy klasę opisującą zwracany wynik (HelloResponse)
2. Utwórzamy klasę kontrolera REST (HelloWorldController) i dodajemy adnotację @RestController
3. Utwórzamy metodę zwracającą obiekt HelloResponse i dodajemy do niej adnotację @GetMapping("/hello")
4. Robimy pakiet `mvn package`
5. Sprawdzamy, czy aplikacja działa:
  1. `java -jar target\demo-0.0.1-SNAPSHOT.jar`
  2. Otwórzamy w przeglądarce adres: http://localhost:8080/hello

## Wdrażanie na AWS Elastic Beanstalk (Tymofii Salashnik 75626)

1. tworzyć konto w taryfie Free Tier
2. W pasku wyszukiwania wpisać Beanstalk i przejść do usługi
3. Na stronie głównej Beanstalk kliknąć przycisk [Create Application](https://eu-north-1.console.aws.amazon.com/elasticbeanstalk/home?region=eu-north-1#/create-environment) — konkretnie tutaj, aby uruchomił się kreator tworzenia
4. Proces tworzenia według kroków kreatora:
    1. Strona *Configure environment*
        1. Environment tier - ostawiamy Web server environment, ponieważ mamy aplikację webową
        2. Application name - wpisujemy nazwę aplikacji – SpringBootProject
        3. Domain - wpisujemy springboot-75626-75381-77332 (i zapamiętujemy co wyszło, to będzie adres strony), czyli: http://springboot-75626-75381-77332.eu-north-1.elasticbeanstalk.com
        4. Platform - Java
        5. Platform branch - wybieramy Corretto 21, czyli 21. wersję Javy (aplikacja jest zbudowana dla niej, można też 25., ale 21. to LTS)
        6. Application code - wybieramy Upload your code, ponieważ będziemy wrzucać własny plik jar
        7. Version label - ja wpisywałem numer commita w git (ea083dd)
        8. Source code origin - Local file i tutaj wgrywamy nasz zbudowany plik jar
        9. Configuration presets - wybieramy Single instance (free tier eligible), ponieważ za nic nie zamierzamy płacić :)
        10. Next 
    2. Strona *Configure service access*
        1. Service role - klikamy create role i przeklikujemy wszystko domyślnie
        2. EC2 instance profile - analogicznie klikamy create role i przeklikujemy wszystko domyślnie
        3. EC2 key pair - nie ustawiamy, nie zamierzamy wchodzić do instancji
    3. Strona *Set up networking, database, and tags*
        1. VPC - wybieramy domyślne z listy
        2. Public IP address - aby został przydzielony adres IP, pod którym będzie dostępna nasza instancja
        3. Instance subnets - wybieramy wszystkie
        4. Database - *NIE włączamy*,nie potrzebujemy
    4. Strona *Configure instance traffic and scaling*
        1. Environment type zostawiamy jako Single Instance
        2. Instance types ostawiamy TYLKO t3.micro, pozostałe wykraczają poza Free Tier.
        3. Architecture - zostawiamy x86_64.
        4. Nic więcej nie ruszamy
    5. Strona *Configure updates, monitoring, and logging*
        1. Health reporting - ustawiamy Basic
        2. W Email notifications wpisujemy e-mail, aby otrzymywać powiadomienia o WAŻNYCH zdarzeniach
        3. W Environment properties wpisujemy SERVER_PORT z wartością 5000, ponieważ Beanstalk oczekuje aplikacji na porcie 5000, a SpringBoot na 8080. Podając to w środowisku, mówimy SpringBootowi, aby działał na porcie 5000
        4. Nic więcej nie ruszamy
    6. Strona *Review*
        1. Sprawdzamy, co skonfigurowaliśmy
        2. Klikamy Create
5. Widzimy komunikat "Elastic Beanstalk is launching your environment. This will take a few minutes." i czekamy, aż wszystko wystartuje
6. W przyszłości wchodzimy na stronę Beanstalk [tutaj](https://eu-north-1.console.aws.amazon.com/elasticbeanstalk/)

        

