PGDMP     /    3                {            eps-rest-api    15.3    15.3                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false                       0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false                        0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            !           1262    16398    eps-rest-api    DATABASE     �   CREATE DATABASE "eps-rest-api" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Spain.1252';
    DROP DATABASE "eps-rest-api";
                postgres    false            �            1259    27371    appointments    TABLE     �  CREATE TABLE public.appointments (
    id bigint NOT NULL,
    created_at timestamp(6) without time zone,
    date timestamp(6) without time zone NOT NULL,
    deleted_at timestamp(6) without time zone,
    office integer NOT NULL,
    specialty character varying(255) NOT NULL,
    updated_at timestamp(6) without time zone,
    doctor_id bigint NOT NULL,
    patient_id bigint NOT NULL
);
     DROP TABLE public.appointments;
       public         heap    postgres    false            �            1259    27370    appointments_id_seq    SEQUENCE     |   CREATE SEQUENCE public.appointments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE public.appointments_id_seq;
       public          postgres    false    215            "           0    0    appointments_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE public.appointments_id_seq OWNED BY public.appointments.id;
          public          postgres    false    214            �            1259    27378    doctors    TABLE     �  CREATE TABLE public.doctors (
    id bigint NOT NULL,
    cedula bigint NOT NULL,
    deleted_at timestamp(6) without time zone,
    email character varying(255) NOT NULL,
    lastname character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    office integer NOT NULL,
    phone bigint NOT NULL,
    specialty character varying(255) NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    CONSTRAINT doctors_cedula_check CHECK (((cedula <= '99999999999'::bigint) AND (cedula >= 1))),
    CONSTRAINT doctors_office_check CHECK (((office >= 100) AND (office <= 999))),
    CONSTRAINT doctors_phone_check CHECK (((phone >= 1000000000) AND (phone <= '9999999999'::bigint)))
);
    DROP TABLE public.doctors;
       public         heap    postgres    false            �            1259    27377    doctors_id_seq    SEQUENCE     w   CREATE SEQUENCE public.doctors_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.doctors_id_seq;
       public          postgres    false    217            #           0    0    doctors_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.doctors_id_seq OWNED BY public.doctors.id;
          public          postgres    false    216            �            1259    27390    patients    TABLE     �  CREATE TABLE public.patients (
    id bigint NOT NULL,
    age integer NOT NULL,
    cedula bigint NOT NULL,
    deleted_at timestamp(6) without time zone,
    email character varying(255) NOT NULL,
    lastname character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    phone bigint NOT NULL,
    created_at timestamp(6) without time zone,
    updated_at timestamp(6) without time zone,
    CONSTRAINT patients_age_check CHECK (((age <= 130) AND (age >= 0))),
    CONSTRAINT patients_cedula_check CHECK (((cedula <= '99999999999'::bigint) AND (cedula >= 1))),
    CONSTRAINT patients_phone_check CHECK (((phone >= 1000000000) AND (phone <= '9999999999'::bigint)))
);
    DROP TABLE public.patients;
       public         heap    postgres    false            �            1259    27389    patients_id_seq    SEQUENCE     x   CREATE SEQUENCE public.patients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 &   DROP SEQUENCE public.patients_id_seq;
       public          postgres    false    219            $           0    0    patients_id_seq    SEQUENCE OWNED BY     C   ALTER SEQUENCE public.patients_id_seq OWNED BY public.patients.id;
          public          postgres    false    218            o           2604    27374    appointments id    DEFAULT     r   ALTER TABLE ONLY public.appointments ALTER COLUMN id SET DEFAULT nextval('public.appointments_id_seq'::regclass);
 >   ALTER TABLE public.appointments ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    214    215    215            p           2604    27381 
   doctors id    DEFAULT     h   ALTER TABLE ONLY public.doctors ALTER COLUMN id SET DEFAULT nextval('public.doctors_id_seq'::regclass);
 9   ALTER TABLE public.doctors ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    217    216    217            q           2604    27393    patients id    DEFAULT     j   ALTER TABLE ONLY public.patients ALTER COLUMN id SET DEFAULT nextval('public.patients_id_seq'::regclass);
 :   ALTER TABLE public.patients ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    218    219    219                      0    27371    appointments 
   TABLE DATA           ~   COPY public.appointments (id, created_at, date, deleted_at, office, specialty, updated_at, doctor_id, patient_id) FROM stdin;
    public          postgres    false    215   H(                 0    27378    doctors 
   TABLE DATA           �   COPY public.doctors (id, cedula, deleted_at, email, lastname, name, office, phone, specialty, created_at, updated_at) FROM stdin;
    public          postgres    false    217   �)                 0    27390    patients 
   TABLE DATA           u   COPY public.patients (id, age, cedula, deleted_at, email, lastname, name, phone, created_at, updated_at) FROM stdin;
    public          postgres    false    219   -       %           0    0    appointments_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.appointments_id_seq', 10, true);
          public          postgres    false    214            &           0    0    doctors_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.doctors_id_seq', 13, true);
          public          postgres    false    216            '           0    0    patients_id_seq    SEQUENCE SET     =   SELECT pg_catalog.setval('public.patients_id_seq', 9, true);
          public          postgres    false    218            y           2606    27376    appointments appointments_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.appointments DROP CONSTRAINT appointments_pkey;
       public            postgres    false    215            {           2606    27388    doctors doctors_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT doctors_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.doctors DROP CONSTRAINT doctors_pkey;
       public            postgres    false    217            �           2606    27398    patients patients_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.patients
    ADD CONSTRAINT patients_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.patients DROP CONSTRAINT patients_pkey;
       public            postgres    false    219            �           2606    27406 %   patients uk_a370hmxgv0l5c9panryr1ji7d 
   CONSTRAINT     a   ALTER TABLE ONLY public.patients
    ADD CONSTRAINT uk_a370hmxgv0l5c9panryr1ji7d UNIQUE (email);
 O   ALTER TABLE ONLY public.patients DROP CONSTRAINT uk_a370hmxgv0l5c9panryr1ji7d;
       public            postgres    false    219            }           2606    27402 $   doctors uk_caifv0va46t2mu85cg5afmayf 
   CONSTRAINT     `   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT uk_caifv0va46t2mu85cg5afmayf UNIQUE (email);
 N   ALTER TABLE ONLY public.doctors DROP CONSTRAINT uk_caifv0va46t2mu85cg5afmayf;
       public            postgres    false    217                       2606    27400 $   doctors uk_kfofqdxlfj5hcxlfh5xf2w2jw 
   CONSTRAINT     a   ALTER TABLE ONLY public.doctors
    ADD CONSTRAINT uk_kfofqdxlfj5hcxlfh5xf2w2jw UNIQUE (cedula);
 N   ALTER TABLE ONLY public.doctors DROP CONSTRAINT uk_kfofqdxlfj5hcxlfh5xf2w2jw;
       public            postgres    false    217            �           2606    27404 %   patients uk_q1usnw6equ3262v6py8kyw5a1 
   CONSTRAINT     b   ALTER TABLE ONLY public.patients
    ADD CONSTRAINT uk_q1usnw6equ3262v6py8kyw5a1 UNIQUE (cedula);
 O   ALTER TABLE ONLY public.patients DROP CONSTRAINT uk_q1usnw6equ3262v6py8kyw5a1;
       public            postgres    false    219            �           2606    27412 (   appointments fk8exap5wmg8kmb1g1rx3by21yt    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fk8exap5wmg8kmb1g1rx3by21yt FOREIGN KEY (patient_id) REFERENCES public.patients(id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fk8exap5wmg8kmb1g1rx3by21yt;
       public          postgres    false    3201    215    219            �           2606    27407 (   appointments fkmujeo4tymoo98cmf7uj3vsv76    FK CONSTRAINT     �   ALTER TABLE ONLY public.appointments
    ADD CONSTRAINT fkmujeo4tymoo98cmf7uj3vsv76 FOREIGN KEY (doctor_id) REFERENCES public.doctors(id);
 R   ALTER TABLE ONLY public.appointments DROP CONSTRAINT fkmujeo4tymoo98cmf7uj3vsv76;
       public          postgres    false    217    3195    215               o  x���MN1���)��l'v~������"��4�Z]q�^�T��+�`��ޗg;��.�/�+��1Yo,�%��N�d!�գB��l�:�u�ڴMWoo�(�if HL��֞�x�YVwu�ɻ���x�oE�����l ����pb��٩�ͮ�_a�D�D�^D�°s&��:'a���1�K1����D-���Ɍ���4��X萇g�������~�,)Z.e�$��"T�<�ID,��/6��G�@�!�jټ��y���:?���x���_h}�/�0�Kj4��Lh8*T6�_xn�M�^`�1N��s��༏���W ��T�s�t���ɍ3�
�U,�x��J���(�+����9�4         E  x��UMs�0=˿� �>-ɧJ�0:���e����b��t�G=q�?�Ji!M[�d�Ѯ��o�[1�9RI)ȧ��\��rKG�8���fW��a]�����y�!\("�MÍ�3����9�S.�Q����������R-gj$Tq��#��eɃ����/��̷?zD���w2類��~��0N���Tgn�3�w	�ay�Q�)�<�����aV!+A$B,��4�)���"�7�?w�q��A�whsw ����@�~p�?�kp�h�4�>�$�h9����f�g�ܻ��H� �9�(�!7����xxG�mW��#�
��o��7VN��^7O��o���y�������T�6�Ia�p���0��_}}��Y���mGY#խ	U-��f���Rփ[��k�=�/o�����b)�nc�16��LP
s�(���H���s%�fe�t���?,��# K�$D$�� #1 R�������
unM��H�2�;�1BuM!E��Q�!���k����Wf��u	1d������lp�@�`Iw���9���Pe��;�u��p�	�G�9$7s���ï/S�#��D!JMù6��������NȆ	�e;�%�H]��:�)�؇�o��q�u\��y����t�����bIr�O�R[�r��	U�� �YD|�5�}H����}�+!��Ұ��ޡ�R.�YK���X�p��Fۖq5��SFH*�xA8K����ұ�'i�|�)^\�2'اq_�N�F0��^�UL��K����p����ҏ�/���l��.������ӸllG)�ek�Z�Bէ���?�z�           x�}�Kn�0���)t�|�|iU�)���E���w,8Je��� ����r�����Z��83��:`���������ۗ�w�֍xjWl�]WWi���{�g�f^u붘m�5���(���R�;��-�,!��
id�v,�R{a8 ���LI#���[�*�!z%������t��KL��5�É�;�u��=���7F �Z8��-����e�0a
�U1�V������M_�{���t�E:V�6
kbt�$(=mX�}�` �ާ�j[|k]��T[����=a{� cS� �p���-�\�c#Ș������v 0����FP�(���J�1
>G���Pi�	�E�xn�'��AZ���N����a��4�#�e@�A_�8*��L�g2�Gz�����צ~��b֦-6B>r26z|�-�Qy�oH<�:K������s���yC+����謹!A�x��@⑁�o�~�l��*��`����A�)�;�c^�Wj'T�^t3��������.N     