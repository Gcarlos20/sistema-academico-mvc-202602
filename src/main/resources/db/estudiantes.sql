-- Crea las rutinas para la tabla existente public.estudiante.
-- NIT y celular son opcionales en la tabla y se almacenan como BIGINT o NULL.

-- CREATE (C): inserta los datos y devuelve el ID generado.
CREATE OR REPLACE PROCEDURE public.sp_insertar_estudiante(
    IN p_name VARCHAR(100),
    IN p_lastname VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_nit BIGINT,
    IN p_numcelular BIGINT,
    OUT p_id BIGINT
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO public.estudiante (name, lastname, email, nit, numcelular)
    VALUES (p_name, p_lastname, p_email, p_nit, p_numcelular)
    RETURNING id INTO p_id;
END;
$$;

-- READ (R): devuelve las filas ordenadas para que JDBC pueda consultarlas.
-- Se elimina la versión anterior porque ahora también retorna NIT y celular.
DROP FUNCTION IF EXISTS public.fn_listar_estudiantes();

CREATE OR REPLACE FUNCTION public.fn_listar_estudiantes()
RETURNS TABLE (
    id BIGINT,
    name VARCHAR,
    lastname VARCHAR,
    email VARCHAR,
    nit BIGINT,
    numcelular BIGINT
)
LANGUAGE sql
STABLE
AS $$
    SELECT e.id::BIGINT, e.name::VARCHAR, e.lastname::VARCHAR, e.email::VARCHAR,
           e.nit::BIGINT, e.numcelular::BIGINT
    FROM public.estudiante AS e
    ORDER BY e.id;
$$;

-- UPDATE (U): modifica los datos y devuelve si encontró el ID solicitado.
CREATE OR REPLACE PROCEDURE public.sp_actualizar_estudiante(
    IN p_id BIGINT,
    IN p_name VARCHAR(100),
    IN p_lastname VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_nit BIGINT,
    IN p_numcelular BIGINT,
    OUT p_actualizado BOOLEAN
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE public.estudiante
    SET name = p_name, lastname = p_lastname, email = p_email,
        nit = p_nit, numcelular = p_numcelular
    WHERE id = p_id;
    p_actualizado := FOUND;
END;
$$;

-- DELETE (D): borra por ID y devuelve si se eliminó una fila.
CREATE OR REPLACE PROCEDURE public.sp_eliminar_estudiante(
    IN p_id BIGINT,
    OUT p_eliminado BOOLEAN
)
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM public.estudiante
    WHERE id = p_id;
    p_eliminado := FOUND;
END;
$$;
