--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- Name: actor_id_seq; Type: SEQUENCE; Schema: public; Owner: lcjury
--

CREATE SEQUENCE actor_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actor_id_seq OWNER TO lcjury;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: actor; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE actor (
    id integer DEFAULT nextval('actor_id_seq'::regclass) NOT NULL,
    name character varying(255)
);


ALTER TABLE public.actor OWNER TO lcjury;

--
-- Name: rank_id_seq; Type: SEQUENCE; Schema: public; Owner: lcjury
--

CREATE SEQUENCE rank_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.rank_id_seq OWNER TO lcjury;

--
-- Name: article; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE article (
    id integer DEFAULT nextval('rank_id_seq'::regclass) NOT NULL,
    title character varying(255),
    content text
);


ALTER TABLE public.article OWNER TO lcjury;

--
-- Name: article_has_tag; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE article_has_tag (
    idarticle integer,
    idtag integer
);


ALTER TABLE public.article_has_tag OWNER TO lcjury;

--
-- Name: department_id_seq; Type: SEQUENCE; Schema: public; Owner: lcjury
--

CREATE SEQUENCE department_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.department_id_seq OWNER TO lcjury;

--
-- Name: employee_id_seq; Type: SEQUENCE; Schema: public; Owner: lcjury
--

CREATE SEQUENCE employee_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.employee_id_seq OWNER TO lcjury;

--
-- Name: genre_id_seq; Type: SEQUENCE; Schema: public; Owner: lcjury
--

CREATE SEQUENCE genre_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.genre_id_seq OWNER TO lcjury;

--
-- Name: genre; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE genre (
    id integer DEFAULT nextval('genre_id_seq'::regclass) NOT NULL,
    name character varying(255)
);


ALTER TABLE public.genre OWNER TO lcjury;

--
-- Name: movie; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE movie (
    title character varying(255) NOT NULL,
    synopsis text,
    debut_date date
);


ALTER TABLE public.movie OWNER TO lcjury;

--
-- Name: movie_actor; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE movie_actor (
    movietitle character varying(255),
    idactor integer
);


ALTER TABLE public.movie_actor OWNER TO lcjury;

--
-- Name: movie_genre; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE movie_genre (
    movietitle character varying(255),
    idgenre integer
);


ALTER TABLE public.movie_genre OWNER TO lcjury;

--
-- Name: rank_article; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE rank_article (
    username character varying(255),
    rankedarticle integer,
    rank integer
);


ALTER TABLE public.rank_article OWNER TO lcjury;

--
-- Name: rank_movie; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE rank_movie (
    username character varying(255),
    rankedmovie character varying(255),
    rank integer
);


ALTER TABLE public.rank_movie OWNER TO lcjury;

--
-- Name: relation; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE relation (
    titleoriginalmovie character varying(255),
    titlerecommendedmovie character varying(255),
    content text,
    num_approvals integer
);


ALTER TABLE public.relation OWNER TO lcjury;

--
-- Name: site_user; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE site_user (
    name character varying(25) NOT NULL,
    password character varying(25),
    mail character varying(255),
    rights integer
);


ALTER TABLE public.site_user OWNER TO lcjury;

--
-- Name: tag_id_seq; Type: SEQUENCE; Schema: public; Owner: lcjury
--

CREATE SEQUENCE tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tag_id_seq OWNER TO lcjury;

--
-- Name: tag; Type: TABLE; Schema: public; Owner: lcjury; Tablespace: 
--

CREATE TABLE tag (
    id integer DEFAULT nextval('tag_id_seq'::regclass) NOT NULL,
    name character varying(255)
);


ALTER TABLE public.tag OWNER TO lcjury;

--
-- Name: actor_pkey; Type: CONSTRAINT; Schema: public; Owner: lcjury; Tablespace: 
--

ALTER TABLE ONLY actor
    ADD CONSTRAINT actor_pkey PRIMARY KEY (id);


--
-- Name: article_pkey; Type: CONSTRAINT; Schema: public; Owner: lcjury; Tablespace: 
--

