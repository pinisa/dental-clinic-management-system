# Dental Clinic Management System

A small Spring Boot + Thymeleaf admin system for clinic staff. The admin workflow focuses on patient registration, appointments, walk-in queues, treatment records, and read-only dentist directory.

## Main features

- Dashboard counts for the six domain tables/entities: `patients`, `patient_profiles`, `dentist`, `appointments`, `appointment_queues`, and `treatment_records`.
- Patient registration/editing writes patient and profile data through the existing one-to-one JPA relationship.
- Appointment scheduling validates the selected patient/dentist and rejects a dentist's duplicate time slot.
- Queue management tracks WAITING, IN_TREATMENT, COMPLETED, and CANCELLED. Cancelled queues are retained for history.
- Treatment records must match the selected queue's patient and dentist. Saving a treatment and marking its queue completed occur in one transaction; duplicate treatment records per queue are blocked.
- Dentist records are seeded only when the dentist table is empty. The directory is intentionally read-only to keep the assignment scope manageable.
- This build focuses on the admin web interface; developer API endpoints and Swagger UI are intentionally removed.

## Run locally

Requirements: Java 17+ (the project targets Java 17) and network access on first Maven dependency download.

```bash
cd code/dental-clinic
chmod +x mvnw
./mvnw clean test
./mvnw spring-boot:run
```

Open `http://localhost:8080` for the admin dashboard. Local development uses a file-backed H2 database at `code/dental-clinic/data/dentalclinic`, so data persists across restarts. Hibernate `ddl-auto=update` creates missing entity tables when the app starts.

## Use PostgreSQL / Neon

Do not put database credentials in source code. Set environment variables in the terminal/session or deployment configuration:

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL='jdbc:postgresql://<host>/<database>?sslmode=require'
export DB_USERNAME='<database-user>'
export DB_PASSWORD='<database-password>'
./mvnw spring-boot:run
```

`application-prod.properties` uses those environment variables and disables the H2 console. Back up the database before deploying schema changes. `ddl-auto=update` can create missing tables and add columns but is not a substitute for reviewed production migrations.

## Key admin routes

- `/` or `/admin` — dashboard
- `/patients` — patient directory; `/patients/new` — register patient
- `/admin/dentists` — dentist directory
- `/admin/appointments` — appointment list; `/admin/appointments/new` — schedule appointment
- `/queues` — queue management; `/admin/queues/new` — create queue
- `/admin/treatments` — treatment history; `/admin/treatments/new` — record treatment

## Data integrity notes

The application should not create dummy patient, appointment, queue, or treatment rows just to make all tables non-empty. All six tables are expected to exist after startup, but rows in appointments, queues, and treatment records are created only when staff performs those workflows. Dentist seed rows are reference data. The current data model treats an appointment as a scheduled slot and a queue as an actual service/walk-in queue; there is not yet a direct foreign-key link between those two records.

Before real clinical use, add authentication/authorization, backups, audit policies, and formal database migrations. This assignment-focused version is not a production-ready medical records system.


## Core application logic
- **Strategy Pattern:** treatment queue pricing is calculated by patient coverage type (`DIRECT_PAY`, `SOCIAL_SECURITY`, `CIVIL_SERVANT`).
- **Observer Pattern:** queue status changes notify the registered observers (audit log and notification observer).
- The dashboard's completed-revenue figure sums final prices for `COMPLETED` queues only; waiting and cancelled queues are excluded.

## Neon PostgreSQL configuration
Set `SPRING_PROFILES_ACTIVE=prod`, `DB_URL` to the Neon JDBC URL (include `sslmode=require`), and `DB_USERNAME` / `DB_PASSWORD` to the Neon credentials. The production profile uses PostgreSQL. Hibernate schema update is retained for the current project setup; verify the actual Neon schema and take a backup before deploying schema changes. Never commit real credentials.
