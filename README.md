# Dental Clinic Admin System

This project is scoped around the clinic staff/admin workflow: patient registration, dentist directory, appointment scheduling, queue handling, and treatment records.

## Run

```bash
cd code/dental-clinic
chmod +x mvnw
./mvnw clean test
./mvnw spring-boot:run
```

Open `http://localhost:8080`. Local development uses a persistent H2 file database at `code/dental-clinic/data/dentalclinic`.

## Main routes

- `/` or `/admin` — dashboard
- `/patients` and `/patients/new` — patient registration and editing
- `/admin/dentists` — dentist directory
- `/admin/appointments` — scheduled appointments
- `/queues` — queue operations
- `/admin/treatments` — treatment records
- `/swagger-ui.html` — REST API docs

## PostgreSQL / Neon

Set `SPRING_PROFILES_ACTIVE=prod`, `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` in the environment before running. Never commit database credentials. `application-prod.properties` uses these variables. The application currently uses Hibernate `ddl-auto=update` to create missing entity tables on startup; back up an existing production database before using it.

The six entity tables are `patients`, `patient_profiles`, `dentist`, `appointments`, `appointment_queues`, and `treatment_records`. All tables should exist after startup, but not every table should contain records before the corresponding workflow is used. No dummy patient or treatment rows are inserted just to make counts non-zero.

This is an assignment-focused admin app, not production-ready medical-records software. Authentication/authorization, formal migrations, backup policy, and audit controls should be added before real clinical use.