ALTER TABLE ONLY article
    ADD CONSTRAINT article_pkey PRIMARY KEY (id);


--
-- Name: genre_pkey; Type: CONSTRAINT; Schema: public; Owner: lcjury; Tablespace: 
--

ALTER TABLE ONLY genre
    ADD CONSTRAINT genre_pkey PRIMARY KEY (id);


--
-- Name: movie_pkey; Type: CONSTRAINT; Schema: public; Owner: lcjury; Tablespace: 
--

ALTER TABLE ONLY movie
    ADD CONSTRAINT movie_pkey PRIMARY KEY (title);


--
-- Name: site_user_pkey; Type: CONSTRAINT; Schema: public; Owner: lcjury; Tablespace: 
--

ALTER TABLE ONLY site_user
    ADD CONSTRAINT site_user_pkey PRIMARY KEY (name);


--
-- Name: tag_pkey; Type: CONSTRAINT; Schema: public; Owner: lcjury; Tablespace: 
--

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);


--
-- Name: article_has_tag_idarticle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY article_has_tag
    ADD CONSTRAINT article_has_tag_idarticle_fkey FOREIGN KEY (idarticle) REFERENCES article(id);


--
-- Name: article_has_tag_idtag_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY article_has_tag
    ADD CONSTRAINT article_has_tag_idtag_fkey FOREIGN KEY (idtag) REFERENCES tag(id);


--
-- Name: movie_actor_idactor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY movie_actor
    ADD CONSTRAINT movie_actor_idactor_fkey FOREIGN KEY (idactor) REFERENCES actor(id);


--
-- Name: movie_actor_movietitle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY movie_actor
    ADD CONSTRAINT movie_actor_movietitle_fkey FOREIGN KEY (movietitle) REFERENCES movie(title);


--
-- Name: movie_genre_idgenre_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY movie_genre
    ADD CONSTRAINT movie_genre_idgenre_fkey FOREIGN KEY (idgenre) REFERENCES genre(id);


--
-- Name: movie_genre_movietitle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY movie_genre
    ADD CONSTRAINT movie_genre_movietitle_fkey FOREIGN KEY (movietitle) REFERENCES movie(title);


--
-- Name: rank_article_rankedarticle_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY rank_article
    ADD CONSTRAINT rank_article_rankedarticle_fkey FOREIGN KEY (rankedarticle) REFERENCES article(id);


--
-- Name: rank_article_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY rank_article
    ADD CONSTRAINT rank_article_username_fkey FOREIGN KEY (username) REFERENCES site_user(name);


--
-- Name: rank_movie_rankedmovie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY rank_movie
    ADD CONSTRAINT rank_movie_rankedmovie_fkey FOREIGN KEY (rankedmovie) REFERENCES movie(title);


--
-- Name: rank_movie_username_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY rank_movie
    ADD CONSTRAINT rank_movie_username_fkey FOREIGN KEY (username) REFERENCES site_user(name);


--
-- Name: relation_titleoriginalmovie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY relation
    ADD CONSTRAINT relation_titleoriginalmovie_fkey FOREIGN KEY (titleoriginalmovie) REFERENCES movie(title);


--
-- Name: relation_titlerecommendedmovie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: lcjury
--

ALTER TABLE ONLY relation
    ADD CONSTRAINT relation_titlerecommendedmovie_fkey FOREIGN KEY (titlerecommendedmovie) REFERENCES movie(title);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: department_id_seq; Type: ACL; Schema: public; Owner: lcjury
--

REVOKE ALL ON SEQUENCE department_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE department_id_seq FROM lcjury;
GRANT ALL ON SEQUENCE department_id_seq TO lcjury;


--
-- Name: employee_id_seq; Type: ACL; Schema: public; Owner: lcjury
--

REVOKE ALL ON SEQUENCE employee_id_seq FROM PUBLIC;
REVOKE ALL ON SEQUENCE employee_id_seq FROM lcjury;
GRANT ALL ON SEQUENCE employee_id_seq TO lcjury;


--
-- PostgreSQL database dump complete
--

