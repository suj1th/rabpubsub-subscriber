package com.suj1th.rabpubsub;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;





/**
 * This class provides an abstraction for mapping JSONObjects to POJOs and vice-versa. The current
 * implementation uses {@link ObjectMapper} to perform the transformation.
 *
 * @version 0.1
 * @author suj1th
 */

@Singleton
public class MapperService implements IMapperService {

	private ObjectMapper jacksonMapper;

	@Inject
	public MapperService(ObjectMapper jacksonMapper){
		this.jacksonMapper=jacksonMapper;
	}


	/* (non-Javadoc)
	 * @see com.suj1th.rabpubsub.IMapperService#serialize(java.lang.Object)
	 */
	@Override
	public String serialize(Object model) throws JsonParseException, JsonMappingException, IOException {
		return this.jacksonMapper.writeValueAsString(model);
	}


	/* (non-Javadoc)
	 * @see com.suj1th.rabpubsub.IMapperService#deserialize(java.lang.String, java.lang.Class)
	 */
	@Override
	public <T> T deserialize(String document, Class<T> claas) throws JsonParseException, JsonMappingException,
	IOException {
		return this.jacksonMapper.readValue(document, claas);
	}


	/* (non-Javadoc)
	 * @see com.suj1th.rabpubsub.IMapperService#deserialize(java.lang.String, com.fasterxml.jackson.core.type.TypeReference)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T deserialize(String document, TypeReference<T> claas) throws JsonParseException, JsonMappingException,
	IOException {
		return (T) this.jacksonMapper.readValue(document, claas);
	}


	/* (non-Javadoc)
	 * @see com.suj1th.rabpubsub.IMapperService#deserialize(byte[], java.lang.Class)
	 */
	@Override
	public <T> T deserialize(byte[] document, Class<T> claas) throws JsonParseException, JsonMappingException,
	IOException {
		return this.jacksonMapper.readValue(document, claas);
	}

	
}
