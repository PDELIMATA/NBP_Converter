Przed uruchomieniem aplikacji konieczne jest utworzenie bazy danych MySQL na porcie 3306 z nazwą użytkownika "root" oraz hasłem "root".

W celu uruchomienia aplikacji należy w terminalu wprowadzić:

./mvnw clean install

./mvnw spring-boot:run

Po uruchomieniu aplikacji należy wejść w przeglądarce pod adres: "localhost:8080/".

Aplikacja przelicza kwotę z USD na PLN w oparciu o API NPB na dany dzień, zapisuje do pliku XML oraz bazy danych i następnie wyświetla dane z zapisanej bazy danych w odpowiednim formacie.

 
Opis działania:

Firma kupiła 3 komputery za kwotę 1234 USD.

- komputer 1 – kwota 345 USD

- komputer 2 – kwota 543 USD

- komputer 3 – kwota 346 USD

APlikacja korzysta ze strony NBP (wykorzystuje JSON i odpyuje serwis NBP pod adresem: http://api.nbp.pl), gdzie parametrem jest data przewalutowania i przelicza koszt zakupu komputerów na PLN (dokumentacja i API pod adresem http://api.nbp.pl).

 

Po przeliczeniu wynik zostaje zwrócony do użytkownika i dane zostaną zapisane w bazie danych, oraz w pliku XML

 

Format pliku XML:

<faktura>

  <komputer>

      <nazwa></nazwa>

      <data_ksiegowania></data_ksiegowania>

      <koszt_USD></koszt_USD>

      <koszt_PLN></koszt_PLN>

  </komputer>

  <komputer>

      <nazwa></nazwa>

      <data_ksiegowania></data_ksiegowania>

      <koszt_USD></koszt_USD>

      <koszt_PLN></koszt_PLN>

  </komputer>

</faktura>

 

Tabela w bazie danych:

nazwa | data_ksiegowania | koszt_USD | koszt_PLN
