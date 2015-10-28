package com.suj1th.rabpubsub;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IMapperService {

	/**
	 * Serializes a POJO into a String Object.
	 * 
	 * @param model
	 * @return JSONObject
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public abstract String serialize(Object model) throws JsonParseException,
			JsonMappingException, IOException;

	/**
	 * Maps a JSON-formatted String Object into a POJO.
	 *
	 * @param document
	 * @param claas
	 * @return model
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public abstract <T> T deserialize(String document, Class<T> claas)
			throws JsonParseException, JsonMappingException, IOException;

	/**
	 * @param document
	 * @param claas
	 * @return model
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public abstract <T> T deserialize(String document, TypeReference<T> claas)
			throws JsonParseException, JsonMappingException, IOException;

	/**
	 * @param document
	 * @param claas
	 * @return model
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public abstract <T> T deserialize(byte[] document, Class<T> claas) throws JsonParseException,
			JsonMappingException, IOException;
	
	

}