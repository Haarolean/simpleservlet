CREATE TABLE "public"."parts" (
  "id" int4 DEFAULT nextval('parts_id_seq'::regclass) NOT NULL,
  "name" varchar(255) COLLATE "default" NOT NULL,
  "number" varchar(255) COLLATE "default" NOT NULL,
  "vendor" varchar(255) COLLATE "default" NOT NULL,
  "qty" int4 NOT NULL,
  "shipped" date,
  "received" date,
  CONSTRAINT "parts_pkey" PRIMARY KEY ("id")
)
  WITH (OIDS=FALSE)
;

CREATE UNIQUE INDEX "parts_id_idx" ON "public"."parts" USING btree ("id");