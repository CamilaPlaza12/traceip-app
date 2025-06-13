# IP Info Tracker

**IP Info Tracker** is a web application that provides contextual information about a given IP address. It displays data such as country, official languages, currency and exchange rate, local time(s), distance to Buenos Aires, and usage statistics.  
Developed as part of a technical challenge.

## Technologies Used

- **Java 17** – Backend development language.
- **Spring Boot** – Framework to build the backend in a fast and structured way.
- **Maven** – Dependency manager and project builder for Java.
- **Angular** – Framework used to build a modern and responsive frontend.
- **Docker** – For containerizing both frontend and backend applications.
- **Public REST APIs** – Used to retrieve data such as IP info, country details, currencies, etc.
- **Mono-repo** – Both frontend and backend are housed in a single repository.
- **Lombok** – Simplifies code using annotations like `@Data`.
- **Java Bean Validation** – Used for model validation with annotations like `@Min`, `@Max`, etc.

## Features and Validations

### Expected Validations

- Null or empty IP → `400 Bad Request`
- Malformed IP → `400 Bad Request`
- Failure calling external API → `502 Bad Gateway`
- Country not found → `404 Not Found`
- Unexpected error → `500 Internal Server Error`

### Output Data

- Country name and ISO code
- Official languages
- Local time(s)
- Distance from Buenos Aires
- Local currency and exchange rate (in USD)

## API Breakdown

### 1. ipapi.com (Main entry point)

- `country_name`, `country_code`: Basic country info.
- `latitude`, `longitude`: Used for distance calculation.
- `languages`: Example format: `"es,en"`.
- `currency.code`, `currency.name`, `currency.symbol`
- `time_zone`: Returns one timezone only.

**Use it as the initial API for getting IP info.**

### 2. restcountries.com (Country data)

- `languages`: More detailed list than ipapi.
- `timezones[]`: Returns multiple timezones if applicable.
- `alpha2Code`, `alpha3Code`: ISO standards.
- `currencies`: More detailed currency info.

**Complements ipapi data if necessary.**

### 3. fixer.io (Exchange rates)

- Provides exchange rates using USD or EUR as the base.
- Use currency code (e.g., ARS, USD) to fetch conversion value.

**Provides real-time conversion from local currency to USD.**

## Processing Steps

1. Validate the IP.
2. Call ipapi.com.
3. Call restcountries.com.
4. Call fixer.io.
5. Calculate distance to Buenos Aires.
6. Return a complete `TraceIpResponseTO`.

## Services

- **Farthest distance** to Buenos Aires from any query made.
- **Nearest distance** to Buenos Aires from any query made.
- **Average distance** of all IPs queried.

## Database

H2 console available at: http://localhost:8080/h2-console

Database settings:

- **JDBC URL**: `jdbc:h2:file:./data/traceipdb`
- **User**: `sa`
- **Password**: *(leave blank)*

> The database file is not included in the repo to keep the project lightweight and portable.  
> To view persisted data, run the system and make queries to the API or run the included automated tests.

## Running the Application

### Backend (Spring Boot)

Run with Maven:

```bash
./mvnw spring-boot:run
```

### Frontend (Angular)
This project was generated using Angular CLI version 19.2.13.

#### Development server
To start a local development server, run:
```bash
ng serve
```

Once the server is running, open your browser and navigate to http://localhost:4200/. The application will automatically reload whenever you modify any of the source files.

## Running unit tests
To execute unit tests with the Karma test runner, use the following command:
```bash
ng test
```

For end-to-end (e2e) testing, run:
```bash
ng e2e
```

## Docker Instructions
Each part of the app (frontend and backend) has its own Dockerfile.
To build and run the full stack together, use Docker Compose.

From a terminal, **navigate to the root directory** where the `docker-compose.yml` file is located, then run:

```bash
docker-compose up --build
```

Docker will:

- Build the backend using its Dockerfile

- Build the frontend using its Dockerfile

- Set up both containers and link them in a single network

- Expose necessary ports

Make sure Docker and Docker Compose are installed on your machine.

You can access it at: http://localhost:4200/index.html
