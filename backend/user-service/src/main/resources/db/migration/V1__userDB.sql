--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-06-09 15:57:59

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 16667)
-- Name: addresses; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.addresses (
    addressid bigint NOT NULL,
    street text NOT NULL,
    city text NOT NULL,
    zip text NOT NULL
);


ALTER TABLE public.addresses OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16666)
-- Name: addresses_addressid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.addresses_addressid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.addresses_addressid_seq OWNER TO postgres;

--
-- TOC entry 3375 (class 0 OID 0)
-- Dependencies: 219
-- Name: addresses_addressid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.addresses_addressid_seq OWNED BY public.addresses.addressid;


--
-- TOC entry 223 (class 1259 OID 16740)
-- Name: distances; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.distances (
    distance numeric(10,3),
    address1 bigint NOT NULL,
    address2 bigint NOT NULL
);


ALTER TABLE public.distances OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16659)
-- Name: lessonforms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lessonforms (
    lessonformid bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.lessonforms OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16710)
-- Name: lessonforms_teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.lessonforms_teachers (
    teacherid bigint NOT NULL,
    lessonform text NOT NULL
);


ALTER TABLE public.lessonforms_teachers OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16652)
-- Name: subjects; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subjects (
    subjectid bigint NOT NULL,
    name text NOT NULL
);


ALTER TABLE public.subjects OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16722)
-- Name: subjects_teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subjects_teachers (
    teacherid bigint NOT NULL,
    subject text NOT NULL
);


ALTER TABLE public.subjects_teachers OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16640)
-- Name: teachers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.teachers (
    userid bigint NOT NULL,
    hourlyrate numeric(10,2),
    maxdistance integer,
    verification text,
    teachinglevel text
);


ALTER TABLE public.teachers OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16630)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    userid bigint NOT NULL,
    name text NOT NULL,
    surname text NOT NULL,
    phone text,
    mail text NOT NULL,
    addressid bigint
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16629)
-- Name: users_userid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.users_userid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_userid_seq OWNER TO postgres;

--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 214
-- Name: users_userid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.users_userid_seq OWNED BY public.users.userid;


--
-- TOC entry 3203 (class 2604 OID 16670)
-- Name: addresses addressid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses ALTER COLUMN addressid SET DEFAULT nextval('public.addresses_addressid_seq'::regclass);


--
-- TOC entry 3202 (class 2604 OID 16633)
-- Name: users userid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users ALTER COLUMN userid SET DEFAULT nextval('public.users_userid_seq'::regclass);


--
-- TOC entry 3215 (class 2606 OID 16674)
-- Name: addresses addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.addresses
    ADD CONSTRAINT addresses_pkey PRIMARY KEY (addressid);


--
-- TOC entry 3221 (class 2606 OID 16744)
-- Name: distances distances_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.distances
    ADD CONSTRAINT distances_pkey PRIMARY KEY (address1, address2);


--
-- TOC entry 3213 (class 2606 OID 16665)
-- Name: lessonforms lessonforms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessonforms
    ADD CONSTRAINT lessonforms_pkey PRIMARY KEY (lessonformid);


--
-- TOC entry 3217 (class 2606 OID 16716)
-- Name: lessonforms_teachers lessonforms_teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessonforms_teachers
    ADD CONSTRAINT lessonforms_teachers_pkey PRIMARY KEY (teacherid, lessonform);


--
-- TOC entry 3211 (class 2606 OID 16658)
-- Name: subjects subjects_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects
    ADD CONSTRAINT subjects_pkey PRIMARY KEY (subjectid);


--
-- TOC entry 3219 (class 2606 OID 16728)
-- Name: subjects_teachers subjects_teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects_teachers
    ADD CONSTRAINT subjects_teachers_pkey PRIMARY KEY (subject, teacherid);


--
-- TOC entry 3209 (class 2606 OID 16646)
-- Name: teachers teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (userid);


--
-- TOC entry 3205 (class 2606 OID 16639)
-- Name: users users_mail_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_mail_key UNIQUE (mail);


--
-- TOC entry 3207 (class 2606 OID 16637)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (userid);


--
-- TOC entry 3226 (class 2606 OID 16745)
-- Name: distances distances_address1_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.distances
    ADD CONSTRAINT distances_address1_fkey FOREIGN KEY (address1) REFERENCES public.addresses(addressid);


--
-- TOC entry 3227 (class 2606 OID 16750)
-- Name: distances distances_address2_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.distances
    ADD CONSTRAINT distances_address2_fkey FOREIGN KEY (address2) REFERENCES public.addresses(addressid);


--
-- TOC entry 3224 (class 2606 OID 16717)
-- Name: lessonforms_teachers lessonforms_teachers_teacherid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.lessonforms_teachers
    ADD CONSTRAINT lessonforms_teachers_teacherid_fkey FOREIGN KEY (teacherid) REFERENCES public.teachers(userid);


--
-- TOC entry 3225 (class 2606 OID 16729)
-- Name: subjects_teachers subjects_teachers_teacherid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subjects_teachers
    ADD CONSTRAINT subjects_teachers_teacherid_fkey FOREIGN KEY (teacherid) REFERENCES public.teachers(userid);


--
-- TOC entry 3223 (class 2606 OID 16647)
-- Name: teachers teachers_userid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.teachers
    ADD CONSTRAINT teachers_userid_fkey FOREIGN KEY (userid) REFERENCES public.users(userid);


--
-- TOC entry 3222 (class 2606 OID 16675)
-- Name: users users_addressid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_addressid_fkey FOREIGN KEY (addressid) REFERENCES public.addresses(addressid) NOT VALID;


-- Completed on 2023-06-09 15:58:02

--
-- PostgreSQL database dump complete
--

