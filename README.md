# Framework di Test E2E con Java, Selenium, Cucumber (JUnit 5 + PicoContainer)

Framework di test automatizzato che esegue scenari **end-to-end** sul
sito demo **[AutomationExercise](http://automationexercise.com/)**.\
Il progetto è stato sviluppato in **Java 21** con **Selenium
WebDriver**, **Cucumber BDD**, **JUnit 5** e **PicoContainer** per
l'iniezione delle dipendenze.

------------------------------------------------------------------------

## Stack Tecnico

-   **Java 21**
-   **Selenium WebDriver** → libreria principale per l'automazione
    browser.
-   **Cucumber** → gestione feature `.feature` e step definitions in
    BDD.
-   **JUnit 5 / JUnit Platform** → esecuzione test.
-   **PicoContainer** → iniezione delle dipendenze tra step.
-   **WebDriverManager** → gestione automatica dei driver dei browser.
-   **Maven** → build ed esecuzione dei test.

------------------------------------------------------------------------

## Struttura del Progetto

### Main (`src/main/java`)

    gianluca.com
     ├─ seleniumWrapper/          # Wrapper per interazioni Selenium
     ├─ uiLocators/               # Locatori centralizzati
     ├─ webDriverFactory/         # Gestione driver cross-browser
     ├─ model/                    # Classi modello per i dati (POJO/DTO)

### Test (`src/test/java`)

    gianluca.com.configuration
     ├─ BaseStepDefinition        # Classe base per gli step
     ├─ ContextKey                # Chiavi per dati condivisi
     ├─ Hooks                     # Ciclo di vita test (Before/After/Step)
     ├─ ScenarioContext           # Dati per singolo scenario
     └─ TestContext               # Contesto principale (WebDriver & oggetti)

    gianluca.com.datahooks        # Hook dedicati ai dati
    gianluca.com.runners          # Runner JUnit per i test
    gianluca.com.stepdefinitions  # Step Definitions Cucumber
    gianluca.com.utils
     ├─ ConfigReader              # Lettura config.properties
     ├─ FilePathUtils             # Utility file path / upload file
     └─ JsonReader                # Lettura dati JSON

    gianluca.com.reports
     ├─ LogConfiguration          # Config dei log
     ├─ PathManager               # Percorsi report/log/screenshot
     ├─ ReportManager             # ExtentReports
     └─ ScreenshotManager         # Screenshot

### Resources (`src/test/resources`)

    features/                     # File .feature in Gherkin
    files/                        # File di test (es. upload)
    json/                         # File JSON con dati test

------------------------------------------------------------------------

## Dipendenze (descrizione)

-   **Selenium Java** -- automazione browser via WebDriver.
-   **Cucumber Java** -- step definitions per BDD.
-   **Cucumber JUnit Platform Engine** -- integrazione Cucumber con
    JUnit 5.
-   **Cucumber PicoContainer** -- DI tra step (context condivisi).
-   **JUnit Jupiter (API + Engine)** -- framework di test.
-   **JUnit Platform Suite** -- definizione di suite con `@Suite`.
-   **WebDriverManager** -- gestione automatica dei driver.

------------------------------------------------------------------------

## Runner e Comandi Maven

Esecuzione singoli runner:

``` bash
mvn clean test -Dtest=ContactUsFormSubmissionRunnerTest
mvn clean test -Dtest=LoginRunnerTest
mvn clean test -Dtest=RegisterDeleteRunnerTest
mvn clean test -Dtest=SignUpExisistingEmailRunnerTest
```

Esecuzione **suite completa**:

``` bash
mvn clean test -Dtest=RunAllTests
```

La configurazione del **parallelismo** è gestita tramite file
`.properties`.

------------------------------------------------------------------------

## Configurazione (`config.properties`)

``` properties
browser=chrome
timeOut=5
report.baseDir=reports

# path json
login.valid.json=File_Json/Login/loginValid.json
registration.data.json=File_Json/RegisterDeleteAccount/registration.data.json
registration.ui.json=File_Json/RegisterDeleteAccount/registration.ui.json
signupexsisting.data.json=File_Json/RegisterDeleteAccount/signupExistingEmail.json
contacts.data.json=File_Json/Contacts/contact.json
```

> Le proprietà sono lette da `ConfigReader` e usate da `Hooks`,
> `WebDriverFactory`, `JsonReader` e componenti di reporting.

------------------------------------------------------------------------

## Test Case Implementati

-   **Registrazione + Eliminazione Account**
-   **Login (successo e fallimento con Examples)**
-   **Logout**
-   **Signup con Email Esistente**
-   **Contact Us Form (con upload file)** ✅
------------------------------------------------------------------------

## Reporting

-   **Cucumber HTML Report** → `target/cucumber-report.html`
-   **ExtentReports** → `target/reports/<data_run>/ExtentReport.html`
-   **Log** → `target/reports/<data_run>/logs/`
-   **Screenshot** → `target/reports/<data_run>/screenshots/`

Esempio:

    target/reports/2025-08-08_17-45-22/
     ├─ ExtentReport.html
     ├─ logs/
     │   └─ execution.log
     └─ screenshots/
         └─ Nome_Scenario/
             └─ FAIL_145522_123.png

------------------------------------------------------------------------

## Buone Pratiche

-   Config esterne tramite `config.properties`.
-   PicoContainer per context condivisi negli step.
-   SeleniumWrapper per azioni/attese riutilizzabili.
-   Runner specifici + uno globale per regression suite.
-   Report professionali con log e screenshot allegati.
