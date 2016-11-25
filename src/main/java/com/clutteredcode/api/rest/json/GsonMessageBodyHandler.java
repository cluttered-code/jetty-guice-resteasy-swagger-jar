package com.clutteredcode.api.rest.json;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by david on 11/24/16.
 */
@Provider
@Singleton
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class GsonMessageBodyHandler implements MessageBodyWriter<Object>, MessageBodyReader<Object> {

    private static final String UTF_8 = "UTF-8";
    private static final Gson GSON = new Gson();

    @Inject
    private GsonMessageBodyHandler() {}

    @Override
    public boolean isReadable(final Class<?> type, final Type genericType,
                              final Annotation[] annotations, final MediaType mediaType) {
        return true;
    }

    @Override
    public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations,
                           final MediaType mediaType, final MultivaluedMap<String, String> httpHeaders,
                           final InputStream entityStream) throws IOException {
        try (final InputStreamReader streamReader = new InputStreamReader(entityStream, UTF_8)) {
            final Type jsonType = type.equals(genericType) ? type : genericType;
            return GSON.fromJson(streamReader, jsonType);
        }
    }

    @Override
    public boolean isWriteable(final Class<?> type, final Type genericType,
                               final Annotation[] annotations, final MediaType mediaType) {
        return true;
    }

    @Override
    public long getSize(final Object object, final Class<?> type, final Type genericType,
                        final Annotation[] annotations, final MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(final Object object, Class<?> type, final Type genericType, final Annotation[] annotations,
                        final MediaType mediaType, final MultivaluedMap<String, Object> httpHeaders,
                        final OutputStream entityStream) throws IOException {
        try (final OutputStreamWriter writer = new OutputStreamWriter(entityStream, UTF_8)) {
            final Type jsonType = type.equals(genericType) ? type : genericType;
            GSON.toJson(object, jsonType, writer);
        }
    }
}