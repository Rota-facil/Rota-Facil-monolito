CREATE TABLE "prefectures_tb" (
  "prefecture_id" uuid PRIMARY KEY,
  "name" varchar(100) NOT NULL,
  "region" varchar(50) NOT NULL,
  "active" boolean NOT NULL DEFAULT true
);

CREATE TABLE "users_tb" (
  "user_id" uuid PRIMARY KEY,
  "prefecture_id" uuid,
  "name" varchar(120) NOT NULL,
  "email" varchar(120) UNIQUE NOT NULL,
  "cpf" varchar(14) UNIQUE,
  "password" varchar(120),
  "google_id" text,
  "trips" bigint NOT NULL DEFAULT 0,
  "completed_trips" bigint NOT NULL DEFAULT 0,
  "score" double NOT NULL DEFAULT 5,
  "role" varchar(10) NOT NULL,
  "status" varchar(20),
  "active" boolean NOT NULL DEFAULT true,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "token_complete_google_login_tb" (
  "id" uuid PRIMARY KEY,
  "user_id" uuid NOT NULL,
  "pending_token" uuid UNIQUE NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  "expiration" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "institutions_tb" (
  "institution_id" uuid PRIMARY KEY,
  "prefecture_id" uuid NOT NULL,
  "name" varchar(100) NOT NULL,
  "latitude" double NOT NULL,
  "longitude" double NOT NULL,
  "geom" geography NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  "active" boolean NOT NULL DEFAULT true
);

CREATE TABLE "board_points_tb" (
  "board_point_id" uuid PRIMARY KEY,
  "prefecture_id" uuid NOT NULL,
  "name" varchar(100) NOT NULL,
  "latitude" double NOT NULL,
  "longitude" double NOT NULL,
  "geom" geography NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP),
  "active" boolean NOT NULL DEFAULT true
);

CREATE TABLE "feedbacks_tb" (
  "feedback_id" uuid PRIMARY KEY,
  "sender_user_id" uuid NOT NULL,
  "receiver_user_id" uuid NOT NULL,
  "feedback" text NOT NULL,
  "note" float NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "vehicles_tb" (
  "vehicle_id" uuid PRIMARY KEY,
  "driver_id" uuid,
  "prefecture_id" uuid NOT NULL,
  "capacity" bigint NOT NULL,
  "plate" varchar(10) NOT NULL,
  "active" boolean NOT NULL DEFAULT true,
  "status" varchar(30) NOT NULL DEFAULT 'OUT_OF_OPERATION',
  "vehicle_type" varchar(30) NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "routes_tb" (
  "route_id" uuid PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "shift" varchar(20) NOT NULL,
  "going" time NOT NULL,
  "return" time NOT NULL,
  "going_finish" time NOT NULL,
  "return_finish" time NOT NULL,
  "prefecture_id" uuid NOT NULL,
  "active" boolean NOT NULL DEFAULT true,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "route_recurring_tb" (
  "route_recurring_id" uuid PRIMARY KEY,
  "route_id" uuid NOT NULL,
  "vehicle_id" uuid NOT NULL
);

CREATE TABLE "route_recurring_day_of_week_tb" (
  "route_id" uuid NOT NULL,
  "days_of_week" varchar(50) NOT NULL
);

CREATE TABLE "routes_institutions_tb" (
  "route_id" uuid NOT NULL,
  "institution_id" uuid NOT NULL,
  "institution_time_going" time NOT NULL,
  "institution_time_finish" time NOT NULL,
  PRIMARY KEY ("route_id", "institution_id")
);

CREATE TABLE "board_points_routes_tb" (
  "board_point_route_id" uuid PRIMARY KEY,
  "board_point_id" uuid NOT NULL,
  "route_id" uuid NOT NULL,
  "board_time_going" time NOT NULL,
  "board_time_finish" time NOT NULL
);

CREATE TABLE "trips_tb" (
  "trip_id" uuid PRIMARY KEY,
  "name" varchar(50) NOT NULL,
  "vehicle_id" uuid NOT NULL,
  "route_id" uuid NOT NULL,
  "latitude" double NOT NULL DEFAULT 0,
  "longitude" double NOT NULL DEFAULT 0,
  "prefecture_id" uuid NOT NULL,
  "geom" geography NOT NULL,
  "students" bigint NOT NULL DEFAULT 0,
  "actual_status" text NOT NULL,
  "reason_of_cancellation" text,
  "created_at" date NOT NULL DEFAULT (CURRENT_DATE)
);

CREATE TABLE "trip_status_tb" (
  "trip_status_id" uuid PRIMARY KEY,
  "trip_id" uuid NOT NULL,
  "progress" varchar(20) NOT NULL,
  "description" text NOT NULL,
  "delay" varchar(20),
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE "trip_users_tb" (
  "trip_user_id" uuid PRIMARY KEY,
  "user_id" uuid NOT NULL,
  "trip_id" uuid NOT NULL,
  "institution_id" uuid NOT NULL,
  "board_point_id" uuid NOT NULL,
  "presence" text NOT NULL DEFAULT 'PENDING',
  "score" double NOT NULL DEFAULT 0,
  "going" boolean NOT NULL DEFAULT false,
  "return" boolean NOT NULL DEFAULT false
);

CREATE TABLE "board_points_visiteds_tb" (
  "board_point_visited_id" uuid PRIMARY KEY,
  "trip_id" uuid NOT NULL,
  "board_point_id" uuid NOT NULL,
  "going" boolean NOT NULL DEFAULT false,
  "return" boolean NOT NULL DEFAULT false
);

CREATE TABLE "ignored_board_points_tb" (
  "trip_id" uuid NOT NULL,
  "board_point_id" uuid NOT NULL,
  PRIMARY KEY ("trip_id", "board_point_id")
);

CREATE TABLE "institutions_visiteds_tb" (
  "institution_visited_id" uuid PRIMARY KEY,
  "trip_id" uuid NOT NULL,
  "institution_id" uuid NOT NULL,
  "going" boolean NOT NULL DEFAULT false,
  "return" boolean NOT NULL DEFAULT false
);

CREATE TABLE "ignored_institutions_tb" (
  "institution_id" uuid NOT NULL,
  "trip_id" uuid NOT NULL,
  PRIMARY KEY ("institution_id", "trip_id")
);

CREATE TABLE "user_token_tb" (
  "user_token_id" uuid PRIMARY KEY,
  "user_id" uuid UNIQUE NOT NULL,
  "access_token" text NOT NULL,
  "refresh_token" text NOT NULL
);

CREATE TABLE "routes_recurring_tb" (
  "route_recurring_id" uuid PRIMARY KEY,
  "route_id" uuid NOT NULL,
  "vehicle_id" uuid NOT NULL
);

CREATE TABLE "files_tb" (
  "file_id" uuid PRIMARY KEY,
  "original_filename" varchar(255) NOT NULL,
  "object_key" varchar(500) UNIQUE NOT NULL,
  "owner_id" uuid NOT NULL,
  "creator_id" uuid NOT NULL,
  "prefecture_id" uuid NOT NULL,
  "owner_type" varchar(30) NOT NULL,
  "file_category" varchar(30) NOT NULL,
  "mime_type" varchar(100) NOT NULL,
  "file_size_bytes" bigint NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (CURRENT_TIMESTAMP)
);

CREATE UNIQUE INDEX ON "board_points_routes_tb" ("board_point_id", "route_id");

CREATE UNIQUE INDEX ON "trip_users_tb" ("user_id", "trip_id");

CREATE UNIQUE INDEX ON "routes_recurring_tb" ("route_id", "vehicle_id");

CREATE INDEX ON "files_tb" ("owner_id", "prefecture_id", "file_category");

ALTER TABLE "users_tb" ADD FOREIGN KEY ("prefecture_id") REFERENCES "prefectures_tb" ("prefecture_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "token_complete_google_login_tb" ADD FOREIGN KEY ("user_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "institutions_tb" ADD FOREIGN KEY ("prefecture_id") REFERENCES "prefectures_tb" ("prefecture_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "board_points_tb" ADD FOREIGN KEY ("prefecture_id") REFERENCES "prefectures_tb" ("prefecture_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "feedbacks_tb" ADD FOREIGN KEY ("sender_user_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "feedbacks_tb" ADD FOREIGN KEY ("receiver_user_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "vehicles_tb" ADD FOREIGN KEY ("driver_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "route_recurring_tb" ADD FOREIGN KEY ("route_id") REFERENCES "routes_tb" ("route_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "route_recurring_tb" ADD FOREIGN KEY ("vehicle_id") REFERENCES "vehicles_tb" ("vehicle_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "route_recurring_day_of_week_tb" ADD FOREIGN KEY ("route_id") REFERENCES "routes_tb" ("route_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "routes_institutions_tb" ADD FOREIGN KEY ("route_id") REFERENCES "routes_tb" ("route_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "routes_institutions_tb" ADD FOREIGN KEY ("institution_id") REFERENCES "institutions_tb" ("institution_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "board_points_routes_tb" ADD FOREIGN KEY ("board_point_id") REFERENCES "board_points_tb" ("board_point_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "board_points_routes_tb" ADD FOREIGN KEY ("route_id") REFERENCES "routes_tb" ("route_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "trips_tb" ADD FOREIGN KEY ("vehicle_id") REFERENCES "vehicles_tb" ("vehicle_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "trips_tb" ADD FOREIGN KEY ("route_id") REFERENCES "routes_tb" ("route_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "trip_status_tb" ADD FOREIGN KEY ("trip_id") REFERENCES "trips_tb" ("trip_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "trip_users_tb" ADD FOREIGN KEY ("user_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "trip_users_tb" ADD FOREIGN KEY ("trip_id") REFERENCES "trips_tb" ("trip_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "trip_users_tb" ADD FOREIGN KEY ("institution_id") REFERENCES "institutions_tb" ("institution_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "board_points_visiteds_tb" ADD FOREIGN KEY ("trip_id") REFERENCES "trips_tb" ("trip_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "board_points_visiteds_tb" ADD FOREIGN KEY ("board_point_id") REFERENCES "board_points_tb" ("board_point_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "ignored_board_points_tb" ADD FOREIGN KEY ("trip_id") REFERENCES "trips_tb" ("trip_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "ignored_board_points_tb" ADD FOREIGN KEY ("board_point_id") REFERENCES "board_points_tb" ("board_point_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "institutions_visiteds_tb" ADD FOREIGN KEY ("trip_id") REFERENCES "trips_tb" ("trip_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "institutions_visiteds_tb" ADD FOREIGN KEY ("institution_id") REFERENCES "institutions_tb" ("institution_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "ignored_institutions_tb" ADD FOREIGN KEY ("institution_id") REFERENCES "institutions_tb" ("institution_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "ignored_institutions_tb" ADD FOREIGN KEY ("trip_id") REFERENCES "trips_tb" ("trip_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "user_token_tb" ADD FOREIGN KEY ("user_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "routes_recurring_tb" ADD FOREIGN KEY ("route_id") REFERENCES "routes_tb" ("route_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "routes_recurring_tb" ADD FOREIGN KEY ("vehicle_id") REFERENCES "vehicles_tb" ("vehicle_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "files_tb" ADD FOREIGN KEY ("creator_id") REFERENCES "users_tb" ("user_id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "files_tb" ADD FOREIGN KEY ("prefecture_id") REFERENCES "prefectures_tb" ("prefecture_id") DEFERRABLE INITIALLY IMMEDIATE;
