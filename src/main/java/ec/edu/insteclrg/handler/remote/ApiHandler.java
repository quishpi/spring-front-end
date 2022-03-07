package ec.edu.insteclrg.handler.remote;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ec.edu.insteclrg.dto.ApiResponseDTO;
import lombok.Getter;

@Getter
@Component
public class ApiHandler<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	static RestTemplate restTemplate;

	public String consumeREST(T dto, HttpMethod httpNethod, String servidor) {
		HttpEntity<String> request = createRequest(dto);
		String responseJsonStr = null;
		try {
			ResponseEntity<String> responseEntity = null;
			responseEntity = restTemplate.exchange(servidor, httpNethod, request, String.class);
			responseJsonStr = responseEntity.getBody();
		} catch (Exception e) {
			responseJsonStr = e.getMessage();
			Integer pos = responseJsonStr.indexOf("\"");
			if (pos == -1) {
				ApiResponseDTO<T> remoreServ = new ApiResponseDTO<T>(false, null,
						"I/O error. No se pudo conectar con los servidores remotos. " + e.getMessage());
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					responseJsonStr = objectMapper.writeValueAsString(remoreServ);
				} catch (JsonProcessingException e1) {
					responseJsonStr = null;
				}
			} else
				responseJsonStr = responseJsonStr.substring(pos + 1, responseJsonStr.length() - 1);
		}
		return responseJsonStr;
	}

	private HttpEntity<String> createRequest(T dto) {
		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			return null;
		}

		HttpEntity<String> request = new HttpEntity<String>(jsonInString, headers);
		return request;
	}
}
