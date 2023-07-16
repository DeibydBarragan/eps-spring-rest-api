# EPS SPRING BOOT REST-API
Hi, mi name is **Deibyd** and this is my project to manage patients, doctors and appointments of an EPS.
## Prerequisite
You have to install these tools
- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Git](https://git-scm.com/)

## Features and Functionalities
- Get a list of the appointments, patients and doctors from the database and their information
- Create new appointments, patients and doctors in a PostgreSQL database
- Update appointments, patients and doctors
- Delete appointments, patients and doctors
- Filter appointments by patient's cedula and doctor's cedula and filter by speciality

## Tech Stack
- [PostgreSQL](https://www.postgresql.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [NextJS (React)](https://nextjs.org/)
- [Java](https://www.oracle.com/java/technologies/java-se-glance.html)
- [TypeScript](https://www.typescriptlang.org/)

## Data model
![Data model](docs/dataModel.png)
## Installation and running

**1. Clone this repo by running the following commands**

`git clone https://github.com/DeibydBarragan/eps-spring-rest-api.git`

**2. Then, go to the root folder and install the dependencies**

`cd eps-spring-rest-api`

`mvn clean install`

**3. Configure the application.properties file in `eps-spring-rest-api/src/main/resources/application.properties` with these values**

```
spring.datasource.url = *YOUR POSTGRESQL DATABASE URL*
spring.datasource.username = *YOUR POSTGRESQL DATABASE USERNAME*
spring.datasource.password = *YOUR POSTGRESQL DATABASE PASSWORD*
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true
```

**4. Then, go to the root folder and run the following command**

`mvn spring-boot:run`

now you can test the API with Postman or another client with this url http://localhost:8080/api

**5. Then, in another terminal go to the root folder and run the following**

`cd webui`

`npm install`

**6. Add a .env.local file with the following**

`NEXT_PUBLIC_API_URL=http://localhost:8080/api`

**7. Now run the following command**

`npm run dev`

Then go to http://localhost:3000 and use the frontend app

## Build and run the backend API with Docker
To execute the backend app (with the postgreSQL database) in a docker container:

1. Make sure you have docker installed on your system
2. Configure the application.properties file in `eps-spring-rest-api/src/main/resources/application.properties` **exactly** like this
   ```
   spring.datasource.url = ${DATABASE_URL}
   spring.datasource.username = ${DATABASE_USERNAME}
   spring.datasource.password = ${DATABASE_PASSWORD}
   spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto = update
   spring.jpa.show-sql = true
   ```
3. Open the root directory in the terminal and run the following

   ```shell
   mvn clean package
4. Then execute the following command
   
   ```shell
   docker-compose build java_app

5. Then run the following command:
   ```shell
   docker compose up

Now you can use the api from http://localhost:8080/api and the database from http://localhost:5432

## Build and run the frontend APP with Docker
To execute the frontend app in a docker container:

1. Make sure you have docker installed on your system, also make sure that you have the backend API running and the .env.local file configured with the port of the backend API
2. Open the **webui** root directory in the terminal
3. Execute the following command

   ```shell
   docker build -t eps-client-api .

4. Then run the following command:
   ```shell
   docker run --env-file=./.env.local -p 3000:3000 eps-client-api:latest

Now you can use the frontend app in http://localhost:3000

## How to use the backend API?
### Patients API
#### Create a patient
**Endpoint:** `POST /api/patients`
##### Body example
```json
{
  "name": "Deibyd Santiago",
  "lastname": "Barragán Gaitán",
  "cedula": 1069445930,
  "age": 19,
  "email": "deibydbarragan@hotmail.com",
  "phone": 3134828849
}
```
##### Example
[Post patient](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/b861fa4f-2690-424d-adf8-61ad74b75d20)

#### Update a patient
**Endpoint:** `PUT /api/patients/{id}`
##### Params
- `id` (required): patient id.
##### Body
Use the same fields as creating a patient.

##### Example
[Update patient](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/3a868c41-cfb2-4102-b91c-5e8cfad1be19)

#### Delete a patient
**Endpoint:** `DELETE /api/patients/{id}`
##### Params
- `id` (required): patient id.

##### Example
[Delete patient](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/e887c271-aab9-4534-ad6a-9e2492fa3557)

#### Get and paginate patients
**Endpoint:** `GET /api/patients`
##### Query params
- `size` (optional): limit the number of patients in every page.
- `page` (optional): bring patients of a specific page.

##### Example
[Get and paginate patients](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/9eafeba1-2a0e-4a04-b37b-ba1dc14fd367)

### Doctors API

#### Create a doctor
**Endpoint:** `POST /api/doctors`
##### Body example
```json
{
  "name": "Pablo",
  "lastname": "Casas Mejía",
  "cedula": 221345443,
  "specialty": "Cardiología",
  "office": 205,
  "email": "pablomejia@hotmail.com",
  "phone": 3134828849
}
```
##### Example
[Create doctor](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/e1ea7896-aff9-4b69-bfb6-543656c7bb3d)

#### Update a doctor
**Endpoint:** `PUT /api/doctors/{id}`
##### Params
- `id` (required): doctor id.
##### Body
Use the same fields as creating a doctor

##### Example
[Update doctor](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/2e9c73be-a2a4-459a-9331-49a33632017e)

#### Delete a doctor
**Endpoint:** `DELETE /api/doctors/{id}`
##### Params
- `id` (required): patient id.

##### Example
[Delete a doctor](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/f49f982b-a587-49ad-bacb-dc1ea04c2a70)

#### Get all doctors without pagination and filter them by speciality
**Endpoint:** `GET /api/doctors/all`
##### Query params
- `specialty` (optional): value must between 0 and 7. Filter doctors by their speciality.

##### Example
[Get all doctors without pagination and filter them by specialty](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/4a3baea7-715b-470f-91eb-5f6a413c1ade)

#### Get and paginate doctors
**Endpoint:** `GET /api/doctors`
##### Query params
- `size` (optional): limit the number of doctors in every page.
- `page` (optional): bring doctors of a specific page.

##### Example
[Get and paginate doctors](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/3b26b6b4-e805-421e-ae5d-5d878498d202)

### Appointments API
#### Create an appointment
**Endpoint:** `POST /api/appointments`
##### Body example
```json
{
    "patientId": 4,
    "doctorId": 2,
    "date": "2023-09-15T06:30:00.0"
}
```
- `date` (required): It must be a date in 'YYYY-MM-DDTHH:MM:SS.0' format. The time must be in 24 hours and 30 minutes format and the date must be in the future.

##### Example
[Create an appointment](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/c3f79316-d461-41c7-bc69-30e34a83b281)

#### Update an appointment
**Endpoint:** `PUT /api/appointments/{id}`
##### Params
- `id` (required): appointment id.
##### Body
Use the same fields as creating an appointment.

##### Example
[Update an appointment](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/b589a857-9686-484a-a29d-356be57d48d2)

#### Delete an appointment
**Endpoint:** `DELETE /api/appointments/{id}`
##### Params
- `id` (required): appointment id.

##### Example
[Delete an appointment](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/44e8d810-cb0b-4406-87e4-09cedd08b15f)

#### Get appointments by patient's cedula
**Endpoint:** `GET /api/appointments/patient/{cedula}`
##### Params
- `cedula` (required): patient's cedula.

##### Example
[Get appointments by patient's cedula](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/12b938ad-32d6-4221-9ee8-e7a8af8773a4)

#### Get appointments by doctor's cedula
**Endpoint:** `GET /api/appointments/doctor/{cedula}`
##### Params
- `cedula` (required): doctor's cedula.

##### Example
[Get appointments by doctor's cedula](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/e388fc06-fda7-4667-8913-1a7de232b1cb)

#### Get and paginate appointments
**Endpoint:** `GET /api/appointments`
##### Query params
- `specialty` (optional): Get appointments by specialty.

##### Example
[Get, paginate and filter appointments](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/aa22a793-1b40-42ad-9754-46104fafd76d)

## How to use the frontend API?
### For patients
[Patients](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/5b6f6eb4-f7f0-4d4c-842e-45f6fb30ae2c)
### For doctors
[Doctors](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/0f215303-3add-4cd5-b1f6-42c6b1c1474b)
### For appointments
[Appointments](https://github.com/DeibydBarragan/eps-rest-api/assets/116578796/a445850e-de47-44fd-a531-4750d78238b2)

## Additionally, this project uses:
### For Backend
- [JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Boot Starter Validation](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation)
- [Spring Initializr](https://start.spring.io/;)
### For frontend
- [NextUI](https://nextui.org/)
- [React Hook Form](https://react-hook-form.com/)
- [React Toastify](https://fkhadra.github.io/react-toastify/introduction/)
- [Yup](https://www.npmjs.com/package/yup)
- [Lucide React](https://lucide.dev/guide/packages/lucide-react)
- [Momentjs](https://momentjs.com/)
- [ESlint](https://eslint.org/)





