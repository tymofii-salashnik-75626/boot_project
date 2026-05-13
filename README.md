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



## Wdrażanie na platformie Azure App Service (Oleksandr Mandziuk 75381)

1. Utworzyć darmowe konto studenckie Microsoft Azure (Azure for Students).
2. Zrobić "Fork" oryginalnego repozytorium projektu na własne konto GitHub. Jest to wymagane, aby uzyskać pełne uprawnienia do konfiguracji automatycznego wdrażania (CI/CD) za pomocą GitHub Actions.
3. W pasku wyszukiwania portalu Azure wpisać "App Services" i przejść do usługi.
4. Kliknąć przycisk [Create] -> [Web App], aby uruchomić kreator konfiguracji.
5. Proces tworzenia według kroków kreatora:
1. Zakładka Basics
1. Subscription - wybieramy Azure for Students.
2. Resource Group - klikamy Create new i wpisujemy nazwę grupy (np. BinaryBearsGroup).
3. Name - wpisujemy nazwę aplikacji: boot-project-binarybears (i zapamiętujemy, to będzie adres strony: (https://hello-binarybears-fabda0g2evdrfmfs.polandcentral-01.azurewebsites.net/).
4. Publish - wybieramy Code.
5. Runtime stack - wybieramy Java 21 (zgodnie z wersją projektu).
6. Java web server stack - wybieramy Java SE (ponieważ Spring Boot ma wbudowany serwer).
7. Operating System - wybieramy Linux.
8. Pricing plan - upewniamy się, że wybrany jest darmowy plan Free F1 (nie zamierzamy płacić).
9. Klikamy Next i przechodzimy do Review + create, a następnie Create.
6. Czekamy na zakończenie tworzenia zasobu (komunikat "Deployment succeeded") i klikamy "Go to resource".
7. Konfiguracja automatycznego wdrażania w zakładce Deployment Center:
1. Z menu po lewej stronie wybieramy Deployment Center.
2. Source - wybieramy GitHub.
3. Autoryzujemy połączenie z kontem GitHub (jeśli wymaga).
4. Organization - wybieramy swój profil GitHub z forkiem projektu.
5. Repository - wybieramy nasze repozytorium z kodem (boot_project).
6. Branch - wybieramy main.
7. Authentication type - zostawiamy User-assigned identity.
8. Klikamy Save na górze ekranu.
8. Rozwiązywanie napotkanych problemów (Troubleshooting):
1. Początkowa próba wdrożenia bez forka, za pomocą opcji "External Git", zakończyła się błędem "parking page".
2. Wewnętrzny system wdrożeniowy Azure (KuduSync) ograniczał się do zwykłego kopiowania plików źródłowych, nie uruchamiając kompilacji Maven (brak gotowego pliku .jar).
3. Rozwiązaniem było zastosowanie natywnej integracji z GitHubem (opisanej w kroku 7). Spowodowało to wygenerowanie pliku workflow na GitHubie, który przejął proces kompilacji i automatycznie przesłał działający plik .jar bezpośrednio na serwer Azure.
9. Po zakończeniu budowania (GitHub Actions), w zakładce "Overview" w Azure sprawdzamy status (powinien być "Running").
10. Klikamy przycisk "Browse", dopisujemy /hello do adresu w przeglądarce i sprawdzamy poprawność działania aplikacji.


### Wnioski końcowe

1. Platforma Azure App Service umożliwia wygodne wdrażanie aplikacji Spring Boot bez konieczności ręcznej konfiguracji serwera.
2. Integracja z GitHub Actions pozwala na pełną automatyzację procesu CI/CD oraz automatyczne budowanie projektu Maven.
3. Największą zaletą platformy jest możliwość szybkiego publikowania aplikacji bez konieczności zarządzania infrastrukturą.
4. Początkowe problemy z wdrażaniem przez External Git pokazały, że poprawna konfiguracja pipeline CI/CD jest kluczowa dla działania aplikacji Java w środowisku cloud.
5. Dzięki wykorzystaniu GitHub Actions aplikacja była automatycznie kompilowana i publikowana po każdej zmianie w repozytorium.
6. Platforma Azure App Service dobrze sprawdza się w projektach edukacyjnych oraz nowoczesnych aplikacjach webowych opartych na Spring Boot.
7. Po zakończeniu wdrożenia aplikacja była publicznie dostępna w internecie i poprawnie obsługiwała endpoint `/hello`.



## Wdrażanie aplikacji Spring Boot na platformie Railway (Mykola Havryliuk 77332)

### Przygotowanie aplikacji do wdrożenia

1. Sprawdzenie poprawności działania aplikacji lokalnie
    1. Uruchomienie projektu w środowisku IntelliJ IDEA
    2. Weryfikacja poprawności działania endpointu `/hello`
    3. Sprawdzenie zwracanego JSON-a z numerami indeksów członków zespołu
    4. Test działania aplikacji pod adresem:
       `http://localhost:8080/hello`

2. Konfiguracja środowiska Java
    1. Instalacja oraz konfiguracja JDK 25 (Eclipse Temurin)
    2. Sprawdzenie wersji Java komendą:
       `java -version`
    3. Weryfikacja poprawności konfiguracji Maven:
       `mvn -version`

3. Budowanie aplikacji
    1. W terminalu wykonujemy:
       `mvn clean package`
    2. Po poprawnym zbudowaniu projektu w katalogu `target/` został wygenerowany plik:
       `demo-0.0.1-SNAPSHOT.jar`

### Wdrażanie aplikacji na Railway (Mykola Havryliuk 77332)

1. Rejestracja oraz logowanie do platformy Railway
    1. Przechodzimy na stronę https://railway.app/
    2. Logujemy się przy pomocy konta GitHub

2. Przygotowanie repozytorium GitHub
    1. Oryginalne repozytorium należało do innego członka zespołu
    2. Railway wymaga dostępu właściciela repozytorium
    3. Wykonałem Fork projektu na własne konto GitHub
    4. Po wykonaniu Fork platforma Railway mogła uzyskać dostęp do kodu źródłowego

3. Tworzenie projektu Railway
    1. Klikamy przycisk **New Project**
    2. Wybieramy opcję:
       `Deploy from GitHub repo`
    3. Wybieramy wcześniej sforkowane repozytorium
    4. Railway automatycznie wykrył projekt Spring Boot oparty na Mavenie

4. Automatyczny proces budowania aplikacji
    1. Railway automatycznie uruchomił:
       `mvn clean package`
    2. Platforma pobrała wszystkie zależności Maven
    3. Następnie został utworzony plik `.jar`
    4. Railway uruchomił aplikację w środowisku chmurowym

5. Konfiguracja aplikacji
    1. Spring Boot domyślnie działa na porcie `8080`
    2. Railway automatycznie wykrył port aplikacji
    3. Nie była wymagana dodatkowa konfiguracja portów
    4. Platforma wygenerowała publiczny adres URL

### Publiczny adres aplikacji

Aplikacja została pomyślnie wdrożona i działa pod adresem:

`https://boot-project-binarybears.up.railway.app`

Po wejściu na stronę aplikacja zwraca poprawny wynik JSON zawierający numery indeksów członków zespołu.

### Napotkane problemy i rozwiązania

#### Problem — brak dostępu Railway do repozytorium

1. Railway nie mógł pobrać kodu źródłowego
2. Powodem był brak odpowiednich uprawnień do repozytorium należącego do innego członka zespołu
3. Rozwiązanie:
    1. wykonanie Fork repozytorium
    2. podłączenie własnego repozytorium GitHub do Railway


## Porównanie platform i wnioski zespołu

Nasz zespół przetestował trzy różne platformy do wdrożenia aplikacji Spring Boot: AWS Elastic Beanstalk, Azure App Service oraz Railway.

### 1. AWS Elastic Beanstalk
* Zalety: Platforma oferuje darmowe środowisko w ramach Free Tier przy użyciu instancji t3.micro. Obsługuje standardowe środowiska, w tym Corretto 21.
* Trudności: Proces wymaga przejścia przez wieloetapowy kreator konfiguracji ról dostępu oraz sieci. Wdrożenie opierało się na ręcznym wgraniu wcześniej zbudowanego lokalnie pliku .jar. Największym technicznym niuansem była konieczność ręcznego dodania zmiennej środowiskowej SERVER_PORT z wartością 5000, ponieważ Beanstalk oczekuje ruchu na tym porcie, podczas gdy Spring Boot domyślnie startuje na porcie 8080.

### 2. Azure App Service
* Zalety: Posiada wygodny darmowy plan studencki Free F1. Oferuje bardzo zaawansowaną natywną integrację z GitHub Actions, co pozwala na pełną automatyzację procesu CI/CD (automatyczna kompilacja Mavena i przesyłanie pliku .jar przy każdej zmianie w kodzie).
* Trudności: Opcja wdrożenia poprzez "External Git" skutkowała błędem, ponieważ wewnętrzny system KuduSync nie kompilował projektu Maven, a jedynie kopiował pliki. Poprawna konfiguracja z GitHub Actions wymagała pełnych uprawnień, co wymusiło zrobienie "Forka" oryginalnego repozytorium.

### 3. Railway
* Zalety: Zdecydowanie najszybsze rozwiązanie oferujące tzw. Zero-Config Deployment. Platforma po podłączeniu konta automatycznie rozpoznała projekt Maven, pobrała zależności, zbudowała aplikację i bez dodatkowej konfiguracji zmapowała domyślny port 8080. Proces trwał zaledwie kilka minut.
* Trudności: Podobnie jak w Azure, wystąpił problem z uprawnieniami. Railway wymaga dostępu właściciela do repozytorium, co zablokowało pierwsze wdrożenie i zmusiło do wykonania Forka projektu na własne konto GitHub.

### Ostateczne podsumowanie
* Najłatwiejsza platforma: Railway. Zapewnił najszybsze uruchomienie aplikacji z zerową koniecznością ręcznej konfiguracji serwera czy portów.
* Najtrudniejsza platforma: Azure App Service na początku stwarzał problemy przez brak wsparcia budowania w KuduSync. Z kolei AWS Elastic Beanstalk był najbardziej manualny pod kątem wgrywania pliku oraz wymagał pilnowania nadpisywania portów.
* Rekomendacja: Dla małych projektów edukacyjnych nastawionych na szybki wynik, Railway jest bezkonkurencyjny. Jednak w przypadku chęci poznania profesjonalnych narzędzi CI/CD, świetnie sprawdził się zautomatyzowany pipeline GitHub Actions z Azure App Service.



        

