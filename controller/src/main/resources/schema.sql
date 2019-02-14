CREATE VIRTUAL TABLE IF NOT EXISTS movie using FTS4(
"title" text NOT NULL ,
"length" text NOT NULL ,
"add_time" text NOT NULL ,
"author" text NOT NULL ,
"view" integer NOT NULL ,
"collect" integer NOT NULL ,
"message_number" integer NOT NULL ,
"integration" integer NOT NULL ,
"detail_url" text NOT NULL ,
"download_url" text ,
"file_name" text NOT NULL ,
"key" text NOT NULL ,
"add_date_time" integer NOT NULL ,
"status" text NOT NULL
);