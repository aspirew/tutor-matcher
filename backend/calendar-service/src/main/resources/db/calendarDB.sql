--
-- PostgreSQL database dump
--

-- Dumped from database version 15.2
-- Dumped by pg_dump version 15.2

-- Started on 2023-06-09 15:53:14

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

--
-- TOC entry 841 (class 1247 OID 16402)
-- Name: timeRange; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public."timeRange" AS RANGE (
    subtype = time without time zone,
    multirange_type_name = public."timeRange_multirange"
);


ALTER TYPE public."timeRange" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 16565)
-- Name: calendars; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.calendars (
    userid bigint NOT NULL,
    googleid text,
    googleavailableid text
);


ALTER TABLE public.calendars OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16573)
-- Name: events; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.events (
    eventid bigint NOT NULL,
    calendarid bigint NOT NULL,
    isavailable boolean NOT NULL,
    googleid text,
    name text NOT NULL
);


ALTER TABLE public.events OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16572)
-- Name: events_eventid_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.events_eventid_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.events_eventid_seq OWNER TO postgres;

--
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 215
-- Name: events_eventid_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.events_eventid_seq OWNED BY public.events.eventid;


--
-- TOC entry 217 (class 1259 OID 16581)
-- Name: singleevents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.singleevents (
    eventid bigint NOT NULL,
    daterange tsrange
);


ALTER TABLE public.singleevents OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16588)
-- Name: weeklyevents; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.weeklyevents (
    eventid bigint NOT NULL,
    weekday numeric(1,0) NOT NULL,
    timerange public."timeRange" NOT NULL
);


ALTER TABLE public.weeklyevents OWNER TO postgres;

--
-- TOC entry 3192 (class 2604 OID 16576)
-- Name: events eventid; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events ALTER COLUMN eventid SET DEFAULT nextval('public.events_eventid_seq'::regclass);


--
-- TOC entry 3194 (class 2606 OID 16571)
-- Name: calendars Calendars_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.calendars
    ADD CONSTRAINT "Calendars_pkey" PRIMARY KEY (userid);


--
-- TOC entry 3196 (class 2606 OID 16580)
-- Name: events Events_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT "Events_pkey" PRIMARY KEY (eventid);


--
-- TOC entry 3198 (class 2606 OID 16587)
-- Name: singleevents SingleEvents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.singleevents
    ADD CONSTRAINT "SingleEvents_pkey" PRIMARY KEY (eventid);


--
-- TOC entry 3200 (class 2606 OID 16594)
-- Name: weeklyevents WeeklyEvents_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weeklyevents
    ADD CONSTRAINT "WeeklyEvents_pkey" PRIMARY KEY (eventid);


--
-- TOC entry 3201 (class 2606 OID 16595)
-- Name: events Events_calendarId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT "Events_calendarId_fkey" FOREIGN KEY (calendarid) REFERENCES public.calendars(userid) NOT VALID;


--
-- TOC entry 3202 (class 2606 OID 16610)
-- Name: singleevents singleevents_eventid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.singleevents
    ADD CONSTRAINT singleevents_eventid_fkey FOREIGN KEY (eventid) REFERENCES public.events(eventid) ON DELETE CASCADE NOT VALID;


--
-- TOC entry 3203 (class 2606 OID 16615)
-- Name: weeklyevents weeklyevents_eventid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.weeklyevents
    ADD CONSTRAINT weeklyevents_eventid_fkey FOREIGN KEY (eventid) REFERENCES public.events(eventid) ON DELETE CASCADE NOT VALID;


-- Completed on 2023-06-09 15:53:16

--
-- PostgreSQL database dump complete
--

