# Instrukcja
Aplikacja uruchomiona zostanie na porcie 8080, przykładowy url to http://localhost:8080/...
Dane prelekcji są wczytywane z pliku data.sql, po uruchomieniu aplikacji
Wymagania:
* Docker

## Uruchomienie aplikacji
## Wariant 1 Terminal:
* Przechodzimy do katalogu głównego projektu, gdzie znajduje się plik docker-compose.yml oraz pom.xml
  1. Wykonujemy ```docker-compose up```
   
   ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/6c1003a1-bd0c-483f-8eb4-74aff4b2457b)
 
 2. Następnie ```mvn install```
 
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/37aa190e-f08c-470a-bedf-9608d0c142f1)

3. Końcowo mvn spring-boot:run
   
   ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/1713f2e8-1c5d-4d07-b49b-cd0e8503027f)

## Wariant 2 Intellij:
* Otwieramy projekt w Intellij
  1. Otwieramy plik docker-compose.yml, i następnie klikamy zieloną strzałkę:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/5dc143a1-668d-41db-a8b2-d155f01e0115)
  2. Przechodzimy w pliku ITConferenceApplication.java i ponownie klikamy zieloną strzałkę:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/8a1383f6-8c31-4b9e-943a-0d259d89628d)
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/22a6a5e3-0847-4cc2-a9cb-25d1d516390e)
    
## Adresy URL funkcjonalności:
Prefix ```http://localhost:8080```, zaprezentowane żądania wykonane za pomocą aplikacji Postman.
  1. Pobranie planu konferencji ```GET /lectures```:
    Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/69c36e81-fe57-44e7-9461-bff0b4d57ae6)
    Odpowiedź:
    ```[
    {
        "lectureStart": "2023-06-01T10:00:00",
        "lectureEnd": "2023-06-01T11:45:00",
        "topicPath": "SPRING",
        "freeSpots": 5,
        "duration": 105
    },
    ...
    {
        "lectureStart": "2023-06-01T14:00:00",
        "lectureEnd": "2023-06-01T15:45:00",
        "topicPath": "MICROSERVICES",
        "freeSpots": 5,
        "duration": 105
    }
]```
  2. Pobranie wykładów zarezerowanych przez użytkownika o podanym loginie ```GET /lectures/participant/msocha```:\
    Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/b4c6922d-72f3-442f-956e-8ff098a34129)
    Odpowiedź:
    ```[
    {
        "lectureStart": "2023-06-01T10:00:00",
        "lectureEnd": "2023-06-01T11:45:00",
        "topicPath": "DATABASES",
        "freeSpots": 4,
        "duration": 105
    }
      ]```
  3. Pobranie statystyk dla pojedyńczego wykładu ```GET /lectures/statistics```:
    Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/da5258c7-ce33-4f0a-97d5-42c94ed25edb)
    Odpowiedź:
        ```[
        {
            "lectureStart": "2023-06-01T10:00:00",
            "lectureEnd": "2023-06-01T11:45:00",
            "duration": 105,
            "topicPath": "SPRING",
            "taken": 0,
            "totalSpots": 5,
            "percentageOfTakenSpots": "0.0%"
        },
        ...
        {
            "lectureStart": "2023-06-01T10:00:00",
            "lectureEnd": "2023-06-01T11:45:00",
            "duration": 105,
            "topicPath": "DATABASES",
            "taken": 1,
            "totalSpots": 5,
            "percentageOfTakenSpots": "20.0%"
        }
    ]```
  4. Pobranie statystyk dla ścieżek tematycznych ```GET /lectures/path-statistics```:
    Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/d5fed310-1501-4822-8a52-8aa9f89cb0f1)
    Odpowiedź:
      ```[
      {
          "topicPath": "SPRING",
          "takenSpots": 0,
          "availableSpots": 15,
          "takenPercentage": "0.0%"
      },
      {
          "topicPath": "DATABASES",
          "takenSpots": 1,
          "availableSpots": 15,
          "takenPercentage": "6.666666666666667%"
      },
      {
          "topicPath": "MICROSERVICES",
          "takenSpots": 0,
          "availableSpots": 15,
          "takenPercentage": "0.0%"
      }
    ]```
  5. Zarezerwowanie miejsca na wykładzie przez użytkownika ```POST /participants/book-lecture, request body {"id": "LECTURE_ID", "login": "LOGIN", "email": "EMAIL" }```:
    Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/b35398dc-0e36-4cf6-8a5c-b5dd39cde7e4)
    Odpowiedź:
    W przypadku pozytywnego przypadku:
      * Otrzymujemy kod 204 No Content
    Przykładowy błąd:
          ```{
        "timestamp": "2023-06-08T15:17:58.401+00:00",
        "status": 409,
        "error": "Conflict",
        "message": "Email already in use",
        "path": "/participants/book-lecture"
        }```,
        ```{
          "timestamp": "2023-06-08T15:19:29.869+00:00",
          "status": 409,
          "error": "Conflict",
          "message": "Given login is taken",
          "path": "/participants/book-lecture"
        }```
  6. Anulowanie rezerwacji przez użytkownika ```DELETE /participants/{PARTICIPANT_LOGIN}/cancel-lecture?lectureId={LECTURE_ID}```: 
    Żądanie:
      ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/27dc3330-d590-4c02-8468-eb67f3c3b482)
    Odpowiedź:
    W przypadku pozytywnego przypadku:
      * Otrzymujemy kod 204 No Content
  7. Aktualizacja adresu email przez użytkownika ```PUT /participants/{PARTICIPANT_LOGIN}/update-email?newEmail={PARTICIPANT_NEW_EMAIL}```:
    Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/8af3960d-6a09-4def-aadd-35fe0c5b3024)
    Odpowiedź:
    W przypadku pozytywnego przypadku:
      * Otrzymujemy kod 204 No Content
  8. Pobranie listy wszystkich dostępnych użytkowników ```GET /participants```:
  Żądanie:
    ![image](https://github.com/Msocha19/Sii-IT-Conference/assets/101000339/37caa487-3436-4cc0-8824-68da1ac812b9)

  Odpowiedź:
    ```[
      {
          "id": 1,
          "login": "user",
          "email": "user@gmail.local"
      }
    ]```
