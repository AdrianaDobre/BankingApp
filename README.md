# Banking App
Tema proiectului : Aplicatie Bancara

### Obiecte:
 - Account
 - SavingsAccount (mosteneste Account)
 - Card
 - MasterCard (mosteneste Card)
 - VisaCard (mosteneste Card)
 - Transaction
 - Person
 - Client (mosteneste Person)

### Interogari:
 - addClient : putem crea un client introdus de la tastatura
 - addClientsFromCsv : citeste clientii din CSV
 - showClient : afiseaza detaliile clientului cu id-ul introdus de la tastatura
 - showClients : afiseaza detaliile despre toti clientii bancii
 - deleteClient : sterge un client cu toate datele sale(cards, accounts,tranzactions,savings accounts)
 - updateClient : actualizeaza nr. de telefon,adresa si adresa de email
 - createAccount : putem crea un cont bancar introdus de la tastatura
 - addAccountsFromCsv : citeste conturile din CSV
 - deleteAccount : putem inchide un cont bancar cu id-ul introdus de la tastatura (se sterg si cardurile,tranzactiile)
 - showAccounts : afiseaza detaliile despre toate conturile clientului cu id-ul introdus de la tastatura
 - createCard : puteam crea un card introdus de la tastatura
 - addCardsFromCsv : citeste cardurile din CSV
 - deleteCard : putem sterge un card al carui id este dat de la tastatura
 - updateCard : actualizeaza data expirarii in cazul in care cardul expira anul acesta (daca esti de acord)
 - showCards : afiseaza detaliile despre toate cardurile unui cont
 - createTransaction : putem transfera bani de la un cont la altul
 - addTransactionsFromCsv : citeste tranzactiile din CSV
 - showTransactions : afiseaza istoricul tranzactiilor unui cont
 - createSavingsAccount : putem crea un cont de economii introdus de la tastatura
 - showSavingsAccounts : afiseaza conturile de economii ale unui client introdus de la tastatura
 - updateSavingsAccount : actualizeaza data inceperii, data inchiderii si suma pe care vrei sa o depui a unui cont de economii
 - deleteSavingsAccount : inchide un cont de economii

 ### Baza de date
 In baza de date se afla tabele pentru:
 - clienti (nume tabel: client)
 - conturi (nume tabel: account)
 - conturi de economii (nume tabel: savingsaccount)
 - carduri (Master Card sau Visa card) (nume tabel: card)

### Operatii CRUD
 Am creat operatii de tip CRUD:
 - read (toate se gasesc in metoda loadDataFromDb)
 - create (se gasesc in metodele addClient, createAccount, createCard,createSavingsAccount)
 - update (se gasesc in metodele updateClient, updateCard, updateSavingsAccount, createTransaction(in momentul realizarii unei tranzactii se actualizeaza soldul fiecarui cont implicat in tranzactie))
 - delete (se gasesc in metodele deleteClient, deleteAccount, deleteCard, deleteSavingsAccount)
 
 ## Cerinte
 Fiecare student va lucra la un proiect individual. Proiectul este structurat în mai multe etape. 
### Etapa I
1. Definirea sistemului
   - Să se creeze o lista pe baza temei alese cu cel puțin 10 acțiuni/interogări care se pot face în 
cadrul sistemului și o lista cu cel puțin 8 tipuri de obiecte.
2. Implementare
   - Sa se implementeze în limbajul Java o aplicație pe baza celor definite la primul punct. Aplicația va conține:
     - clase simple cu atribute private / protected și metode de acces
     - cel puțin 2 colecții diferite capabile să gestioneze obiectele definiteanterior (eg: List, Set, Map, etc.) dintre care cel puțin una sa fie sortata – se vor folosi array-uri uni-
/bidimensionale în cazul în care nu se parcurg colectiile pana la data checkpoint-ului.
     - utilizare moștenire pentru crearea de clase adiționale și utilizarea lor încadrul colecțiilor;
     - cel puțin o clasă serviciu care sa expună operațiile sistemului
     - o clasa Main din care sunt făcute apeluri către servicii
### Etapa II
1. Extindeți proiectul din prima etapa prin realizarea persistentei utilizând fișiere:
   - Se vor realiza fișiere de tip CSV pentru cel puțin 4 dintre clasele definite în prima etapa. Fiecare coloana din fișier este separata de virgula. Exemplu:nume,prenume,varsta
   - Se vor realiza servicii singleton generice pentru scrierea și citirea din fișiere;
   - La pornirea programului se vor încărca datele din fișiere utilizând serviciile create;
2. Realizarea unui serviciu de audit
   - Se va realiza un serviciu care sa scrie într-un fișier de tip CSV de fiecare data când este 
executată una dintre acțiunile descrise în prima etapa. Structura fișierului: nume_actiune, 
timestamp
### Etapa III
Înlocuiți serviciile realizate în etapa a II-a cu servicii care sa asigure persistenta utilizând baza de date folosind 
JDBC.
Să se realizeze servicii care sa expună operații de tip create, read, update si delete pentru cel 
puțin 4 dintre clasele definite.
 
