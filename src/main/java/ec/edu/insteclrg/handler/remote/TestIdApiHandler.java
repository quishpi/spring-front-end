package ec.edu.insteclrg.handler.remote;

import org.springframework.http.HttpMethod;

import ec.edu.insteclrg.dto.ApiResponseDTO;
import ec.edu.insteclrg.dto.TestIdDTO;
import ec.edu.insteclrg.handler.error.ErrorJsonHandler;

public class TestIdApiHandler extends ApiHandler<TestIdDTO> {

	private static final long serialVersionUID = 1L;

	public ApiResponseDTO<TestIdDTO> buscarTestId(Long id) {
		TestIdDTO dto = new TestIdDTO();
		String remotePath = "http://localhost:8080/api/v1.0/testid/" + id + "/buscar";
		String responseJsonStr = super.consumeREST(dto, HttpMethod.GET, remotePath);
		ErrorJsonHandler<TestIdDTO> jsonHandler = new ErrorJsonHandler<TestIdDTO>(dto);
		ApiResponseDTO<TestIdDTO> response = jsonHandler.getServerResponse(responseJsonStr);
		return response;

	}

}
